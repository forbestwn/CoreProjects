package com.nosliw.data.core.expression;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.component.HAPDefinitionEntityComplex;
import com.nosliw.data.core.component.HAPUtilityComponentConstant;
import com.nosliw.data.core.component.attachment.HAPAttachment;
import com.nosliw.data.core.component.attachment.HAPAttachmentEntity;
import com.nosliw.data.core.component.attachment.HAPContainerAttachment;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.script.context.HAPContextStructure;
import com.nosliw.data.core.script.context.HAPUtilityContext;

public class HAPUtilityExpressionComponent {

	public static HAPDefinitionExpressionSuiteImp buildExpressionSuiteFromComponent(HAPDefinitionEntityComplex complexEntity, HAPRuntimeEnvironment runtimeEnv) {
		return buildExpressionSuiteFromComponent(complexEntity, null, runtimeEnv);
	}
	
	public static HAPDefinitionExpressionSuiteImp buildExpressionSuiteFromComponent(HAPDefinitionEntityComplex complexEntity, HAPContextStructure context, HAPRuntimeEnvironment runtimeEnv) {
		HAPDefinitionExpressionSuiteImp out = new HAPDefinitionExpressionSuiteImp();
		
		//build context
		if(context==null) {
			context = HAPUtilityExpression.getContext(complexEntity, null, runtimeEnv);
		}
		out.setContextStructure(context);
		
		//build constant from attachment
		for(HAPDefinitionConstant constantDef : HAPUtilityComponentConstant.buildDataConstantDefinition(complexEntity.getAttachmentContainer())) {
			out.addConstantDefinition(constantDef);
		}
		
		//constant from context
		Map<String, Object> constantsValue = HAPUtilityContext.discoverContantsValueFromContextStructure(context);
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
		Map<String, HAPAttachment> expressionAtts = attachmentContainer.getAttachmentByType(HAPConstantShared.RUNTIME_RESOURCE_TYPE_DATAEXPRESSION);
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
