package com.nosliw.data.core.structure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.component.attachment.HAPContainerAttachment;
import com.nosliw.data.core.component.attachment.HAPUtilityAttachment;
import com.nosliw.data.core.expression.HAPUtilityExpressionProcessConfigure;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.runtime.js.rhino.task.HAPRuntimeTaskExecuteScript;
import com.nosliw.data.core.script.expression.HAPExecutableScriptEntity;
import com.nosliw.data.core.script.expression.HAPExecutableScriptGroup;
import com.nosliw.data.core.script.expression.HAPProcessorScript;
import com.nosliw.data.core.value.HAPResourceDefinitionValue;

public class HAPProcessorElementConstant {

	static public HAPStructure process(
			HAPStructure structure,
			HAPContainerAttachment attachmentContainer,
			HAPConfigureProcessorStructure configure,
			HAPRuntimeEnvironment runtimeEnv){

		//process constant ref in context
		HAPStructure out =  solidateConstantRefs(structure, attachmentContainer, runtimeEnv);

		//figure out constant value (some constant may use another constant)
		out =  solidateConstantElements(out, configure, runtimeEnv);
		
		//figure out root that ture out to be constant value, then convert to constant root
		out = discoverConstantContextRoot(out);
		
		return out;
	}

	//find all the context root which is actually constant, convert it to constant element 
	static private HAPStructure discoverConstantContextRoot(HAPStructure structure) {
		HAPStructure out = structure.cloneStructure();
		for(HAPRoot root : out.getAllRoots()) {
			Object value = discoverConstantValue(root.getDefinition());
			if(value!=null) {
				HAPElementLeafConstant constantEle = new HAPElementLeafConstant(value);
				root.setDefinition(constantEle);
			}
		}
		return out;
	}
	
	static private Object discoverConstantValue(HAPElement element) {
		String type = element.getType();
		if(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT.equals(type)) {
			HAPElementLeafConstant constantEle = (HAPElementLeafConstant)element;
			return constantEle.getValue();
		}
		else if (HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE.equals(type)) {
			HAPElementNode nodeEle = (HAPElementNode)element;
			JSONObject out = new JSONObject();
			for(String nodeName : nodeEle.getChildren().keySet()) {
				Object childOut = discoverConstantValue(nodeEle.getChild(nodeName));
				if(childOut==null)   return null;
				else   out.put(nodeName, childOut);
			}
			return out;
		}
		else return null;
	}

	//process constant reference element, replace with constant element
	static private HAPStructure solidateConstantRefs(
			HAPStructure structure,
			HAPContainerAttachment attachmentContainer,
			HAPRuntimeEnvironment runtimeEnv){
		if(attachmentContainer==null)   return structure;
		HAPStructure out = structure.cloneStructure();
		for(HAPRoot root : out.getAllRoots()) {
			HAPUtilityStructure.traverseElement(root, new HAPProcessorContextDefinitionElement() {
				@Override
				public Pair<Boolean, HAPElement> process(HAPInfoElement eleInfo, Object value) {
					if(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANTREF.equals(eleInfo.getElement().getType())) {
						HAPElementLeafConstantReference constantRefEle = (HAPElementLeafConstantReference)eleInfo.getElement();
						HAPResourceDefinitionValue valueResourceDef = (HAPResourceDefinitionValue)HAPUtilityAttachment.getResourceDefinition(attachmentContainer, HAPConstantShared.RUNTIME_RESOURCE_TYPE_VALUE, constantRefEle.getConstantName(), runtimeEnv.getResourceDefinitionManager());
						HAPElementLeafConstant newEle = new HAPElementLeafConstant(valueResourceDef.getValue().getValue());
						return Pair.of(false, newEle);
					}
					return null;
				}

				@Override
				public void postProcess(HAPInfoElement eleInfo, Object value) {}
			}, null);
		}
		return out;
	}

	static private HAPStructure solidateConstantElements(
			HAPStructure structure,
			HAPConfigureProcessorStructure configure,
			HAPRuntimeEnvironment runtimeEnv){
		HAPStructure out = structure.cloneStructure();
		for(HAPRoot root : out.getAllRoots()) {
			HAPUtilityStructure.traverseElement(root, new HAPProcessorContextDefinitionElement() {
				@Override
				public Pair<Boolean, HAPElement> process(HAPInfoElement eleInfo, Object value) {
					if(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT.equals(eleInfo.getElement().getType())) {
						HAPElementLeafConstant constantEle = (HAPElementLeafConstant)eleInfo.getElement();
						solidateConstantDefEle(constantEle, structure, configure, runtimeEnv);
						return null;
					}
					return null;
				}

				@Override
				public void postProcess(HAPInfoElement eleInfo, Object value) {}
			}, null);
		}
		return out;
	}

