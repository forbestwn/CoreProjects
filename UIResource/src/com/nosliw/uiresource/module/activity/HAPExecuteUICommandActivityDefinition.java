package com.nosliw.uiresource.module.activity;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.data.core.process.HAPDefinitionActivityNormal;

public class HAPExecuteUICommandActivityDefinition extends HAPDefinitionActivityNormal{

	@HAPAttribute
	public static String UI = "ui";

	@HAPAttribute
	public static String COMMAND = "command";

	private String m_ui;
	
	private String m_command;
	
	public HAPExecuteUICommandActivityDefinition(String type) {
		super(type);
	}

	public String getUI() {    return this.m_ui;  }
	
	public String getCommand() {   return this.m_command;  }
	public void setCommand(String command) {   this.m_command = command;   }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.setCommand(jsonObj.optString(COMMAND));
		this.m_ui = (String)jsonObj.opt(UI);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(UI, this.m_ui);
		jsonMap.put(COMMAND, this.m_command);
	}

}
