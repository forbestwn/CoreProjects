package com.nosliw.data.core.task111.expression;

import java.util.Map;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.task111.HAPDefinitionTask;
import com.nosliw.data.core.task111.HAPExecutableTask;
import com.nosliw.data.core.task111.HAPManagerTask;

public class HAPProcessorStepLoop implements HAPProcessorStep{

	private HAPManagerTask m_managerTask;
	
	public HAPProcessorStepLoop(HAPManagerTask managerTask) {
		this.m_managerTask = managerTask;
	}
	
	@Override
	public HAPExecutableStep process(HAPDefinitionStep stepDefinition, 
			HAPExecutableTaskExpression taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPData> contextConstants, HAPProcessContext context) {
		
		HAPDefinitionStepLoop stepDefLoop = (HAPDefinitionStepLoop)stepDefinition;
		
		HAPExecutableStepLoop out = new HAPExecutableStepLoop(stepDefLoop, index, name);
		
		HAPOperandUtility.updateConstantData(out.getContainerOperand(), contextConstants);

		return out;
	}

	@Override
	public void postProcess(HAPExecutableStep executableStep, 
			HAPDefinitionStep stepDefinition, 
			HAPExecutableTaskExpression taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, Map<String, HAPData> contextConstants,
			HAPProcessContext context) {

		HAPDefinitionStepLoop stepDefLoop = (HAPDefinitionStepLoop)stepDefinition;
		HAPExecutableStepLoop stepExeLoop = (HAPExecutableStepLoop)executableStep;
		
		HAPReferenceInfo refInfo = taskExpressionExe.getReferencesInfo().get(stepExeLoop.getExecuteTaskName());
		
		HAPExecutableTask executable = m_managerTask.processTask(contextTaskDefinitions.get(stepDefLoop.getExecuteTask()), null, HAPBasicUtility.reverseMapping(refInfo.getVariablesMap()), contextTaskDefinitions, contextConstants, context);
		stepExeLoop.setExecuteTask(executable);
		
	}

}