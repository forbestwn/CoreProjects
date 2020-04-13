package com.nosliw.uiresource.page.processor;

import java.util.Map;

import com.nosliw.data.core.component.HAPUtilityComponent;
import com.nosliw.data.core.expression.HAPParserExpression;
import com.nosliw.data.core.expression.HAPUtilityExpressionComponent;
import com.nosliw.data.core.expression.resource.HAPResourceDefinitionExpressionSuite;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIEmbededScriptExpressionInAttribute;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIEmbededScriptExpressionInContent;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.page.execute.HAPUIEmbededScriptExpressionInAttribute;
import com.nosliw.uiresource.page.execute.HAPUIEmbededScriptExpressionInContent;

//compile definition to executable
public class HAPProcessorCompile {

	public static void process(HAPExecutableUIUnit exeUnit, HAPDefinitionUIUnit parentUnitDef, HAPParserExpression expressionParser) {
		
		HAPDefinitionUIUnit uiUnitDef = exeUnit.getUIUnitDefinition();

		//attachment
		HAPUtilityComponent.mergeWithParentAttachment(uiUnitDef, parentUnitDef.getAttachmentContainer());
		HAPResourceDefinitionExpressionSuite expressionSuite = HAPUtilityExpressionComponent.buildExpressionSuiteFromComponent(uiUnitDef, expressionParser);
		
		//expression suite from attachment
		exeUnit.getExpressionContext().setExpressionDefinitionSuite(expressionSuite);
		
		//embeded script in content
		for(HAPDefinitionUIEmbededScriptExpressionInContent embededContent : uiUnitDef.getScriptExpressionsInContent()) {
			exeUnit.addScriptExpressionsInContent(new HAPUIEmbededScriptExpressionInContent(embededContent));
		}
		//embeded script in tag attribute 
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInAttribute()) {
			exeUnit.addScriptExpressionsInAttribute(new HAPUIEmbededScriptExpressionInAttribute(embededAttribute));
		}
		//embeded script in custom tag attribute
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInTagAttribute()) {
			exeUnit.addScriptExpressionsInTagAttribute(new HAPUIEmbededScriptExpressionInAttribute(embededAttribute));
		}
		
		//attribute
		Map<String, String> attrs = uiUnitDef.getAttributes();
		for(String attrName : attrs.keySet()) {
			exeUnit.addAttribute(attrName, attrs.get(attrName));
		}

		//other expressions : merge with parent + convert
//		if(parentUnitDef!=null) {
//			//from parent
//			Map<String, String> parentExps = parentUnitDef.getExpressionDefinitions();
//			for(String name : parentExps.keySet()) 		exeUnit.getExpressionContext().addExpressionDefinition(name, new HAPResourceDefinitionExpressionGroup(parentExps.get(name)));
//			
//			//it self
//			Map<String, String> expressions = uiUnitDef.getExpressionDefinitions();
//			for(String name : expressions.keySet()) 		exeUnit.getExpressionContext().addExpressionDefinition(name, new HAPResourceDefinitionExpressionGroup(expressions.get(name)));
//		}
	
		//child tag
		for(HAPExecutableUIUnitTag childTag : exeUnit.getUITags()) {
			process(childTag, exeUnit.getUIUnitDefinition(), expressionParser);			
		}
		
	}
}
