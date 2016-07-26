(function () {
    'use strict';

    angular
        .module('greatWalksApp')
        .factory('Info', Info);

    Info.$inject = ['$resource'];

    function Info ($resource) {
        var service = $resource('api/info', {}, {
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
