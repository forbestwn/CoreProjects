package com.nosliw.data.core.runtime.js;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPDataTypeConverter;
import com.nosliw.data.core.runtime.HAPResourceId;

public class HAPResourceIdConverter extends HAPResourceId{

	private HAPDataTypeConverter m_dataTypeConverter;
	
	public HAPResourceIdConverter(){}

	public HAPResourceIdConverter(HAPResourceId resourceId){
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdConverter(String idLiterate, String alias) {
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_CONVERTER, idLiterate, alias);
	}

	public HAPResourceIdConverter(HAPDataTypeConverter dataTypeConverter, String alias){
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_CONVERTER, null, alias);
		this.setConverter(dataTypeConverter);
	}
	
	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_dataTypeConverter = new HAPDataTypeConverter(id);
	}

	public HAPDataTypeConverter getConverter(){  return this.m_dataTypeConverter;	}
	protected void setConverter(HAPDataTypeConverter converter){
		this.m_dataTypeConverter = converter;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(this.m_dataTypeConverter, HAPSerializationFormat.LITERATE); 
	}

	public HAPResourceIdConverter clone(){
		HAPResourceIdConverter out = new HAPResourceIdConverter();
		out.cloneFrom(this);
		return out;
	}

}
