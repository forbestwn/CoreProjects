package com.nosliw.uiresource.tag;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPEnvContextProcessor;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnitTag;

public class HAPUITagUtility1 {

	//build context for ui Tag
//	public static void buildUITagContext(HAPContextGroup parentContext, HAPDefinitionUIUnitTag uiTag, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
//		//get contextDef 
//		HAPUITagDefinitionContext tagContextDefinition = uiTagMan.getUITagDefinition(new HAPUITagId(uiTag.getTagName())).getContext();
//
//		Map<String, String> constants = uiTag.getAttributes();
//		
//		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
//		configure.inheritMode = tagContextDefinition.getInheritMode();
//		HAPContextUtility.processContextGroupDefinition(tagContextDefinition, parentContext, uiTag.getContext(), configure, new LinkedHashMap<String, Object>(constants), contextProcessorEnv);
//	}
}
