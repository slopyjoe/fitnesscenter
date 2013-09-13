var app = angular.module('fitnesscenter', [ 'ui.bootstrap' ]);
app.directive('ngEnter', function() {
	return function(scope, element, attrs) {
		element.bind("keydown keypress", function(event) {
			if (event.which === 13) {
				scope.$apply(function() {
					scope.$eval(attrs.ngEnter);
				});

				event.preventDefault();
			}
		});
	};
});




app.controller('ModalCtrl', function ($scope, $modal, $log,$http) {
  $scope.user = 'item1';
  $scope.enter = function() {

		$http({
			method : 'GET',
			url : '/report/' + $scope.empId
		}).success(function(data, status, headers, config) {
			// this callback will be called asynchronously
			// when the response is available
			data.user.name = data.user.firstName + " " + data.user.lastName;
			$scope.user = data.user;
			$scope.open();
		}).error(function(data, status, headers, config) {
			alert("error");
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
  
  $scope.open = function () {
    var modalInstance = $modal.open({
      templateUrl: 'myModalContent.html',
      controller: ModalInstanceCtrl,
      resolve: {
        user: function () {
          return $scope.user;
        }
      }
    });
    modalInstance.result.then(function (selectedItem) {
      $scope.selected = selectedItem;
    }, function () {
      $log.info('Modal dismissed at: ' + new Date());
    });
  };
});

var ModalInstanceCtrl;
ModalInstanceCtrl = function ($scope, $modalInstance, user) {
    $scope.user = user;
    $scope.ok = function () {
        $modalInstance.close($scope.selected.item);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};







app.controller('AccordionCtrl', function AccordionCtrl($scope,$http) {
	$scope.oneAtATime = true;
	$scope.groups=[];
	$scope.getActivities = function() {
		
		$http({
			method : 'GET',
			url : '/activities'
			}).success(function(data, status, headers, config) {
			// this callback will be called asynchronously
			// when the response is available
			$scope.groups = data.activities;
		}).error(function(data, status, headers, config) {
			alert("error");
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	$scope.getActivities();
});

app.controller('CarouselCtrl', function($scope, $http) {
	$scope.myInterval = 5000;
	$scope.slides=[];
	$scope.getBulletinPosts = function() {
		
		$http({
			method : 'GET',
			url : '/messageBoard'
			}).success(function(data, status, headers, config) {
			// this callback will be called asynchronously
			// when the response is available
			$scope.slides = data.messages;
		}).error(function(data, status, headers, config) {
			alert("error");
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	$scope.getBulletinPosts();
});
