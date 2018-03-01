package com.nosliw.data.core.task.expression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.expression.HAPExpressionUtility;
import com.nosliw.data.core.expression.HAPReferenceInfo;
import com.nosliw.data.core.task.HAPProcessorTask;
import com.nosliw.data.core.task.HAPTaskManager;
import com.nosliw.data.core.task.HAPUpdateVariableDomain;
import com.nosliw.data.core.task.HAPUpdateVariableMap;
import com.nosliw.data.core.task.HAPDefinitionTask;
import com.nosliw.data.core.task.HAPExecutable;
import com.nosliw.data.core.task.HAPProcessTaskContext;

public class HAPProcessorTaskExpression implements HAPProcessorTask{

	@Override
	public HAPExecutable process(HAPDefinitionTask taskDefinition, String domain, Map<String, String> variableMap, 
			Map<String, HAPDefinitionTask> contextTaskDefinitions, Map<String, HAPData> contextConstants,
			HAPProcessTaskContext context) {
		
		HAPDefinitionTaskExpression taskDefExp = (HAPDefinitionTaskExpression)taskDefinition; 
		
		HAPExecuteTaskExpression out = new HAPExecuteTaskExpression(taskDefExp, domain);
		
		//process steps
		this.processSteps(out, taskDefExp, contextConstants);
		
		//process references info
		this.processReferences(out, taskDefExp, contextTaskDefinitions, contextConstants, context);
		
		//get updated variables map according to domain
		Map<String, String> domainedVariableMap = new LinkedHashMap<String, String>();
		for(String name : variableMap.keySet()) {
			domainedVariableMap.put(HAPExpressionUtility.buildFullVariableName(out.getDomain(), name), variableMap.get(name));
		}
		//update variable in task
		out.updateVariable(new HAPUpdateVariableMap(domainedVariableMap));
		
		return out;
	}
	
	private void processSteps(HAPExecuteTaskExpression out, HAPDefinitionTaskExpression taskDefExp, Map<String, HAPData> contextConstants) {
		//update constants according to constants in context and in task
		Map<String, HAPData> updatedContextConstants = this.getUpdatedConstants(taskDefExp, contextConstants);
		HAPDefinitionStep[] stepDefs = taskDefExp.getSteps();
		for(HAPDefinitionStep stepDef : stepDefs) {
			HAPExecuteStep step = HAPManagerExpression.processStep(stepDef, updatedContextConstants);
			step.updateVariable(new HAPUpdateVariableDomain(out.getDomain()));
			out.addStep(step);
		}
	}
	
	private Map<String, HAPData> getUpdatedConstants(HAPDefinitionTaskExpression taskDefExp, Map<String, HAPData> contextConstants){
		Map<String, HAPData> out = new LinkedHashMap<String, HAPData>();
		out.putAll(contextConstants);
		out.putAll(taskDefExp.getConstants());
		return out;
	}
	
	private void processReferences(HAPExecuteTaskExpression out, HAPDefinitionTaskExpression taskDefExp,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, Map<String, HAPData> contextConstants,
			HAPProcessTaskContext context) {

		//gate updated referenceInfos according to domain info
		Map<String, HAPReferenceInfo> updatedReferenceInfos = new LinkedHashMap<String, HAPReferenceInfo>();
		Map<String, HAPReferenceInfo> originalRefInfos = taskDefExp.getReferences();
		for(String refName : originalRefInfos.keySet()) {
			HAPReferenceInfo originalRefInfo = originalRefInfos.get(refName);
			HAPReferenceInfo refInfo = new HAPReferenceInfo(originalRefInfo.getReference());
			Map<String, String> originalMaps = originalRefInfo.getVariablesMap();
			for(String name : originalMaps.keySet()) {
				refInfo.addVariableMap(HAPExpressionUtility.buildFullVariableName(out.getDomain(), name), originalMaps.get(name));
			}
			updatedReferenceInfos.put(refName, refInfo);
		}

		
		//find all reference infos in task
		Map<String, HAPReferenceInfo> referencesInfo = new LinkedHashMap<String, HAPReferenceInfo>();
		for(HAPDefinitionStep step : taskDefExp.getSteps()) {
			for(String refName : step.getReferenceNames()) {
				referencesInfo.put(refName, null);
			}
		}
		referencesInfo.putAll(updatedReferenceInfos);
		
		for(String refName : referencesInfo.keySet()) {
			HAPReferenceInfo referenceInfo = referencesInfo.get(refName);
			
			//referenced task name, by default, use referenceName as task name
			String refTaskName = null;   
			if(referenceInfo!=null)		refTaskName = referenceInfo.getReference();
			if(refTaskName==null)  refTaskName = refName;
			
			HAPDefinitionTask refedTaskDef = contextTaskDefinitions.get(refTaskName);
			
			//domain name for reference
			String refDomain = HAPExpressionUtility.buildFullVariableName(out.getDomain(), refName); 

			//pare reference variable map
			Map<String, String> refVarMap = new LinkedHashMap<String, String>();   //variable mapping from parent to reference expression
			if(referenceInfo!=null)		refVarMap = referenceInfo.getVariablesMap();
		
			//process reference task
			HAPExecutable executable = HAPTaskManager.processTask(refedTaskDef, refDomain, HAPBasicUtility.reverseMapping(refVarMap), contextTaskDefinitions, contextConstants, context);
			out.addReferencedExecute(refName, executable);
			
		}
	}

}
