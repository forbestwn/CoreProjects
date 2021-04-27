package com.nosliw.uiresource.module;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.info.HAPInfoImpSimple;
import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPNosliwUtility;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.component.HAPHandlerLifecycle;
import com.nosliw.data.core.component.attachment.HAPAttachmentReference;
import com.nosliw.data.core.handler.HAPHandler;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPManagerProcess;
import com.nosliw.data.core.process.HAPProcessorProcess;
import com.nosliw.data.core.process.HAPUtilityProcessComponent;
import com.nosliw.data.core.process.resource.HAPResourceDefinitionProcess;
import com.nosliw.data.core.process.resource.HAPResourceDefinitionProcessSuite;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;
import com.nosliw.data.core.service.use.HAPUtilityServiceUse;
import com.nosliw.data.core.structure.HAPConfigureProcessorStructure;
import com.nosliw.data.core.structure.HAPElementNode;
import com.nosliw.data.core.structure.HAPProcessorContext;
import com.nosliw.data.core.structure.HAPRequirementContextProcessor;
import com.nosliw.data.core.structure.HAPRoot;
import com.nosliw.data.core.structure.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.structure.dataassociation.HAPExecutableWrapperTask;
import com.nosliw.data.core.structure.dataassociation.HAPProcessorDataAssociation;
import com.nosliw.data.core.structure.story.HAPParentContext;
import com.nosliw.data.core.structure.value.HAPContextStructureValueDefinitionEmpty;
import com.nosliw.data.core.structure.value.HAPContextStructureValueDefinitionFlat;
import com.nosliw.data.core.structure.value.HAPContextStructureValueDefinitionGroup;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.common.HAPUtilityCommon;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIEvent;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitPage;

public class HAPProcessorModule {

	public static HAPExecutableModule process(
			HAPDefinitionModule moduleDefinition, 
			String id, 
			HAPContextStructureValueDefinitionGroup parentContext, 
			HAPRuntimeEnvironment runtimeEnv,
			HAPUIResourceManager uiResourceMan) {
		
		HAPProcessTracker processTracker = new HAPProcessTracker(); 
		return HAPProcessorModule.process(moduleDefinition, id, parentContext, null, runtimeEnv, uiResourceMan, processTracker);
	}
	
	private static HAPExecutableModule process(
			HAPDefinitionModule moduleDefinition,
			String id, 
			HAPContextStructureValueDefinitionGroup parentContext, 
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPRuntimeEnvironment runtimeEnv,
			HAPUIResourceManager uiResourceMan,
			HAPProcessTracker processTracker) {

		HAPExecutableModule out = new HAPExecutableModule(moduleDefinition, id);

		HAPRequirementContextProcessor contextProcessRequirement1 = HAPUtilityCommon.getDefaultContextProcessorRequirement(runtimeEnv.getResourceDefinitionManager(), runtimeEnv.getDataTypeHelper(), runtimeEnv.getRuntime(), runtimeEnv.getExpressionManager(), runtimeEnv.getServiceManager().getServiceDefinitionManager());
		HAPConfigureProcessorStructure contextProcessConfg = HAPUtilityConfiguration.getContextProcessConfigurationForModule();
		
		//service providers
		Map<String, HAPDefinitionServiceProvider> allServiceProviders = HAPUtilityServiceUse.buildServiceProvider(moduleDefinition.getAttachmentContainer(), serviceProviders, runtimeEnv.getServiceManager().getServiceDefinitionManager()); 
		
		//process context 
		out.setContextGroup((HAPContextStructureValueDefinitionGroup)HAPProcessorContext.process(moduleDefinition.getValueContext(), HAPParentContext.createDefault(parentContext==null?new HAPContextStructureValueDefinitionGroup():parentContext), contextProcessConfg, runtimeEnv));

		//process process suite
		HAPResourceDefinitionProcessSuite processSuite = HAPUtilityProcessComponent.buildProcessSuiteFromComponent(moduleDefinition, runtimeEnv.getProcessManager().getPluginManager()).cloneProcessSuiteDefinition(); 
		processSuite.setValueContext(out.getContext());   //kkk
		out.setProcessSuite(processSuite);
		
		//process lifecycle action
		Set<HAPHandlerLifecycle> lifecycleActions = moduleDefinition.getLifecycleAction();
		for(HAPHandlerLifecycle action : lifecycleActions) {
			HAPResourceDefinitionProcess processDef = out.getProcessDefinition(action.getTask().getTaskDefinition());
			HAPExecutableProcess processExe = HAPProcessorProcess.process(processDef, null, allServiceProviders, runtimeEnv.getProcessManager(), runtimeEnv, processTracker);
			HAPExecutableWrapperTask processExeWrapper = HAPProcessorDataAssociation.processDataAssociationWithTask(action.getTask(), processExe, HAPParentContext.createDefault(out.getContext()), null, runtimeEnv);			
			out.addLifecycle(action.getName(), processExeWrapper);
		}
		
		//process ui
		for(HAPDefinitionModuleUI ui : moduleDefinition.getUIs()) {
			if(!HAPUtilityEntityInfo.isEnabled(ui)) {
				HAPExecutableModuleUI uiExe = processModuleUI(ui, ui.getName(), out, allServiceProviders, runtimeEnv.getProcessManager(), uiResourceMan, runtimeEnv, processTracker);
				out.addModuleUI(uiExe);
			}
		}
		
		return out;
	}
	
