{
	"parts" : {
		"nosliw_decoration1" :{
			"parts" : [
				{
					"parts" : {
						"nosliw_info" : {
							"name" : "firstDec",
							"type" : "testdecoration1",
							"id": "decoration1",
							"description": "this is for testing decoration"
						},
						"nosliw_core" : {
							"nosliw_debug" : "true",
							"configureDec1" : "configureDec1Value",
							"configureDec2" : ["configureDec21Value", "configureDec22Value", "configureDec23Value"],
						}
					}
				}
			],
		},
		"nosliw_core" : {
			"nosliw_debug_package" : "true",
			"nosliw_debug" : "true",
			"configure1" : "configure1Value",
			"configure2" : ["configure21Value", "configure22Value", "configure23Value"],
			"globalresource2_none_testcomplex1" : {
				"nosliw_debug_package" : "true",
				"nosliw_debug" : "true",
			}
		}
	},
}