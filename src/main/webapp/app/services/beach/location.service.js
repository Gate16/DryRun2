(function () {
    'use strict';

    angular
        .module('greatWalksApp')
        .factory('Location', Info);

    Info.$inject = ['$resource'];

    function Info ($resource) {
        var service = $resource('api/location', {}, {
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
