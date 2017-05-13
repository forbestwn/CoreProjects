package com.nosliw.data.core.expression;

import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPConverters;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;

public class HAPOperandVariable extends HAPOperandImp{

	public final static String VARIABLENAME = "variableName";
	
	protected String m_variableName;
	
	//store the expectDataTypeCriteria from last time. So that we may bypass discover if  
	private HAPDataTypeCriteria m_expectDataTypeCriteria;
	
//	protected HAPDataTypeCriteria m_dataTypeCriteria;

	public HAPOperandVariable(String name){
		super(HAPConstant.EXPRESSION_OPERAND_VARIABLE);
		this.m_variableName = name;
	}
	
	public String getVariableName(){  return this.m_variableName;  }
	public void setVariableName(String name){   this.m_variableName = name;  }
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildFullJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, m_variableName);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, m_variableName);
	}

	@Override
	public HAPConverters discover(
			Map<String, HAPVariableInfo> variablesInfo,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessVariablesContext context,
			HAPDataTypeHelper dataTypeHelper) {
		HAPVariableInfo variableInfo = variablesInfo.get(this.getVariableName());
		if(variableInfo==null){
			//found a new variable
			variableInfo = new HAPVariableInfo();
			variablesInfo.put(m_variableName, variableInfo);
		}
		
		if(variableInfo.getStatus().equals(HAPConstant.EXPRESSION_VARIABLE_STATUS_OPEN)){
			//if variable info is open, calculate new criteria for this variable
			if(expectCriteria==null){
				
			}
			else if(variableInfo.getCriteria()==null){
				variableInfo.setCriteria(expectCriteria);
			}
			else{
				HAPDataTypeCriteria adjustedCriteria = dataTypeHelper.and(dataTypeHelper.looseCriteria(variableInfo.getCriteria()), dataTypeHelper.looseCriteria(expectCriteria));
				if(adjustedCriteria==null){
					context.addMessage("error");
					return null;
				}
				else{
					variableInfo.setCriteria(adjustedCriteria);
				}
			}
		}
		
		//set output criteria
		this.setDataTypeCriteria(variableInfo.getCriteria());
		//cal converter
		return this.isConvertable(variableInfo.getCriteria(), expectCriteria, context, dataTypeHelper);
	}

	@Override
	public HAPDataTypeCriteria normalize(Map<String, HAPDataTypeCriteria> variablesInfo, HAPDataTypeHelper dataTypeHelper){
		this.setDataTypeCriteria(variablesInfo.get(this.getVariableName()));
		return this.getDataTypeCriteria();
	}
}
