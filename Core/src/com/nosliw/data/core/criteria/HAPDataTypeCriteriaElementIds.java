package com.nosliw.data.core.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPDataTypeId;

public class HAPDataTypeCriteriaElementIds extends HAPDataTypeCriteriaImp{

	Set<HAPDataTypeId> m_eles;
	
	public HAPDataTypeCriteriaElementIds(Set<HAPDataTypeId> eles, HAPDataTypeCriteriaManager criteriaMan){
		super(criteriaMan);
		this.m_eles = new HashSet<HAPDataTypeId>();
		this.m_eles.addAll(eles);
	}
	
	@Override
	public String getType() {		return HAPConstant.DATATYPECRITERIA_TYPE_DATATYPEIDS;	}

	@Override
	public Set<HAPDataTypeId> getValidDataTypeId() {		return this.m_eles;	}

	public HAPDataTypeCriteriaOr toOrCriteria(){
		HAPDataTypeCriteriaOr out = new HAPDataTypeCriteriaOr(new ArrayList(this.m_eles), this.getDataTypeCriteraManager());
		return out;
	}
	
}