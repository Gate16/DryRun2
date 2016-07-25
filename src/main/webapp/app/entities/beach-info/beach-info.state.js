(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beach-info', {
            parent: 'entity',
            url: '/beach-info',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BeachInfos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beach-info/beach-infos.html',
                    controller: 'BeachInfoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('beach-info-detail', {
            parent: 'entity',
            url: '/beach-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BeachInfo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beach-info/beach-info-detail.html',
                    controller: 'BeachInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BeachInfo', function($stateParams, BeachInfo) {
                    return BeachInfo.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('beach-info.new', {
            parent: 'beach-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-info/beach-info-dialog.html',
                    controller: 'BeachInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                region: null,
                                beach: null,
                                excellent: null,
                                satisfactory: null,
                                unsatisfactory: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beach-info', null, { reload: true });
                }, function() {
                    $state.go('beach-info');
                });
            }]
        })
        .state('beach-info.edit', {
            parent: 'beach-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-info/beach-info-dialog.html',
                    controller: 'BeachInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BeachInfo', function(BeachInfo) {
                            return BeachInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beach-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beach-info.delete', {
            parent: 'beach-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beach-info/beach-info-delete-dialog.html',
                    controller: 'BeachInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BeachInfo', function(BeachInfo) {
                            return BeachInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beach-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
