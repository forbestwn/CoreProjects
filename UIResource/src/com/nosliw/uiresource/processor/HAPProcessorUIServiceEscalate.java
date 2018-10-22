package com.nosliw.uiresource.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.data.core.service.HAPDefinitionServiceInfo;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.tag.HAPUITagId;
import com.nosliw.uiresource.tag.HAPUITagManager;

public class HAPProcessorUIServiceEscalate {

	public static void process(HAPExecutableUIUnit exeUnit, HAPUITagManager uiTagMan) {
		if(HAPConstant.UIRESOURCE_TYPE_TAG.equals(exeUnit.getType())) {
			HAPExecutableUIUnitTag exeTag = (HAPExecutableUIUnitTag)exeUnit;
			if(HAPUtilityContext.getContextGroupEscalateMode(uiTagMan.getUITagDefinition(new HAPUITagId(exeTag.getUIUnitTagDefinition().getTagName())).getContext())) {
				Map<String, HAPDefinitionServiceInfo> mappedServiceDefs = new LinkedHashMap<String, HAPDefinitionServiceInfo>();
				
				Map<String, String> nameMapping = HAPNamingConversionUtility.parsePropertyValuePairs(exeTag.getAttributes().get(HAPConstant.UITAG_PARM_SERVICE));
				exeTag.setServiceMapping(nameMapping);
				Map<String, HAPDefinitionServiceInfo> exeServiceDefs = exeTag.getServiceDefinitions();
				for(String serviceName : exeServiceDefs.keySet()) {
					String mappedName = nameMapping.get(serviceName);
					if(mappedName==null)   mappedName = serviceName;
					mappedServiceDefs.put(mappedName, exeServiceDefs.get(serviceName));
				}
				escalate(exeTag.getParent(), mappedServiceDefs, uiTagMan);
			}
		}

		//child tag
		for(HAPExecutableUIUnitTag childTag : exeUnit.getUITags()) {
			process(childTag, uiTagMan);
		}
	}
	
	private static void escalate(HAPExecutableUIUnit exeUnit, Map<String, HAPDefinitionServiceInfo> servicesDef, HAPUITagManager uiTagMan) {
		if(HAPConstant.UIRESOURCE_TYPE_RESOURCE.equals(exeUnit.getType())){
			for(String serviceName : servicesDef.keySet()) {
				if(exeUnit.getServiceDefinition(serviceName)==null) {
					exeUnit.addServiceDefinition(servicesDef.get(serviceName));
				}
			}
		}
		else {
			if(HAPUtilityContext.getContextGroupEscalateMode(uiTagMan.getUITagDefinition(new HAPUITagId(((HAPExecutableUIUnitTag)exeUnit).getUIUnitTagDefinition().getTagName())).getContext())) {
				escalate(exeUnit.getParent(), servicesDef, uiTagMan);
			}
		}
	}
}
