package com.nosliw.servlet.core;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPSystemUtility;
import com.nosliw.data.core.resource.HAPResourceUtility;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPGatewayOutput;
import com.nosliw.data.core.runtime.js.HAPJSScriptInfo;
import com.nosliw.data.core.runtime.js.browser.HAPRuntimeBrowserUtility;
import com.nosliw.servlet.HAPServiceServlet;

/**
 * Servlet for gateway
 */
@HAPEntityWithAttribute
public class HAPGatewayServlet extends HAPServiceServlet{

	private static final long serialVersionUID = 3449216679929442927L;

	@HAPAttribute
	final public static String COMMAND_PARM_RUNTIMEINFO = "runtimeInfo";

	private static int index = 0;
	
	@Override
	protected HAPServiceData processServiceRequest(String gatewayCommand, JSONObject parms) throws Exception {
		HAPServiceData out = null;

		String[] segs = HAPNamingConversionUtility.parseLevel1(gatewayCommand);
		String gatewayId = segs[0];
		String command = segs[1];
		
		out = this.getRuntimeEnvironment().getGatewayManager().executeGateway(gatewayId, command, parms, new HAPRuntimeInfo(HAPConstant.RUNTIME_LANGUAGE_JS, HAPConstant.RUNTIME_ENVIRONMENT_BROWSER));
		if(out.isSuccess()){
			HAPGatewayOutput output = (HAPGatewayOutput)out.getData();
			for(HAPJSScriptInfo scriptInfo : output.getScripts()){
				String file = scriptInfo.isFile();
				if(file==null){
					if(HAPResourceUtility.LOADRESOURCEBYFILE_MODE_ALWAYS.equals(HAPSystemUtility.getLoadResourceByFileMode())){
						String name = "gatewayCommand_"+gatewayId+"_"+command+""+index++;
						String resourceFile = HAPFileUtility.getResourceTempFileFolder() + name + ".js";
						resourceFile = HAPFileUtility.writeFile(resourceFile, scriptInfo.getScript());
						scriptInfo.setFile(HAPRuntimeBrowserUtility.getBrowserScriptPath(resourceFile));
						scriptInfo.setScript(null);
					}
					else {
						String escaptedScript = StringEscapeUtils.escapeJavaScript(scriptInfo.getScript());
						scriptInfo.setScript(escaptedScript);
					}
				}
				else{
					scriptInfo.setFile(HAPRuntimeBrowserUtility.getBrowserScriptPath(file));
				}
			}
		}
		return out;
	}

}
