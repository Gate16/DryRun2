(function() {
    'use strict';

    angular
        .module('greatWalksApp')
        .config(uiGmapConfig);

    uiGmapConfig.$inject = ['uiGmapGoogleMapApiProvider'];

    function uiGmapConfig(uiGmapGoogleMapApiProvider) {
    	uiGmapGoogleMapApiProvider.configure({
            key: 'AIzaSyB9hysKwRJEqh5eYjhSPxpzMTSUxX4bnh0',
            v: '3.20', //defaults to latest 3.X anyhow
            libraries: 'weather,geometry,visualization'
        });
    }
})();
