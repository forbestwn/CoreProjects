package com.nosliw.uiresource.tag;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.data.context.HAPConfigureContextProcessor;
import com.nosliw.data.context.HAPContextGroup;
import com.nosliw.data.context.HAPContextUtility;
import com.nosliw.data.context.HAPEnvContextProcessor;
import com.nosliw.uiresource.page.HAPUIDefinitionUnitTag;

public class HAPUITagUtility {

	
	//build context for ui Tag
	public static void buildUITagContext(HAPContextGroup parentContext, HAPUIDefinitionUnitTag uiTag, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		//get contextDef 
		HAPUITagDefinitionContext tagContextDefinition = uiTagMan.getUITagDefinition(new HAPUITagId(uiTag.getTagName())).getContext();

		Map<String, String> constants = uiTag.getAttributes();
		
		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
		configure.inheritMode = tagContextDefinition.getInheritMode();
		HAPContextUtility.processContextGroupDefinition(tagContextDefinition, parentContext, uiTag.getContext(), configure, new LinkedHashMap<String, Object>(constants), contextProcessorEnv);
		
		int kkk = 5555;
		kkk++;
		
//		if(tagContextDefinition.isInherit()){
//			//add inheriable context from parent
//			for(String contextType : HAPContextGroup.getInheritableContextTypes()) {
//				for(String rootEleName : parentContext.getElements(contextType).keySet()){
//					HAPContextNodeRootRelative relativeEle = new HAPContextNodeRootRelative();
//					relativeEle.setPath(rootEleName);
//					uiTag.getContext().addElement(rootEleName, HAPContextUtility.processContextDefinitionElement(rootEleName, relativeEle, parentContext, constants, contextProcessorEnv), contextType);
//				}
//			}
//		}
//
//		//element defined in tag definition
//		HAPContextUtility.processContextGroupDefinition(tagContextDefinition, parentContext, uiTag.getContext(), constants, contextProcessorEnv);
	}
}
