package com.nosliw.uiresource.module;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImpWrapper;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.dataassociation.HAPExecutableWrapperTask;
import com.nosliw.data.core.process1.HAPExecutableProcess;
import com.nosliw.data.core.resource.HAPResourceData;
import com.nosliw.data.core.resource.HAPResourceDependency;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.HAPExecutable;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPResourceDataFactory;
import com.nosliw.uiresource.common.HAPInfoDecoration;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitPage;

@HAPEntityWithAttribute
public class HAPExecutableModuleUI extends HAPEntityInfoImpWrapper implements HAPExecutable{

	@HAPAttribute
	public static String ID = "id";

	@HAPAttribute
	public static String PAGE = "page";

	@HAPAttribute
	public static String UIDECORATION = "uiDecoration";

	@HAPAttribute
	public static String INPUTMAPPING = "inputMapping";

	@HAPAttribute
	public static String OUTPUTMAPPING = "outputMapping";

	@HAPAttribute
	public static String EVENTHANDLER = "eventHandler";

	@HAPAttribute
	public static String PAGENAME = "pageName";
	
	private HAPDefinitionModuleUI m_moduleUIDefinition;
	
	private String m_id;

	private HAPExecutableUIUnitPage m_page;
	
	private List<HAPInfoDecoration> m_uiDecoration;
	
	// hook up with real data during runtime
	private HAPExecutableDataAssociation m_inputMapping;
	private HAPExecutableDataAssociation m_outputMapping;
	
	private Map<String, HAPExecutableWrapperTask<HAPExecutableProcess>> m_eventHandlers;
	
	public HAPExecutableModuleUI(HAPDefinitionModuleUI moduleUIDefinition, String id) {
		super(moduleUIDefinition);
		this.m_uiDecoration = new ArrayList<HAPInfoDecoration>();
		this.m_eventHandlers = new LinkedHashMap<String, HAPExecutableWrapperTask<HAPExecutableProcess>>();
		this.m_moduleUIDefinition = moduleUIDefinition;
		this.m_id = id;
	}

	public void addEventHandler(String eventName, HAPExecutableWrapperTask<HAPExecutableProcess> eventHander) {   this.m_eventHandlers.put(eventName, eventHander);   }
	
	public void setInputMapping(HAPExecutableDataAssociation contextMapping) {   this.m_inputMapping = contextMapping;	}
	public HAPExecutableDataAssociation getInputMapping() {   return this.m_inputMapping;   }

	public void setOutputMapping(HAPExecutableDataAssociation contextMapping) {   this.m_outputMapping = contextMapping;	}
	public HAPExecutableDataAssociation getOutputMapping() {   return this.m_outputMapping;   }

	public void setPage(HAPExecutableUIUnitPage page) {  this.m_page = page;   }
	public HAPExecutableUIUnitPage getPage() {  return this.m_page;   }
	
	public void addUIDecoration(List<HAPInfoDecoration> dec) {   this.m_uiDecoration.addAll(dec);   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ID, this.m_id);
		jsonMap.put(PAGE, HAPJsonUtility.buildJson(this.m_page, HAPSerializationFormat.JSON));
		jsonMap.put(UIDECORATION, HAPJsonUtility.buildJson(this.m_uiDecoration, HAPSerializationFormat.JSON));
		jsonMap.put(INPUTMAPPING, HAPJsonUtility.buildJson(this.m_inputMapping, HAPSerializationFormat.JSON));
		jsonMap.put(OUTPUTMAPPING, HAPJsonUtility.buildJson(this.m_outputMapping, HAPSerializationFormat.JSON));
		jsonMap.put(EVENTHANDLER, HAPJsonUtility.buildJson(this.m_eventHandlers, HAPSerializationFormat.JSON));
		jsonMap.put(PAGENAME, this.m_moduleUIDefinition.getPage());
	}
	
	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
		this.buildFullJsonMap(jsonMap, typeJsonMap);

		jsonMap.put(PAGE, this.m_page.toResourceData(runtimeInfo).toString());
		
		Map<String, String> eventJsonMap = new LinkedHashMap<String, String>();
		for(String eventName :this.m_eventHandlers.keySet()) {	eventJsonMap.put(eventName, this.m_eventHandlers.get(eventName).toResourceData(runtimeInfo).toString());	}
		jsonMap.put(EVENTHANDLER, HAPJsonUtility.buildMapJson(eventJsonMap));

		jsonMap.put(INPUTMAPPING, this.m_inputMapping.toResourceData(runtimeInfo).toString());
		jsonMap.put(OUTPUTMAPPING, this.m_outputMapping.toResourceData(runtimeInfo).toString());
		
		return HAPResourceDataFactory.createJSValueResourceData(HAPJsonUtility.buildMapJson(jsonMap, typeJsonMap));
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo, HAPResourceManagerRoot resourceManager) {
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		out.addAll(this.m_page.getResourceDependency(runtimeInfo, resourceManager));
		out.addAll(this.m_inputMapping.getResourceDependency(runtimeInfo, resourceManager));
		out.addAll(this.m_outputMapping.getResourceDependency(runtimeInfo, resourceManager));
		for(HAPExecutableWrapperTask eventHandler : this.m_eventHandlers.values()) {	out.addAll(eventHandler.getResourceDependency(runtimeInfo, resourceManager));	}
		return out;
	}
}
