(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachController', BeachController);

    BeachController.$inject = ['$scope', 'Principal', 'LoginService', 'Region', '$state'];

    function BeachController ($scope, Principal, LoginService, Region, $state) {
    	
    	var vm = this;
        
        vm.regions = [];
        
        getRegions();
        
    	function getRegions() {
    		Region.query(function(result) {
                vm.regions = result;
            });
    	}
        
    }
})();
