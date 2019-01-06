package com.nosliw.data.core.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.process.plugin.HAPManagerActivityPlugin;
import com.nosliw.data.core.process.util.HAPImporterProcessSuiteDefinition;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextDefEleProcessor;
import com.nosliw.data.core.script.context.HAPContextDefinitionElement;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafRelative;
import com.nosliw.data.core.script.context.HAPContextFlat;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextPath;
import com.nosliw.data.core.script.context.HAPEnvContextProcessor;
import com.nosliw.data.core.script.context.HAPProcessorContext;
import com.nosliw.data.core.script.context.HAPUtilityContext;

public class HAPUtilityProcess {

	private static String helpCategary = HAPConstant.UIRESOURCE_CONTEXTTYPE_PRIVATE;

	//process input configure for activity and generate flat context for activity
	public static HAPExecutableDataAssociationGroup processDataAssociation(HAPContextGroup inputContext, HAPDefinitionDataAssociationGroup mapping, HAPEnvContextProcessor contextProcessorEnv) {
		HAPExecutableDataAssociationGroup out = new HAPExecutableDataAssociationGroup(mapping);
		mapping = mapping.cloneDataAssocationGroup();
		
		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
		configure.inheritMode = HAPUtilityContext.getContextGroupInheritMode(mapping.getInfo()); 
//				HAPConfigureContextProcessor.VALUE_INHERITMODE_NONE; 

		//set input context to help categary
		HAPContextGroup context = new HAPContextGroup();
		
		//help context to store input context defintion
		HAPContext helpContext = new HAPContext();
		for(String eleName : mapping.getElementNames()) {
			helpContext.addElement(eleName, mapping.getElement(eleName));
		}
		
		//consolidate context in help context
		helpContext = consolidateContextRoot(helpContext);
		
		context.setContext(helpCategary, helpContext);

		//process input context
		context = HAPProcessorContext.process(context, inputContext, configure, contextProcessorEnv);
		out.setContext(HAPUtilityContext.buildFlatContextFromContextGroup(context, null));
		
		//build path mapping
		Map<String, String> pathMapping = new LinkedHashMap<String, String>();
		HAPContext helpContext1 = context.getContext(helpCategary);
		for(String eleName : helpContext1.getElementNames()) {
			pathMapping.putAll(HAPUtilityContext.buildRelativePathMapping(helpContext1.getElement(eleName), eleName));
		}
		out.setPathMapping(pathMapping);
		
		return out;
	}

	
	public static HAPExecutableDataAssociationGroup processDataAssociation(HAPContext inputContext, HAPDefinitionDataAssociationGroup mapping, HAPEnvContextProcessor contextProcessorEnv) {
		
		HAPExecutableDataAssociationGroup out = new HAPExecutableDataAssociationGroup(mapping);
		mapping= mapping.cloneDataAssocationGroup();
		
		//build parent context group
		HAPContextGroup inputContextGroup = new HAPContextGroup();
		inputContextGroup.setContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC, inputContext.cloneContext());
		
		//build context group to be processed
		HAPContextGroup mappingContextGroup = new HAPContextGroup();
		mappingContextGroup.setContext(helpCategary, mapping.cloneContext());
		
		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
		configure.inheritMode = HAPConfigureContextProcessor.VALUE_INHERITMODE_NONE;
		//process context to build context
		HAPContextGroup outputContextGroup = HAPProcessorContext.process(mappingContextGroup, inputContextGroup, configure, contextProcessorEnv);

		//build context
		HAPContext outputContext = outputContextGroup.getContext(helpCategary);
		outputContext = consolidateContextRoot(outputContext);
		out.setContext(new HAPContextFlat(outputContext));
		
		//build path mapping
		Map<String, String> pathMapping = new LinkedHashMap<String, String>();
		for(String eleName : outputContext.getElementNames()) {
			HAPContextDefinitionElement contextDefEle = outputContext.getElement(eleName).getDefinition();
			//remove categary info in relative element first
			HAPUtilityContext.processContextDefElement(contextDefEle, new HAPContextDefEleProcessor() {
				@Override
				public boolean process(HAPContextDefinitionElement ele, Object value) {
					if(ele.getType().equals(HAPConstant.CONTEXT_ELEMENTTYPE_RELATIVE)) {
						HAPContextDefinitionLeafRelative relativeEle = (HAPContextDefinitionLeafRelative)ele;
						relativeEle.setPath(new HAPContextPath(relativeEle.getPath().getPath()));
					}
					return true;
				}

				@Override
				public boolean postProcess(HAPContextDefinitionElement ele, Object value) {		return true;	}
			}, null);
			
			pathMapping.putAll(HAPUtilityContext.buildRelativePathMapping(contextDefEle, eleName));
		}
		out.setPathMapping(pathMapping);

		return out;
	}

	//context root name can be like a.b.c and a.b.d
	//these two root name can be consolidated to one root name with a and child of b.c and b.d
	private static HAPContext consolidateContextRoot(HAPContext context) {
		HAPContext out = new HAPContext();
		
		for(String rootName : context.getElementNames()) {
			HAPContextDefinitionElement def = context.getElement(rootName).getDefinition();
			HAPUtilityContext.setDescendant(out, rootName, def);
		}
		return out;
	}
	
	public static String buildOutputVarialbeName(String name) {
		return "nosliw_" + name;
	}
	
	public static HAPDefinitionProcessSuite getProcessSuite(String id, HAPManagerActivityPlugin activityPluginMan) {
		HAPDefinitionProcessSuite suite = null;
		try {
			suite = HAPImporterProcessSuiteDefinition.readProcessSuiteDefinitionFromFile(new FileInputStream(new File(HAPFileUtility.getProcessFolder()+id+".process")), activityPluginMan);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return suite;
	}
}