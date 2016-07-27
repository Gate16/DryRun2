'use strict';

describe('Controller Tests', function() {

    describe('BeachLocation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBeachLocation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBeachLocation = jasmine.createSpy('MockBeachLocation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'BeachLocation': MockBeachLocation
            };
            createController = function() {
                $injector.get('$controller')("BeachLocationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'greatWalksApp:beachLocationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
