package com.nosliw.data.core.service.resource;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.resource.HAPResourceIdSimple;
import com.nosliw.data.core.service.provide.HAPIdServcieDefinition;

public class HAPResourceIdServiceDefinition  extends HAPResourceIdSimple{

	private HAPIdServcieDefinition m_serviceDefinitionId; 
	
	public HAPResourceIdServiceDefinition(){	super(HAPConstantShared.RUNTIME_RESOURCE_TYPE_SERVICE);	}

	public HAPResourceIdServiceDefinition(HAPResourceIdSimple resourceId){
		this();
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdServiceDefinition(String idLiterate) {
		this();
		init(idLiterate, null);
	}

	public HAPResourceIdServiceDefinition(HAPIdServcieDefinition uiTagId){
		this();
		init(null, null);
		this.m_serviceDefinitionId = uiTagId;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(uiTagId, HAPSerializationFormat.LITERATE); 
	}

	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_serviceDefinitionId = new HAPIdServcieDefinition(id);
	}

	public HAPIdServcieDefinition getServiceDefinitionId(){  return this.m_serviceDefinitionId;	}
	
	@Override
	public HAPResourceIdServiceDefinition clone(){
		HAPResourceIdServiceDefinition out = new HAPResourceIdServiceDefinition();
		out.cloneFrom(this);
		return out;
	}

}
