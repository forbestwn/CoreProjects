package com.nosliw.data.core.runtime.js;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.component.HAPManagerResourceDefinition;
import com.nosliw.data.core.cronjob.HAPManagerCronJob;
import com.nosliw.data.core.cronjob.HAPResourceDefinitionPluginCronJob;
import com.nosliw.data.core.cronjob.HAPResourceManagerCronJob;
import com.nosliw.data.core.err.HAPGatewayErrorLogger;
import com.nosliw.data.core.expressionsuite.HAPExpressionSuiteManager;
import com.nosliw.data.core.process.HAPManagerProcess;
import com.nosliw.data.core.process.HAPRuntimeProcess;
import com.nosliw.data.core.process.resource.HAPResourceDefinitionPluginProcess;
import com.nosliw.data.core.process.resource.HAPResourceDefinitionPluginProcessSuite;
import com.nosliw.data.core.process.resource.HAPResourceManagerActivityPlugin;
import com.nosliw.data.core.process.resource.HAPResourceManagerProcess;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.HAPGatewayManager;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.runtime.js.gateway.HAPGatewayCriteriaOperation;
import com.nosliw.data.core.runtime.js.gateway.HAPGatewayExpressionDiscovery;
import com.nosliw.data.core.runtime.js.gateway.HAPGatewayResource;
import com.nosliw.data.core.runtime.js.gateway.HAPGatewayResourceDefinition;
import com.nosliw.data.core.service.provide.HAPFactoryServiceProcess;
import com.nosliw.data.core.service.provide.HAPManagerService;
import com.nosliw.data.core.template.HAPManagerTemplate;

@HAPEntityWithAttribute(baseName="RUNTIME")
public abstract class HAPRuntimeEnvironmentJS implements HAPRuntimeEnvironment{

	@HAPAttribute
	public static final String NODENAME_GATEWAY = "gatewayObj";
	
	@HAPAttribute
	public static final String GATEWAY_RESOURCE = "resources";

	@HAPAttribute
	public static final String GATEWAY_RESOURCEDEFINITION = "resourceDefinition";

	@HAPAttribute
	public static final String GATEWAY_CRITERIA = "criteria";
	
	@HAPAttribute
	public static final String GATEWAY_DISCOVERY = "discovery";

	@HAPAttribute
	public static final String GATEWAY_ERRORLOG = "errorLog";

	private HAPResourceManagerRoot m_resourceManager;
	
	private HAPManagerProcess m_processManager;
	
	private HAPRuntimeProcess m_processRuntime;
	
	private HAPExpressionSuiteManager m_expressionSuiteManager;
	
	private HAPGatewayManager m_gatewayManager;
	
	private HAPManagerService m_serviceManager;
	
	private HAPManagerResourceDefinition m_resourceDefinitionManager;
	
	private HAPManagerTemplate m_templateManager;
	
	private HAPManagerCronJob m_cronJobManager;
	
	private HAPRuntime m_runtime;
	
	public HAPRuntimeEnvironmentJS(){}
	
	public HAPRuntimeEnvironmentJS(HAPResourceManagerRoot resourceMan,
									HAPManagerProcess processManager,
									HAPRuntimeProcess processRuntime,
									HAPExpressionSuiteManager expressionSuiteManager,
								    HAPGatewayManager gatewayManager,
								    HAPManagerService serviceManager,
								    HAPManagerTemplate templateManager,
								    HAPManagerResourceDefinition resourceDefManager,
								    HAPManagerCronJob cronJobManager,
								    HAPRuntime runtime){
		super();
		this.init(resourceMan, processManager, processRuntime, expressionSuiteManager, gatewayManager, serviceManager, templateManager, resourceDefManager, cronJobManager, runtime);
	}
	
