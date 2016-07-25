(function() {
    'use strict';
    angular
        .module('greatWalksApp')
        .factory('BeachInfo', BeachInfo);

    BeachInfo.$inject = ['$resource'];

    function BeachInfo ($resource) {
        var resourceUrl =  'api/beach-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
