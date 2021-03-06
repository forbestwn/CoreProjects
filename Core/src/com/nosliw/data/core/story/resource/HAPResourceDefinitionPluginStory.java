package com.nosliw.data.core.story.resource;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.resource.HAPPluginResourceDefinition;
import com.nosliw.data.core.resource.HAPResourceDefinition;
import com.nosliw.data.core.resource.HAPResourceIdSimple;
import com.nosliw.data.core.system.HAPSystemFolderUtility;

public class HAPResourceDefinitionPluginStory implements HAPPluginResourceDefinition{

	public HAPResourceDefinitionPluginStory() {
	}
	
	@Override
	public HAPResourceDefinition getResourceDefinitionBySimpleResourceId(HAPResourceIdSimple resourceId) {
		//read content
		String file = HAPSystemFolderUtility.getStoryFolder()+resourceId.getId()+".story";
		//parse content
		HAPResourceDefinitionStory out = HAPParserStoryResource.parseFile(file);
		return out;
	}

	@Override
	public String getResourceType() {		return HAPConstantShared.RUNTIME_RESOURCE_TYPE_STORY;	}

	@Override
	public HAPResourceDefinition parseResourceDefinition(Object content) {
		JSONObject jsonObj = (JSONObject)content;
		return HAPParserStoryResource.parseStoryDefinition(jsonObj);
	}

}
