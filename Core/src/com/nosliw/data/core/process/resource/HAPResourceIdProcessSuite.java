package com.nosliw.data.core.process.resource;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.resource.HAPResourceIdSimple;
import com.nosliw.data.core.resource.HAPSupplementResourceId;

public class HAPResourceIdProcessSuite  extends HAPResourceIdSimple{

	private HAPProcessSuiteId m_processSuiteId; 
	
	public HAPResourceIdProcessSuite(){  super(HAPConstantShared.RUNTIME_RESOURCE_TYPE_PROCESSSUITE);  }

	public HAPResourceIdProcessSuite(HAPResourceIdSimple resourceId){
		this();
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdProcessSuite(String idLiterate) {
		this();
		init(idLiterate, null);
	}

	public HAPResourceIdProcessSuite(HAPProcessSuiteId processSuiteId){
		this();
		init(null, null);
		this.m_processSuiteId = processSuiteId;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(processSuiteId, HAPSerializationFormat.LITERATE); 
	}

	public HAPResourceIdProcessSuite(String id, HAPSupplementResourceId supplement){
		this();
		init(id, supplement);
	}
	
	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_processSuiteId = new HAPProcessSuiteId(id);
	}

	public HAPProcessSuiteId getProcessSuiteId(){  return this.m_processSuiteId;	}
	
	@Override
	public HAPResourceIdProcessSuite clone(){
		HAPResourceIdProcessSuite out = new HAPResourceIdProcessSuite();
		out.cloneFrom(this);
		return out;
	}
}
