/**
 * 
 */
//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_storyChangeUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createComponentQuestionItemSwitch = function(){

	var loc_vueComponent = {
		data : function(){
			return {
			};
		},
		props : ['data'],
		components : {
		},
		methods : {
		},
		computed: {
			choices : {
				get : function(){
					var out = [];
					_.each(this.data.element.elements, function(ele, i){
						out.push(ele.name);
					});
				}
			},
			serviceId : {
				get : function(){
					return this.data.element.choice;
				},
				
				set : function(choiceId){
					var changeItem = node_storyChangeUtility.createChangeItemPatch(this.data.element, node_COMMONATRIBUTECONSTANT.ELEMENTGROUPSWITCH_CHOICE, choiceId);
					node_storyChangeUtility.applyChangeToElement(this.data.element, changeItem);
					this.data.changes.push(changeItem);
				}
			}
		},
		template : `
			<div>
				<select v-model="serviceId">
				  <option v-for="choice in this.data.element.elements" v-bind:value="choice.id">
				    {{ choice.name }}
				  </option>
				</select>			

				<br>
				Question Switch
				<br>
			</div>
		`
	};
	return loc_vueComponent;
};	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.storyChangeUtility", function(){node_storyChangeUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createComponentQuestionItemSwitch", node_createComponentQuestionItemSwitch); 

})(packageObj);
