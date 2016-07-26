(function () {
    'use strict';

    angular
        .module('greatWalksApp')
        .factory('Region', Region);

    Region.$inject = ['$resource'];

    function Region ($resource) {
        var service = $resource('api/regions', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    };
    
})();
