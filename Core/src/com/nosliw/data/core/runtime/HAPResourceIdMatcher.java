package com.nosliw.data.core.runtime;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.expression.HAPMatcher;

public class HAPResourceIdMatcher extends HAPResourceId{

	private HAPMatcher m_matcher;
	
	public HAPResourceIdMatcher(){}

	public HAPResourceIdMatcher(HAPResourceId resourceId){
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdMatcher(String idLiterate) {
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_CONVERTER, idLiterate);
	}

	public HAPResourceIdMatcher(HAPMatcher matcher){
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_CONVERTER, null);
		this.setMatcher(matcher);
	}
	
	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_matcher = new HAPMatcher(id);
	}

	public HAPMatcher getMatcher(){  return this.m_matcher;	}
	protected void setMatcher(HAPMatcher matcher){
		this.m_matcher = matcher;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(this.m_matcher, HAPSerializationFormat.LITERATE); 
	}

	public HAPResourceIdMatcher clone(){
		HAPResourceIdMatcher out = new HAPResourceIdMatcher();
		out.cloneFrom(this);
		return out;
	}
}