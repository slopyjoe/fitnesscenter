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

app.controller('EmpIdCtrl',function ($scope){
    $scope.enter = function() {
        alert($scope.user.employee_id);
    };
});

app.controller('ModalCtrl', function ($scope, $modal, $log,$http) {

  $scope.multipleUsers = [];
  $scope.activities = [];
  $scope.classes = [];

  var blankUser = {
        firstName: "",
        lastName: "",
        email: "",
        organization: "",
        employee_id: "",
        dob:""
  };
  $scope.user = blankUser;

  $scope.enter = function() {
		$http({
			method : 'GET',
			url : '/report',
            params : {
                empId:$scope.user.employee_id,
                organization:$scope.user.organization
            }
		}).success(function(data, status, headers, config) {
			// this callback will be called asynchronously
			// when the response is available
            $scope.setUp(data);
		}).error(function(data, status, headers, config) {
			alert(status);
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
  };


  $scope.updateCount = function(activity){
      $http({
          method : 'POST',
          url : '/signIn',
          data : {
              user:$scope.user,
              activityName:activity
          }
      }).success(function(data, status, headers, config) {
         $scope.user = blankUser;
         $scope.user.organization = "";
         $scope.user.employee_id = "";
      }).error(function(data, status, headers, config) {
              alert(status);
              // called asynchronously if an error occurs
              // or server returns response with an error status.
      });
  };

  $scope.newMember = function(){
        $http({
            method : 'POST',
            url : '/newMember',
            data : {
                user:$scope.user
            }
        }).success(function(data, status, headers, config) {
           $scope.enter();
        }).error(function(data, status, headers, config) {
                alert(status);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
        });
  };

  $scope.setUp = function(data) {

      if(data.found)
      {
          if(data.multipleUsers != undefined)
          {
              $scope.multipleUsers = data.multipleUsers;
              $scope.multipleModal();
          }
          else
          {
              $scope.user = data.user;
              $scope.defaultActivity = data.defaultActivity;
              $scope.activities = data.activities;
              $scope.classes = data.classes;
              $scope.counts = data.counts;
              $scope.userReport();
          }
      }
      else
      {
          $scope.template = 'invalidUser.html';
          $scope.user = blankUser;

          $scope.createUser();
      }
  };

  $scope.createUser = function () {
        var modalInstance = $modal.open({
            templateUrl: 'invalidUser.html',
            controller: NewInstanceCtrl,
            resolve: {
                user: function () {
                    return blankUser;
                }
            }
        });

        modalInstance.result.then(function (user) {
            $scope.user = user;

            $scope.newMember();
        });

  };

  $scope.userReport = function () {
    var modalInstance = $modal.open({
      templateUrl: 'validUser.html',
      controller: SignInInstanceCtrl,
      resolve: {
        user: function () {
          return $scope.user;
        },
        activities:function(){
            return $scope.activities;
        },
        classes:function(){
            return $scope.classes;
        },
        counts:function(){
            return $scope.counts;
        },defaultAct:function(){
            return $scope.defaultActivity;
        }
      }
    });

    modalInstance.result.then(function (activity) {
       $scope.updateCount(activity);
    },function(){
        //Since there isn't a cancel button the default
        //behavior is to update the account but with activity being the
        //exercise facility
        $scope.updateCount($scope.defaultActivity.name);
        console.log("Closing the User Report differently ");
    });

  };

  $scope.multipleModal = function () {
        var modalInstance = $modal.open({
            templateUrl: 'multipleUsers.html',
            controller: MultipleInstanceCtrl,
            resolve: {
                multipleUsers:function(){
                    return $scope.multipleUsers.users;
                }
            }
        });

        modalInstance.result.then(function (organization) {
            $scope.user.organization = organization;
            $scope.enter();
        },function(){
            console.log("This is the modal closings");
        });

   };
});

var SignInInstanceCtrl, MultipleInstanceCtrl, NewInstanceCtrl;

MultipleInstanceCtrl = function ($scope, $modalInstance, multipleUsers) {
    $scope.users = multipleUsers;
    $scope.ok = function (organization) {
        $modalInstance.close(organization);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


SignInInstanceCtrl = function ($scope, $modalInstance, user, activities, classes, counts, defaultAct) {
    $scope.user = user;
    $scope.activities = activities;
    $scope.classes = classes;
    $scope.counts = counts;
    $scope.defaultAct = defaultAct;
    $scope.signIn = function(activity){
        $modalInstance.close(activity);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


NewInstanceCtrl = function ($scope, $modalInstance, user) {
    $scope.user = user;
    $scope.ok = function () {
        $modalInstance.close($scope.user);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
var UserInstanceCtrl







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
