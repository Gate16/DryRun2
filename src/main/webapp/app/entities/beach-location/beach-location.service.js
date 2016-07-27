(function() {
    'use strict';
    angular
        .module('greatWalksApp')
        .factory('BeachLocation', BeachLocation);

    BeachLocation.$inject = ['$resource'];

    function BeachLocation ($resource) {
        var resourceUrl =  'api/beach-locations/:id';

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
