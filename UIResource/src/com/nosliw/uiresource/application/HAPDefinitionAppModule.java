package com.nosliw.uiresource.application;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionGroupDataAssociation;
import com.nosliw.uiresource.common.HAPDefinitionEventHandler;
import com.nosliw.uiresource.module.HAPDefinitionModuleUI;

@HAPEntityWithAttribute
public class HAPDefinitionAppModule  extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static final String ROLE = "role";

	@HAPAttribute
	public static final String MODULE = "module";
	
	@HAPAttribute
	public static String STATUS = "status";
	
	@HAPAttribute
	public static String EVENTHANDLER = "eventHandler";

	@HAPAttribute
	public static final String INPUTMAPPING = "inputMapping";
	
	@HAPAttribute
	public static final String OUTPUTMAPPING = "outputMapping";
	
	private String m_role;
	
	private String m_module;
	
	private String m_status;
	
	//event handlers
	private Map<String, HAPDefinitionEventHandler> m_eventHandlers;
	
	private HAPDefinitionGroupDataAssociation m_outputMapping;
	
	private HAPDefinitionGroupDataAssociation m_inputMapping;
	
	public HAPDefinitionAppModule() {
		this.m_outputMapping = new HAPDefinitionGroupDataAssociation();
		this.m_inputMapping = new HAPDefinitionGroupDataAssociation();
		this.m_eventHandlers = new LinkedHashMap<String, HAPDefinitionEventHandler>();
	}
	
	public String getRole() {   return this.m_role;   }
	public void setRole(String role) {   this.m_role = role;     }
	
	public String getModule() {   return this.m_module;   }
	public void setModule(String module) {  this.m_module = module;   }
	
	public String getStatus() {   return this.m_status;    }
	public void setStatus(String status) {   this.m_status = status;   }
	
	public HAPDefinitionGroupDataAssociation getInputMapping() {   return this.m_inputMapping;   }
	public HAPDefinitionGroupDataAssociation getOutputMapping() {   return this.m_outputMapping;    }
	
	public Map<String, HAPDefinitionEventHandler> getEventHandlers(){   return this.m_eventHandlers;   }
	public void addEventHandler(String name, HAPDefinitionEventHandler eventHandler) {  this.m_eventHandlers.put(name, eventHandler);   }
	public void addEventHandler(Map<String, HAPDefinitionEventHandler> eventHandler) {  this.m_eventHandlers.putAll(eventHandler);   }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ROLE, this.m_role);
		jsonMap.put(MODULE, this.m_module);
		jsonMap.put(INPUTMAPPING, this.m_inputMapping.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(OUTPUTMAPPING, this.m_outputMapping.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(EVENTHANDLER, HAPJsonUtility.buildJson(this.m_eventHandlers, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.m_role = (String)jsonObj.opt(ROLE);
		this.m_module = (String)jsonObj.opt(MODULE);
		this.m_status = (String)jsonObj.opt(HAPDefinitionModuleUI.STATUS);
		this.m_inputMapping.buildObject(jsonObj.optJSONArray(INPUTMAPPING), HAPSerializationFormat.JSON);
		this.m_outputMapping.buildObject(jsonObj.optJSONArray(OUTPUTMAPPING), HAPSerializationFormat.JSON);
		return true;
	}
}
