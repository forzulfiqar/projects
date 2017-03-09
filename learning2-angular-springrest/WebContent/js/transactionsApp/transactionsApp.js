transactionsApp = angular.module("TransactionsApp", []);

transactionsApp.controller("TransactionsController", function($scope, $http) {
	$scope.contact = {};
	
	$scope.getAllAccountsOfUser = function() {
		$http.get('transactions/rest/getallaccountsofuser')
			.success(function(data) {				
				$scope.accounts = data;
			});
	};
	
	$scope.getAll = function() {
		$http.get('transactions/rest/getallaccounts')
			.success(function(data) {				
				$scope.accounts = data;
			});
	};
	
		
	$scope.transfer = function() {		
		$http.put('transactions/rest/transfer', $scope.transactionHistory)
			.success(function() {
				$scope.transactionHistory = {};
				$scope.getAllAccountsOfUser();
			});
	};
	
	$scope.get = function(id) {
		$http.get('transactions/rest/gettransactionbyid/' + id)
			.success(function(data) {	
				$scope.transactionHistory = data;				
				$scope.getAllAccountsOfUser();
			});
	};
	
	$scope.getAllAccountsOfUser();

});





