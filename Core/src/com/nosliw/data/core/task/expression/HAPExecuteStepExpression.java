package com.nosliw.data.core.task.expression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.expression.HAPProcessExpressionDefinitionContext;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.operand.HAPOperandWrapper;
import com.nosliw.data.core.task.HAPProcessTaskContext;
import com.nosliw.data.core.task.HAPUpdateVariable;

public class HAPExecuteStepExpression extends HAPExecuteStep{

	// original expression definition
	private HAPDefinitionStepExpression m_expressionDefinition;

	//Operand to represent the expression
	private HAPOperandWrapper m_operand;
	
	
	public HAPExecuteStepExpression(HAPDefinitionStepExpression stepDef) {
		this.m_expressionDefinition = stepDef.clone();
	}
	
	public HAPOperandWrapper getOperand() {	return this.m_operand;	}


	@Override
	public HAPMatchers discover(
			Map<String, HAPVariableInfo> variablesInfo, 
			Map<String, HAPVariableInfo> localVariablesInfo, 
			HAPDataTypeCriteria expectOutputCriteria, 
			HAPProcessTaskContext context,
			HAPDataTypeHelper dataTypeHelper){
		
		HAPMatchers matchers = null;
		//store all variables info including global and local, it is used for operand discovery
		Map<String, HAPVariableInfo> varsInfo = new LinkedHashMap<String, HAPVariableInfo>();
		//only local variable
		Map<String, HAPVariableInfo> localVarsInfo = new LinkedHashMap<String, HAPVariableInfo>();
		varsInfo.putAll(variablesInfo);
		varsInfo.putAll(localVariablesInfo);
		localVarsInfo.putAll(localVariablesInfo);

		//Do discovery until variables definition not change or fail 
		Map<String, HAPVariableInfo> oldVarsInfo;
		do{
			oldVarsInfo = new LinkedHashMap<String, HAPVariableInfo>();
			oldVarsInfo.putAll(varsInfo);
			
			context.clear();
			matchers = this.getOperand().getOperand().discover(varsInfo, expectOutputCriteria, context, dataTypeHelper);
		}while(!HAPBasicUtility.isEqualMaps(varsInfo, oldVarsInfo) && context.isSuccess());

		//handle output variable
		String outVarName = this.m_expressionDefinition.getOutput();
		if(outVarName!=null) {
			HAPVariableInfo localOutVarInfo = new HAPVariableInfo(this.getOperand().getOperand().getOutputCriteria());
			varsInfo.put(outVarName, localOutVarInfo);
			localVarsInfo.put(outVarName, localOutVarInfo);
		}

		//update variables in output
		variablesInfo.clear();
		localVariablesInfo.clear();
		for(String varName : varsInfo.keySet()) {
			HAPVariableInfo varInfo = variablesInfo.get(varName);
			if(localVarsInfo.get(varName)!=null) {
				//local var
				localVariablesInfo.put(varName, varInfo);
			}
			else {
				variablesInfo.put(varName, varInfo);
			}
		}
		
		return matchers;
	}
	
	@Override
	public HAPDataTypeCriteria getOutput() {	return this.m_operand.getOperand().getOutputCriteria();	}

	public HAPExecuteStepExpression clone() {
		return null;
	}

	@Override
	public void updateVariable(HAPUpdateVariable updateVar) {
		// TODO Auto-generated method stub
		
	}
	
	
}
