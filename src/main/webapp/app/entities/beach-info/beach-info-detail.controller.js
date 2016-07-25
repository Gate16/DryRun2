(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachInfoDetailController', BeachInfoDetailController);

    BeachInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BeachInfo'];

    function BeachInfoDetailController($scope, $rootScope, $stateParams, entity, BeachInfo) {
        var vm = this;

        vm.beachInfo = entity;

        var unsubscribe = $rootScope.$on('greatWalksApp:beachInfoUpdate', function(event, result) {
            vm.beachInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
