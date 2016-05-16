var app = angular.module('my-app', ['ngRoute', 'ui.bootstrap']);

var ROOT_URL = "/api/";

app.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: '/views/list.html',
			controller: 'ListCtrl'
		}).
		when('/:model', {
			templateUrl: '/views/list.html',
			controller: 'ListCtrl'
		}).
		otherwise({
			redirectTo: '/'
		});
	}]);
  
app.controller('ListCtrl', function($scope, $http, $routeParams) {
	$scope.list = [];
	$scope.editModes = [];
	$scope.properties = [];

	console.log('route param', $routeParams.model);

	$http.get(ROOT_URL + $routeParams.model).then((res) => {
		console.log(res);
		
		$scope.list = res.data;
		
		for (var i in $scope.list) {
			$scope.editModes.push(false);
		}
		
		if ($scope.list.length > 0) {
			$scope.properties = Object.keys(res.data[0]);
			var index = $scope.properties.indexOf("id");
			$scope.properties.splice(0, 1);
			console.log('properties', $scope.properties);
		}
	})
	
	$scope.message = 'Main';
	
	$scope.changeEditMode = function($index) {
		console.log('changeEditMode', $index);
		$scope.editModes[$index] = !$scope.editModes[$index];
	};
	
	$scope.showDetails = function($index) {
		return $scope.editModes[$index];
	};

	$scope.addItemToList = function() {
		$scope.list.push({});
		$scope.editModes.push(true);
	};
	
	$scope.saveItem = function($index) {
		var item = $scope.list[$index];

		// Update
		if (item.id) {
			$http.put(ROOT_URL + $routeParams.model + '/' + item.id, item)
			.then((data) => {
				console.log('Item saved');
			});
		}
		// Create new
		else {
			$http.post(ROOT_URL + $routeParams.model, item)
			.then((data) => {
			console.log('Item saved');
			});
		}
	};
	
	$scope.deleteItem = function($index) {
		var item = $scope.list[$index];
		
		$http.delete(ROOT_URL + $routeParams.model + '/' + item.id, item)
			.then((data) => {
				console.log('Item deleted');
			});
	};
});