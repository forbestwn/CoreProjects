package com.nosliw.data.core.expression;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.common.HAPWithConstantDefinition;
import com.nosliw.data.core.common.HAPWithDataContext;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextStructure;

public class HAPDefinitionExpressionSuiteImp implements HAPDefinitionExpressionSuite, HAPWithDataContext, HAPWithConstantDefinition{

	private Map<String, HAPDefinitionExpressionGroup> m_expressionGroups;

	private HAPContext m_context;
	
	private Map<String, HAPDefinitionConstant> m_constantDefinitions;
	
	public HAPDefinitionExpressionSuiteImp() {
		this.m_expressionGroups = new LinkedHashMap<String, HAPDefinitionExpressionGroup>();
		this.m_constantDefinitions = new LinkedHashMap<String, HAPDefinitionConstant>();
	}
	
	@Override
	public Set<HAPDefinitionExpressionGroup> getEntityElements() {  return new HashSet<HAPDefinitionExpressionGroup>(this.m_expressionGroups.values()); }

	@Override
	public HAPDefinitionExpressionGroup getEntityElement(String id) {  return this.m_expressionGroups.get(id);  }

	@Override
	public void addEntityElement(HAPDefinitionExpressionGroup expressionGroup) {
		expressionGroup.setContextStructure(this.m_context.cloneContextStructure());
		for(String id : this.m_constantDefinitions.keySet()) {
			expressionGroup.addConstantDefinition(this.m_constantDefinitions.get(id).cloneConstantDefinition());
		}
		this.m_expressionGroups.put(expressionGroup.getId(), expressionGroup); 
	}

	@Override
	public Set<HAPDefinitionConstant> getConstantDefinitions() {  return new HashSet<HAPDefinitionConstant>(this.m_constantDefinitions.values()); }

	@Override
	public HAPDefinitionConstant getConstantDefinition(String id) {    return this.m_constantDefinitions.get(id);  }

	@Override
	public void addConstantDefinition(HAPDefinitionConstant constantDef) {  
		this.m_constantDefinitions.put(constantDef.getId(), constantDef);
		for(String id : this.m_expressionGroups.keySet()) {
			this.m_expressionGroups.get(id).addConstantDefinition(constantDef);
		}
	}

	@Override
	public HAPContextStructure getContextStructure() {  return this.m_context; }

	@Override
	public void setContextStructure(HAPContextStructure context) {  
		this.m_context = (HAPContext)context;  
		for(String id : this.m_expressionGroups.keySet()) {
			this.m_expressionGroups.get(id).setContextStructure(context.cloneContextStructure());
		}
	}
	
	@Override
	public HAPDefinitionExpressionSuite cloneExpressionSuiteDefinition() {
		HAPDefinitionExpressionSuiteImp out = new HAPDefinitionExpressionSuiteImp();
		if(this.m_context!=null)	out.m_context = this.m_context.cloneContext();
		for(String id : this.m_expressionGroups.keySet()) {
			out.m_expressionGroups.put(id, this.m_expressionGroups.get(id).cloneExpressionGroupDefinition());
		}
		for(String id : this.m_constantDefinitions.keySet()) {
			out.m_constantDefinitions.put(id, this.m_constantDefinitions.get(id).cloneConstantDefinition());
		}
		return out;
	}

	@Override
	public void cloneToDataContext(HAPWithDataContext dataContext) {
		dataContext.setContextStructure(this.m_context);
	}
}