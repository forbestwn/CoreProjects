package com.nosliw.test.runtimerhino;

import java.util.Map;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataWrapper;
import com.nosliw.data.core.imp.expression.HAPExpressionDefinitionSuiteImp;
import com.nosliw.data.core.imp.expression.HAPExpressionImp;
import com.nosliw.data.core.imp.expression.HAPExpressionImporter;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.data.core.runtime.HAPRunTaskEventListener;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.runtime.HAPRuntimeTask;
import com.nosliw.data.core.runtime.js.rhino.HAPRuntimeTaskExecuteExpressionRhino;
import com.nosliw.data.core.runtime.js.rhino.HAPRuntimeImpRhino;
import com.nosliw.data.expression.test.HAPExpressionTest;

public class HAPRuntimeRhinoMain {

	private static void executeSuite(String suiteName, HAPRuntimeEnvironment runtimeEnvironment){
		//new runtime
		HAPRuntimeImpRhino runtime = (HAPRuntimeImpRhino)runtimeEnvironment.getRuntime();

		try{
			//parse to build expression object
			final HAPExpressionDefinitionSuiteImp suite = (HAPExpressionDefinitionSuiteImp)runtimeEnvironment.getExpressionManager().getExpressionDefinitionSuite(suiteName);
			HAPExpressionImp expression = (HAPExpressionImp)runtimeEnvironment.getExpressionManager().processExpression(null, suiteName, "main", null);
			Map<String, HAPData> varData = suite.getVariableData();
			
			//execute expression
			HAPRuntimeTask task1 = new HAPRuntimeTaskExecuteExpressionRhino(expression, varData);
			task1.registerListener(new HAPRunTaskEventListener(){
				@Override
				public void finish(HAPRuntimeTask task){
					try{
						HAPServiceData serviceData = task.getResult();
						if(serviceData.isSuccess()){
							Object resultObj = serviceData.getData();
							HAPDataWrapper exprectResult = suite.getResult();
							HAPDataWrapper result = new HAPDataWrapper();
							result.buildObject(resultObj, HAPSerializationFormat.JSON);
							System.out.println("Expression Result : " + resultObj);
							if(result.equals(exprectResult)){
							}
							else{
								throw new Exception();
							}
						}
						else{
							if((serviceData.getCode()+"").equals(suite.getErrorCode())){
								System.out.println("Expression Result : " + serviceData.getCode());
							}
							else{
								throw new Exception();
							}
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					finally{
						//shut down runtime
						runtime.close();
					}
				}
			});

			runtime.executeTask(task1);
			
//			HAPRuntimeTask task2 = new HAPRuntimeTaskExecuteExpressionRhino(expression, varData);
//			Object result = runtime.executeTaskSync(task2).getData();
//			System.out.println("Expression Result : " + result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		
		//module init
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();
		
		//start runtime
		runtimeEnvironment.getRuntime().start();
		
		HAPExpressionImporter.importExpressionSuiteFromFolder(HAPFileUtility.getClassFolderName(HAPExpressionTest.class), runtimeEnvironment.getExpressionManager());
		
		executeSuite("expression1", runtimeEnvironment);
		
//		for(String suiteName : expressionMan.getExpressionDefinitionSuites()){
//			executeSuite(suiteName, runtimeEnvironment);
//		}
	}
}
