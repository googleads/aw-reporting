(function () {

	function HomeTemplatesController ($scope, $location, coreProvider) {

		$scope.loading_task_count = 1;
		$scope.load_templates_error = "";
		$scope.templates = [];

		coreProvider.getTemplates(function (err, templates) {
			if (!err) {
				$scope.templates = templates;
			} else {
				$scope.load_templates_error = "Unable to load your Templates (" + err.error + ": " + err.message + ")";
			}

			$scope.loading_task_count--;
		});

		$scope.createTemplate = function () {
			$location.path("/template");  
		};
	}

	awrcApp.controller("HomeTemplatesController", HomeTemplatesController);

})();
