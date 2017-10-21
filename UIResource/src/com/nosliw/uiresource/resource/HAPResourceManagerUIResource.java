package com.nosliw.uiresource.resource;

import java.util.List;

import com.nosliw.data.core.runtime.HAPLoadResourceResponse;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceDependent;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceInfo;
import com.nosliw.data.core.runtime.HAPResourceManager;
import com.nosliw.uiresource.HAPUIResource;
import com.nosliw.uiresource.HAPUIResourceManager;

public class HAPResourceManagerUIResource implements HAPResourceManager{

	private HAPUIResourceManager m_uiResourceMan;
	
	public HAPResourceManagerUIResource(HAPUIResourceManager uiResourceMan){
		this.m_uiResourceMan = uiResourceMan;
	}
	
	@Override
	public HAPLoadResourceResponse getResources(List<HAPResourceId> resourcesId) {
		HAPLoadResourceResponse out = new HAPLoadResourceResponse();
		for(HAPResourceId resourceId : resourcesId){
			HAPResource resource = this.getResource(resourceId);
			if(resource!=null)  out.addLoadedResource(resource);
			else out.addFaildResourceId(resourceId);
		}
		return out;
	}

	@Override
	public HAPResource getResource(HAPResourceId resourceId) {
		HAPResourceIdUIResource uiResourceId = new HAPResourceIdUIResource(resourceId); 
		HAPUIResource uiResource = this.m_uiResourceMan.getUIResource(uiResourceId.getId());
		if(uiResource==null)  return null;
		HAPResourceDataUIResource resourceData = new HAPResourceDataUIResource(uiResource);
		return new HAPResource(resourceId, resourceData, null);
	}

	@Override
	public HAPResourceInfo discoverResource(HAPResourceId resourceId) {
		HAPResourceDataUIResource resourceData = (HAPResourceDataUIResource)this.getResource(resourceId).getResourceData();
		HAPResourceInfo out = new HAPResourceInfo(resourceId);
		
		HAPUIResource uiResource = resourceData.getUIResource();
		List<HAPResourceId> dependencyResourceIds = HAPResourceUtility.discoverUIResource(uiResource);
		for(HAPResourceId dependencyResourceId : dependencyResourceIds){
			out.addDependency(new HAPResourceDependent(dependencyResourceId));
		}
		
		return out;
	}

}
