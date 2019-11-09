package com.nosliw.data.core.process.activity;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.data.core.process.HAPDefinitionActivityNormal;
import com.nosliw.data.core.script.context.dataassociation.mirror.HAPDefinitionDataAssociationMirror;

public class HAPDebugActivityDefinition extends HAPDefinitionActivityNormal{

	public HAPDebugActivityDefinition(String type) {
		super(type);
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.setInputMapping(new HAPDefinitionDataAssociationMirror());
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
}
