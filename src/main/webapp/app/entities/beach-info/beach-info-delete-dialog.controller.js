(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachInfoDeleteController',BeachInfoDeleteController);

    BeachInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'BeachInfo'];

    function BeachInfoDeleteController($uibModalInstance, entity, BeachInfo) {
        var vm = this;

        vm.beachInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BeachInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
