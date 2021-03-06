package com.nosliw.data.core.activity;

import org.json.JSONObject;

import com.nosliw.data.core.component.HAPDefinitionEntityComplex;

public class HAPParserActivity {

	public static HAPDefinitionActivity parseActivityDefinition(JSONObject activityObjJson, HAPDefinitionEntityComplex complexEntity, HAPManagerActivityPlugin activityPluginMan) {
		String activityType = activityObjJson.getString(HAPDefinitionActivity.TYPE);
		return activityPluginMan.getPlugin(activityType).buildActivityDefinition(activityObjJson, complexEntity);
	}
	
}
