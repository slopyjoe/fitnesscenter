var app = angular.module('fitnesscenter',[]);
app.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
});
app.controller('UserReportCtrl',function($scope, $http){
	$scope.enter = function() {
		$http({method: 'GET', url: '/report/' + $scope.empId}).
		  success(function(data, status, headers, config) {
			  $scope.user = data.user;
			  $scope.activities = data.activities;
			  $scope.classes = data.classes;
			  $('#user_report').modal('show');
		  }).
		  error(function(data, status, headers, config) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
		
		
	};
	
	$scope.signIn = function(activity) {
		$http({
			method: 'POST', 
			url: '/signIn',
			data:JSON.stringify({empId:$scope.empId, activityName:activity})
			}).
		  success(function(response) {
			  $('#user_report').modal('hide');
		  }).
		  error(function(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
	};
});
    



