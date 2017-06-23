package com.nosliw.data.core.runtime;

import java.util.List;

public abstract class HAPLoadResourcesTask extends HAPRuntimeTask{

	private List<HAPResourceInfo> m_resourcesInfo;
	
	public HAPLoadResourcesTask(List<HAPResourceInfo> resourcesInfo){
		this.m_resourcesInfo = resourcesInfo;
	}
	
	public List<HAPResourceInfo> getResourcesInfo(){		return this.m_resourcesInfo;	}
	
	@Override
	public String getTaskType(){  return "LoadResources"; }

}