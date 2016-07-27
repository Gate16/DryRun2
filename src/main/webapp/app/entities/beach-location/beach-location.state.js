(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beach-location', {
            parent: 'entity',
            url: '/beach-location',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BeachLocations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beach-location/beach-locations.html',
                    controller: 'BeachLocationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('beach-location-detail', {
            parent: 'entity',
            url: '/beach-location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BeachLocation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beach-location/beach-location-detail.html',
                    controller: 'BeachLocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BeachLocation', function($stateParams, BeachLocation) {
                    return BeachLocation.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('beach-location.new', {
            parent: 'beach-location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-location/beach-location-dialog.html',
                    controller: 'BeachLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                beachname: null,
                                longitude: null,
                                latitude: null,
                                zoomlevel: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beach-location', null, { reload: true });
                }, function() {
                    $state.go('beach-location');
                });
            }]
        })
        .state('beach-location.edit', {
            parent: 'beach-location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-location/beach-location-dialog.html',
                    controller: 'BeachLocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BeachLocation', function(BeachLocation) {
                            return BeachLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beach-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beach-location.delete', {
            parent: 'beach-location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-location/beach-location-delete-dialog.html',
                    controller: 'BeachLocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BeachLocation', function(BeachLocation) {
                            return BeachLocation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beach-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
