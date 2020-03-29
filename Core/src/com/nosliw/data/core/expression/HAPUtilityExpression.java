package com.nosliw.data.core.expression;

import com.nosliw.common.updatename.HAPUpdateName;
import com.nosliw.common.updatename.HAPUpdateNamePrefix;

public class HAPUtilityExpression {

	public static HAPUpdateName getUpdateExpressionVariableName(HAPExecutableExpression expression) {
		return new HAPUpdateNamePrefix(expression.getId()+"_");
	}
	
	public static String getBeforeUpdateName(HAPExecutableExpression expression, String name) {
		int index = name.indexOf(expression.getId()+"_");
		return name.substring(index);
	}
	
}
