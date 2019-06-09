package com.nosliw.data.core.service.provide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPImporterDataSourceDefinition {

	private static String[] serviceClasses = {
			"com.nosliw.service.school.HAPServiceImp",
			"com.nosliw.service.realtor.HAPServiceImp",
	};
	
	public static List<HAPDefinitionService> loadDataSourceDefinition() {
		List<HAPDefinitionService> out = new ArrayList<HAPDefinitionService>();

		for(String serviceClasse : serviceClasses) {
			Class cls;
			try {
				cls = Class.forName(serviceClasse);
				HAPDefinitionService dataSourceDef = loadDataSourceDefinition(cls);
				if(dataSourceDef!=null)		out.add(dataSourceDef);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
/*		
		new HAPClassFilter(){
			@Override
			protected void process(Class cls, Object data) {
				HAPDefinitionService dataSourceDef = loadDataSourceDefinition(cls);
				if(dataSourceDef!=null)		out.add(dataSourceDef);
			}

			@Override
			protected boolean isValid(Class cls) {
				Class[] interfaces = cls.getInterfaces();
				for(Class inf : interfaces){
					if(inf.equals(HAPProviderService.class)){
						return true;
					}
				}
				return false;
			}
		}.process(null);
*/		
		return out;
	}
	
	private static HAPDefinitionService loadDataSourceDefinition(Class cls){
		HAPDefinitionService out = null;
		try{
			InputStream inputStream = cls.getResourceAsStream("service.ds");
			if(inputStream!=null) {
				String content = HAPFileUtility.readFile(inputStream);
				out = new HAPDefinitionService();
				out.buildObject(new JSONObject(content), HAPSerializationFormat.JSON);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}

}
