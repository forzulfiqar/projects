contactApp = angular.module("UserApp", []);

contactApp.controller("UserController", function($scope, $http) {
	$scope.contact = {};
	
	$scope.getAll = function() {
		
		var encodedString = encode('bill:abc123');		
		
		$http({
			url : "users/rest/getallusers",
			method : 'GET',
			headers : {
				Accept: 'application/json',   				
			    Authorization: 'Basic ' + encodedString
			      }
			}).success(function(data){
				$scope.users = data;
			}).error(function(error){
			    alert("login error: " + error);
			});
				
		/*
		$http.get('users/rest/getallusers')
			.success(function(data) {				
				$scope.users = data;
			});
			*/
	};
	
	$scope.getCountries = function() {
		
		var encodedString = encode('bill:abc123');		
		
		$http({
			url : "users/rest/getallcountries",
			method : 'GET',
			headers : {
				Accept: 'application/json',   				
			    Authorization: 'Basic ' + encodedString
			      }
			}).success(function(data){
				$scope.countries = data;
			}).error(function(error){
			    alert("login error: " + error);
			});
		
		/*	
		$http.get('users/rest/getallcountries')
			.success(function(data) {				
				$scope.countries = data;
			});
			*/
	};
	
	$scope.createUser = function() {		
		$http.put('users/rest/createuser', $scope.user)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.updateUser = function() {		
		$http.post('users/rest/updateuser', $scope.user)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.deleteUser = function() {		
		$http.post('users/rest/deleteuser', $scope.user)
			.success(function() {
				$scope.user = {};
				$scope.getAll();
			});
	};
	
	$scope.login = function() {	
		
		$http.put('users/rest/login', $scope.user)
			.success(function() {
				$scope.user = {};				
			});
	};
	
	$scope.get = function(id) {
		$http.get('users/rest/getuserbyid/' + id)
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

var PADCHAR = '=';

var ALPHA = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';

function encode(s) {
   
    var i, b10;
    var x = [];

    // convert to string
    s = "" + s;

    var imax = s.length - s.length % 3;

    if (s.length === 0) {
        return s;
    }
    for (i = 0; i < imax; i += 3) {
        b10 = (getbyte(s,i) << 16) | (getbyte(s,i+1) << 8) | getbyte(s,i+2);
        x.push(ALPHA.charAt(b10 >> 18));
        x.push(ALPHA.charAt((b10 >> 12) & 0x3F));
        x.push(ALPHA.charAt((b10 >> 6) & 0x3f));
        x.push(ALPHA.charAt(b10 & 0x3f));
    }
    switch (s.length - imax) {
        case 1:
            b10 = getbyte(s,i) << 16;
            x.push(ALPHA.charAt(b10 >> 18) + ALPHA.charAt((b10 >> 12) & 0x3F) +
                PADCHAR + PADCHAR);
            break;
        case 2:
            b10 = (getbyte(s,i) << 16) | (getbyte(s,i+1) << 8);
            x.push(ALPHA.charAt(b10 >> 18) + ALPHA.charAt((b10 >> 12) & 0x3F) +
                ALPHA.charAt((b10 >> 6) & 0x3f) + PADCHAR);
            break;
    }
    return x.join('');
}

function getbyte64(s,i) {
    var idx = ALPHA.indexOf(s.charAt(i));
    if (idx === -1) {
        throw "Cannot decode base64";
    }
    return idx;
}
function getbyte(s,i) {
    var x = s.charCodeAt(i);
    if (x > 255) {
        throw "INVALID_CHARACTER_ERR: DOM Exception 5";
    }
    return x;
}



