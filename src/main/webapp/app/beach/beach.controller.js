(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .controller('BeachController', BeachController);

    BeachController.$inject = ['$scope', 'Principal', 'LoginService', 'Region', 'Beach', 'Info', '$state'];

    function BeachController ($scope, Principal, LoginService, Region, Beach, Info, $state) {
    	
    	var vm = this;
        
    	$scope.regions = [];
    	$scope.beaches = [];
    	
    	vm.selectedRegion = null;
    	vm.selectedBeach = null;
    	vm.info = null;
    	
        getRegions();
        
    	function getRegions() {
    		Region.query(function(result) {
    			$scope.regions = result;
            });
    	}
    	
    	function getBeaches(region) {
    		Beach.query({region: region}, function(result) {
    			$scope.beaches = result;
            });
    	}
    	
    	function getInfo(region, beach) {
    		Info.query({region: region, beach: beach}, function(result) {
    			vm.info = result[0];
            });
    	}
    	
    	$scope.$watchCollection('vm.selectedRegion', function () {
    	    //alert(vm.selectedRegion);
    		if(vm.selectedRegion !== null){
    			getBeaches(vm.selectedRegion);
    		}
    	  });
    	
    	$scope.$watchCollection('vm.selectedBeach', function () {

    		if(vm.selectedBeach !== null){
    			getInfo(vm.selectedRegion, vm.selectedBeach);
    		}
    	  });
        
    }
})();
