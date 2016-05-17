var app = angular.module('my-app', ['ngRoute', 'ui.bootstrap']);

var ROOT_URL = "/api/";

app.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
		when('/', {
			templateUrl: '/views/main.html',
			controller: 'MainCtrl'
		}).
		when('/dvds', {
			templateUrl: '/views/dvds.html',
			controller: 'DvdCtrl'
		}).
		when('/:model', {
			templateUrl: '/views/list.html',
			controller: 'ListCtrl'
		}).
		otherwise({
			redirectTo: '/'
		});
	}]);
	
app.controller('MainCtrl', function($scope) {
	$scope.list = [
		'dvd',
		'movies',
		'languages',
		'audios',
		'genres'
	];
});
  
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
			$scope.properties.splice(index, 1);
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

		function saveCallback() {
			console.log('Item saved');
			$scope.editModes[$index] = false;
		}
		
		// Update
		if (item.id) {
			$http.put(ROOT_URL + $routeParams.model + '/' + item.id, item)
			.then(saveCallback);
		}
		// Create new
		else {
			$http.post(ROOT_URL + $routeParams.model, item)
			.then(saveCallback);
		}
	};
	
	$scope.deleteItem = function($index) {
		var item = $scope.list[$index];
		
		$http.delete(ROOT_URL + $routeParams.model + '/' + item.id, item)
			.then((data) => {
				console.log('Item deleted');
				$scope.list.splice($index, 1);
				$scope.editModes.splice($index, 1);
			});
	};
});

app.controller('DvdCtrl', function($scope, $http, $routeParams) {
	$scope.list = [];
	$scope.editModes = [];
	$scope.properties = [];

	$http.get(ROOT_URL + 'dvds').then((res) => {
		console.log(res);
		
		$scope.list = res.data;
		
		for (var item of $scope.list) {
			$scope.editModes.push(false);
			
			item.audioIds = '';
			for (var audio in item.audios) {
				item.audioIds = audio +',' + item.audioIds;
			}
			item.langIds = '';
			for (var lang in item.languages) {
				item.langIds =  lang +',' + item.langIds;
			}
			item.movieId = item.movie.id;
			item.movieTitle = item.movie.title;
			delete item.movie;
		}
		
		if ($scope.list.length > 0) {
			$scope.properties = Object.keys(res.data[0]);
			var index = $scope.properties.indexOf("id");
			$scope.properties.splice(index, 1);
			var languages = $scope.properties.indexOf("languages");
			$scope.properties.splice(languages, 1);
			var audios = $scope.properties.indexOf("audios");
			$scope.properties.splice(audios, 1);
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

		function saveCallback() {
			console.log('Item saved');
			$scope.editModes[$index] = false;
		}
		
		// Update
		if (item.id) {
			$http.put(ROOT_URL + 'dvds/' + item.id, item)
			.then(saveCallback);
		}
		// Create new
		else {
			$http.post(ROOT_URL + $routeParams.model, item)
			.then(saveCallback);
		}
	};
	
	$scope.deleteItem = function($index) {
		var item = $scope.list[$index];
		
		$http.delete(ROOT_URL + 'dvds/' + item.id, item)
			.then((data) => {
				console.log('Item deleted');
				$scope.list.splice($index, 1);
				$scope.editModes.splice($index, 1);
			});
	};
});