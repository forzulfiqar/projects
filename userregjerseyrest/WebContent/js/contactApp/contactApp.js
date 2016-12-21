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
		$http.put('rest/users/rest/createuser', $scope.user)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.get = function(id) {
		$http.get('rest/users/rest/getuserbyid/' + id)
			.success(function(data) {	
				$scope.user = data;
				$scope.getCountries();
				$scope.getAll();
			});
	};

	$scope.getCountries();
	$scope.getAll();

});



askEmmaApp = angular.module("AskEmmaApp", []);

askEmmaApp.controller("AskEmmaController", function($scope, $http) {
	//$scope.question = {};
			
	$scope.sendQuestion = function() {
		$http.get('localhost')
		.success(function(data) {	
			
			//alert('data: ' + data.getString());
			
			alert('data;' + data);
		});
};
	
	
	/*
	
	$scope.sendQuestion = function() {
		
		alert("Hello from sendQuestion");
		
		
		var req = {
				 method: 'POST',
				 url: 'http://localhost:9200/askemma/question',
				 headers: {
				   'Content-Type': undefined
				 },
				 //data: { test: 'test' }
				 data: {email: 'forzulfiqar@yahoo.com', statement:'after how much the site is updated-1',status:'new'}
			}

			$http(req).then(function(data1) {
				alert('data1: ' + data1);});
		
		
	};	
	*/			
	
	
});

