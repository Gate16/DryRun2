(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachLocationDetailController', BeachLocationDetailController);

    BeachLocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BeachLocation'];

    function BeachLocationDetailController($scope, $rootScope, $stateParams, entity, BeachLocation) {
        var vm = this;

        vm.beachLocation = entity;

        var unsubscribe = $rootScope.$on('greatWalksApp:beachLocationUpdate', function(event, result) {
            vm.beachLocation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
