package com.nosliw.data.core.criteria;

import com.nosliw.data.core.HAPDataTypeId;

public abstract class HAPDataTypeCriteriaImp implements HAPDataTypeCriteria{

	@Override
	public boolean validate(HAPDataTypeCriteria criteria) {
		return this.getValidDataTypeId().containsAll(criteria.getValidDataTypeId());
	}

	@Override
	public boolean validate(HAPDataTypeId dataTypeId) {
		return this.getValidDataTypeId().contains(dataTypeId);
	}
}
