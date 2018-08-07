package com.nosliw.data.context;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;


/**
 * Store information for node context
 * For now, the information is data type criteria for the node 
 */
@HAPEntityWithAttribute
public class HAPContextNodeCriteria implements HAPSerializable{
	
	private HAPDataTypeCriteria m_criteria;

	public HAPContextNodeCriteria(HAPDataTypeCriteria criteria){
		this.m_criteria = criteria;
	}

	public HAPDataTypeCriteria getValue(){
		return this.m_criteria;
	}

	@Override
	public String toStringValue(HAPSerializationFormat format) {
		return HAPSerializeManager.getInstance().toStringValue(m_criteria, HAPSerializationFormat.LITERATE);
	}

	@Override
	public boolean buildObject(Object value, HAPSerializationFormat format) {
		return false;
	}
	
}