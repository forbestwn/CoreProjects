package com.nosliw.data.core.script.expression;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.expression.HAPDefinitionExpression;

//script expression definition
//it is used in embeded
public class HAPDefinitionScriptExpression {

	public static final String EXPRESSION_TOKEN_OPEN = "#|";
	public static final String EXPRESSION_TOKEN_CLOSE = "|#";

	//definition literate
	private String m_definition;

	//after parsing content, we got a list of elements in ui expression:
	//     	js expression:  HAPScriptInScriptExpression
	//		data expression : HAPExpressionInScriptExpression
	private List<Object> m_elements;

	public HAPDefinitionScriptExpression(String definition){
		this.m_elements = new ArrayList<Object>();
		this.m_definition = definition;
		this.parseDefinition();
	}
	
	public String getDefinition() {   return this.m_definition;  }

	public List<Object> getElements(){  return this.m_elements;   }
	
	public boolean isDataExpression() {
		if(this.m_elements.size()==1 && this.m_elements.get(0) instanceof HAPDefinitionExpression) return true;
		return false;
	}
	
	public List<HAPDefinitionExpression> getExpressionDefinitions(){
		List<HAPDefinitionExpression> out = new ArrayList<HAPDefinitionExpression>();
		for(Object element : this.m_elements){
			if(element instanceof HAPDefinitionExpression){
				out.add((HAPDefinitionExpression)element);
			}
		}
		return out;
	}

	public List<HAPScriptInScriptExpression> getScriptSegments(){
		List<HAPScriptInScriptExpression> out = new ArrayList<HAPScriptInScriptExpression>();
		for(Object element : this.m_elements){
			if(element instanceof HAPScriptInScriptExpression){
				out.add((HAPScriptInScriptExpression)element);
			}
		}
		return out;
	}
	
	//parse definition to get segments
	private void parseDefinition(){
		String content = this.m_definition;
		int i = 0;
		while(HAPBasicUtility.isStringNotEmpty(content)){
			int index = content.indexOf(EXPRESSION_TOKEN_OPEN);
			if(index==-1){
				//no expression
				this.m_elements.add(new HAPScriptInScriptExpression(content));
				content = null;
			}
			else if(index!=0){
				//start with text
				HAPScriptInScriptExpression scriptSegment = new HAPScriptInScriptExpression(content.substring(0, index));
				this.m_elements.add(scriptSegment);
				content = content.substring(index);
			}
			else{
				//start with expression
				int expEnd = content.indexOf(EXPRESSION_TOKEN_CLOSE);
				int expStart = index + EXPRESSION_TOKEN_OPEN.length();
				//get expression element
				String expressionStr = content.substring(expStart, expEnd);
				content = content.substring(expEnd + EXPRESSION_TOKEN_CLOSE.length());
				//build expression definition
				HAPDefinitionExpression expressionDefinition = new HAPDefinitionExpression(expressionStr);
				this.m_elements.add(expressionDefinition);
			}
			i++;
		}
	}
}
