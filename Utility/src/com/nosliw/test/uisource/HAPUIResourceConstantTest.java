package com.nosliw.test.uisource;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.imp.runtime.js.HAPRuntimeEnvironmentImpJS;
import com.nosliw.uiresource.HAPConstantDef;
import com.nosliw.uiresource.HAPUIResourceIdGenerator;

public class HAPUIResourceConstantTest {

	public static void main(String[] agrs) throws JSONException{

		//module init
		HAPRuntimeEnvironmentImpJS runtimeEnvironment = new HAPRuntimeEnvironmentImpJS();
		
		//start runtime
		runtimeEnvironment.getRuntime().start();

		String file = HAPFileUtility.getFileNameOnClassPath(HAPUIResourceTest.class, "Constants.txt");
		String fileContent = HAPFileUtility.readFile(new File(file));
		JSONObject rootJson = new JSONObject(fileContent);

		//expected output
		Map<String, Object> constantsExprectOutput = new LinkedHashMap<String, Object>();
		JSONObject expectOutputJson = rootJson.getJSONObject("expectedOutput");
		Iterator<String> outputConstantNames = expectOutputJson.keys();
		while(outputConstantNames.hasNext()){
			String name = outputConstantNames.next();
			constantsExprectOutput.put(name, expectOutputJson.get(name));
		}
		
		Map<String, HAPConstantDef> constantsDef = new LinkedHashMap<String, HAPConstantDef>();
		JSONObject constantsJson = rootJson.getJSONObject("constants");
		Iterator<String> defNames = constantsJson.keys();
		while(defNames.hasNext()){
			String defName = defNames.next();
			constantsDef.put(defName, new HAPConstantDef(constantsJson.get(defName)));
		}
		
		//process constant defs
		HAPUIResourceIdGenerator idGenerator = new HAPUIResourceIdGenerator(1);
		for(String name : constantsDef.keySet()){
			HAPConstantDef constantDef = constantsDef.get(name);
			constantDef.process(constantsDef, idGenerator, runtimeEnvironment.getExpressionManager(), runtimeEnvironment.getRuntime());
			compareOutput(name, constantDef, constantsExprectOutput.get(name));
		}

		//compare output
		for(String name : constantsDef.keySet()){
			HAPConstantDef constantDef = constantsDef.get(name);
			compareOutput(name, constantDef, constantsExprectOutput.get(name));
		}
	}

	private static void compareOutput(String name, HAPConstantDef constantDef, Object expected){
		boolean result = false;
		if(expected instanceof JSONObject || expected instanceof JSONArray){
			result = constantDef.getValue().toString().equals(expected.toString());
		}
		else{
			result = constantDef.getValue().equals(expected);
		}

		System.out.println();
		System.out.println();
		if(result)		System.out.println("------   " + name +  "   ------");
		else  		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX   " + name +  "   XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println(constantDef.getValue().toString());
		System.out.println();
		System.out.println(expected.toString());
		if(result)		System.out.println("------   " + name +  "   ------");
		else  		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX   " + name +  "   XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println();
		System.out.println();
	}
	
}