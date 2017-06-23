package com.nosliw.data.core.runtime.js.rhino;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdScriptableObject; 
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJSON;
import org.mozilla.javascript.NativeJavaObject; 
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager; 
 
/**
 * Collection of JSON Utility methods. 
 *  
 */ 
 
public class HAPRhinoDataUtility 
{ 
    /**
     * Takes a java object and converts it to a native java script object 
     *  
     * @param  jsonString       a valid json string 
     * @return NativeObject     the created native JS object that represents the JSON object 
     */ 
    public static ScriptableObject toRhinoScriptableObjectFromObject(Object obj) 
    { 
        // Parse JSON string 
    	try { 
    		String jsonString = HAPSerializeManager.getInstance().toStringValue(obj, HAPSerializationFormat.JSON);
    		Object json = null;

    		try{json = new JSONObject(jsonString);}catch(Exception e){}

    		if(json==null){
        		try{json = new JSONArray(jsonString);}catch(Exception e){}
    		}
    		if(json==null)  json = jsonString;
    		
    		// Create native object
    		return toSciptableObjectFromJson(json);
    	} catch(Exception e){ 
    		e.printStackTrace(); 
    	} 
    	return null;
    } 
     
    private static ScriptableObject toSciptableObjectFromJson(Object json) throws JSONException{
		ScriptableObject out = null;
    	if(json instanceof JSONObject){
    		JSONObject jsonObject = (JSONObject)json;
            NativeObject object = new NativeObject(); 
            Iterator<String> keys = jsonObject.keys(); 
            while (keys.hasNext()) 
            { 
                String key = (String)keys.next(); 
                Object value = jsonObject.get(key); 
                if (value instanceof JSONObject || value instanceof JSONArray)                object.put(key, object, toSciptableObjectFromJson(value)); 
                else            object.put(key, object, value); 
            }  
            return object;
    	}
    	else if(json instanceof JSONArray){
    		JSONArray jsonArray = (JSONArray)json;
        	int length = jsonArray.length();
        	NativeArray array = new NativeArray(length);
        	for(int i=0; i<length; i++){
        		Object value = jsonArray.get(i);
                if (value instanceof JSONObject || value instanceof JSONArray)            array.put(i, array, toSciptableObjectFromJson(value)); 
                else           array.put(i, array, value); 
        	}
        	return array;
    	}
    	return out;
    }
    
    public static String toJSONStringValue(Object object, Context context, Scriptable scope){
    	String json = (String)NativeJSON.stringify(context, scope, object, null, null);
    	return json;
    }
	
	
    /**
     * Converts a given JavaScript native object and converts it to the relevant JSON string. 
     *  
     * @param object            JavaScript object 
     * @return String           JSON       
     */ 
    public static String toJSONString(Object object) 
    { 
    	if(object instanceof String){
    		return object.toString();
    	}
    	else if (object instanceof NativeArray) 
        { 
            return nativeArrayToJSONString((NativeArray)object); 
        } 
        else if (object instanceof NativeObject) 
        {  
           return nativeObjectToJSONString((NativeObject)object); 
        } 
    	return null; 
    } 
     
    /**
     * Build a JSON string for a native object 
     *  
     * @param nativeObject 
     * @param json 
     */ 
    private static String nativeObjectToJSONString(NativeObject nativeObject) 
    { 
    	try{
        	JSONObject json = new JSONObject(); 
            
            Object[] ids = nativeObject.getIds(); 
            for (Object id : ids) 
            { 
                String key = id.toString(); 
                Object value = nativeObject.get(key, nativeObject); 
                json.put(key, valueToJSONString(value)); 
            } 
         
            return json.toString();  
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    } 
     
    /**
     * Build JSON string for a native array 
     *  
     * @param nativeArray 
     */ 
    private static String nativeArrayToJSONString(NativeArray nativeArray) 
    { 
    	try{
            Object[] propIds = nativeArray.getIds(); 
            if (isArray(propIds) == true) 
            {       
                JSONArray jsonArray = new JSONArray(); 
                for (int i=0; i<propIds.length; i++) 
                { 
                    Object propId = propIds[i]; 
                    if (propId instanceof Integer) 
                    { 
                        Object value = nativeArray.get((Integer)propId, nativeArray); 
                        jsonArray.put(valueToJSONString(value)); 
                    } 
                } 
                return jsonArray.toString(); 
            } 
            else 
            { 
            	JSONObject json = new JSONObject(); 
                for (Object propId : propIds) 
                { 
                    Object value = nativeArray.get(propId.toString(), nativeArray); 
                    json.put(propId.toString(), valueToJSONString(value)); 
                } 
                return json.toString();
            }
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    } 
     
    /**
     * Look at the id's of a native array and try to determine whether it's actually an Array or a HashMap 
     *  
     * @param ids       id's of the native array 
     * @return boolean  true if it's an array, false otherwise (ie it's a map) 
     */ 
    static private boolean isArray(Object[] ids) 
    { 
        boolean result = true; 
        for (Object id : ids) 
        { 
            if (id instanceof Integer == false) 
            { 
               result = false; 
               break; 
            } 
        } 
        return result; 
    } 
     
    /**
     * Convert value to JSON string 
     *  
     * @param value 
     */ 
    private static String valueToJSONString(Object value) 
    {
    	try{
    		 JSONObject json = new JSONObject(); 
    	        if (value instanceof IdScriptableObject && 
    	            ((IdScriptableObject)value).getClassName().equals("Date") == true) 
    	        { 
    	            // Get the UTC values of the date 
    	            Object year = NativeObject.callMethod((IdScriptableObject)value, "getUTCFullYear", null); 
    	            Object month = NativeObject.callMethod((IdScriptableObject)value, "getUTCMonth", null); 
    	            Object date = NativeObject.callMethod((IdScriptableObject)value, "getUTCDate", null); 
    	            Object hours = NativeObject.callMethod((IdScriptableObject)value, "getUTCHours", null); 
    	            Object minutes = NativeObject.callMethod((IdScriptableObject)value, "getUTCMinutes", null); 
    	            Object seconds = NativeObject.callMethod((IdScriptableObject)value, "getUTCSeconds", null); 
    	            Object milliSeconds = NativeObject.callMethod((IdScriptableObject)value, "getUTCMilliseconds", null); 
    	             
    	            // Build the JSON object to represent the UTC date 
    	            
    	            json.put("zone","UTC"); 
    	     json.put("year",year); 
    	     json.put("month",month); 
    	     json.put("date",date); 
    	     json.put("hours",hours); 
    	     json.put("minutes",minutes); 
    	     json.put("seconds",seconds); 
    	     json.put("milliseconds",milliSeconds); 
    	     return json.toString(); 
    	        } 
    	        else if (value instanceof NativeJavaObject) 
    	        { 
    	            Object javaValue = Context.jsToJava(value, Object.class); 
    	            return javaValue.toString(); 
    	        } 
    	        else if (value instanceof NativeArray) 
    	        { 
    	            // Output the native array 
    	            return nativeArrayToJSONString((NativeArray)value); 
    	        } 
    	        else if (value instanceof NativeObject) 
    	        { 
    	            // Output the native object 
    	            return nativeObjectToJSONString((NativeObject)value); 
    	        } 
    	        else if( value instanceof Function){
    	        	return Context.toString(value);
    	        }
    	        else 
    	        { 
    	           return value.toString();  
    	        } 
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    	
    }     
}