var app = angular.module('app', [ 'ds.clock', 'ui.bootstrap' ]);

app.controller('ctrl', ['$compile', '$scope',
    function($compile, $scope) {
        $scope.timeZoneToGMT = function (timeZone) {
            var conv = timeZone / (1000 * 60 * 60);
            var decimal = conv % 1;
            return (conv - decimal) + decimal * 60 / 100;
        }

        onPageFinished = function(args) {
            $scope.$apply(function () {
                $scope.clock = { format: 'dd/MM/yyyy HH:mm:ss', gmtOffset: args.timeZone, startTime: args.date };
            });
        }
    }
]);

function onPageFinished(args) {
    // Silence is golden
}