(function () {

	function HomeAccountsListController ($scope, $location, $modal, $route, coreProvider) {

		$scope.loading_task_count = 1;
		$scope.load_mccs_error = "";
		$scope.mccs = [];

		coreProvider.getMccs(function (err, mccs) {
			if (!err) {
				$scope.mccs = mccs;
			} else {
				$scope.load_mccs_error = "Unable to load your MCCs (" + err.error + ": " + err.message + ")";
			}

			$scope.loading_task_count--;
		});

		$scope.selectMCC = function (mccid) {
			$location.path("/account/" + format_ccid(mccid));
		};
	}

	awrcApp.controller("HomeAccountsListController", HomeAccountsListController);

})();
