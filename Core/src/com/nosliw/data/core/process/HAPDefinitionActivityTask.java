package com.nosliw.data.core.process;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.data.core.structure.dataassociation.HAPDefinitionWrapperTask;
import com.nosliw.data.core.structure.value.HAPContextStructureValueDefinition;

public abstract class HAPDefinitionActivityTask extends HAPDefinitionActivityNormal{

	private HAPDefinitionWrapperTask m_taskMapping;

	public HAPDefinitionActivityTask(String type) {
		super(type);
		this.m_taskMapping = new HAPDefinitionWrapperTask();
	}

	public HAPDefinitionWrapperTask getTaskMapping() {   return this.m_taskMapping;  }
	
	@Override
	public HAPContextStructureValueDefinition getInputContextStructure(HAPContextStructureValueDefinition parentContextStructure) {  return parentContextStructure;   }

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.m_taskMapping = HAPUtilityProcess.parseTaskDefinition(this, jsonObj);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
	
	protected void cloneToTaskActivityDefinition(HAPDefinitionActivityTask activity) {
		if(this.m_taskMapping!=null)   activity.m_taskMapping = this.m_taskMapping.clone();
		this.cloneToNormalActivityDefinition(activity);
	}
	
}
