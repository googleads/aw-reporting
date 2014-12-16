
awrcApp.controller("settingsdropdowncontroller", function ($scope, $log) {
	$scope.status = {
			isopen: false
	};

	$scope.toggled = function(open) {
	};

	$scope.toggleDropdown = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.status.isopen = !$scope.status.isopen;
	};
});