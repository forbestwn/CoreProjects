package com.nosliw.data.core.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.service.interfacee.HAPServiceParm;

@HAPEntityWithAttribute
public class HAPManagerService {

	private Map<String, HAPFactoryService> m_serviceFactorys;
	
	private HAPManagerServiceDefinition m_serviceDefinitionManager;
	
	private Map<String, HAPInstanceService> m_serviceInstances;
	
	public HAPManagerService(){
		this.m_serviceDefinitionManager = new HAPManagerServiceDefinition();
		this.m_serviceInstances = new LinkedHashMap<String, HAPInstanceService>();
		this.m_serviceFactorys = new LinkedHashMap<String, HAPFactoryService>();
	}
	
	public HAPManagerServiceDefinition getDataSourceDefinitionManager() {   return this.m_serviceDefinitionManager;   }
	
	public void registerServiceInstance(String name, HAPInstanceService dataSource){
		this.m_serviceInstances.put(name, dataSource);
	}

	public void registerServiceFactory(String name, HAPFactoryService serviceFactory){
		this.m_serviceFactorys.put(name, serviceFactory);
	}
	
	public HAPResultService execute(String serviceId, Map<String, HAPData> parms){
		//get service instance according to serviceId
		HAPInstanceService serviceInstance = this.m_serviceInstances.get(serviceId);
		if(serviceInstance==null){
			try{
				//not exists, then create one using factory
				HAPDefinitionService serviceDef = this.m_serviceDefinitionManager.getDefinition(serviceId);
				HAPExecutableService serviceExe;
				String imp = serviceDef.getRuntimeInfo().getImplementation();
				if(imp.contains(".")){
					//it is class name
					serviceExe = (HAPExecutableService)Class.forName(imp).newInstance();
				}
				else{
					//it is factory name
					serviceExe = this.m_serviceFactorys.get(imp).newService(serviceDef);
				}
				serviceInstance = new HAPInstanceService(serviceDef, serviceExe);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(serviceInstance!=null)   this.registerServiceInstance(serviceId, serviceInstance);
		}
		
		//execute service instance
		HAPResultService out = null;
		if(serviceInstance!=null) {
			Map<String, HAPData> serviceParms = new LinkedHashMap<String, HAPData>();
			Map<String, HAPServiceParm> parmsDef = serviceInstance.getDefinition().getStaticInfo().getInterface().getParms();
			for(String parmName : parmsDef.keySet()) {
				HAPData parmData = parms.get(parmName);
				if(parmData==null) parmData = parmsDef.get(parmName).getDefault();   //not provide, use default 
				serviceParms.put(parmName, parmData);
			}
			out = serviceInstance.getExecutable().execute(serviceParms);
		}
		return out;
	}
	
}
