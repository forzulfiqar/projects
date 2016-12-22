contactApp = angular.module("UserApp", []);

contactApp.controller("UserController", function($scope, $http) {
	$scope.contact = {};
	
	$scope.getAll = function() {
		$http.get('api/users')
			.success(function(data) {				
				$scope.users = data;
			});
	};
	
	$scope.getCountries = function() {
		$http.get('api/getallcountries')
			.success(function(data) {				
				$scope.countries = data;
			});
	};
	
	$scope.create = function() {
		$http.put('api/user/create', $scope.user)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.get = function(id) {
		$http.get('api/user/' + id)
			.success(function(data) {				
			
				$scope.user = data;
			});
	};
	
	$scope.getCountries();
	$scope.getAll();
});