	private static HAPExecutableModuleUI processModuleUI(
			HAPDefinitionModuleUI moduleUIDefinition,
			String id,
			HAPExecutableModule moduleExe,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcess processMan,
			HAPUIResourceManager uiResourceMan,
			HAPRuntimeEnvironment runtimeEnv,
			HAPProcessTracker processTracker) {
		HAPExecutableModuleUI out = new HAPExecutableModuleUI(moduleUIDefinition, id);
		
		//process page, use context in module override context in page
		//find pape resource id from attachment mapping
		HAPAttachmentReference pageAttachment = (HAPAttachmentReference)moduleExe.getDefinition().getAttachmentContainer().getElement(HAPConstantShared.RUNTIME_RESOURCE_TYPE_UIRESOURCE, moduleUIDefinition.getPage());
		HAPResourceId pageResourceId = pageAttachment.getReferenceId();

		//attachment
//		HAPAttachmentContainer mappedParentAttachment = HAPComponentUtility.buildInternalAttachment(pageResourceId, moduleExe.getDefinition().getAttachmentContainer(), moduleUIDefinition);

		//ui decoration
		//ui decoration from page first
//		out.addUIDecoration(pageInfo.getDecoration());
		//ui decoration from module ui
		out.addUIDecoration(moduleUIDefinition.getUIDecoration());
		//ui decoration from module
		out.addUIDecoration(moduleExe.getDefinition().getUIDecoration());
		
		//context
		HAPContextStructureValueDefinitionGroup mappingContextGroup = new HAPContextStructureValueDefinitionGroup();
		HAPExecutableDataAssociation daEx = HAPProcessorDataAssociation.processDataAssociation(HAPParentContext.createDefault(moduleExe.getContext()), moduleUIDefinition.getInputMapping().getDefaultDataAssociation(), HAPParentContext.createDefault(HAPContextStructureValueDefinitionEmpty.flatStructure()), null, runtimeEnv);
		mappingContextGroup.setContext(HAPConstantShared.UIRESOURCE_CONTEXTTYPE_PUBLIC, (HAPContextStructureValueDefinitionFlat)daEx.getOutput().getOutputStructure());  //.getAssociation().getSolidContext());  kkkk

		HAPExecutableUIUnitPage page = uiResourceMan.getEmbededUIPage(pageResourceId, id, mappingContextGroup, null, moduleExe.getDefinition().getAttachmentContainer(), moduleUIDefinition);
		out.setPage(page);

		HAPInfo daConfigure = HAPProcessorDataAssociation.withModifyOutputStructureConfigureFalse(new HAPInfoImpSimple());
		//build input data association
		HAPExecutableDataAssociation inputDataAssocation = HAPProcessorDataAssociation.processDataAssociation(HAPParentContext.createDefault(moduleExe.getContext()), moduleUIDefinition.getInputMapping().getDefaultDataAssociation(), HAPParentContext.createDefault(page.getBody().getFlatContext().getContext()), daConfigure, runtimeEnv);
		out.setInputMapping(inputDataAssocation);
		
		//build output data association
		HAPExecutableDataAssociation outputDataAssocation = HAPProcessorDataAssociation.processDataAssociation(HAPParentContext.createDefault(page.getBody().getContext()), moduleUIDefinition.getOutputMapping().getDefaultDataAssociation(), HAPParentContext.createDefault(moduleExe.getContext()), daConfigure, runtimeEnv);
		out.setOutputMapping(outputDataAssocation);
		
		//event handler
		Set<HAPHandler> eventHandlerDefs = moduleUIDefinition.getEventHandlers();
		for(HAPHandler eventHandlerDef : eventHandlerDefs) {
			String eventName = eventHandlerDef.getName();
			HAPRoot eventRootNode = buildContextRootFromEvent(out.getPage().getBody().getEventDefinition(eventName));
			HAPContextStructureValueDefinitionGroup eventContext = new HAPContextStructureValueDefinitionGroup();
			eventContext.addElement(HAPNosliwUtility.buildNosliwFullName("EVENT"), eventRootNode, HAPConstantShared.UIRESOURCE_CONTEXTTYPE_PUBLIC);
			HAPResourceDefinitionProcess processDef = moduleExe.getProcessDefinition(eventHandlerDef.getTask().getTaskDefinition());
			HAPExecutableProcess eventProcessor = HAPProcessorProcess.process(processDef, eventContext, serviceProviders, processMan, runtimeEnv, processTracker);
			HAPExecutableWrapperTask processExeWrapper = HAPProcessorDataAssociation.processDataAssociationWithTask(eventHandlerDef.getTask(), eventProcessor, HAPParentContext.createDefault(moduleExe.getContext()), null, runtimeEnv);			
			out.addEventHandler(eventName, processExeWrapper);
		}	
		return out;
	}

	private static HAPRoot buildContextRootFromEvent(HAPDefinitionUIEvent eventDef) {
		HAPRoot root = new HAPRoot();
		HAPElementNode nodeEle = new HAPElementNode();
		
		HAPContextStructureValueDefinitionFlat dataDef = eventDef.getDataDefinition();
		for(String dataName : dataDef.getElementNames()) {
			nodeEle.addChild(dataName, dataDef.getElement(dataName).getDefinition());
		}
		root.setDefinition(nodeEle);
		return root;
	}

}
