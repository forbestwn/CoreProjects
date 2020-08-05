/**
 * 
 */
//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoCommon;
	var node_makeObjectWithName;
	var node_makeObjectWithLifecycle;
	var node_getLifecycleInterface;
	var node_createEventObject;
	var node_createBasicLayout;
//*******************************************   Start Node Definition  ************************************** 	

var node_createStoryNodeElementChildInfo = function(nodeElement, connectionId){
	var loc_nodeElement = nodeElement;
	var loc_connectionId = connectionId;
	
	var loc_out = {
		
		getElementNode : function(){   return loc_nodeElement;   },
		getConnectionId : function(){   return loc_connectionId;     },
		
		getNeededSize : function(){   return loc_nodeElement.getNeededSize();  },
		
		setLocation : function(x, y, width, height){   return loc_nodeElement.setLocation(x, y, width, height);  },
		
		addToPaper : function(paper){  return loc_nodeElement.addToPaper(paper);  },
		
	};
	return loc_out;
};
	
var node_createStoryNodeElement = function(storyNodeId, module){
	
	var loc_storyNodeId = storyNodeId;
	var loc_module = module;
	
	var loc_layer = 1;
	
	var loc_graphElement;
	
	var loc_layout = node_createBasicLayout(); 
	
    var loc_createElement = function(){
        var element = new joint.shapes.standard.Rectangle();
        var location = loc_layer.getLocation();
        element.position(location.x, location.y);
        element.resize(location.width, location.height);
        element.attr({
            body: {
                fill: node_storyOverviewUtility.getColorByLayer(loc_layer),
            },
            label: {
                text: loc_storyNodeId,
                fill: 'white'
            }
        });
    	return element
    };
    
	var loc_out = {
		
		getStoryNodeId : function(){	return loc_storyNodeId;	},
	
		addChild : function(nodeElement, connectionId){
			var childInfo = node_createStoryNodeElementChildInfo(nodeElement, connectionId);
			node_storyOverviewUtility.updateChild(childInfo, parentNode);
			loc_layout.addChild(childInfo);
		},
		
		getChildren : function(){   return loc_layout.getChildren();   },
		
		setLayer : function(layer){    loc_layer = layer;    },
		
		getNeededSize : function(){  loc_layout.getNeededSize();  },
		
		setLocation : function(x, y, width, height){   loc_layout.setLocation(x, y, width, height);  },
		
		addToPaper : function(paper){
			if(loc_graphElement==undefined){
				loc_graphElement = loc_createElement();
			}
			loc_graph.addCells(loc_graphElement);
			_.each(this.getChildren(), function(child, i){
				child.addToPaper(paper);
			});
			return loc_graphElement;
		},
	};
	
	return loc_out;
};	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoCommon", function(){	node_createServiceRequestInfoCommon = this.getData();	});
nosliw.registerSetNodeDataEvent("common.objectwithname.makeObjectWithName", function(){node_makeObjectWithName = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.getLifecycleInterface", function(){node_getLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("application.story.module.overview.createBasicLayout", function(){ node_createBasicLayout = this.getData();});

//Register Node by Name
packageObj.createChildNode("createStoryNodeElement", node_createStoryNodeElement); 
packageObj.createChildNode("createStoryNodeElementChildInfo", node_createStoryNodeElementChildInfo); 

})(packageObj);