package com.nosliw.data.core.script.expression.literate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.updatename.HAPUpdateNamePrefix;
import com.nosliw.data.core.expression.HAPExecutableExpressionGroup;
import com.nosliw.data.core.resource.HAPResourceData;
import com.nosliw.data.core.resource.HAPResourceDependency;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.HAPExecutableImp;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPResourceDataFactory;
import com.nosliw.data.core.runtime.js.HAPUtilityRuntimeJSScript;
import com.nosliw.data.core.script.expression.expression.HAPScriptExpression111;

/**
 *  EmbededScriptExpression: a string which contains script expression
 */
@HAPEntityWithAttribute
public class HAPEmbededScriptExpression111 extends HAPExecutableImp{

	@HAPAttribute
	public static final String SCRIPTEXPRESSIONS = "scriptExpressions";

	@HAPAttribute
	public static final String SCRIPTFUNCTION = "scriptFunction";

	@HAPAttribute
	public static final String SCRIPTEXPRESSIONSCRIPTFUNCTION = "scriptExpressionScriptFunction";

	private HAPDefinitionEmbededScriptExpression m_definition;
	
	private List<Object> m_elements;
	
	//all script expressions by id
	private Map<String, HAPScriptExpression111> m_scriptExpressions;
	
	//from index in elements to script expression id
	private Map<Integer, String> m_indexToId;
	
	public HAPEmbededScriptExpression111(HAPDefinitionEmbededScriptExpression definition) {
		this.m_elements = new ArrayList<Object>();
		this.m_indexToId = new LinkedHashMap<Integer, String>();
		this.m_scriptExpressions = new LinkedHashMap<String, HAPScriptExpression111>();
		this.m_definition = definition;
	}
	
	public HAPDefinitionEmbededScriptExpression getDefinition() {   return this.m_definition;    }
	
	public void addElement(Object element) {
		int index = this.m_elements.size();
		this.m_elements.add(element);
		if(element instanceof HAPScriptExpression111) {
			HAPScriptExpression111 scriptExp = (HAPScriptExpression111)element;
			String scriptExpressionId = index + "";
			//update expression id in script expression 
			scriptExp.updateExpressionId(new HAPUpdateNamePrefix(scriptExpressionId+"_"));
			this.m_scriptExpressions.put(scriptExpressionId, scriptExp);
			this.m_indexToId.put(index, scriptExpressionId);
		}
	}
	
	public List<Object> getElements(){  return this.m_elements;  }
	
	public boolean isConstant(){
		boolean out = true;
		for(Object ele : this.m_elements){
			if(ele instanceof HAPScriptExpression111){
				if(!((HAPScriptExpression111)ele).isConstant()){
					out = false;
				}
			}
		}
//		for(Object ele : this.m_definition.getElements()){
//			if(ele instanceof HAPScriptExpression){
//				if(!((HAPScriptExpression)ele).isConstant()){
//					out = false;
//				}
//			}
//		}
		return out;
	}
	
	public boolean isString() {  return this.m_definition.isString();  }
	
//	public void updateWithConstantsValue(Map<String, Object> constantsValue) {
//		for(Object ele : this.m_elements){
//			if(ele instanceof HAPScriptExpression){
//				((HAPScriptExpression)ele).updateWithConstantsValue(constantsValue);
//			}
//		}
//	}

	
	public String getValue(){
		if(this.isConstant()){
			StringBuffer out = new StringBuffer();
			for(Object ele : this.m_elements){
				if(ele instanceof HAPScriptExpression111){
					HAPScriptExpression111 scriptExpression = (HAPScriptExpression111)ele; 
					if(!scriptExpression.isConstant()){
						out.append(scriptExpression.getValue().toString());
					}
					else if(ele instanceof String){
						out.append(ele.toString());
					}
				}
			}
			return out.toString();
		}
		else{
			return null;
		}
	}
	
	public List<HAPScriptExpression111> getScriptExpressionsList(){		return new ArrayList(this.m_scriptExpressions.values());	}
	
	public String getScriptExpressionIdByIndex(int index) {		return this.m_indexToId.get(index);	}
	
	public Map<String, HAPScriptExpression111> getScriptExpressions(){  return this.m_scriptExpressions;  }
	public Map<String, HAPExecutableExpressionGroup> getExpressions(){
		Map<String, HAPExecutableExpressionGroup> out = new LinkedHashMap<String, HAPExecutableExpressionGroup>();
		for(HAPScriptExpression111 scriptExpression : this.m_scriptExpressions.values()){
			out.putAll(scriptExpression.getExpressions());
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(SCRIPTEXPRESSIONS, HAPJsonUtility.buildJson(m_scriptExpressions, HAPSerializationFormat.JSON));
	}

	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>(); 
		Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
		this.buildJsonMap(jsonMap, typeJsonMap);

		jsonMap.put(SCRIPTFUNCTION, new HAPJsonTypeScript(HAPUtilityRuntimeJSScript.buildMainScriptEmbededScriptExpression(this)).toStringValue(HAPSerializationFormat.JSON_FULL));
		typeJsonMap.put(SCRIPTFUNCTION, HAPJsonTypeScript.class);
		
		Map<String, String> scriptJsonMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> scriptTypeJsonMap = new LinkedHashMap<String, Class<?>>();
		for(String name : this.getScriptExpressions().keySet()) {
			HAPScriptExpression111 scriptExpression = this.getScriptExpressions().get(name);
			scriptJsonMap.put(name, scriptExpression.toResourceData(runtimeInfo).toString());
			scriptTypeJsonMap.put(name, HAPJsonTypeScript.class);
		}
		jsonMap.put(SCRIPTEXPRESSIONSCRIPTFUNCTION, HAPJsonUtility.buildMapJson(scriptJsonMap, scriptTypeJsonMap));

		return HAPResourceDataFactory.createJSValueResourceData(HAPJsonUtility.buildMapJson(jsonMap, typeJsonMap));
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo, HAPResourceManagerRoot resourceManager) {
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		for(HAPScriptExpression111 scriptExpression : this.getScriptExpressionsList()){
			out.addAll(scriptExpression.getResourceDependency(runtimeInfo, resourceManager));
		}
		return out;
	}
}