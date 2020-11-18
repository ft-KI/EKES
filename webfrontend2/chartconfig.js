Chart.defaults.global.elements.point.radius = 0;
export var config_averageage = {
    type: 'line',
    data: {
        labels: [],
        datasets: [ {
            label: 'Average Age',
            fill: false,
            backgroundColor: 'red',
            borderColor: 'orange',
            data: [


            ],
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Average Age'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            x: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            },
            y: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            }
        }
    }
};
export var config_actorssize={
    type: 'line',
    data: {
        labels: [],
        datasets: [ {
            label: 'Actor Size',
            fill: false,
            backgroundColor: 'red',
            borderColor: 'red',
            data: [


            ],
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Actor Size'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            x: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            },
            y: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            }
        }
    }
};
export var config_foodavailable={
    type: 'line',
    data: {
        labels: [],
        datasets: [ {
            label: 'durchschnitt der Foodvalues auf Landflächen',
            fill: false,
            backgroundColor: 'red',
            borderColor: 'yellow',
            data: [


            ],
        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Food Available'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            x: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            },
            y: {
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            }
        }
    }
};


