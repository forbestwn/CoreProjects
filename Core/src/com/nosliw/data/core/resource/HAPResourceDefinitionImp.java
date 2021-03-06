package com.nosliw.data.core.resource;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.component.HAPLocalReferenceBase;

public abstract class HAPResourceDefinitionImp extends HAPEntityInfoImp implements HAPResourceDefinition{

	@HAPAttribute
	public static String RESOURCEID = "resourceId";

	private HAPResourceId m_resourceId;
	
	private HAPLocalReferenceBase m_localReferenceBase;
	
	@Override
	public String getEntityOrReferenceType() {   return HAPConstantShared.ENTITY;    }

	@Override
	public void setResourceId(HAPResourceId resourceId) {   this.m_resourceId = resourceId;  }

	@Override
	public HAPResourceId getResourceId() {   return this.m_resourceId; }

	@Override
	public HAPResourceDefinitionOrId getChild(String path) {   return null;    }
	
	//path base for local resource reference
	@Override
	public HAPLocalReferenceBase getLocalReferenceBase() {   return this.m_localReferenceBase;	}

	@Override
	public void setLocalReferenceBase(HAPLocalReferenceBase localRefBase) {   this.m_localReferenceBase = localRefBase;  }

	@Override
	public void cloneToResourceDefinition(HAPResourceDefinition resourceDef) {
		resourceDef.setResourceId(this.getResourceId());
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject objJson = (JSONObject)json;
		super.buildObjectByJson(objJson);
		this.m_resourceId = HAPFactoryResourceId.newInstance(objJson.opt(RESOURCEID));
		return true;  
	}
	
	@Override
	public void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(RESOURCEID, HAPJsonUtility.buildJson(this.m_resourceId, HAPSerializationFormat.JSON));
	}
}
