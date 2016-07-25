(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachInfoDialogController', BeachInfoDialogController);

    BeachInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BeachInfo'];

    function BeachInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BeachInfo) {
        var vm = this;

        vm.beachInfo = entity;
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
            if (vm.beachInfo.id !== null) {
                BeachInfo.update(vm.beachInfo, onSaveSuccess, onSaveError);
            } else {
                BeachInfo.save(vm.beachInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('greatWalksApp:beachInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