	static private void solidateConstantDefEle(
			HAPElementLeafConstant contextElement, 
			HAPStructure structure,
			HAPConfigureProcessorStructure configure,
			HAPRuntimeEnvironment runtimeEnv) {

		if(!contextElement.isProcessed()) {
			Object data = processConstantDefJsonNode(contextElement.getValue(), structure, configure, runtimeEnv);
			if(data==null)   data = contextElement.getValue();
			contextElement.setValue(data);
			contextElement.processed();
		}
	}

	/**
	 * Process any json node
	 * @param nodeValue  
	 * @param refConstants   reference constants
	 * @param constantDefs
	 * @param idGenerator
	 * @param expressionMan
	 * @param runtime
	 * @return
	 */
	static private Object processConstantDefJsonNode(
			Object nodeValue,
			HAPStructure structure,
			HAPConfigureProcessorStructure configure,
			HAPRuntimeEnvironment runtimeEnv) {
		Object out = null;
		try{
			if(nodeValue instanceof JSONObject){
				JSONObject outJsonObj = new JSONObject();
				JSONObject jsonObj = (JSONObject)nodeValue;
				Iterator<String> keys = jsonObj.keys();
				while(keys.hasNext()){
					String key = keys.next();
					Object childValue = jsonObj.get(key);
					Object data = processConstantDefJsonNode(childValue, structure, configure, runtimeEnv);
					if(data!=null)  outJsonObj.put(key, data);   
				}
				out = outJsonObj;
			}
			else if(nodeValue instanceof JSONArray){
				JSONArray outJsonArray = new JSONArray();
				JSONArray jsonArray = (JSONArray)nodeValue;
				for(int i=0; i<jsonArray.length(); i++){
					Object childNode = jsonArray.get(i);
					Object data = processConstantDefJsonNode(childNode, structure, configure, runtimeEnv);
					if(data!=null)   outJsonArray.put(i, data);
				}
				out = outJsonArray;
			}
			else{
				out = processConstantDefLeaf(nodeValue, structure, configure, runtimeEnv);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}

	
	/**
	 * Calculate leaf data
	 * as script expression can only defined in leaf node 
	 * @param leafData
	 * @param constantDefs
	 * @param idGenerator
	 * @param expressionMan
	 * @param runtime
	 * @return
	 * 		if leafData does not contain script expression, then return leafData (respect the value type)
	 * 		if leafData contains both script expression and plain text, then return string value
	 * 		if leafData contains a script expression only, then return the result value of sript expression
	 */
	static private Object processConstantDefLeaf(
			Object leafData,
			HAPStructure structure,
			HAPConfigureProcessorStructure configure,
			HAPRuntimeEnvironment runtimeEnv) {

		//simply process script
		HAPExecutableScriptGroup groupExe = HAPProcessorScript.processSimpleScript(leafData.toString(), null, null, null, runtimeEnv.getExpressionManager(), HAPUtilityExpressionProcessConfigure.setDontDiscovery(null), runtimeEnv, new HAPProcessTracker());
		HAPExecutableScriptEntity scriptExe = groupExe.getScript(null);
		
		String scriptType = scriptExe.getScriptType();
		//if pure data
		if(HAPConstantShared.SCRIPT_TYPE_TEXT.equals(scriptType))  return leafData;
		
		///if contain script
		//discover all constant
		Map<String, Object> constantsValue = new LinkedHashMap<String, Object>();
		Set<HAPDefinitionConstant> constantsDef = groupExe.getConstantsDefinition();
		for(HAPDefinitionConstant constantDef : constantsDef){
			String constantId = constantDef.getId();
			Set<String> types = new HashSet<String>();
			types.add(HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT);
			HAPInfoReferenceResolve resolveInfo = HAPUtilityStructure.resolveElementReference(constantId, structure, configure.relativeResolveMode, types);
			solidateConstantDefEle((HAPElementLeafConstant)resolveInfo.realSolved.resolvedElement, structure, configure, runtimeEnv);
			constantsValue.put(constantId, ((HAPElementLeafConstant)HAPUtilityStructure.resolveElement(resolveInfo.realSolved)).getValue());
		}

		//process script again with constant and discovery
		groupExe = HAPProcessorScript.processSimpleScript(leafData.toString(), null, null, constantsValue, runtimeEnv.getExpressionManager(), HAPUtilityExpressionProcessConfigure.setDoDiscovery(null), runtimeEnv, new HAPProcessTracker());		

		//execute script expression
		HAPRuntimeTaskExecuteScript task = new HAPRuntimeTaskExecuteScript(groupExe, null, null, null);
		HAPServiceData out = runtimeEnv.getRuntime().executeTaskSync(task);
		return out.getData();
	}
	
}
