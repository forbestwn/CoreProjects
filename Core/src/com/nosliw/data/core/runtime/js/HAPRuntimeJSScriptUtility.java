package com.nosliw.data.core.runtime.js;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.runtime.HAPRuntimeTaskExecuteExpression;
import com.nosliw.data.core.runtime.HAPRuntimeTaskLoadResources;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceInfo;
import com.nosliw.data.core.runtime.js.rhino.HAPGatewayRuntimeTaskResponse;
import com.nosliw.data.core.runtime.js.rhino.HAPRuntimeImpRhino;

public class HAPRuntimeJSScriptUtility {

	public static List<HAPJSScriptInfo> buildScriptForResource(HAPResourceInfo resourceInfo, HAPResource resource){
		List<HAPJSScriptInfo> out = new ArrayList<HAPJSScriptInfo>();
		//build library script info first
		if(resource.getId().getType().equals(HAPConstant.RUNTIME_RESOURCE_TYPE_JSLIBRARY)){
			out.addAll(buildScriptInfoForLibrary(resource));
		}
		
		
		StringBuffer script = new StringBuffer();
		if(HAPRuntimeImpRhino.ADDTORESOURCEMANAGER.equals(resourceInfo.getInfo().getValue(HAPRuntimeImpRhino.ADDTORESOURCEMANAGER)))  return out;
		
		//build script for resource data
		String valueScript = null;
		if(resource.getResourceData() instanceof HAPResourceDataJSValue){
			valueScript = ((HAPResourceDataJSValue)resource.getResourceData()).getValue();
		}
		
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("resourceInfo", resourceInfo.toStringValue(HAPSerializationFormat.JSON));
		
		String infoJson = HAPSerializeManager.getInstance().toStringValue(resource.getInfo(), HAPSerializationFormat.JSON);
		if(HAPBasicUtility.isStringEmpty(infoJson)){
			templateParms.put(HAPResource.INFO, "undefined");
		}
		else{
			templateParms.put(HAPResource.INFO, infoJson);
		}

		if(valueScript==null)  valueScript = "undefined";
		templateParms.put(HAPResourceDataJSValue.VALUE, valueScript);

		InputStream javaTemplateStream = HAPFileUtility.getInputStreamOnClassPath(HAPRuntimeJSScriptUtility.class, "ImportResource.temp");
		script.append("\n");
		String resoruceDataScript = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		script.append(resoruceDataScript);
		script.append("\n");
		
		out.add(HAPJSScriptInfo.buildByScript(script.toString(), "Resource__"+resource.getId().toStringValue(HAPSerializationFormat.LITERATE)));
		return out;
	}
	
	private static List<HAPJSScriptInfo> buildScriptInfoForLibrary(HAPResource resource){
		List<HAPJSScriptInfo> out = new ArrayList<HAPJSScriptInfo>();
		
		HAPResourceDataJSLibrary resourceLibrary = (HAPResourceDataJSLibrary)resource.getResourceData();
		List<URI> uris = resourceLibrary.getURIs();
		for(URI uri : uris){
			File file = new File(uri);
			out.add(HAPJSScriptInfo.buildByFile(file.toString(), "Library__" + resource.getId().getId() + "__" + file.getName()));
		}
		return out;
	}

	public static HAPJSScriptInfo buildRequestScriptForExecuteExpressionTask(HAPRuntimeTaskExecuteExpression executeExpressionTask, HAPRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("expression", HAPJsonUtility.formatJson(HAPSerializeManager.getInstance().toStringValue(executeExpressionTask.getExpression(), HAPSerializationFormat.JSON)));
		templateParms.put("variables", HAPJsonUtility.formatJson(HAPJsonUtility.buildJson(executeExpressionTask.getVariablesValue()==null?new LinkedHashMap<String, HAPData>() : executeExpressionTask.getVariablesValue(), HAPSerializationFormat.JSON)));

		templateParms.put("successCommand", HAPGatewayRuntimeTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRuntimeTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRuntimeTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("gatewayId", runtime.getTaskResponseGatewayName());
		templateParms.put("parmTaskId", HAPGatewayRuntimeTaskResponse.PARM_TASKID);
		templateParms.put("taskId", executeExpressionTask.getTaskId());
		templateParms.put("parmResponseData", HAPGatewayRuntimeTaskResponse.PARM_RESPONSEDATA);
		
		InputStream javaTemplateStream = HAPFileUtility.getInputStreamOnClassPath(HAPRuntimeJSScriptUtility.class, "ExecuteExpressionScript.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, executeExpressionTask.getTaskId());
		return out;
	}
	
	public static HAPJSScriptInfo buildRequestScriptForLoadResourceTask(HAPRuntimeTaskLoadResources loadResourcesTask, HAPRuntimeImpRhino runtime){
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		templateParms.put("successCommand", HAPGatewayRuntimeTaskResponse.COMMAND_SUCCESS);
		templateParms.put("errorCommand", HAPGatewayRuntimeTaskResponse.COMMAND_ERROR);
		templateParms.put("exceptionCommand", HAPGatewayRuntimeTaskResponse.COMMAND_EXCEPTION);
		
		templateParms.put("gatewayId", runtime.getTaskResponseGatewayName());
		templateParms.put("parmTaskId", HAPGatewayRuntimeTaskResponse.PARM_TASKID);
		templateParms.put("taskId", loadResourcesTask.getTaskId());
		templateParms.put("parmResponseData", HAPGatewayRuntimeTaskResponse.PARM_RESPONSEDATA);

		templateParms.put("resourceInfos", HAPJsonUtility.formatJson(HAPJsonUtility.buildJson(loadResourcesTask.getResourcesInfo(), HAPSerializationFormat.JSON)));
		
		InputStream javaTemplateStream = HAPFileUtility.getInputStreamOnClassPath(HAPRuntimeJSScriptUtility.class, "LoadResources.temp");
		String script = HAPStringTemplateUtil.getStringValue(javaTemplateStream, templateParms);
		HAPJSScriptInfo out = HAPJSScriptInfo.buildByScript(script, loadResourcesTask.getTaskId());
		return out;
	}
}
