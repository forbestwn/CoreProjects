package com.nosliw.uiresource.definition;

import com.nosliw.data.core.expression.HAPExpressionManager;
import com.nosliw.uiresource.expression.HAPEmbededScriptExpression;
import com.nosliw.uiresource.expression.HAPScriptExpression;

/*
 * class for script expression part in html content
 */
public class HAPEmbededScriptExpressionInContent extends HAPEmbededScriptExpression{

	public HAPEmbededScriptExpressionInContent(String uiId, HAPScriptExpression scriptExpression, HAPExpressionManager expressionManager){
		super(uiId, scriptExpression, expressionManager);
	}

}
