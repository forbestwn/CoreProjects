package com.nosliw.data.core.task;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeHelper;

import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.task.expression.HAPExecuteTaskExpression;

public class HAPManagerTask {
	
	private static Map<String, HAPProcessorTask> m_taskProcessors;

	private static Map<String, HAPExecutorTask> m_taskExecutors;
	
	private HAPDataTypeHelper m_dataTypeHelper;
	
	//all expression definition suites
	private Map<String, HAPDefinitionTaskSuite> m_taskDefinitionSuites;

	
	//used to generate id
	private int m_idIndex;
	
	public HAPManagerTask(){
		this.init();
	}
	
	public HAPData executeTask(String taskName, HAPDefinitionTaskSuite suite, Map<String, HAPData> parms) {
		
		//compile task
		HAPProcessContext  processContext = new HAPProcessContext();
		this.compileTask(this.generateId(), suite.getTask(taskName), suite.getAllTasks(), suite.getVariables(), suite.getConstants(), null, processContext);
		
		//convert parms
		
		//execute task
		
		
		return null;
	}
	
	public static void registerTaskProcessor(String type, HAPProcessorTask processor) {
		m_taskProcessors.put(type, processor);
	}
	
	private void init(){
		HAPValueInfoManager.getInstance().importFromClassFolder(this.getClass());
		
		this.m_taskDefinitionSuites = new LinkedHashMap<String, HAPDefinitionTaskSuite>();
		this.m_idIndex = 1;
	}

	private String generateId() {    
		this.m_idIndex++;
		return this.m_idIndex+"";
	}
	
	public HAPExecutableTask compileTask(
			String id,
			HAPDefinitionTask taskDefinition, 
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPVariableInfo> parentVariablesInfo, 
			Map<String, HAPData> contextConstants,
			HAPVariableInfo expectOutput,
			HAPProcessContext context) {
		HAPExecuteTaskExpression task = (HAPExecuteTaskExpression)processTask(taskDefinition, null, null, contextTaskDefinitions, contextConstants, context);
		task.setId(id);
		
		Map<String, HAPVariableInfo> variableInfos = parentVariablesInfo;
		if(variableInfos==null) {
			//if no overriding var info, use var info defined in task definition
			variableInfos = taskDefinition.getVariables(); 
		}
		
		task.discoverVariable(variableInfos, expectOutput, context, this.m_dataTypeHelper);
		
		return task;
	}
	
	public static HAPExecutableTask processTask(HAPDefinitionTask taskDefinition, String domain, Map<String, String> variableMap,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, Map<String, HAPData> contextConstants,
			HAPProcessContext context) {
		HAPProcessorTask taskProcessor = m_taskProcessors.get(taskDefinition.getType());
		HAPExecutableTask out = taskProcessor.process(taskDefinition, domain, variableMap, contextTaskDefinitions, contextConstants, context);
		return out;
	}
	
	
	
	
	
	
	
	
	
	
/*	
	
	public HAPDefinitionTaskSuite getTaskDefinitionSuite(String suiteName){		return this.m_taskDefinitionSuites.get(suiteName);	}
	
	public Set<String> getTaskDefinitionSuites() {		return this.m_taskDefinitionSuites.keySet();	}
	
	public void addTaskDefinitionSuite(HAPDefinitionTaskSuite expressionDefinitionSuite){
		//parse expression in suite
        Map<String, HAPDefinitionTask> expDefs = expressionDefinitionSuite.getAllTaskDefinitions();
        for(String name : expDefs.keySet()){
        	HAPDefinitionTask expDef = expDefs.get(name);
        	HAPOperand operand = this.m_expressionProcessor.parseExpression(expDef.getExpression());
        	expDef.setOperand(operand);
        }

        //add to expression manager
		HAPExpressionDefinitionSuiteImp suite = (HAPExpressionDefinitionSuiteImp)this.getTaskDefinitionSuite(expressionDefinitionSuite.getName());
		if(suite==null){
			this.m_taskDefinitionSuites.put(expressionDefinitionSuite.getName(), (HAPExpressionDefinitionSuiteImp)expressionDefinitionSuite);
		}
		else{
			suite.merge((HAPExpressionDefinitionSuiteImp)expressionDefinitionSuite);
		}
	}
	
*/
	
/*	
	
	public HAPDefinitionTask getTaskDefinition(String suite, String name) {		return this.getTaskDefinitionSuite(suite).getTask(name);	}

	public HAPDefinitionTask newExpressionDefinition(String expression, String name,
			Map<String, HAPData> constants, Map<String, HAPDataTypeCriteria> variableCriterias) {
		HAPDefinitionTask expDefinition = new HAPTaskDefinitionSimple(expression, name, constants, null, null, null);
		expDefinition.setOperand(this.m_expressionProcessor.parseExpression(expression));
		return expDefinition;
	}

	public HAPDefinitionTaskSuite newTaskDefinitionSuite(String name) {
		return new HAPDefinitionTaskSuite();
	}
	*/
}
