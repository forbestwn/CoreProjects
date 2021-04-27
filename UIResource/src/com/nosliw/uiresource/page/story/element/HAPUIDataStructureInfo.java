package com.nosliw.uiresource.page.story.element;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.structure.value.HAPContextStructureValueDefinitionGroup;

@HAPEntityWithAttribute
public class HAPUIDataStructureInfo extends HAPSerializableImp{

	@HAPAttribute
	public static final String CONTEXT = "context";

	private HAPContextStructureValueDefinitionGroup m_context;
	
	public HAPUIDataStructureInfo() {
		this.m_context = new HAPContextStructureValueDefinitionGroup();
	}
	
	public HAPContextStructureValueDefinitionGroup getContext() {	return this.m_context;	}
	
	public void setContext(HAPContextStructureValueDefinitionGroup context) {   this.m_context = context;      }

	public HAPUIDataStructureInfo cloneUIDataStructureInfo() {
		HAPUIDataStructureInfo out = new HAPUIDataStructureInfo();
		if(this.m_context!=null) {
			out.m_context = this.m_context.cloneContextGroup();
		}
		return out;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		JSONObject contextObj = jsonObj.optJSONObject(CONTEXT);
		if(contextObj!=null) {
			this.m_context = new HAPContextStructureValueDefinitionGroup();
			this.m_context.buildObject(contextObj, HAPSerializationFormat.JSON);
		}
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONTEXT, HAPJsonUtility.buildJson(this.m_context, HAPSerializationFormat.JSON));
	}
}
