package com.nosliw.uiresource.module;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImpWrapper;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.data.core.process.HAPDefinitionProcess;
import com.nosliw.data.core.runtime.HAPExecutable;
import com.nosliw.data.core.runtime.HAPResourceData;
import com.nosliw.data.core.runtime.HAPResourceDependent;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPDefinitionModuleUIEventHander extends HAPEntityInfoWritableImp implements HAPExecutable{

	@HAPAttribute
	public static String PROCESS = "process";
	
	private HAPDefinitionProcess m_process;

	public HAPDefinitionProcess getProcess() {   return this.m_process;   }

	public void setProcess(HAPDefinitionProcess process) {   this.m_process = process;  }
	
	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HAPResourceDependent> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
