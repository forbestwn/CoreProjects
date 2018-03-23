package com.nosliw.data.core.datasource.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.HAPDataTypeConverter;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.datasource.HAPDefinition;
import com.nosliw.data.core.expression.HAPExpressionManager;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.operand.HAPOperand;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.operand.HAPOperandWrapper;
import com.nosliw.data.core.runtime.HAPExecuteExpression;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceIdConverter;
import com.nosliw.data.core.runtime.HAPResourceUtility;
import com.nosliw.data.core.task.HAPExecutableTask;
import com.nosliw.data.core.task.HAPUpdateVariable;

public class HAPExecutableTaskDataSource implements HAPExecutableTask{

	private HAPDefinition m_dataSourceDefinition;
	
	private Map<String, HAPOperandWrapper> m_parmsOperand;

	private String m_id;
	
	private Map<String, HAPMatchers> m_matchers;

	public HAPExecutableTaskDataSource(HAPDefinition dataSourceDefinition) {
		this.m_dataSourceDefinition = dataSourceDefinition;
		this.m_matchers = new LinkedHashMap<String, HAPMatchers>();
		this.m_parmsOperand = new LinkedHashMap<String, HAPOperandWrapper>();
	}
	
	public Map<String, HAPOperandWrapper> getParmsOperand(){   return this.m_parmsOperand;   }
	public void addParmOperand(String parmName, HAPOperand operand) {		this.m_parmsOperand.put(parmName, new HAPOperandWrapper(operand));	}
	
	public HAPDefinition getDataSourceDefinition() {   return this.m_dataSourceDefinition;   }
	public String getDataSource() {   return this.m_dataSourceDefinition.getName();   }
	
	public Map<String, HAPMatchers> getMatchers(){   return this.m_matchers;   }
	
	@Override
	public String getType() {	return HAPConstant.DATATASK_TYPE_DATASOURCE;	}

	@Override
	public void updateVariable(HAPUpdateVariable updateVar) {
		for(String parmName : this.m_parmsOperand.keySet()) {
			HAPOperandUtility.updateVariable(this.m_parmsOperand.get(parmName), updateVar);
		}
	}

	@Override
	public HAPDataTypeCriteria getOutput() {		return this.m_dataSourceDefinition.getOutput().getCriteria();	}

	@Override
	public void discoverVariable(Map<String, HAPVariableInfo> variablesInfo, HAPDataTypeCriteria expectOutputCriteria,
			HAPProcessContext context) {
		
		Map<String, HAPVariableInfo> varsInfo = new LinkedHashMap<String, HAPVariableInfo>();
		varsInfo.putAll(variablesInfo);

		Map<String, HAPVariableInfo> oldVarsInfo = null;
		do {
			oldVarsInfo = new LinkedHashMap<String, HAPVariableInfo>();
			oldVarsInfo.putAll(varsInfo);
			context.clear();

			for(String parmName : this.m_parmsOperand.keySet()) {
				this.m_parmsOperand.get(parmName).getOperand().discover(varsInfo, this.m_dataSourceDefinition.getParms().get(parmName).getVaraibleInfo().getCriteria(), context, HAPExpressionManager.dataTypeHelper);
				if(!context.isSuccess())  break;
			}
		}while(!HAPBasicUtility.isEqualMaps(varsInfo, oldVarsInfo) && context.isSuccess());

		variablesInfo.clear();
		variablesInfo.putAll(varsInfo);
		
		for(String parmName : this.m_parmsOperand.keySet()) {
			HAPDataTypeCriteria parmCriteria = this.m_parmsOperand.get(parmName).getOperand().getOutputCriteria();
			HAPMatchers parmMatchers = HAPExpressionManager.dataTypeHelper.buildMatchers(parmCriteria, m_dataSourceDefinition.getParms().get(parmName).getVaraibleInfo().getCriteria());
			this.m_matchers.put(parmName, parmMatchers);
		}
	}

	@Override
	public List<HAPResourceId> getResourceDependency() {
		List<HAPResourceId> out = new ArrayList<HAPResourceId>();
		//converters
		Set<HAPDataTypeConverter> converters = new HashSet<HAPDataTypeConverter>();
		for(String parm : this.m_matchers.keySet()){
			converters.addAll(HAPResourceUtility.getConverterResourceIdFromRelationship(this.m_matchers.get(parm).discoverRelationships()));
		}
		for(HAPDataTypeConverter converter : converters){
			out.add(new HAPResourceIdConverter(converter));
		}
		
		//parms
		for(String parm : this.m_parmsOperand.keySet()) {
			out.addAll(this.m_parmsOperand.get(parm).getOperand().getResources());
		}
		return out;
	}

	@Override
	public Set<String> getReferences() {
		Set<String> out = new HashSet<String>();
		for(String parm : this.m_parmsOperand.keySet()) {
			out.addAll(HAPOperandUtility.discoverReferences(this.m_parmsOperand.get(parm)));
		}
		return out;
	}

	@Override
	public Set<String> getVariables() {
		Set<String> out = new HashSet<String>();
		for(String parm : this.m_parmsOperand.keySet()) {
			out.addAll(HAPOperandUtility.discoverVariables(this.m_parmsOperand.get(parm)));
		}
		return out;
	}

	@Override
	public void setId(String id) {		this.m_id = id;	}
}