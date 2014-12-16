(function () {

	function HealthCheckController ($scope, $location, $route, $routeParams, $document, $interval, coreProvider) {

		$scope.mccid = $routeParams.mccid;

		$scope.load_error = '';
		$scope.no_data_yet = false;
		$scope.show_choices = false;
		$scope.mcc_tasks_remaining = -1;

		coreProvider.getMccById($scope.mccid, function (err, results) {
			if (err) {
				$scope.load_error = err;
			} else {
				coreProvider.dataAvailableForMcc($scope.mccid, function (err, available) {
					if (err) {
						$scope.load_error = err;
						$scope.show_choices = true;
						return;
					}

					var ready =  (Array.isArray(available.reports_available)
							&& available.reports_available.length > 0);
					if (!ready) {
						$scope.no_data_yet = true;
						$scope.start_data_timer();
					} else {
						coreProvider.store_value_for_key("verified_loaded_" + $scope.mccid, true);
						$scope.show_choices = true;
					}
				});
			}
		});
	}

	awrcApp.controller("HealthCheckController", HealthCheckController);

})();
