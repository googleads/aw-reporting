(function () {

	function DocExportController ($scope, $location, $route, $routeParams, $document, $interval, $filter, $window, coreProvider) {
		$scope.mccid = $routeParams.mccid;

		// make sure we've verified we have data ready to go.
		if (!coreProvider.get_value_for_key("verified_loaded_" + $scope.mccid)) {
			$location.path("/account/" + $scope.mccid);
			return;
		}

		$scope.mccid = $routeParams.mccid;

		$scope.step = 0;
		$scope.no_data_yet = false;

		$scope.TOTAL_LOADING_TASKS = 4;
		$scope.loading_task_count = 4;
		$scope.load_error_text = '';
		$scope.go_button = "";
		$scope.client_accounts = [];
		$scope.mcc_info = null;
		$scope.templates = [];

		$scope.accounts = [];
		$scope.source_accounts = [];
		$scope.selected_accounts = [];
		$scope.selected_account = null;

		$scope.report_selected = null;
		$scope.report_selectedIndex = -1;
		$scope.account_selectedIndexes = [];
		$scope.selectedAccount_selectedIndexes = [];
		$scope.report_url = "";

		$scope.mcc_tasks_remaining = -1;
		$scope.export_tasks_remaining = -1;
		$scope.select_all = 1;

		var emsg = "Unable to load account info";

		async.waterfall([
			   function (cb) {
			  	 // 1. get the core mcc info
			  	 coreProvider.getMccById($scope.mccid, cb);
			   },
			   function (mcc, cb) {
			  	 $scope.mcc_info = mcc;
			  	 if (!$scope.mcc_info.name)
			  		 $scope.mcc_info.name = format_ccid($scope.mcc_info.topAccountId) + " (Unknown Name)";
			
			  	 $scope.loading_task_count--;
			  	 // 2. get the avilable templates
			  	 coreProvider.getTemplates(cb);
			   },
			   function (templates, cb) {
			  	 $scope.templates = templates;
			  	 $scope.loading_task_count--;
			  	 
			  	 // 3. get the MCC's account list
			  	 coreProvider.getAccountsForMcc($scope.mccid, true, cb);
			   },
			
			   function (accounts, cb) {
			  	 // make CIDs searchable
			  	 if (Array.isArray(accounts)) {
			  		 for (var i = 0; i < accounts.length; i++) {
			  			 accounts[i]._accountId = format_ccid(accounts[i].id);
			  		 }
			  	 }
			  	 $scope.loading_task_count--;
			  	 $scope.accounts = accounts;
			  	 $scope.source_accounts = JSON.parse(JSON.stringify(accounts));
			
			  	 // 4. get the available dates of data for this account
			  	 emsg = "Unable to determine which account reports are available";
			  	 coreProvider.dataAvailableForMcc($scope.mccid, 'month', cb);
			   },
			
			   function (available_data, cb) {
			  	 if (available_data && available_data.reports_available
			  			 && Array.isArray(available_data.reports_available)) {
			  		 for (var i = 0; i < available_data.reports_available.length; i++) {
			  			 var rad = available_data.reports_available[i];
			  			 if (rad.ReportType == "ReportAccount") {
			  				 $scope.reports_min_date = new Date(rad.startMonth);
			  				 $scope.reports_max_date = new Date(rad.endMonth);
			  				 break;
			  			 }
			  		 }
			  		 
			  		 // Watch any changes to either of the date fields for validation etc
			  		 $scope.$watch("date_start", validate_selected_dates);
			  		 $scope.$watch("date_end", validate_selected_dates);
			  	 }
			
			  	 $scope.loading_task_count--;
			  	 cb(null);
			   },
			
			   ], function (err) {
			if (err) {
				$scope.loading_task_count = 0;
				$scope.load_error = err.error;
				$scope.load_error_text = "Unable to load account info (" + err.error + ": " + err.message +")";
				$scope.load_error_text = emsg + "(" + err.error + "): " + err.message + ")";
			} else {
				$scope.$apply();
			}
		});
		
		/**
		 * Select a report and move the view to the next step
		 */
		$scope.selectReport = function (idx, name, template) {
			$scope.report_selectedIndex = idx;
			$scope.report_selected = template;
			$scope.switchStep(3); // TODO: invoke the switchStep independently from the view instead?
		};


		$scope.selectAccount = function (index, account) {
			if ($scope.source_accounts[index].name) {
				$scope.selected_account = account;
				$scope.switchStep(2); // TODO: invoke the switchStep independently from the view instead?
			} else {
				$scope.filter_accounts = $scope.source_accounts[index]._accountId; // TODO: Is this still needed?
			}
		};
		
		var validate_selected_dates = function() {
			if($scope.date_start && $scope.date_end) {
				if( ! ($scope.date_start > $scope.date_end) ) {
					$scope.switchStep(4);
				}
				else {
					var invalid_date_alert_div = angular.element(document.getElementById('invalid_date_alert'));
					$document.scrollTo(invalid_date_alert_div, 0, 700);
				}
			}
		};

		$scope.switchStep = function (step) {
			var section = angular.element(document.getElementById('pdf_export_step' + step));
			$document.scrollTo(section, 0, 700);
			$scope.step = step;
		};
		$scope.resetDatePickers = function() {
			$scope.date_start = null;
			$scope.date_end = null;
		};
	}

	awrcApp.controller("DocExportController", DocExportController);
})();
