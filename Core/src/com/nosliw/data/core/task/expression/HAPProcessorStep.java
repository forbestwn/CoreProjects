package com.nosliw.data.core.task.expression;

import java.util.Map;

import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPProcessExpressionDefinitionContext;
import com.nosliw.data.core.task.HAPDefinitionTask;
import com.nosliw.data.core.task.HAPExecutable;
import com.nosliw.data.core.task.HAPMatchers;
import com.nosliw.data.core.task.HAPProcessTaskContext;
import com.nosliw.data.core.task.HAPVariableInfo;

public interface HAPProcessorStep {

	/**
	 * Process expression definition
	 * 		parse it to operand
	 * 		solve constant
	 * 		solve reference
	 * 		discovery 
	 * @param id  the id assigned to expression
	 * @param expDef   expression definition need to process
	 * @param contextExpressionDefinitions   other expressions that may need during solving reference
	 * @param variableCriterias   variable criterias that need to respect during discovery
	 * @return
	 */
	HAPExecuteStep process(
			HAPDefinitionStep stepDefinition,
			Map<String, HAPData> contextConstants,
			HAPProcessTaskContext context
	);

	HAPMatchers discover(Map<String, HAPVariableInfo> parentVariablesInfo, HAPDataTypeCriteria expectOutputCriteria, HAPProcessExpressionDefinitionContext context);
	
}
