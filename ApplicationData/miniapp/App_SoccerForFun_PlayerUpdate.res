{
	"name" : "App_SoccerForFun_PlayerUpdate",
	"id" : "App_SoccerForFun_PlayerUpdate",
	"entry": [{
		"name": "main",
		"module": [
			{
				"status": "disabled1111",
				"role": "application",
				"name": "application",
				"module": "Module_SoccerForFun_PlayerUpdate",
				"inputMapping": [
					{
						"element": {
							"player": {
								"definition": {
									"path": "player.name",
								},
							},
						}
					}
				],
			}
		],
		"context" : {
			"group" : {
				"public" : {
					"element" : {
						"player" : {
							"definition" : {
								"child" : {
									"registered" : {criteria:"test.boolean;1.0.0"},
									"name" : {criteria:"test.string;1.0.0"},
									"email" : {criteria:"test.string;1.0.0"},
								}
							},
						}
					}
				}
			}			
		},
		"process": {

		}

	}],

	"pageInfo": [
	],

	"applicationData" : {
	},

}