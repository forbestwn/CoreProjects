package com.nosliw.uiresource.application;

import java.util.Map;

import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.info.HAPInfoImpSimple;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.component.HAPComponentUtility;
import com.nosliw.data.core.component.HAPDefinitionExternalMapping;
import com.nosliw.data.core.expressionsuite.HAPExpressionSuiteManager;
import com.nosliw.data.core.process.HAPDefinitionProcess;
import com.nosliw.data.core.process.HAPDefinitionProcessWithContext;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPManagerProcessDefinition;
import com.nosliw.data.core.process.HAPProcessorProcess;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextStructure;
import com.nosliw.data.core.script.context.HAPParentContext;
import com.nosliw.data.core.script.context.HAPProcessorContext;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionDataAssociation;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionWrapperTask;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableWrapperTask;
import com.nosliw.data.core.script.context.dataassociation.HAPProcessorDataAssociation;
import com.nosliw.data.core.service.provide.HAPManagerServiceDefinition;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;
import com.nosliw.data.core.service.use.HAPUtilityServiceUse;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.common.HAPDefinitionEventHandler;
import com.nosliw.uiresource.common.HAPExecutableEventHandler;
import com.nosliw.uiresource.common.HAPUtilityCommon;
import com.nosliw.uiresource.module.HAPDefinitionModule;
import com.nosliw.uiresource.module.HAPDefinitionModuleUI;
import com.nosliw.uiresource.module.HAPExecutableModule;
import com.nosliw.uiresource.module.HAPProcessorModule;
import com.nosliw.uiresource.module.HAPUtilityModule;

public class HAPProcessMiniAppEntry {

	public static HAPExecutableAppEntry process(
			HAPDefinitionApp miniAppDef,
			String entry, 
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcessDefinition processMan,
			HAPUIResourceManager uiResourceMan,
			HAPDataTypeHelper dataTypeHelper, 
			HAPRuntime runtime, 
			HAPExpressionSuiteManager expressionManager,
			HAPManagerServiceDefinition serviceDefinitionManager,
			HAPProcessTracker processTracker) {

		HAPExecutableAppEntry out = new HAPExecutableAppEntry(entry, miniAppDef);
		
		HAPDefinitionAppEntryUI entryDefinition = miniAppDef.getEntry(entry);
		
		HAPRequirementContextProcessor contextProcessRequirement = HAPUtilityCommon.getDefaultContextProcessorRequirement(dataTypeHelper, runtime, expressionManager, serviceDefinitionManager);
		HAPConfigureContextProcessor contextProcessConfg = HAPUtilityApp.getContextProcessConfigurationForApp();
		
		//service providers
		Map<String, HAPDefinitionServiceProvider> entryServiceProviders = HAPUtilityServiceUse.buildServiceProvider(miniAppDef.getServiceProviderDefinitions(), entryDefinition, contextProcessRequirement.serviceDefinitionManager); 

		//context
		out.setContext(HAPProcessorContext.process(entryDefinition.getContext(), HAPParentContext.createDefault(miniAppDef.getContext()), contextProcessConfg, contextProcessRequirement));

		//data definition
		Map<String, HAPDefinitionAppData> dataDefs = miniAppDef.getApplicationData();
		for(String dataDefName : dataDefs.keySet()) {
			HAPContext processedContext = HAPProcessorContext.process(dataDefs.get(dataDefName), HAPParentContext.createDefault(miniAppDef.getContext()), contextProcessConfg, contextProcessRequirement);
			HAPDefinitionAppData processedAppData = dataDefs.get(dataDefName).cloneAppDataDefinition();
			processedContext.toContext(processedAppData);
			out.addApplicationData(dataDefName, processedAppData);
		}
		
		//process
		Map<String, HAPDefinitionWrapperTask<HAPDefinitionProcess>> processes = entryDefinition.getProcesses();
		for(String name : processes.keySet()) {
			HAPExecutableProcess processExe = HAPProcessorProcess.process(name, new HAPDefinitionProcessWithContext(processes.get(name).getTaskDefinition()), out.getContext(), entryServiceProviders, processMan, contextProcessRequirement, processTracker);
			HAPExecutableWrapperTask processExeWrapper = HAPProcessorDataAssociation.processDataAssociationWithTask(processes.get(name), processExe, HAPParentContext.createDefault(out.getContext()), null, contextProcessRequirement);			
			out.addProcess(name, processExeWrapper);
		}

		//prepare extra context
		Map<String, HAPContextStructure> extraContext = out.getExtraContext();
		
		//module
		for(HAPDefinitionAppModule moduleDef : entryDefinition.getModules()) {
			if(!HAPDefinitionModuleUI.STATUS_DISABLED.equals(moduleDef.getStatus())) {
				HAPExecutableAppModule module = processModule(moduleDef, out, entryDefinition.getExternalMapping(), extraContext, entryServiceProviders, processMan, uiResourceMan, contextProcessRequirement, processTracker);
				out.addUIModule(moduleDef.getName(), module);
			}
		}
		
		return out;
	}
	
