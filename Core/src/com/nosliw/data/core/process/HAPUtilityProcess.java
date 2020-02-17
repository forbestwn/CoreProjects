package com.nosliw.data.core.process;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.exception.HAPErrorUtility;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.component.HAPAttachment;
import com.nosliw.data.core.component.HAPAttachmentContainer;
import com.nosliw.data.core.component.HAPAttachmentEntity;
import com.nosliw.data.core.component.HAPAttachmentReference;
import com.nosliw.data.core.criteria.HAPVariableInfo;
import com.nosliw.data.core.process.plugin.HAPManagerActivityPlugin;
import com.nosliw.data.core.process.util.HAPParserProcessDefinition;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextDefinitionElement;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafData;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafRelative;
import com.nosliw.data.core.script.context.HAPContextDefinitionRoot;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextPath;
import com.nosliw.data.core.script.context.HAPContextStructure;
import com.nosliw.data.core.script.context.HAPParentContext;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionDataAssociation;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionWrapperTask;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.script.context.dataassociation.HAPProcessorDataAssociation;
import com.nosliw.data.core.script.context.dataassociation.mirror.HAPDefinitionDataAssociationMirror;
import com.nosliw.data.core.script.context.dataassociation.none.HAPDefinitionDataAssociationNone;
import com.nosliw.data.core.script.expression.HAPProcessContextScriptExpression;

public class HAPUtilityProcess {

	public static String buildOutputVarialbeName(String name) {
		return "nosliw_" + name;
	}
	
	public static HAPDefinitionProcessSuite buildProcessSuiteFromAttachment(HAPAttachmentContainer attachmentContainer, HAPManagerActivityPlugin activityPluginMan) {
		HAPDefinitionProcessSuite out = new HAPDefinitionProcessSuite();
		Map<String, HAPAttachment> attachments = attachmentContainer.getAttachmentByType(HAPConstant.RUNTIME_RESOURCE_TYPE_PROCESS);
		for(String id : attachments.keySet()) {
			HAPAttachmentEntity entityAttachment = (HAPAttachmentEntity)attachments.get(id);
			HAPDefinitionProcessSuiteElementEntity processDef = HAPParserProcessDefinition.parseProcess(entityAttachment.getEntity(), activityPluginMan);
			out.addProcess(id, processDef);
		}
		return out;
	}

	public static HAPDefinitionProcessSuiteElement getProcessDefinitionElementFromAttachment(String name, HAPAttachmentContainer attachmentContainer, HAPManagerActivityPlugin activityPluginMan) {
		HAPAttachment attachment = attachmentContainer.getElement(HAPConstant.RUNTIME_RESOURCE_TYPE_PROCESS, name);
		return getProcessDefinitionElementFromAttachment(attachment, activityPluginMan);
	}

	public static HAPDefinitionProcessSuiteElement getProcessDefinitionElementFromAttachment(HAPAttachment attachment, HAPManagerActivityPlugin activityPluginMan) {
		HAPDefinitionProcessSuiteElement out = null;
		if(HAPConstant.ATTACHMENT_TYPE_ENTITY.equals(attachment.getType())) {
			HAPAttachmentEntity entityAttachment = (HAPAttachmentEntity)attachment;
			out = HAPParserProcessDefinition.parseProcess(entityAttachment.getEntity(), activityPluginMan);
		}
		else if(HAPConstant.ATTACHMENT_TYPE_REFERENCE.equals(attachment.getType())) {
			HAPAttachmentReference referenceAttachment = (HAPAttachmentReference)attachment;
			out = new HAPDefinitionProcessSuiteElementReference(referenceAttachment.getId());
		}
		return out;
	}

	public static void buildScriptExpressionProcessContext(HAPContext context, HAPProcessContextScriptExpression expProcessContext) {
		//prepare constant value 
		expProcessContext.addConstants(context.getConstantValue());
		//prepare variables 
		expProcessContext.addDataVariables(HAPUtilityContext.discoverDataVariablesInContext(context));
	}

	public static void processNormalActivityInputDataAssocation(HAPExecutableActivityNormal activity, HAPContextGroup processContext, HAPRequirementContextProcessor contextProcessRequirement) {
		HAPExecutableDataAssociation da = HAPProcessorDataAssociation.processDataAssociation(
				HAPParentContext.createDefault(processContext), 
				activity.getNormalActivityDefinition().getInputMapping(), 
				HAPParentContext.createDefault(activity.getNormalActivityDefinition().getInputContextStructure(processContext)), 
				null, 
				contextProcessRequirement);
		activity.setInputDataAssociation(da);
	}
	
	public static void processBranchActivityInputDataAssocation(HAPExecutableActivityBranch activity, HAPContextGroup processContext, HAPRequirementContextProcessor contextProcessRequirement) {
		HAPExecutableDataAssociation da = HAPProcessorDataAssociation.processDataAssociation(
				HAPParentContext.createDefault(processContext), 
				activity.getBranchActivityDefinition().getInputMapping(), 
				HAPParentContext.createDefault(activity.getBranchActivityDefinition().getInputContextStructure(processContext)), 
				null, 
				contextProcessRequirement);
		activity.setInputDataAssociation(da);
	}
	
