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
app.controller('UserReportCtrl',function($scope){
	$scope.enter = function() {
		$http({method: 'GET', url: '/report/' + $('#employee_id').val}).
		  success(function(data, status, headers, config) {
		    // this callback will be called asynchronously
		    // when the response is available
			  alert(data.user.firstName);
		  }).
		  error(function(data, status, headers, config) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
		
		$('#user_report').modal('show');
	};
});
    



