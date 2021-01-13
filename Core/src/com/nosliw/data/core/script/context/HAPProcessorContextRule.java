package com.nosliw.data.core.script.context;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.data.variable.HAPDataRule;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPProcessorContextRule {

	//process rule in data variable in context 
	public static HAPContextGroup process(HAPContextGroup orgContext, HAPRuntimeEnvironment runtimeEnv) {

		for(String group : orgContext.getAllContextTypes()) {
			HAPContext context = orgContext.getContext(group);
			for(String eleName : context.getElementNames()) {
				HAPUtilityContext.processContextDefElement(context.getElement(eleName).getDefinition(), new HAPContextDefEleProcessor() {
					@Override
					public boolean process(HAPContextDefinitionElement ele, Object obj) {
						if(ele.getType().equals(HAPConstant.CONTEXT_ELEMENTTYPE_DATA)) {
							HAPContextDefinitionLeafData dataEle = (HAPContextDefinitionLeafData)ele;
							for(HAPDataRule rule : dataEle.getDataInfo().getRules()) {
								rule.process(dataEle.getCriteria(), runtimeEnv);
							}
						}
						return true;
					}

					@Override
					public boolean postProcess(HAPContextDefinitionElement ele, Object value) {
						return true;
					}
				}, null);
			}
		}
		

		
		return orgContext;
	}

}