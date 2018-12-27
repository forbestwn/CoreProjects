package com.nosliw.data.core.script.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.updatename.HAPUpdateName;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.runtime.HAPExecutableExpression;

/**
 * Represent script expression executable
 * Script expression is a mix of data expression and javascript expression
 * The result of script expression is javascript entity (string, boolean number, or object)
 * It is used in:
 * 		part of html text
 * 		tag attribute
 * 		constant definition
 */
@HAPEntityWithAttribute
public class HAPScriptExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String DEFINITION = "definition";

	@HAPAttribute
	public static final String SCRIPTFUNCTION = "scriptFunction";

	@HAPAttribute
	public static final String EXPRESSIONS = "expressions";
	
	@HAPAttribute
	public static final String VARIABLENAMES = "variableNames";

	private HAPDefinitionScriptExpression m_definition;
	
	//ScriptInScriptExpression
	//HAPExecutableExpression
	private List<Object> m_elements;
	
	//expressions used in script expression
	//element index ---- processed expression
	private Map<String, HAPExecutableExpression> m_expressions;

	private Map<Integer, String> m_indexToId;
	
	//when script expression does not contain any variable
	//it means that the script expression can be executed and get result during expression processing stage
	//then script expression turn to constant instead
	private boolean m_isConstant;
	private Object m_value;

	public HAPScriptExpression(HAPDefinitionScriptExpression definition){
		this.m_elements = new ArrayList<Object>();
		this.m_expressions = new LinkedHashMap<String, HAPExecutableExpression>();
		this.m_indexToId = new LinkedHashMap<Integer, String>();
		this.m_definition = definition;
		this.m_isConstant = false;
	}
	
	public void updateExpressionId(HAPUpdateName update) {
		Map<String, HAPExecutableExpression> expressions = new LinkedHashMap<String, HAPExecutableExpression>();
		for(String id : this.m_expressions.keySet()) 		expressions.put(update.getUpdatedName(id), this.m_expressions.get(id));
		this.m_expressions.clear();
		this.m_expressions.putAll(expressions);
		
		Map<Integer, String> indexToId = new LinkedHashMap<Integer, String>();
		for(Integer index : this.m_indexToId.keySet())    indexToId.put(index, update.getUpdatedName(this.m_indexToId.get(index)));
		this.m_indexToId.clear();
		this.m_indexToId.putAll(indexToId);
	}
	
	public void addElement(Object ele) {
		int index = this.m_elements.size();
		this.m_elements.add(ele);
		if(ele instanceof HAPExecutableExpression) {
			String expId = index+"";
			this.m_expressions.put(expId, (HAPExecutableExpression)ele);
			this.m_indexToId.put(index, expId);
		}
	}
	
	public List<Object> getElements(){  return this.m_elements;   }

	public String getIdByIndex(int index) {   return this.m_indexToId.get(index);   }
	
	public HAPDefinitionScriptExpression getDefinition(){  return this.m_definition;  } 

	public Map<String, HAPExecutableExpression> getExpressions(){   return this.m_expressions;    }
	
	public boolean isConstant(){  return this.m_isConstant;  }
	public Object getValue(){  return this.m_value;  }
	public void setValue(Object value){  
		this.m_value = value;
		this.m_isConstant = true;
	}

	public Set<String> getVariableNames(){ 
		Set<String> out = new HashSet<String>();
		for(Object ele : this.m_elements){
			if(ele instanceof HAPExecutableExpression){
				HAPExecutableExpression expExe = (HAPExecutableExpression)ele;
				out.addAll(HAPOperandUtility.discoverVariables(expExe.getOperand()));
			}
			else if(ele instanceof HAPScriptInScriptExpression){
				HAPScriptInScriptExpression scriptSegment = (HAPScriptInScriptExpression)ele;
				out.addAll(scriptSegment.getVariableNames());
			}
		}
		return out;
	}

/*	
	//update script constant information 
	public void updateWithConstantsValue(Map<String, Object> constantsValue) {
		for(Object ele : this.m_elements){
			if(ele instanceof HAPExpressionInScriptExpression){
				HAPExpressionInScriptExpression expDef = (HAPExpressionInScriptExpression)ele;
				HAPOperandUtility.updateConstantData(expDef.getOperand(), HAPUtilityScriptExpression.getConstantData(constantsValue));
			}
			else if(ele instanceof HAPScriptInScriptExpression){
				HAPScriptInScriptExpression scriptSegment = (HAPScriptInScriptExpression)ele;
				scriptSegment.updateConstantValue(constantsValue);
			}
		}
	}
	
	//process all expression definitions in script expression
	public void processExpressions(HAPProcessContextScriptExpression expressionContext, Map<String, String> configure, HAPExpressionSuiteManager expressionManager){
		//preprocess attributes operand in expressions, some attributes operand can be combine into one variable operand
		for(HAPExpressionInScriptExpression expDef : this.getExpressionDefinitions()){
			HAPUtilityScriptExpression.processAttributeOperandInExpression(expDef, expressionContext.getDataVariables());
		}

		this.m_expressions = new LinkedHashMap<String, HAPExecutableExpression>();
		for(int i=0; i<this.m_elements.size(); i++) {
			Object element = this.m_elements.get(i);
			if(element instanceof HAPExpressionInScriptExpression){
				HAPExpressionInScriptExpression expEle = (HAPExpressionInScriptExpression)element;
				HAPProcessContext context = new HAPProcessContext();
				m_expressions.put(expEle.getId(), expressionManager.compileExpression(expEle, expressionContext.getExpressionDefinitionSuite(), null, configure, context));
			}
		}
	}
*/	 
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DEFINITION, this.m_definition.getDefinition());
		jsonMap.put(VARIABLENAMES, HAPJsonUtility.buildJson(this.getVariableNames(), HAPSerializationFormat.JSON));
		jsonMap.put(EXPRESSIONS, HAPJsonUtility.buildJson(m_expressions, HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildFullJsonMap(jsonMap, typeJsonMap);
	}
}
