package com.nosliw.data.core.event;

import java.util.Map;

import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPRuntimeProcess;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableWrapperTask;

public class HAPExecutableEventHandler {

	private HAPRuntimeProcess m_processRuntime;
	
	private HAPDefinitionEventHandle m_eventHandlerDefinition;
	
	private HAPExecutableWrapperTask<HAPExecutableProcess> m_process;

	public HAPExecutableEventHandler(HAPDefinitionEventHandle definition) {
		this.m_eventHandlerDefinition = definition;
	}
	
	public HAPExecutableWrapperTask<HAPExecutableProcess> getProcess() {    return this.m_process;    }
	public void setProcess(HAPExecutableWrapperTask<HAPExecutableProcess> process) {   this.m_process = process;   }
	
	public HAPPollTaskResult execute(Map<String, HAPData> input) {
//		HAPServiceData serviceData = this.m_processRuntime.executeProcess(m_process, input);
		return null;
	}
}