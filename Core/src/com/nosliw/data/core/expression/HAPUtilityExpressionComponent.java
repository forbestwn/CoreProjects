package com.nosliw.data.core.expression;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.component.HAPDefinitionEntityComplex;
import com.nosliw.data.core.component.attachment.HAPAttachment;
import com.nosliw.data.core.component.attachment.HAPAttachmentEntity;
import com.nosliw.data.core.component.attachment.HAPContainerAttachment;
import com.nosliw.data.core.data.HAPUtilityDataComponent;
import com.nosliw.data.core.script.context.HAPContext;

public class HAPUtilityExpressionComponent {

	public static HAPDefinitionExpressionSuiteImp buildExpressionSuiteFromComponent(HAPDefinitionEntityComplex complexEntity) {
		return buildExpressionSuiteFromComponent(complexEntity, null);
	}
	
	public static HAPDefinitionExpressionSuiteImp buildExpressionSuiteFromComponent(HAPDefinitionEntityComplex complexEntity, HAPContext context) {
		HAPDefinitionExpressionSuiteImp out = new HAPDefinitionExpressionSuiteImp();
		
		//build context
		if(context==null)		complexEntity.cloneToDataContext(out);
		else   out.setContext(context);
		
		//build constant from attachment
		for(HAPDefinitionConstant constantDef : HAPUtilityDataComponent.buildDataConstantDefinition(complexEntity.getAttachmentContainer())) {
			out.addConstantDefinition(constantDef);
		}
		
		//constant from context
		Map<String, Object> constantsValue = context.getConstantValue();
		for(String id : constantsValue.keySet()) {
			HAPDefinitionConstant constantDef = new HAPDefinitionConstant(id, constantsValue.get(id));
			if(constantDef.isData()) {
				out.addConstantDefinition(constantDef);
			}
		}

		//build expression definition
		buildExpressionSuiteFromAttachment(out, complexEntity.getAttachmentContainer());
		
		return out;
	}
	
	public static void buildExpressionSuiteFromAttachment(HAPDefinitionExpressionSuite suite, HAPContainerAttachment attachmentContainer) {
		Map<String, HAPAttachment> expressionAtts = attachmentContainer.getAttachmentByType(HAPConstantShared.RUNTIME_RESOURCE_TYPE_EXPRESSION);
		for(String name : expressionAtts.keySet()) {
			HAPAttachment attachment = expressionAtts.get(name);
			suite.addEntityElement(buildExpressionGroup(attachment));
		}
	}

	private static HAPDefinitionExpressionGroup buildExpressionGroup(HAPAttachment attachment) {
		HAPDefinitionExpressionGroupImp out = new HAPDefinitionExpressionGroupImp();
		attachment.cloneToEntityInfo(out);
		if(HAPConstantShared.ATTACHMENT_TYPE_ENTITY.equals(attachment.getType())) {
			HAPAttachmentEntity entityAttachment = (HAPAttachmentEntity)attachment;
			entityAttachment.cloneToEntityInfo(out);
			JSONObject attachmentEntityJsonObj = entityAttachment.getEntityJsonObj();
			HAPParserExpressionDefinition.parseExpressionDefinitionList(out, attachmentEntityJsonObj);
		}
		else if(HAPConstantShared.ATTACHMENT_TYPE_REFERENCEEXTERNAL.equals(attachment.getType())) {
		}
		else if(HAPConstantShared.ATTACHMENT_TYPE_REFERENCELOCAL.equals(attachment.getType())) {
		}
		return out;
	}
}
