package com.nosliw.data.core.expression;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.complex.valuestructure.HAPComplexValueStructure;
import com.nosliw.data.core.complex.valuestructure.HAPUtilityComplexValueStructure;
import com.nosliw.data.core.valuestructure.HAPValueStructure;

//normal expression group
public class HAPDefinitionExpressionGroupImp extends HAPEntityInfoImp implements HAPDefinitionExpressionGroup{

	private Map<String, HAPDefinitionExpression> m_elements;
	
	private HAPComplexValueStructure m_valueStructureComplex;
	
	private Map<String, HAPDefinitionConstant> m_constantDefinitions;
	
	public HAPDefinitionExpressionGroupImp() {
		this.m_elements = new LinkedHashMap<String, HAPDefinitionExpression>();
		this.m_constantDefinitions = new LinkedHashMap<String, HAPDefinitionConstant>();
	}
	
	public void addExpression(HAPDefinitionExpression element) {
		this.addEntityElement(element);
	}

	@Override
	public HAPComplexValueStructure getValueStructureComplex() {  return this.m_valueStructureComplex;  }

	public void setValueStructure(HAPValueStructure valueStructure) {	HAPUtilityComplexValueStructure.setValueStructureDefault(this, valueStructure); }

	@Override
	public Set<HAPDefinitionExpression> getEntityElements() {  return new HashSet<HAPDefinitionExpression>(this.m_elements.values()); }

	@Override
	public HAPDefinitionExpression getEntityElement(String id) {  return this.m_elements.get(id);  }

	@Override
	public void addEntityElement(HAPDefinitionExpression expression) {  
		HAPUtilityEntityInfo.processEntityId(expression);
		this.m_elements.put(expression.getId(), expression);  
	}

	@Override
	public Set<HAPDefinitionConstant> getConstantDefinitions() {  return new HashSet(this.m_constantDefinitions.values());  }  

	@Override
	public HAPDefinitionConstant getConstantDefinition(String id) {  return this.m_constantDefinitions.get(id);  }

	@Override
	public void addConstantDefinition(HAPDefinitionConstant constantDef) {  this.m_constantDefinitions.put(constantDef.getId(), constantDef);  }

	@Override
	public HAPDefinitionExpressionGroup cloneExpressionGroupDefinition() {
		HAPDefinitionExpressionGroupImp out = new HAPDefinitionExpressionGroupImp();
		out.m_valueStructureComplex = this.m_valueStructureComplex.cloneValueStructureComplex();
		for(String id : this.m_elements.keySet()) {
			out.m_elements.put(id, this.m_elements.get(id).cloneDefinitionExpression());
		}
		for(String id : this.m_constantDefinitions.keySet()) {
			out.m_constantDefinitions.put(id, this.m_constantDefinitions.get(id).cloneConstantDefinition());
		}
		return out;
	}

	@Override
	public String getValueStructureTypeIfNotDefined() {  return HAPConstantShared.STRUCTURE_TYPE_VALUEFLAT;  }
}
