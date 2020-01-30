package com.nosliw.uiresource.resource;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPUIAppId extends HAPSerializableImp{

	private String m_id;
	
	public HAPUIAppId(String id){
		this.m_id = id;
	}
	
	public String getId(){  return this.m_id;  }

	@Override
	protected String buildLiterate(){
		return this.m_id;
	}

	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		this.m_id = literateValue;
		return true;
	}
}
