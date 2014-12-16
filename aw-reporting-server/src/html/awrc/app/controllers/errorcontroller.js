(function () {

    function ErrorController ($scope, $routeParams) {

        $scope.error = $routeParams.error_code;
    }

    awrcApp.controller("ErrorController", ErrorController);


})();
