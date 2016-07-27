(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachLocationController', BeachLocationController);

    BeachLocationController.$inject = ['$scope', '$state', 'BeachLocation'];

    function BeachLocationController ($scope, $state, BeachLocation) {
        var vm = this;
        
        vm.beachLocations = [];

        loadAll();

        function loadAll() {
            BeachLocation.query(function(result) {
                vm.beachLocations = result;
            });
        }
    }
})();
