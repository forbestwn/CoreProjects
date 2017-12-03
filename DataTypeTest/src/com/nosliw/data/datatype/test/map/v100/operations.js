//run seperately
//		which data type (name + version) belong to
//      dependency
//		each operation (operation name, script, dependency)

var dataTypeDefition = nosliw.getDataTypeDefinition("test.map");

//define what this data type globlely requires (operation, datatype, library)
dataTypeDefition.requires = {
};

//define what operations in this page requires (operation, datatype, library)

//define operation
//define operation
dataTypeDefition.operations['put'] = {
		//defined operation
		//in operation can access all the required resources by name through context
		operation : function(parms, context){
			var name = parms.getParm("name").value;
			var data = parms.getParm("value");
			this.value[name] = data;
			return this;
		},
};


dataTypeDefition.operations['getChildrenNames'] = {
		operation : function(parms, context){
			var names = [];
			_.each(this.value, function(mapValue, name){
				names.push({
					dataTypeId : "test.string;1.0.0",
					value : name
				});
			});
			
			return {
				dataTypeId : "test.array;1.0.0",
				value : names
			};
		},
};

dataTypeDefition.operations['getChildData'] = {
		operation : function(parms, context){
			var name = parms.getParm("name").value;
			return this.value[name];
		}
};



dataTypeDefition.operations['new'] = {
		//defined operation
		//in operation can access all the required resources by name through context
		operation : function(parms, context){
			return {
				dataTypeId : "test.map;1.0.0",
				value : {},
			};
		},
};

nosliw.addDataTypeDefinition(dataTypeDefition);
