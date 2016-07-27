(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachLocationDeleteController',BeachLocationDeleteController);

    BeachLocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'BeachLocation'];

    function BeachLocationDeleteController($uibModalInstance, entity, BeachLocation) {
        var vm = this;

        vm.beachLocation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BeachLocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
