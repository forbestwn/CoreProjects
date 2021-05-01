package com.nosliw.data.core.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.data.core.data.variable.HAPDataRule;
import com.nosliw.data.core.data.variable.HAPVariableDataInfo;
import com.nosliw.data.core.matcher.HAPMatcherUtility;
import com.nosliw.data.core.matcher.HAPMatchers;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.structure.story.HAPReferenceContextNode;
import com.nosliw.data.core.structure.value.HAPStructureValue;
import com.nosliw.data.core.structure.value.HAPStructureValueDefinitionGroup;

public class HAPProcessorElementRelative {

	
	private static HAPElement processRelativeInContextDefinitionElement(HAPInfoElement contextEleInfo, String parentName, HAPStructureValueDefinitionGroup parentContext, Set<String>  dependency, List<HAPServiceData> errors, boolean isParentFlat, HAPConfigureProcessorStructure configure, HAPRuntimeEnvironment runtimeEnv) {
		HAPElement defContextElement = contextEleInfo.getElement();
		HAPElement out = defContextElement;
		switch(defContextElement.getType()) {
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE:
			HAPElementLeafRelative relativeContextElement = (HAPElementLeafRelative)defContextElement;
			if(parentName.equals(relativeContextElement.getParent())) {
				if(dependency!=null)  dependency.add(parentName);
				if(!relativeContextElement.isProcessed()){
					List<String> categaryes = new ArrayList<String>();
					if(relativeContextElement.getParentCategary()!=null) categaryes.add(relativeContextElement.getParentCategary());
					else if(configure.parentCategary==null)   categaryes.addAll(Arrays.asList(HAPStructureValueDefinitionGroup.getVisibleCategaries()));
					else   categaryes.addAll(Arrays.asList(configure.parentCategary));
					out = processRelativeContextDefinitionElement(contextEleInfo, parentContext, errors, isParentFlat, categaryes.toArray(new String[0]), configure, runtimeEnv);
				}
			}
			break;
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
			Map<String, HAPElement> processedChildren = new LinkedHashMap<String, HAPElement>();
			HAPElementNode nodeContextElement = (HAPElementNode)defContextElement;
			for(String childName : nodeContextElement.getChildren().keySet()) { 	
				processedChildren.put(childName, processRelativeInContextDefinitionElement(new HAPInfoElement(nodeContextElement.getChild(childName), contextEleInfo.getElementPath().appendSegment(childName)), parentName, parentContext, dependency, errors, isParentFlat, configure, runtimeEnv));
			}
			break;
		}
		return out;
	}
	
	private static HAPElement processRelativeElement(HAPElementLeafRelative RelativeElement, HAPStructureValue valueStructure, List<HAPServiceData> errors) {
		
	}
	
	private static HAPElement processRelativeContextDefinitionElement(HAPInfoElement contextEleInfo, HAPStructureValueDefinitionGroup parentContext, List<HAPServiceData> errors, boolean isParentFlat, String[] categaryes, HAPConfigureProcessorStructure configure, HAPRuntimeEnvironment runtimeEnv){
		HAPElementLeafRelative defContextElementRelative = (HAPElementLeafRelative)contextEleInfo.getElement();
		HAPElement out = defContextElementRelative;
		
		HAPReferenceElement path = defContextElementRelative.getPathFormat(); 
		HAPInfoReferenceResolve resolveInfo = HAPUtilityContext.resolveElementReference(path, parentContext, categaryes, configure.relativeResolveMode);
		
		if(resolveInfo==null || resolveInfo.referredRoot==null) {
			errors.add(HAPServiceData.createFailureData(contextEleInfo, HAPConstant.ERROR_PROCESSCONTEXT_NOREFFEREDNODE));
			if(!configure.tolerantNoParentForRelative)  throw new RuntimeException();
		}
		else {
			switch(resolveInfo.referredRoot.getDefinition().getType()) {
			case HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT:
			{
				//if refer to a constant element
				out = new HAPElementLeafConstant();
				Object constantValue = ((HAPElementLeafConstant)resolveInfo.referredRoot.getDefinition()).getValue();
				((HAPElementLeafConstant)out).setValue(constantValue);
				break;
			}
			default:
			{
				if(isParentFlat)  path.getRootReference().setCategary(null);
				else path.getRootReference().setCategary(resolveInfo.path.getRootReference().getCategary());
				defContextElementRelative.setPath(path);
				
				HAPElement solvedContextEle = resolveInfo.resolvedNode; 
				if(solvedContextEle!=null){
					//refer to solid
					if(configure.relativeTrackingToSolid) {
						String refRootId = null;
						String refPath = null;
						HAPElement parentContextEle = resolveInfo.referedRealElement; 
						if(parentContextEle.getType().equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE)) {
							HAPReferenceContextNode parentSolidNodeRef = ((HAPElementLeafRelative)parentContextEle).getSolidNodeReference();
							refRootId = parentSolidNodeRef.getRootNodeId();
							refPath = HAPNamingConversionUtility.cascadePath(parentSolidNodeRef.getPath(), resolveInfo.remainPath);
						}
						else {
							refRootId = resolveInfo.referredRoot.getId();
							refPath = HAPNamingConversionUtility.cascadePath(resolveInfo.path.getSubPath(), resolveInfo.remainPath);
						}
						defContextElementRelative.setSolidNodeReference(new HAPReferenceContextNode(refRootId, refPath));
					}
					
					HAPElement relativeContextEle = defContextElementRelative.getDefinition();
					if(relativeContextEle==null) {
						defContextElementRelative.setDefinition(solvedContextEle.cloneStructureElement());
					}
					else {
						//figure out matchers
						Map<String, HAPMatchers> matchers = new LinkedHashMap<String, HAPMatchers>();
						HAPUtilityContext.mergeElement(solvedContextEle, relativeContextEle, false, matchers, null, runtimeEnv);
						//remove all the void matchers
						Map<String, HAPMatchers> noVoidMatchers = new LinkedHashMap<String, HAPMatchers>();
						for(String p : matchers.keySet()){
							HAPMatchers match = matchers.get(p);
							if(!match.isVoid()){
								noVoidMatchers.put(p, match);
							}
						}
						defContextElementRelative.setMatchers(noVoidMatchers);
						
						//inherit rule from parent
						if(configure.relativeInheritRule) {
							HAPElement solidParent = solvedContextEle.getSolidStructureElement();
							if(solidParent.getType().equals(HAPConstantShared.CONTEXT_ELEMENTTYPE_DATA)) {
								HAPVariableDataInfo solidParentDataInfo = ((HAPElementLeafData)solidParent).getDataInfo();
								HAPMatchers ruleMatchers = HAPMatcherUtility.reversMatchers(HAPMatcherUtility.cascadeMatchers(solidParentDataInfo.getRuleMatchers()==null?null:solidParentDataInfo.getRuleMatchers().getReverseMatchers(), noVoidMatchers.get("")));
								for(HAPDataRule rule : solidParentDataInfo.getRules()) {
									HAPVariableDataInfo relativeDataInfo = ((HAPElementLeafData)relativeContextEle).getDataInfo();
									relativeDataInfo.addRule(rule);
									relativeDataInfo.setRuleMatchers(ruleMatchers, solidParentDataInfo.getRuleCriteria());
								}
							}
						}
					}
				}
				else{
					//not find parent node, throw exception
					errors.add(HAPServiceData.createFailureData(contextEleInfo, HAPConstant.ERROR_PROCESSCONTEXT_NOREFFEREDNODE));
					if(!configure.tolerantNoParentForRelative)  throw new RuntimeException();
				}
			}
			}			
		}

		return out;
	}

	
}
