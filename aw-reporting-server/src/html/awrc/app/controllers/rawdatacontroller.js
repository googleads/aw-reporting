(function () {
	function RawDataController ($scope, $location, $routeParams, $timeout, $filter, coreProvider, ngTableParams) {

		$scope.mccid = $routeParams.mccid;

		// make sure we've verified we have data ready to go.
		if (!coreProvider.get_value_for_key("verified_loaded_" + $scope.mccid)) {
			$location.path("/account/" + $scope.mccid);
			return;
		}

		$scope.TOTAL_LOADING_TASKS = 3;
		$scope.loading_task_count = 3;
		$scope.load_error_text = '';
		$scope.mcc_info = null;

		$scope.accounts = [];
		$scope.source_accounts = [];

		$scope.report_selectedIndex = -1;
		$scope.account_selectedIndexes = [];
		$scope.selectedAccount_selectedIndexes = [];

		$scope.dataType = "account";

		$scope.selected_account = null;
		$scope.valid_date_range = false;
		$scope.formDisabed = false;

		$scope.raw_data = [];
		$scope.raw_data_type = "account";

		$scope.account_fields_show = [
		                              { name: 'day', show: true },
		                              { name: 'cost', show: true },
		                              { name: 'clicks', show: true },
		                              { name: 'impressions', show: true },
		                              { name: 'ctr', show: true },
		                              { name: 'avgCpm', show: true },
		                              { name: 'avgCpc', show: true },
		                              { name: 'avgPosition', show: false },
		                              { name: 'conversions', show: false },
		                              { name: 'searchImpressionShare', show: false },
		                              { name: 'searchLostISBudget', show: false },
		                              { name: 'searchLostISRank', show: false },
		                              { name: 'contentImpressionShare', show: false },
		                              { name: 'contentLostISBudget', show: false },
		                              { name: 'contentLostISRank', show: false },
		                              { name: 'currencyCode', show: false },
		                              { name: 'adNetwork', show: false },
		                              { name: 'conversionsManyPerClick', show: false },
		                              { name: 'viewThroughConversions', show: false },
		                              { name: 'timestamp', show: false },
		                              { name: 'dateStart', show: false },
		                              { name: 'dateEnd', show: false }
		                              ];

		$scope.campaign_fields_show = [
		                               { name: 'campaignId', show: true },
		                               { name: 'campaignName', show: true },
		                               { name: 'status', show: false },
		                               { name: 'budget', show: true },
		                               { name: 'accountDescriptiveName', show: false },
		                               { name: 'currencyCode', show: false },
		                               { name: 'day', show: false },
		                               { name: 'cost', show: true },
		                               { name: 'clicks', show: true },
		                               { name: 'impressions', show: true },
		                               { name: 'ctr', show: false },
		                               { name: 'avgCpm', show: false },
		                               { name: 'avgCpc', show: false },
		                               { name: 'avgPosition', show: false },
		                               { name: 'conversionsManyPerClick', show: false },
		                               { name: 'conversions', show: false },
		                               { name: 'viewThroughConversions', show: false },
		                               { name: 'dateStart', show: false },
		                               { name: 'dateEnd', show: false }
		                               ];

		var emsg = "Unable to load account info";

		async.waterfall([
		                 function (cb) {
		                	 // 1. Get MCC info
		                	 coreProvider.getMccById($scope.mccid, cb);
		                 },
		                 function (mcc, cb) {
		                	 $scope.mcc_info = mcc;
		                	 if (!$scope.mcc_info.name)
		                		 $scope.mcc_info.name = format_ccid($scope.mcc_info.topAccountId) + " (Unknown Name)";

		                	 $scope.loading_task_count--;

		                	 // 2. Get available report date range
		                	 emsg = "Unable to load accounts for this MCC";
		                	 coreProvider.dataAvailableForMcc($scope.mccid, 'day', cb);
		                 },

		                 function (available_data, cb) {
		                	 if (available_data && available_data.reports_available
		                			 && Array.isArray(available_data.reports_available)) {
		                		 for (var i = 0; i < available_data.reports_available.length; i++) {
		                			 var rad = available_data.reports_available[i];
		                			 if (rad.ReportType == "ReportAccount") {
		                				 $scope.reports_account_min_date = new Date(rad.startDay);
		                				 $scope.reports_account_max_date = new Date(rad.endDay);
		                			 } else if (rad.ReportType == "ReportCampaign") {
		                				 $scope.reports_campaign_min_date = new Date(rad.startDay);
		                				 $scope.reports_campaign_max_date = new Date(rad.endDay);
		                			 }
		                		 }
		                	 }

		                	 $scope.loading_task_count--;
		                	 // 3. see if it has any reports downloaded, if not, abort.
		                	 emsg = "Unable to load accounts for this MCC";
		                	 coreProvider.getAccountsForMcc($scope.mccid, true, cb);
		                 },
		                 function (accounts, cb) {
		                	 // make them more searchable!
		                	 if (Array.isArray(accounts)) {
		                		 for (var i = 0; i < accounts.length; i++) {
		                			 accounts[i]._accountId = format_ccid(accounts[i].id);
		                		 }
		                	 }

		                	 $scope.total_number_items = accounts.length;
		                	 $scope.max_visible_pages = 10;
		                	 $scope.current_page = 0;
		                	 $scope.loading_task_count--;
		                	 $scope.accounts = accounts;
		                	 $scope.source_accounts = JSON.parse(JSON.stringify(accounts));

		                	 cb(null);
		                 },
		                 ], function (err, reports) {
			if (err) {
				$scope.loading_task_count = 0;
				$scope.load_error = err.error;
				$scope.load_error_text = emsg + "(" + err.error + "): " + err.message + ")";
			} else {
				$scope.loading_task_count = 0;
				$timeout(function () {
					$scope.current_page = 1;
				}, 100);

				// Watch filter fields for changes
				$scope.$watch("dataType", watch_data_type);
				$scope.$watch("date_start", watch_date_range);
				$scope.$watch("date_end", watch_date_range);
			}
		});

		$scope.setPage = function (page) {
			$scope.charts = $scope.processed_reports.slice(0, 10);
		};


		/*
		 * Account selection
		 */
		$scope.selectAccount = function (index, account) {
			if ($scope.source_accounts[index].name) {
				$scope.selected_account = account;
				$scope.filter_accounts = account.name;
			} else {
				$scope.filter_accounts = $scope.source_accounts[index]._accountId;
			}
		};

		$scope.isSelectedAccount = function (account) {
			if($scope.selected_account != null && $scope.selected_account.id == account.id) {
				return true;
			} else {
				return false;
			}
		};

		$scope.resetAccountFilter = function() {
			$scope.selected_account = null;
			$scope.filter_accounts = "";
		};

		/*
		 * Date selection
		 */
		 $scope.start_cal_open = function($event) {
			 $event.preventDefault();
			 $event.stopPropagation();
			 $scope.start_cal_opened = true;
		 };

		 $scope.end_cal_open = function($event) {
			 $event.preventDefault();
			 $event.stopPropagation();
			 $scope.end_cal_opened = true;
		 };

		 $scope.dateOptions = {
				 showWeeks: false,
				 formatYear: 'yy',
				 startingDay: 1
		 };

		 $scope.resetDatePickers = function() {
			 $scope.date_start = null;
			 $scope.date_end = null;
		 };

		 /*
		  * Data type selection
		  */
		 $scope.resetDataType = function() {
			 $scope.dataType = "account";
		 };

		 /*
		  * Filter settings
		  */

		 $scope.resetSettings = function() {
			 $scope.resetAccountFilter();
			 $scope.resetDatePickers();
			 $scope.resetDataType();
		 };

		 $scope.disableForm = function(disable) {
			 if(disable == true)
				 $scope.formDisabed = true;
			 else
				 $scope.formDisabed = false;
		 };

		 $scope.updateRawData = function() {
			 var cids = [$scope.selected_account.id];
			 coreProvider.getReportXYZ(
					 $scope.dataType,
					 $scope.mccid,
					 cids, // TODO: single account, not array
					 $filter('date')($scope.date_start, "yyyy-MM-dd"),
					 $filter('date')($scope.date_end, "yyyy-MM-dd"),
					 function (err, data) {
						 if (err) {
							 $scope.load_error = err; // TODO - display error properly
						 } else {
							 $scope.raw_data = data;
							 $scope.raw_data_type = $scope.dataType;

							 if($scope.raw_data_type == "account")
								 $scope.tableParams.reload();
							 else if($scope.raw_data_type == "campaign")
								 $scope.tableParams2.reload();
						 }
					 }
			 );
		 };

		 /*
		  * Account table params
		  */
		 $scope.tableParams = new ngTableParams({
			 page: 1,            
			 count: 10,
			 sorting: {
				 day: 'asc'
			 }
		 }, {
			 total: $scope.raw_data.length,
			 counts: [10, 25, 50],
			 getData: function($defer, params) {
				 var data = $scope.raw_data;
				 var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
				 params.total(orderedData.length); // set total for recalc pagination
				 $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			 }
		 });

		 $scope.tableParams2 = new ngTableParams({
			 page: 1,            
			 count: 10,
			 sorting: {
				 campaignId: 'asc'
			 }
		 }, {
			 total: $scope.raw_data.length,
			 counts: [10, 25, 50],
			 getData: function($defer, params) {
				 var data = $scope.raw_data;
				 var orderedData = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
				 params.total(orderedData.length); // set total for recalc pagination
				 $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			 }
		 });

		 $scope.partitionedAccountFieldsList = partitionArray($scope.account_fields_show, 8);
		 $scope.partitionedCampaignFieldsList = partitionArray($scope.campaign_fields_show, 8);


		 $scope.showTableField = function( fieldName, tableType) {
			 // Defaults to true
			 var show = true;

			 switch(tableType) {
			 case "account":
				 var result = $.grep($scope.account_fields_show, function(e){ return e.name == fieldName; });
				 if(result.length == 1 && result[0].show == false)
					 show = false;
				 break;
			 case "campaign":
				 var result = $.grep($scope.campaign_fields_show, function(e){ return e.name == fieldName; });
				 if(result.length == 1 && result[0].show == false)
					 show = false;
				 break;
			 default:
				 break;
			 };

			 return show;
		 };

		 var watch_date_range = function() {
			 if($scope.date_start && $scope.date_end && !($scope.date_end < $scope.date_start)) {
				 $scope.valid_date_range = true;
			 } else {
				 $scope.valid_date_range = false;
			 }
		 };

		 var watch_data_type = function() {
			 if($scope.dataType == "account") {
				 $scope.min_date = $scope.reports_account_min_date;
				 $scope.max_date = $scope.reports_account_max_date;

			 } else if($scope.dataType == "campaign") {
				 $scope.min_date = $scope.reports_campaign_min_date;
				 $scope.max_date = $scope.reports_campaign_max_date;
			 }

			 // If the selected date range is outside of the new min - max, reset
			 // both start and end fields
			 if( ($scope.date_start != null && $scope.date_start < $scope.min_date) || 
					 ($scope.date_end != null && $scope.date_end > $scope.max_date)) {
				 resetDatePickers();
			 }
		 };
	}

	awrcApp.controller("RawDataController", RawDataController);

	function partitionArray(arr, size) {
		var newArr = [];
		for (var i=0; i<arr.length; i+=size) {
			newArr.push(arr.slice(i, i+size));
		}
		return newArr;
	};

})();
