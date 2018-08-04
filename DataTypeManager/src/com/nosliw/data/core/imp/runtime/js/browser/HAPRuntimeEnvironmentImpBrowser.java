package com.nosliw.data.core.imp.runtime.js.browser;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.data.core.datasource.HAPDataSourceManager;
import com.nosliw.data.core.datasource.HAPGatewayDataSource;
import com.nosliw.data.core.expression.HAPExpressionManager;
import com.nosliw.data.core.expressionsuite.HAPExpressionSuiteManager;
import com.nosliw.data.core.imp.HAPDataTypeHelperImp;
import com.nosliw.data.core.imp.runtime.js.HAPModuleRuntimeJS;
import com.nosliw.data.core.imp.runtime.js.resource.HAPResourceManagerJSImp;
import com.nosliw.data.core.runtime.HAPGatewayManager;
import com.nosliw.data.core.runtime.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.js.HAPRuntimeEnvironmentJS;
import com.nosliw.data.core.runtime.js.browser.HAPGatewayBrowserLoadLibrary;
import com.nosliw.data.core.runtime.js.browser.HAPGatewayLoadTestExpression;
import com.nosliw.data.core.runtime.js.rhino.HAPRuntimeImpRhino;
import com.nosliw.data.core.service.HAPGatewayService;
import com.nosliw.data.core.service.HAPManagerService;
import com.nosliw.data.core.task.HAPManagerTask;
import com.nosliw.data.imp.expression.parser.HAPExpressionParserImp;

public class HAPRuntimeEnvironmentImpBrowser extends HAPRuntimeEnvironmentJS{

	@HAPAttribute
	public static final String GATEWAY_LOADLIBRARIES = "loadLibraries";

	@HAPAttribute
	public static final String GATEWAY_TESTEXPRESSION = "testExpression";
	
	@HAPAttribute
	public static final String GATEWAY_DATASOURCE = "dataSource";
	
	@HAPAttribute
	public static final String GATEWAY_SERVICE = "service";
	
	HAPModuleRuntimeJS m_runtimeJSModule;
	
	public HAPRuntimeEnvironmentImpBrowser(){
		this(new HAPModuleRuntimeJS().init(HAPValueInfoManager.getInstance()));
	}
	
	public HAPRuntimeEnvironmentImpBrowser(HAPModuleRuntimeJS runtimeJSModule) {
		this.m_runtimeJSModule = runtimeJSModule;
		
		HAPExpressionManager.dataTypeHelper = new HAPDataTypeHelperImp(this, this.m_runtimeJSModule.getDataTypeDataAccess());
		HAPExpressionManager.expressionParser = new HAPExpressionParserImp();
		
		HAPResourceManagerRoot resourceMan = new HAPResourceManagerJSImp(runtimeJSModule.getRuntimeJSDataAccess(), runtimeJSModule.getDataTypeDataAccess());
		HAPRuntimeImpRhino runtime = new HAPRuntimeImpRhino(this); 
		HAPGatewayManager gatewayManager = new HAPGatewayManager(); 
		HAPExpressionSuiteManager expressionManager = new HAPExpressionSuiteManager(); 		
		HAPManagerTask taskManager = new HAPManagerTask(runtime);
		HAPDataSourceManager dataSourceManager = new HAPDataSourceManager();
		HAPManagerService serviceManager = new HAPManagerService();
		
		init(resourceMan,
			taskManager,
			expressionManager,
			gatewayManager,
			dataSourceManager,
			serviceManager,
			runtime
		);
		
		this.getGatewayManager().registerGateway(GATEWAY_DATASOURCE, new HAPGatewayDataSource(this.getDataSourceManager()));
		this.getGatewayManager().registerGateway(GATEWAY_SERVICE, new HAPGatewayService(this.getServiceManager()));
		
		this.getGatewayManager().registerGateway(GATEWAY_LOADLIBRARIES, new HAPGatewayBrowserLoadLibrary(this.getGatewayManager()));
		this.getGatewayManager().registerGateway(GATEWAY_TESTEXPRESSION, new HAPGatewayLoadTestExpression());
	}
}
