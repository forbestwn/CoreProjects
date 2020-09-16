package com.nosliw.uiresource.page.story.element;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.story.change.HAPChangeResult;

@HAPEntityWithAttribute
public class HAPStoryNodePage extends HAPStoryNodeUI{
	
	public final static String STORYNODE_TYPE = HAPConstant.STORYNODE_TYPE_PAGE; 
	
	public HAPStoryNodePage() {
		super(STORYNODE_TYPE);
	}
	
	@Override
	public HAPChangeResult patch(String path, Object value) {
		return super.patch(path, value);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

}