package com.nosliw.uiresource.module;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.process.HAPDefinitionProcess;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionWrapperTask;

@HAPEntityWithAttribute
public class HAPDefinitionModuleUIEventHander extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static String PROCESS = "process";
	
	private HAPDefinitionWrapperTask<HAPDefinitionProcess> m_process;

	public HAPDefinitionWrapperTask<HAPDefinitionProcess> getProcess() {   return this.m_process;   }

	public void setProcess(HAPDefinitionWrapperTask<HAPDefinitionProcess> process) {   this.m_process = process;  }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PROCESS, HAPJsonUtility.buildJson(this.m_process, HAPSerializationFormat.JSON));
	}

}
