package com.nosliw.uiresource.resource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.resource.HAPResource;
import com.nosliw.data.core.resource.HAPResourceDependent;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.resource.HAPResourceManagerImp;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPRuntimeJSUtility;
import com.nosliw.uiresource.page.tag.HAPUITagDefinition;
import com.nosliw.uiresource.page.tag.HAPUITagManager;

public class HAPResourceManagerUITag extends HAPResourceManagerImp{

	private HAPUITagManager m_uiTagMan;
	
	public HAPResourceManagerUITag(HAPUITagManager uiTagMan){
		this.m_uiTagMan = uiTagMan;
	}
	
	@Override
	public HAPResource getResource(HAPResourceId resourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceIdUITag uiTagResourceId = new HAPResourceIdUITag(resourceId); 
		HAPUITagDefinition uiTagDefinition = this.m_uiTagMan.getUITagDefinition(uiTagResourceId.getUITagId());
		if(uiTagDefinition==null)  return null;
		HAPResourceDataUITag resourceData = new HAPResourceDataUITag(uiTagDefinition);
		
		Map<String, Object> info = new LinkedHashMap<String, Object>();
		info.put(HAPRuntimeJSUtility.RESOURCE_LOADPATTERN, HAPRuntimeJSUtility.RESOURCE_LOADPATTERN_FILE);
		return new HAPResource(resourceId, resourceData, info);
	}

	@Override
	protected List<HAPResourceDependent> getResourceDependency(HAPResourceId resourceId, HAPRuntimeInfo runtimeInfo){
		List<HAPResourceDependent> out = new ArrayList<HAPResourceDependent>();
		HAPResourceIdUITag uiTagResourceId = new HAPResourceIdUITag(resourceId); 
		HAPUITagDefinition uiTagDefinition = this.m_uiTagMan.getUITagDefinition(uiTagResourceId.getUITagId());
		out.addAll(uiTagDefinition.getResourceDependency());
		return out;
	}
	
}
