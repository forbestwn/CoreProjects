package com.nosliw.data.core.complex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.data.core.complex.attachment.HAPContainerAttachment;
import com.nosliw.data.core.complex.valuestructure.HAPComplexValueStructure;
import com.nosliw.data.core.component.HAPContextProcessor;
import com.nosliw.data.core.domain.HAPContainerEntity;
import com.nosliw.data.core.domain.HAPContextDomain;
import com.nosliw.data.core.domain.HAPDomainAttachment;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPDomainExecutableEntity;
import com.nosliw.data.core.domain.HAPDomainValueStructure;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPInfoComplexEntityDefinition;
import com.nosliw.data.core.domain.HAPInfoContainerElement;
import com.nosliw.data.core.domain.HAPInfoDefinitionEntityInDomain;
import com.nosliw.data.core.domain.HAPInfoDefinitionEntityInDomainComplex;

public class HAPManagerComplexEntity {

	private Map<String, HAPPluginComplexEntityProcessor> m_processorPlugins;

	public HAPManagerComplexEntity() {
		this.m_processorPlugins = new LinkedHashMap<String, HAPPluginComplexEntityProcessor>();
	}
	
	public HAPIdEntityInDomain process(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
		
		//build executable complexe entity
		Set<HAPIdEntityInDomain> rootIds = defDomain.getRootComplexEntity();
		for(HAPIdEntityInDomain rootId : rootIds) {
			buildExecutableTree(rootId, processContext);
		}
		
		//process attachment
		processAttachmentTree(processContext);
		
		//process value structure
		processValueStructureTree(processContext);
		
		
		
		HAPPluginComplexEntityProcessor processPlugin = this.m_processorPlugins.get(complexEntityDefinitionId.getEntityType());
		processPlugin.process(complexEntityDefinitionId, processContext);
		return out;
	}

	private void processAttachmentTree(HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();

		Set<HAPIdEntityInDomain> rootIds = defDomain.getRootComplexEntity();
		for(HAPIdEntityInDomain rootId : rootIds) {
			buildExecutableTree(rootId, processContext);
		}
	}
	
	private void processValueStructureTree(HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();

		//
		Set<HAPInfoComplexEntityDefinition> complexEntityInfos = defDomain.getAllComplexEntities();
		for(HAPInfoComplexEntityDefinition complexEntityInfo : complexEntityInfos) {
			
		}
	}
	
	private HAPIdEntityInDomain buildExecutableTree(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
		HAPDomainValueStructure valueStructureDomain = exeDomain.getValueStructureDomain();
		HAPDomainAttachment attachmentDomain = exeDomain.getAttachmentDomain();
		
		//create executable and add to domain
		HAPInfoDefinitionEntityInDomainComplex entityDefInfo = defDomain.getComplexEntityInfo(complexEntityDefinitionId);
		HAPDefinitionEntityComplex complexEntityDef = entityDefInfo.getComplexEntity();
		String entityType = complexEntityDef.getEntityType();
		HAPExecutableEntityComplex exeEntity = this.m_processorPlugins.get(entityType).newExecutable();
		HAPIdEntityInDomain complexeEntityExeId = domainContext.addExecutableEntity(exeEntity, complexEntityDefinitionId);
		HAPExecutableEntityComplex complexEntityExe = exeDomain.getExecutableEntity(complexeEntityExeId);
		
		//add attachment container
		attachmentDomain.addAttachmentContainer((HAPContainerAttachment)defDomain.getEntityInfo(complexEntityDef.getValueStructureComplexId()).getEntity(), complexeEntityExeId);
		
		//add value structure to domain
		String valueStructureComplexId = valueStructureDomain.addValueStructureComplex((HAPComplexValueStructure)defDomain.getEntityInfo(complexEntityDef.getValueStructureComplexId()).getEntity());
		complexEntityExe.setValueStructureComplexId(valueStructureComplexId);
		
		
		//build executable for simple complex attribute
		Map<String, HAPIdEntityInDomain> simpleAttributes = complexEntityDef.getSimpleAttributes();
		for(String attrName : simpleAttributes.keySet()) {
			HAPIdEntityInDomain attrEntityDefId = simpleAttributes.get(attrName);
			HAPInfoDefinitionEntityInDomain entityInfo = defDomain.getEntityInfo(attrEntityDefId);
			if(entityInfo.isComplexEntity()) {
				HAPIdEntityInDomain attrEntityExeId = buildExecutableTree(attrEntityDefId, processContext);
				complexEntityExe.setSimpleComplexAttribute(attrName, attrEntityExeId);
			}
		}
		
		//build executable for container complex attribute
		Map<String, HAPContainerEntity> containerAttributes = complexEntityDef.getContainerAttributes();
		for(String attrName : containerAttributes.keySet()) {
			HAPContainerEntity container = containerAttributes.get(attrName);
			List<HAPInfoContainerElement> eleInfos = container.getElements();
			for(HAPInfoContainerElement eleInfo : eleInfos) {
				HAPIdEntityInDomain eleId = eleInfo.getElementId();
				HAPInfoDefinitionEntityInDomain eleEntityInfo = defDomain.getEntityInfo(eleId);
				if(eleEntityInfo.isComplexEntity()) {
					HAPIdEntityInDomain eleEntityExeId = buildExecutableTree(eleId, processContext);
					HAPInfoContainerElement exeEleInfo = eleInfo.cloneElementInfo();
					exeEleInfo.setElementId(eleEntityExeId);
					complexEntityExe.addContainerAttributeElementComplex(attrName, exeEleInfo);
				}
			}
		}
		
		return complexeEntityExeId;
	}
	
	
	public void registerProcessorPlugin(String complexEntityType, HAPPluginComplexEntityProcessor processorPlugin) {
		this.m_processorPlugins.put(complexEntityType, processorPlugin);
	}
	
}
