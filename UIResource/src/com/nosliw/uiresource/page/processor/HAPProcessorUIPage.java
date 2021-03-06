package com.nosliw.uiresource.page.processor;

import java.util.Map;

import com.nosliw.data.core.component.HAPContextProcessAttachmentReference;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinitionGroup;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.common.HAPIdGenerator;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIPage;
import com.nosliw.uiresource.page.definition.HAPParserPage;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitPage;
import com.nosliw.uiresource.page.tag.HAPManagerUITag;

public class HAPProcessorUIPage {

	public static HAPExecutableUIUnitPage processUIResource(
			HAPDefinitionUIPage uiPageDef,
			String id,
			HAPValueStructureDefinitionGroup externalContext,
			HAPValueStructureDefinitionGroup parentContext,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPRuntimeEnvironment runtimeEnv,
			HAPUIResourceManager uiResourceMan,
			HAPManagerUITag uiTagMan,  
			HAPParserPage uiResourceParser,
			HAPIdGenerator idGengerator) {
		
		//----------------------  Normalize definition
		
		//expand include and build mapping for include tag
		HAPProcessorInclude.expandInclude(uiPageDef, uiResourceParser, uiResourceMan, runtimeEnv.getResourceDefinitionManager());
		
		//process attachment
		HAPProcessorAttachment.mergeAttachment(uiPageDef, null, uiTagMan);
		
		//expand referred context part
		HAPProcessorUIValueStructure.expandValueStructureReference(uiPageDef, new HAPContextProcessAttachmentReference(uiPageDef, runtimeEnv), runtimeEnv);
//		HAPWrapperValueStructure valueStructureWrapper = uiPageDef.getValueStructureWrapper();
//		valueStructureWrapper.setValueStructure(HAPProcessorValueStructureInComponent.process((HAPValueStructureInComponent)valueStructureWrapper.getValueStructure(), new HAPContextProcessAttachmentReference(uiPageDef, runtimeEnv), runtimeEnv));

		//enhance value structure (error)
		HAPProcessorUIValueStructure.enhanceValueStructure(uiPageDef);
		
		//normalize service use
		HAPProcessorUIService.normalizeService(uiPageDef, runtimeEnv);
		
		//enhance context by service
		HAPProcessorUIValueStructure.enhanceValueStructureByService(uiPageDef, runtimeEnv);
		
		
		//----------------------  Build executable
		HAPExecutableUIUnitPage out = new HAPExecutableUIUnitPage(uiPageDef, id);

		//process context
		HAPProcessorUIValueStructure.process(out, null, uiTagMan, runtimeEnv);

		//process service
		HAPProcessorUIService.processService(out, runtimeEnv);
		HAPProcessorUIService.escalate(out, uiTagMan);
		
		//process expression
		HAPProcessorUIExpressionScript.buildExpressionScriptProcessContext(out, runtimeEnv);
		HAPProcessorUIExpressionScript.processUIScriptExpression(out, runtimeEnv);
		
		//process handler
		HAPProcessorUIHandler.process(out, runtimeEnv);
		
		//process command
		HAPProcessorUICommand.processCommand(out, runtimeEnv);
		HAPProcessorUICommand.escalateCommand(out, uiTagMan);

		//process event
		HAPProcessorUIEvent.processEvent(out, runtimeEnv);
		HAPProcessorUIEvent.escalateEvent(out, uiTagMan);
		
		//process style
		HAPProcessorStyle.process(out);
		
		//build executable value structure
		HAPProcessorUIValueStructure.buildExecutable(out);
		
		
//		if(serviceProviders==null)  serviceProviders = new LinkedHashMap<String, HAPDefinitionServiceProvider>();
		
		//compile definition to executable
//		HAPProcessorCompile.process(out, null);

//		HAPPorcessorResolveName.resolve(out);
		
//		HAPProcessorUIConstantInContext.resolveConstants(out, runtimeEnv.getRuntime());
		
//		HAPProcessorUIServiceEscalate.process(out, uiTagMan);
		
		return out;
	}

}
