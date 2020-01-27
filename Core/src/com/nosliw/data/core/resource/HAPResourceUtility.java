package com.nosliw.data.core.resource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPSystemUtility;
import com.nosliw.data.core.runtime.js.HAPRuntimeJSUtility;

public class HAPResourceUtility {

	public final static String LOADRESOURCEBYFILE_MODE_NEVER = "never";
	public final static String LOADRESOURCEBYFILE_MODE_ALWAYS = "always";
	public final static String LOADRESOURCEBYFILE_MODE_DEPENDS = "depends";
	
	
	private final static Set<String> loadResourceByFile = HAPSystemUtility.getLoadResoureByFile();
	public static boolean isLoadResoureByFile(String resourceType) {
		String mode = HAPSystemUtility.getLoadResourceByFileMode();
		if(mode==null)  mode = LOADRESOURCEBYFILE_MODE_DEPENDS;
		if(LOADRESOURCEBYFILE_MODE_NEVER.equals(resourceType))  return false;
		if(LOADRESOURCEBYFILE_MODE_ALWAYS.equals(resourceType))  return true;
		return loadResourceByFile.contains(resourceType);
	}
	
	public static List<HAPResourceDependency> buildResourceDependentFromResourceId(List<HAPResourceIdSimple> ids){
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		for(HAPResourceIdSimple id : ids) 	out.add(new HAPResourceDependency(id));
		return out;
	}

	public static Map<String, Object> buildResourceLoadPattern(HAPResourceId resourceId, Map<String, Object> info) {
		if(info==null)   info = new LinkedHashMap<String, Object>();
		if(isLoadResoureByFile(resourceId.getType())) {
			info.put(HAPRuntimeJSUtility.RESOURCE_LOADPATTERN, HAPRuntimeJSUtility.RESOURCE_LOADPATTERN_FILE);
		}
		return info;
	}

	//build literate for id part
	public static String buildResourceCoreIdLiterate(HAPResourceId resourceId) {
		StringBuffer out = new StringBuffer();
		out.append(HAPConstant.SEPERATOR_RESOURCEID_START).append(resourceId.getStructure()).append(HAPConstant.SEPERATOR_RESOURCEID_STRUCTURE).append(resourceId.getIdLiterate());
		return out.toString();
	}
	
	public static String[] parseResourceCoreIdLiterate(String coreIdLiterate) {
		String[] out = new String[2];
		if(coreIdLiterate.startsWith(HAPConstant.SEPERATOR_RESOURCEID_START)) {
			int index = coreIdLiterate.indexOf(HAPConstant.SEPERATOR_RESOURCEID_STRUCTURE, HAPConstant.SEPERATOR_RESOURCEID_START.length());
			out[0] = coreIdLiterate.substring(HAPConstant.SEPERATOR_RESOURCEID_START.length(), index);
			out[1] = coreIdLiterate.substring(index+1);
		}
		else {
			//simple structure
			out[0] = HAPResourceUtility.getDefaultResourceStructure();
			out[1] = coreIdLiterate;
		}
		return out;
	}
	
	public static String[] parseResourceIdLiterate(String idLiterate) {
		return HAPNamingConversionUtility.parseLevel2(idLiterate);
	}
	
	public static String getDefaultResourceStructure() {    return HAPConstant.RESOURCEID_TYPE_SIMPLE;     }
}
