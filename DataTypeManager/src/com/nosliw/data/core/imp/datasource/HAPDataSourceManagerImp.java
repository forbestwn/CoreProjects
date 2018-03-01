package com.nosliw.data.core.imp.datasource;

import com.nosliw.data.core.datasource.HAPDataSourceManager;
import com.nosliw.data.core.datasource.imp.secondhand.HAPDataSourceFactorySecondHand;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.task.HAPTaskManager;

public class HAPDataSourceManagerImp extends HAPDataSourceManager{

	private HAPTaskManager m_expressionManager;
	
	private HAPRuntime m_runtime;
	
	public HAPDataSourceManagerImp(			
			HAPTaskManager expressionManager,
			HAPRuntime runtime) {
		super(new HAPDataSourceDefinitionManagerImp());
		this.m_expressionManager = expressionManager;
		this.m_runtime = runtime;
		
		this.registerDataSourceFactory(HAPDataSourceFactorySecondHand.DATASOURCEFACTORY_NAME, new HAPDataSourceFactorySecondHand(this.getDataSourceDefinitionManager(), this.m_expressionManager, this.m_runtime));
	}

}
