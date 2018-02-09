// Init plugin
function onPageFinished() {
    $('canvas').constellation({
        star: {
            color: 'rgba(0, 96, 178, 1)',
            width: 3
        },
        line: {
            color: 'rgba(255, 143, 0, .5)',
            width: 1
        },
        velocity: .5,
        length: 100,
        distance: 120,
        radius: 150,
        radius: 250
    });
}
