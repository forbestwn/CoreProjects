package com.nosliw.data.core.runtime.js.rhino.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.core.expression.HAPExecutableExpression;
import com.nosliw.data.core.expression.HAPExecutableExpressionItem;
import com.nosliw.data.core.expression.HAPUtilityExpressionResource;
import com.nosliw.data.core.resource.HAPResourceInfo;
import com.nosliw.data.core.runtime.HAPRunTaskEventListener;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.runtime.HAPRuntimeTask;
import com.nosliw.data.core.runtime.js.HAPJSScriptInfo;
import com.nosliw.data.core.runtime.js.HAPRuntimeJSScriptUtility;
import com.nosliw.data.core.runtime.js.rhino.HAPRuntimeImpRhino;
import com.nosliw.data.core.script.expression.HAPExecutableScriptGroup;

public class HAPRuntimeTaskExecuteScriptExpression extends HAPRuntimeTaskExecuteScriptExpressionAbstract{

	final public static String TASK = "ExecuteScriptExpression"; 
	
	@HAPAttribute
	public static String SCRIPTEXPRESSION = "scriptExpression";

	@HAPAttribute
	public static String VARIABLESVALUE = "variablesValue";
	
	private HAPExecutableScriptGroup m_scriptGroup;
	
	private Object m_scriptId;
	
	//variable value can be data or other object
	Map<String, Object> m_variablesValue;
	
	Map<String, Object> m_scriptConstants;
	
	public HAPRuntimeTaskExecuteScriptExpression(
			HAPExecutableScriptGroup scriptGroup, 
			Object scriptId,
			Map<String, Object> variablesValue, 
			Map<String, Object> scriptConstants){
		this.m_scriptGroup = scriptGroup;
		this.m_scriptId = scriptId;
		this.m_variablesValue = variablesValue; 
		this.m_scriptConstants = scriptConstants!=null?scriptConstants:new LinkedHashMap<String, Object>();
	}
	
	@Override
	public String getTaskType() {		return TASK;	}

	@Override
	public String getScriptFunction() {		return HAPRuntimeJSScriptUtility.buildScriptJSFunction(this.m_scriptGroup, this.m_scriptId);	}
	@Override
	public Map<String, HAPExecutableExpressionItem> getExpressionItems(){  return this.m_scriptGroup.getExpression().getExpressionItems(); }
	@Override
	public Map<String, Object> getVariablesValue(){  return this.m_variablesValue;  }
	@Override
	public Map<String, Object> getScriptConstants(){  return this.m_scriptConstants;  }

//	public HAPScriptExpression getScriptExpression(){ return this.m_scriptGroup;  }
	
	@Override
	public HAPRuntimeTask execute(HAPRuntime runtime) {
		try{
			HAPRuntimeImpRhino rhinoRuntime = (HAPRuntimeImpRhino)runtime;
			
			//prepare resources for expression in the runtime (resource and dependency)
			//execute expression after load required resources
			List<HAPExecutableExpression> expressions = new ArrayList();
			expressions.add(this.m_scriptGroup.getExpression());
			List<HAPResourceInfo> resourcesId =  HAPUtilityExpressionResource.discoverResourceRequirement(expressions, rhinoRuntime.getRuntimeEnvironment().getResourceManager(), runtime.getRuntimeInfo());
			HAPRuntimeTask loadResourcesTask = new HAPRuntimeTaskLoadResourcesRhino(resourcesId);
			loadResourcesTask.registerListener(new HAPRunTaskEventListenerInner(this, rhinoRuntime));
			return loadResourcesTask;
		}
		catch(Exception e){
			this.finish(HAPServiceData.createFailureData(e, ""));
			e.printStackTrace();
		}
		return null;
	}

	class HAPRunTaskEventListenerInner implements HAPRunTaskEventListener{
		private HAPRuntimeTaskExecuteScriptExpression m_parent;
		private HAPRuntimeImpRhino m_runtime;
		
		public HAPRunTaskEventListenerInner(HAPRuntimeTaskExecuteScriptExpression parent, HAPRuntimeImpRhino runtime){
			this.m_parent = parent;
			this.m_runtime = runtime;
		}
		
		@Override
		public void finish(HAPRuntimeTask task) {
			HAPServiceData resourceTaskResult = task.getResult();
			if(resourceTaskResult.isSuccess()){
				//after resource loaded, execute expression
				try{
					HAPJSScriptInfo scriptInfo = HAPRuntimeJSScriptUtility.buildRequestScriptForExecuteScriptExpressionTask(this.m_parent, this.m_runtime);
					this.m_runtime.loadTaskScript(scriptInfo, m_parent.getTaskId());
				}
				catch(Exception e){
					this.m_parent.finish(HAPServiceData.createFailureData(e, ""));
				}
			}
			else{
				this.m_parent.finish(resourceTaskResult);
			}
		}
	}
}
