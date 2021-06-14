package com.nosliw.data.core.dataassociation.mapping;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.structure.HAPElementStructure;
import com.nosliw.data.core.structure.HAPElementStructureLeafConstant;
import com.nosliw.data.core.structure.HAPElementStructureLeafRelative;
import com.nosliw.data.core.structure.HAPElementStructureNode;
import com.nosliw.data.core.structure.HAPRootStructure;
import com.nosliw.data.core.structure.temp.HAPUtilityContextInfo;

public class HAPUtilityScript {

	public static HAPJsonTypeScript buildDataAssociationConvertFunction(HAPExecutableDataAssociationMapping dataAssociation) {
		StringBuffer assocationScripts = new StringBuffer();
		Map<String, HAPExecutableAssociation> associations = dataAssociation.getAssociations();
		for(String targetName : associations.keySet()) {
			String associationScript = buildAssociationConvertFunction(associations.get(targetName)).getScript();
			assocationScripts.append("output."+targetName+"="+associationScript+"(input, utilFunction);\n");
		}
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("buildAssociations", assocationScripts.toString());
		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPUtilityScript.class, "DataAssociationFunction.temp");
		String script = HAPStringTemplateUtil.getStringValue(templateStream, templateParms);
		return new HAPJsonTypeScript(script);
	}
	
	public static HAPJsonTypeScript buildAssociationConvertFunction(HAPExecutableAssociation association) {
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("isFlatOutput", association.isFlatOutput()+"");
		templateParms.put("isFlatInput", association.isFlatInput()+"");
		templateParms.put("rootIdSeperator", HAPConstantShared.SEPERATOR_CONTEXT_CATEGARY_NAME);
		templateParms.put("isInherit", (!HAPConstant.INHERITMODE_NONE.equals(HAPUtilityDataAssociation.getContextProcessConfigurationForDataAssociation(null).inheritMode))+"");
		
		//build init output object for mapped root
		HAPValueMapping valueMappinig = association.getMapping();
		JSONObject output = buildSkeletonJsonObject(valueMappinig);
		templateParms.put("outputInit", HAPJsonUtility.formatJson(output.toString()));
		
		//build dynamic part 
		StringBuffer dynamicScript = new StringBuffer();
		Map<String, String> relativePathMapping = association.getRelativePathMappings();
		for(String targePath : relativePathMapping.keySet()) {
			String sourcePath = relativePathMapping.get(targePath);
			String script = "output = utilFunction(output, "+ targePath +", input, "+ sourcePath +");\n";
			dynamicScript.append(script);
		}
		templateParms.put("outputDyanimicValueBuild", dynamicScript.toString());
		
		//build cosntant assignment part
		StringBuffer constantAssignmentScript = new StringBuffer();
		Map<String, Object> constantAssignments = association.getConstantAssignments();
		for(String targePath : constantAssignments.keySet()) {
			Object constantValue = constantAssignments.get(targePath);
			String script = "output = utilFunction(output, "+ targePath +", "+ HAPJsonUtility.buildJsonStringValue(constantValue, HAPSerializationFormat.JSON) +");\n";
			constantAssignmentScript.append(script);
		}
		templateParms.put("outputConstantValueBuild", constantAssignmentScript.toString());
		
		
		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPUtilityScript.class, "AssociationFunction.temp");
		String script = HAPStringTemplateUtil.getStringValue(templateStream, templateParms);
		return new HAPJsonTypeScript(script);
	}
 
	//build skeleton, it is used for data mapping operation
	public static JSONObject buildSkeletonJsonObject(HAPValueMapping valueMapping) {
		JSONObject output = new JSONObject();
		Map<String, HAPRootStructure> elements = valueMapping.getItems();
		for(String targetId : elements.keySet()) {
			HAPRootStructure root = elements.get(targetId);
			if(HAPConstantShared.UIRESOURCE_CONTEXTINFO_RELATIVECONNECTION_PHYSICAL.equals(HAPUtilityContextInfo.getRelativeConnectionValue(root.getInfo()))) {
				HAPElementStructure contextDefEle = root.getDefinition();
				Object contextEleJson = buildJsonValue(contextDefEle);
				JSONObject parentJsonObj = output;
				parentJsonObj.put(targetId, contextEleJson);
			}
		}
		
		return output;
	}
	
	private static Object buildJsonValue(HAPElementStructure contextDefEle) {
		switch(contextDefEle.getType()) {
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT:
		{
			HAPElementStructureLeafConstant constantEle = (HAPElementStructureLeafConstant)contextDefEle;
			return constantEle.getValue();
		}
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
		{
			HAPElementStructureNode nodeEle = (HAPElementStructureNode)contextDefEle;
			JSONObject out = new JSONObject();
			for(String childName : nodeEle.getChildren().keySet()) {
				Object childJsonValue = buildJsonValue(nodeEle.getChild(childName));
				if(childJsonValue!=null) {
					out.put(childName, childJsonValue);
				}
			}
			return out;
		}
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE:
		{
			HAPElementStructureLeafRelative relativeEle = (HAPElementStructureLeafRelative)contextDefEle;
			return new JSONObject();
		}
		default:
			return null;
		}
	}

}
