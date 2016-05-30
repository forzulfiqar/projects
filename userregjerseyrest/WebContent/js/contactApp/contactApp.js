contactApp = angular.module("UserApp", []);

contactApp.controller("UserController", function($scope, $http) {
	$scope.contact = {};
	
	$scope.getAll = function() {
		$http.get('rest/users/rest/getallusers')
			.success(function(data) {				
				$scope.users = data;
			});
	};
	
	$scope.getCountries = function() {
		$http.get('rest/users/rest/getallcountries')
			.success(function(data) {				
				$scope.countries = data;
			});
	};
	
	$scope.create = function() {
		$http.put('rest/users/rest/createuser', $scope.contact)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.get = function(id) {
		$http.get('rest/users/rest/getuserbyid/' + id)
			.success(function(data) {	
				
				//alert('data: ' + data.getString());
				
				$scope.user = data;
			});
	};

	$scope.getCountries();
	$scope.getAll();

});
