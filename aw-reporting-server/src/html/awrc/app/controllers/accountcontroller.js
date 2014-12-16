(function () {

	function AccountController ($scope, $location, $route, $routeParams, $document, $interval, $filter, $window, coreProvider) {

		$scope.mccid = $routeParams.mccid;
		$scope.load_error = '';
		$scope.no_data_yet = false;
		$scope.show_choices = false;
		$scope.mcc_tasks_remaining = -1;
		$scope.show_date_picker = false;

		$scope.kratu_no_data = false;
		$scope.reports_no_data = false;

		$scope.TOTAL_LOADING_TASKS = 4;
		$scope.loading_task_count = 4;

		coreProvider.getMccById($scope.mccid, function (err, results) {
			if (err) {
				$scope.load_error = err;
			} else {
				var kratus_available_loaded = false;
				var reports_available_loaded = false;

				// Get daily available data for Kratu
				coreProvider.dataAvailableForMcc($scope.mccid, 'day', function (err, available) {
					if (err) {
						$scope.load_error = err;
						$scope.show_choices = false;
						return;
					}
					kratus_available_loaded = (available.kratus_available != null);
					
					// If true, Kratu disabled in view
					$scope.kratu_no_data =	kratus_available_loaded && ! available.kratus_available.endDay;

					setKratuDateRange(available);
					if(kratus_available_loaded && reports_available_loaded) {
						coreProvider.store_value_for_key("verified_loaded_" + $scope.mccid, true);
						$scope.show_choices = true;
					}
				});

				// Get monthly available data for reporting functions
				coreProvider.dataAvailableForMcc($scope.mccid, 'monthly', function (err, available) {
					if (err) {
						$scope.load_error = err;
						$scope.show_choices = false;
						return;
					}
					reports_available_loaded = Array.isArray(available.reports_available);
					
					// If true, export report and raw data functions disabled in UI
					$scope.reports_no_data = reports_available_loaded && available.reports_available.length <= 0;
					setReportsDateRange(available);

					if(kratus_available_loaded && reports_available_loaded) {
						coreProvider.store_value_for_key("verified_loaded_" + $scope.mccid, true);
						$scope.show_choices = true;
					}
				});
			}
		});

		setReportsDateRange = function(available_data) {
			if (available_data && available_data.reports_available
					&& Array.isArray(available_data.reports_available)) {
				for (var i = 0; i < available_data.reports_available.length; i++) {
					var availableReport = available_data.reports_available[i];
					if (availableReport.ReportType == "ReportAccount") {
						$scope.reports_min_date = new Date(availableReport.startMonth);
						$scope.reports_max_date = new Date(availableReport.endMonth);
						break;
					}
				}
			}
		};

		setKratuDateRange = function(available_data) {
			if (available_data && available_data.kratus_available) {
				$scope.kratu_min_date = new Date(available_data.kratus_available.startDay);
				$scope.kratu_max_date = new Date(available_data.kratus_available.endDay);
			}
		};

		$scope.scroll_to_date_selection = function () {
			var date_section = angular.element(document.getElementById('date_section'));
			$document.scrollTo(date_section, 0, 700);
		};
	}

	awrcApp.controller("AccountController", AccountController);

})();