	protected void init(HAPResourceManagerRoot resourceMan,
						HAPManagerProcess processManager,
						HAPRuntimeProcess processRuntime,
						HAPExpressionSuiteManager expressionSuiteManager,
					    HAPGatewayManager gatewayManager,
					    HAPManagerService serviceManager,
					    HAPManagerTemplate templateManager,
					    HAPManagerResourceDefinition resourceDefManager,
					    HAPManagerCronJob cronJobManager,
					    HAPRuntime runtime){ 
		this.m_resourceManager = resourceMan;
		this.m_processManager = processManager;
		this.m_processRuntime = processRuntime;
		this.m_expressionSuiteManager = expressionSuiteManager;
		this.m_serviceManager = serviceManager;
		this.m_resourceDefinitionManager = resourceDefManager;
		this.m_cronJobManager = cronJobManager;

		this.m_resourceManager.registerResourceManager(HAPConstant.RUNTIME_RESOURCE_TYPE_PROCESS, new HAPResourceManagerProcess(this.m_processManager));
		this.m_resourceManager.registerResourceManager(HAPConstant.RUNTIME_RESOURCE_TYPE_ACTIVITYPLUGIN, new HAPResourceManagerActivityPlugin(this.m_processManager.getPluginManager()));
		this.m_resourceManager.registerResourceManager(HAPConstant.RUNTIME_RESOURCE_TYPE_CRONJOB, new HAPResourceManagerCronJob(this.m_cronJobManager));

		
//		this.m_dataSourceManager.registerDataSourceFactory(HAPDataSourceFactoryTask.FACTORY_TYPE, new HAPDataSourceFactoryTask(this.getTaskManager()));
//		this.getTaskManager().registerTaskManager(HAPConstant.DATATASK_TYPE_DATASOURCE, new HAPManagerTaskDatasource(this.getDataSourceManager().getDataSourceDefinitionManager(), this.getDataSourceManager(), runtime));
		
		//gateway
		this.m_gatewayManager = gatewayManager;
		this.getGatewayManager().registerGateway(GATEWAY_RESOURCEDEFINITION, new HAPGatewayResourceDefinition(this));
		this.getGatewayManager().registerGateway(GATEWAY_RESOURCE, new HAPGatewayResource(this));
		this.getGatewayManager().registerGateway(GATEWAY_CRITERIA, new HAPGatewayCriteriaOperation());
		this.getGatewayManager().registerGateway(GATEWAY_DISCOVERY, new HAPGatewayExpressionDiscovery(this.getExpressionSuiteManager(), runtime));
		this.getGatewayManager().registerGateway(GATEWAY_ERRORLOG, new HAPGatewayErrorLogger());
		
		//service factory
		
		this.m_serviceManager.registerServiceFactory(HAPFactoryServiceProcess.FACTORY_TYPE, new HAPFactoryServiceProcess(this.m_processRuntime, this.m_processManager));
		
		//component
		this.getResourceDefinitionManager().registerPlugin(new HAPResourceDefinitionPluginProcessSuite(this.getProcessManager().getPluginManager()));
		this.getResourceDefinitionManager().registerPlugin(new HAPResourceDefinitionPluginProcess(this.getResourceDefinitionManager()));
		this.getResourceDefinitionManager().registerPlugin(new HAPResourceDefinitionPluginCronJob());
		
		//runtime
		this.m_runtime = runtime;
		this.m_runtime.start();
	}
	
	@Override
	public HAPResourceManagerRoot getResourceManager() {		return this.m_resourceManager;	}

	@Override
	public HAPManagerProcess getProcessManager() {  return this.m_processManager;  }

	@Override
	public HAPRuntimeProcess getProcessRuntime() {   return this.m_processRuntime;  }

	@Override
	public HAPExpressionSuiteManager getExpressionSuiteManager(){  return this.m_expressionSuiteManager;  }

	@Override
	public HAPGatewayManager getGatewayManager(){  return this.m_gatewayManager;   }

	@Override
	public HAPManagerService getServiceManager() {  return this.m_serviceManager;   }

	@Override
	public HAPManagerResourceDefinition getResourceDefinitionManager() {  return this.m_resourceDefinitionManager;  }

	@Override
	public HAPManagerTemplate getTmeplateManager() {  return this.m_templateManager;	}

	@Override
	public HAPManagerCronJob getCronJobManager() {  return this.m_cronJobManager;	}

	@Override
	public HAPRuntime getRuntime() {		return this.m_runtime;	}

}
