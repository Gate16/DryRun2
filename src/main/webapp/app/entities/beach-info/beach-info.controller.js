(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachInfoController', BeachInfoController);

    BeachInfoController.$inject = ['$scope', '$state', 'BeachInfo'];

    function BeachInfoController ($scope, $state, BeachInfo) {
        var vm = this;
        
        vm.beachInfos = [];

        loadAll();

        function loadAll() {
            BeachInfo.query(function(result) {
                vm.beachInfos = result;
            });
        }
    }
})();
