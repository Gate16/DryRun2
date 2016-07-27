(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachLocationDialogController', BeachLocationDialogController);

    BeachLocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BeachLocation'];

    function BeachLocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BeachLocation) {
        var vm = this;

        vm.beachLocation = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.beachLocation.id !== null) {
                BeachLocation.update(vm.beachLocation, onSaveSuccess, onSaveError);
            } else {
                BeachLocation.save(vm.beachLocation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('greatWalksApp:beachLocationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