	private static HAPExecutableAppModule processModule(
			HAPDefinitionAppModule module,
			HAPExecutableAppEntry entryExe,
			HAPDefinitionExternalMapping parentExternalMapping,
			Map<String, HAPContextStructure> extraContexts,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcessDefinition processMan,
			HAPUIResourceManager uiResourceMan,
			HAPRequirementContextProcessor contextProcessRequirement,
			HAPProcessTracker processTracker) {
		HAPExecutableAppModule out = new HAPExecutableAppModule(module);
		
		HAPResourceId moduleResourceId = parentExternalMapping.getElement(HAPConstant.RUNTIME_RESOURCE_TYPE_UIMODULE, module.getName()).getId();
		HAPDefinitionModule moduleDef = HAPUtilityModule.getUIModuleDefinitionById(moduleResourceId.getId(), uiResourceMan.getModuleParser());
		 
		HAPParentContext parentContext = HAPParentContext.createDefault(entryExe.getContext());
		for(String extraName : extraContexts.keySet()) {
			parentContext.addContext(extraName, extraContexts.get(extraName));
		}
		
		HAPInfo daConfigure = HAPProcessorDataAssociation.withModifyStructureFalse(new HAPInfoImpSimple());

		//input data association
		Map<String, HAPDefinitionDataAssociation> inputDas = module.getInputMapping().getDataAssociations();
		for(String inputDaName : inputDas.keySet()) {
			HAPExecutableDataAssociation inputMapping = HAPProcessorDataAssociation.processDataAssociation(parentContext, inputDas.get(inputDaName), HAPParentContext.createDefault(moduleDef.getContext().getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC)), daConfigure, contextProcessRequirement);
			out.addInputDataAssociation(inputDaName, inputMapping);
		}
		
		//module
		HAPDefinitionExternalMapping pageExternalMapping = HAPComponentUtility.buildInternalComponentExternalMapping(moduleResourceId, entryExe.getDefinition().getExternalMapping(), module);
		HAPExecutableModule moduleExe = HAPProcessorModule.process(moduleDef, moduleDef.getName(), parentExternalMapping, null, processMan, uiResourceMan, contextProcessRequirement.dataTypeHelper, contextProcessRequirement.runtime, contextProcessRequirement.expressionManager, contextProcessRequirement.serviceDefinitionManager);
		out.setModule(moduleExe);
		
		//output data association
		Map<String, HAPDefinitionDataAssociation> outputMapping = module.getOutputMapping().getDataAssociations();
		for(String outputTargetName : outputMapping.keySet()) {
			HAPExecutableDataAssociation outputMappingByTarget = HAPProcessorDataAssociation.processDataAssociation(HAPParentContext.createDefault(moduleDef.getContext()), outputMapping.get(outputTargetName), parentContext, daConfigure, contextProcessRequirement);
			out.addOutputDataAssociation(outputTargetName, outputMappingByTarget);
		}
		
		//event handler
		Map<String, HAPDefinitionEventHandler> eventHandlerDefs = module.getEventHandlers();
		for(String eventName :eventHandlerDefs.keySet()) {
			HAPDefinitionEventHandler eventHandlerDef = eventHandlerDefs.get(eventName);
			HAPExecutableEventHandler eventHandlerExe = new HAPExecutableEventHandler(eventHandlerDef);

			HAPContextGroup eventContext = entryExe.getContext().cloneContextGroup();
			HAPExecutableProcess eventProcessor = HAPProcessorProcess.process(eventName, new HAPDefinitionProcessWithContext(eventHandlerDef.getProcess().getTaskDefinition()), eventContext, serviceProviders, processMan, contextProcessRequirement, processTracker);
			HAPExecutableWrapperTask processExeWrapper = HAPProcessorDataAssociation.processDataAssociationWithTask(eventHandlerDef.getProcess(), eventProcessor, HAPParentContext.createDefault(moduleExe.getContext()), null, contextProcessRequirement);			
			eventHandlerExe.setProcess(processExeWrapper);
			out.addEventHandler(eventName, eventHandlerExe);
		}	
		
		return out;
	}
}
