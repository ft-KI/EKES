import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from "react-router-dom"
import Communication from "./communication";
import React from "react";
import Chart from "chart.js";

var world;
var actors;
var simulation;
var context;

var worldwidth;
var Tilesize;
var worldheight;
var worldfield;
var rawInfos

var wsConnection = new WebSocket('ws://localhost:8080/evodata');
wsConnection.onopen = function () {
};
wsConnection.onerror = function (error) {
};
var ActorsSize;
var Timeinyears;
var averageage;
wsConnection.onmessage = async function (e) {

    // console.log(e.data);

    if (JSON.parse(e.data).type === "world") {
        world = JSON.parse(e.data);
        worldfield = world.world;
    } else if (JSON.parse(e.data).type === "creatures") {
        actors = JSON.parse(JSON.parse(e.data).kreatures);
    }else if(JSON.parse(e.data).type==="info"){
        rawInfos = JSON.parse(e.data);
        ActorsSize=rawInfos.ActorsSize;
        Timeinyears=rawInfos.Timeinyears;
        averageage=rawInfos.averageage;
        infodraw();

    }


};

var XpositionInCanvas = 0;
var YpositionInCanvas = 0;
var worldheightinpx = 0;
var worldwidthinpx = 0;

var infoblock;
var graphupdatespeed;
var Graphsichtfeld;
function infodraw(){
    var infos;


    infos="<h2>Info Daten</h2>"
    infos=infos+"<br>ActorsSize: "+ActorsSize+"</br>";
    infos=infos+"<br>Timeinyears: "+Timeinyears+"</br>";
    infos=infos+"<br>averageage: "+averageage+"</br>";

    infoblock.innerHTML = infos;

}
function updateGraphs(){
    window.graphAverageAge.data.labels.push("");
    window.graphActorsSize.data.labels.push("");

    window.graphAverageAge.data.datasets.forEach(function(dataset) {
        dataset.data.push(averageage);
    });
    window.graphActorsSize.data.datasets.forEach(function(dataset) {
        dataset.data.push(ActorsSize);
    });
    while(window.graphAverageAge.data.labels.length > parseInt(Graphsichtfeld.value)) {
        window.graphAverageAge.data.labels.splice(0, 1); // remove the label first

        window.graphAverageAge.data.datasets.forEach(function (dataset) {
            dataset.data.splice(0,1);
        });
    }
    while(window.graphActorsSize.data.labels.length > parseInt(Graphsichtfeld.value)) {
        window.graphActorsSize.data.labels.splice(0, 1); // remove the label first

        window.graphActorsSize.data.datasets.forEach(function (dataset) {
            dataset.data.splice(0,1);
        });
    }
    window.graphAverageAge.update();
    window.graphActorsSize.update();

}
var drawcount=0;
function draw() {
    try {
        if(parseInt(graphupdatespeed.value) % drawcount) {
            updateGraphs();
        }

        Tilesize = world.tilesize;
        worldheight = world.height;
        worldwidth = world.width;

        worldheightinpx = Tilesize * worldheight;
        worldwidthinpx = Tilesize * worldwidth;
        context.fillStyle = '#14002b'

        context.fillRect(0, 0, 4000,4000);

        ////////Draw Tile World////////

        for (var x = 0; x < worldwidth; x++) {
            for (var y = 0; y < worldheight; y++) {
                if (worldfield[x][y] === -1) {
                    continue;
                }else{

                    context.fillStyle = rgbToHex(Math.floor((1 - worldfield[x][y]) * 255),191,15);
                }
                context.fillRect(Math.floor(x * Tilesize + XpositionInCanvas), Math.floor(YpositionInCanvas + worldheight * Tilesize - y * Tilesize), Tilesize, Tilesize);

            }
        }

        context.beginPath();
        const actorsize = actors.length;
        for (let i = 0; i < actorsize; i++) {
            if (actors[i].gen <= 10) {
                context.fillStyle = rgbToHex(Math.floor((1 - actors[i].gen / 10) * 255),Math.floor(actors[i].gen / 10 * 255),0);
            } else {
                context.fillStyle = '#FFFFFF';
            }
            context.moveTo(Math.floor(actors[i].x + XpositionInCanvas), Math.floor(YpositionInCanvas + worldheight * Tilesize - actors[i].y));
            context.arc(Math.floor(actors[i].x + XpositionInCanvas), Math.floor(YpositionInCanvas + worldheight * Tilesize - actors[i].y), 4, 0, Math.floor(2 * Math.PI));

            var feelersize = actors[i].feelers.length;
            for (var a = 1; a < 2; a++) { //TODO Rendering only one feeler for better performance

                context.moveTo(Math.floor(XpositionInCanvas + actors[i].feelers[a].feelerpos.x), Math.floor(YpositionInCanvas + worldheight * Tilesize - actors[i].feelers[a].feelerpos.y));
                context.lineTo(Math.floor(XpositionInCanvas + actors[i].x), Math.floor(YpositionInCanvas + worldheight * Tilesize - actors[i].y));

            }


        }

        context.stroke();

        context.fill();



    } catch (e) {
        console.error(e);
    }






    drawcount++;
    requestAnimationFrame(draw);
}

document.body.onload = startShow;
Chart.defaults.global.elements.point.radius = 0;
var config = {
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
var config2={
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
async function startShow() {
    var ctx = document.getElementById('graphAverageAge').getContext('2d');
    var ctx2 = document.getElementById('graphActorsSize').getContext('2d');
    window.graphAverageAge = new Chart(ctx, config);
    window.graphActorsSize = new Chart(ctx2,config2);

    simulation = document.getElementById('screen');
    infoblock =  document.getElementById("info");
    graphupdatespeed=document.getElementById("graphupdatespeed");
    Graphsichtfeld=document.getElementById("Graphsichtfeld");
    context = simulation.getContext('2d');
    context.linewidth = 10;

    context.scale(0.80, 0.80);



    var elem = document.querySelector('body'), text = '';
    elem.addEventListener("keydown", function (event) {
        console.log(event.code);
        if (event.code == "ArrowUp") {
            YpositionInCanvas += 10;
        }
        if (event.code == "ArrowDown") {
            YpositionInCanvas += -10;
        }
        if (event.code == "ArrowLeft") {
            XpositionInCanvas += 10;
        }
        if (event.code == "ArrowRight") {
            XpositionInCanvas += -10;
        }
        if (event.key == "a") {
            context.scale(0.9, 0.9);
        }
        if (event.key == "q") {
            context.scale(1.1, 1.1);
        }
    });

    requestAnimationFrame(draw);

}


function App() {
    return (
        <div className="App">
            <div id="wrapper">
                <div id="infos">
                    <p id="info"></p>
                    <div >
                        <canvas id="graphAverageAge"></canvas>
                        <canvas id="graphActorsSize"></canvas>
                    </div>
                </div>
                <canvas id="screen" width="1200" height="800"></canvas>
                <div id="controll">
                    <h2>Controll Elemente</h2>
                    <h3>Darstellungs Optionen:</h3>
                    <label htmlFor="graphupdatespeed">Graph update speezd</label>
                    <input type="range" id="graphupdatespeed" name="graphupdatespeed" min="1" max="100"/>
                    <br></br>
                    <label htmlFor="Graphsichtfeld">Graphsichtfeld</label>
                    <input type="range" id="Graphsichtfeld" name="Graphsichtfeld" min="10" max="1000"/>
                    <h3>Simulations Optionen:</h3>
                </div>

            </div>
        </div>
    );

}

export default App;

function rgbToHex(r, g, b) {
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}
function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}