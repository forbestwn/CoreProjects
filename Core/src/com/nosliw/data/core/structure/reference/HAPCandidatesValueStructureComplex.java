package com.nosliw.data.core.structure.reference;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

//a group of value structure complex for element reference
public class HAPCandidatesValueStructureComplex {

	private Map<String, String> m_valueStructureComplexByName;

	public HAPCandidatesValueStructureComplex() {
		this.m_valueStructureComplexByName = new LinkedHashMap<String, String>();
	}
	
	public HAPCandidatesValueStructureComplex(String selfId, String defaultId) {
		this();
		this.addSelf(selfId);
		this.addDefault(defaultId);
	}
	
	public void addSelf(String valueStructureComplexId) {   this.m_valueStructureComplexByName.put(HAPConstantShared.DATAASSOCIATION_RELATEDENTITY_SELF, valueStructureComplexId);    }

	public void addDefault(String valueStructureComplexId) {   this.m_valueStructureComplexByName.put(HAPConstantShared.DATAASSOCIATION_RELATEDENTITY_DEFAULT, valueStructureComplexId);    }

	public void addValueStructureComplex(String valueStructureComplexName, String valueStructureComplexId) {    this.m_valueStructureComplexByName.put(valueStructureComplexName, valueStructureComplexId);     }

	public String getValueStructureComplex(String name) {   return this.m_valueStructureComplexByName.get(name);    }

}