	//data variables infor in activity merge back to process context
	public static void mergeDataVariableInActivityToProcessContext(Map<String, HAPVariableInfo> activityVariablesInfo, HAPContext activityContext, HAPContextGroup processContext) {
		Map<String, HAPVariableInfo> expectedVariablesInfo = new LinkedHashMap<String, HAPVariableInfo>();
		for(String varName : expectedVariablesInfo.keySet()) {
			HAPVariableInfo expectedVarInfo = activityVariablesInfo.get(varName);
			HAPContextPath varPath = new HAPContextPath(varName);
			//affect global variable 
			HAPContextDefinitionRoot affectedRoot = activityContext.getElement(varPath.getRootElementId().getFullName());
			if(affectedRoot!=null) {
				//ele mapped from context variable
				HAPContextDefinitionElement currentEle = affectedRoot.getDefinition();
				String[] pathSegs = new HAPPath(varPath.getSubPath()).getPathSegs();
				int i = 0;
				while(!HAPConstant.CONTEXT_ELEMENTTYPE_RELATIVE.equals(currentEle.getType())&&currentEle!=null) {
					currentEle = currentEle.getChild(pathSegs[i]);
					i++;
				}
				HAPContextDefinitionLeafRelative relativeEle = (HAPContextDefinitionLeafRelative)currentEle;
				HAPContextPath relativeElePath = relativeEle.getPath();
				String fullName = relativeElePath.getFullPath();
				for(;i<pathSegs.length; i++) {
					fullName = HAPNamingConversionUtility.buildPath(fullName, pathSegs[i]);
				}
				expectedVariablesInfo.put(fullName, expectedVarInfo);
			}
			else {
				//root variable does not exist, generate one
//				HAPContextDefinitionLeafData dataEle = new HAPContextDefinitionLeafData(new HAPVariableInfo(out.getScriptExpressionProcessContext().getDataVariables().get(varName).getCriteria()));
//				affectedContext.addElement(varName, dataEle);
				HAPErrorUtility.invalid("");
			}
		}

		//affect parent context
		for(String basePath : expectedVariablesInfo.keySet()) {
			HAPContextPath cpath = new HAPContextPath(basePath);
			HAPContextDefinitionLeafData affectedEle = new HAPContextDefinitionLeafData(expectedVariablesInfo.get(basePath));
			HAPUtilityContext.updateDataDescendant(processContext, cpath.getRootElementId().getCategary(), cpath.getPath(), affectedEle);
		}
	}
	
	//process result
	public static HAPExecutableResultActivityNormal processNormalActivityResult(
			HAPExecutableActivityNormal activity,
			String resultName, 
			HAPContextGroup parentContext,
			HAPBuilderResultContext resultContextBuilder, 
			HAPRequirementContextProcessor contextProcessRequirement) {
		HAPDefinitionResultActivityNormal resultDef = ((HAPDefinitionActivityNormal)activity.getActivityDefinition()).getResult(resultName);
		HAPExecutableResultActivityNormal resultExe = new HAPExecutableResultActivityNormal(resultDef);
		if(resultContextBuilder!=null) {
			//data association input context
			HAPContextStructure dataAssociationInputContext = resultContextBuilder.buildResultContext(resultName, activity);
			//process data association
			HAPExecutableDataAssociation outputDataAssociation = HAPProcessorDataAssociation.processDataAssociation(HAPParentContext.createDefault(dataAssociationInputContext), resultDef.getOutputDataAssociation(), HAPParentContext.createDefault(parentContext), null, contextProcessRequirement);
			resultExe.setDataAssociation(outputDataAssociation);
		}
		return resultExe;
	}

	//process result
	public static void processBranchActivityBranch(HAPExecutableActivityBranch activity) {
		for(HAPDefinitionResultActivityBranch branch :activity.getBranchActivityDefinition().getBranch()) {
			activity.addBranch(new HAPExecutableResultActivityBranch(branch));
		}
	}
	
	//build task wrapper for activity has task in it
	//all the input and result output for activity is mirror 
	public static HAPDefinitionWrapperTask parseTaskDefinition(HAPDefinitionActivityNormal activity, JSONObject jsonObj) {
		HAPDefinitionWrapperTask out = new HAPDefinitionWrapperTask();
		out.setInputMapping(activity.getInputMapping());
		activity.setInputMapping(new HAPDefinitionDataAssociationMirror());
		
		Map<String, HAPDefinitionResultActivityNormal> results = activity.getResults();
		for(String resultName : results.keySet()) {
			HAPDefinitionResultActivityNormal result = results.get(resultName);
			HAPDefinitionDataAssociation dataAssociation = result.getOutputDataAssociation();
			if(dataAssociation!=null)		out.addOutputMapping(resultName, dataAssociation.cloneDataAssocation());
			else out.addOutputMapping(resultName, new HAPDefinitionDataAssociationNone());
			result.setOutputDataAssociation(new HAPDefinitionDataAssociationMirror());
		}
		return out;
	}	
	
	public static void parseWithProcessTask(HAPWithProcessTask task, JSONObject jsonObj) {
		HAPDefinitionWrapperTask<String> process = new HAPDefinitionWrapperTask<String>();
		process.setTaskDefinition(jsonObj.optString(HAPWithProcessTask.PROCESS));
		process.buildMapping(jsonObj);
		task.setProcess(process);
	}
	
}
