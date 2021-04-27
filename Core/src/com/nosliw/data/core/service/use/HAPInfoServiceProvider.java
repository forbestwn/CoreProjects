package com.nosliw.data.core.service.use;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.data.core.service.definition.HAPDefinitionService;
import com.nosliw.data.core.structure.dataassociation.HAPDefinitionDataMappingTask;

//store all the info related with service provider attachment
public class HAPInfoServiceProvider extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static String DATAMAPPING = "dataMapping";

	//service definition of provider
	private HAPDefinitionService m_serviceDef;
	
	//data mapping to adaptor service provider to service use
	private HAPDefinitionDataMappingTask m_dataMapping;
	
	public HAPInfoServiceProvider(HAPEntityInfo entityInfo, HAPDefinitionService serviceDef, HAPDefinitionDataMappingTask dataMapping) {
		entityInfo.cloneToEntityInfo(this);
		this.m_serviceDef = serviceDef;
		this.m_dataMapping = dataMapping;
	}
	
	public HAPDefinitionService getServiceDefinition() {    return this.m_serviceDef;  }
	
	public HAPDefinitionDataMappingTask getDataMapping() {   return this.m_dataMapping;    }
	
}
