(function () {
    'use strict';

    angular
        .module('greatWalksApp')
        .factory('Beach', Beach);

    Beach.$inject = ['$resource'];

    function Beach ($resource) {
        var service = $resource('api/beach', {}, {
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
