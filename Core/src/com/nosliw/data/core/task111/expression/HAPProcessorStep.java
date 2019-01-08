package com.nosliw.data.core.task111.expression;

import java.util.Map;

import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.task111.HAPDefinitionTask;

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
	HAPExecutableStep process(
			HAPDefinitionStep stepDefinition,
			HAPExecutableTaskExpression taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPData> contextConstants,
			HAPProcessTracker processTracker
	);

	void postProcess(
			HAPExecutableStep executableStep,
			HAPDefinitionStep stepDefinition,
			HAPExecutableTaskExpression taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPData> contextConstants,
			HAPProcessTracker processTracker
	);

}
