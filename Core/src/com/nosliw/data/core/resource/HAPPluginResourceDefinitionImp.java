package com.nosliw.data.core.resource;

import java.io.File;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.component.HAPLocalReferenceBase;

public class HAPPluginResourceDefinitionImp implements HAPPluginResourceDefinition{

	private HAPParserResourceDefinition m_parser;
	
	private String m_resourceType;
	
	public HAPPluginResourceDefinitionImp(String resourceType, HAPParserResourceDefinition parser) {
		this.m_resourceType = resourceType;
		this.m_parser = parser;
	}
	
	@Override
	public String getResourceType() {		return this.m_resourceType;	}

	@Override
	public HAPResourceDefinition getResourceDefinitionBySimpleResourceId(HAPResourceIdSimple resourceId) {
		//get location information
		HAPInfoResourceLocation resourceLocInfo = HAPUtilityResourceId.getResourceLocationInfo(resourceId);
		//parse file
		HAPResourceDefinition moduleDef = this.parseResourceDefinition(resourceLocInfo.getFiile()); 
		//set local base path
		moduleDef.setLocalReferenceBase(new HAPLocalReferenceBase(resourceLocInfo.getBasePath()));
		return moduleDef;
	}

	@Override
	public HAPResourceDefinition getResourceDefinitionByLocalResourceId(HAPResourceIdLocal localResourceId, HAPResourceDefinition relatedResource) {
		HAPResourceDefinition out = null;
		String path = relatedResource.getLocalReferenceBase().getPath() + localResourceId.getType() + "/" + localResourceId.getName() + ".res";
		out = this.parseResourceDefinition(HAPFileUtility.readFile(path));
		out.setLocalReferenceBase(relatedResource.getLocalReferenceBase());
//		if(out instanceof HAPWithAttachment) {
//			((HAPWithAttachment)out).setLocalReferenceBase(localResourceId.getBasePath());
//		}
		return out;
	}

	@Override
	public HAPResourceDefinition parseResourceDefinition(Object obj) {
		HAPResourceDefinition out = null;
		if(obj instanceof JSONObject) {
			out = this.m_parser.parseJson((JSONObject)obj);
		}
		else if(obj instanceof File) {
			out = m_parser.parseFile((File)obj);
		}
		else if(obj instanceof String) {
			out = m_parser.parseContent((String)obj);
		}
		return out;
	}

}
