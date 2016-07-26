(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('beach', {
            parent: 'app',
            url: '/beach',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/beach/beach.html',
                    controller: 'BeachController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
