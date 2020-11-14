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
var foodavailable;
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
        foodavailable=rawInfos.foodavailable;

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
    infos=infos+"<br>foodavailable: "+foodavailable+"</br>";



    infoblock.innerHTML = infos;

}
function updateGraphs(){
    window.graphAverageAge.data.labels.push("");
    window.graphActorsSize.data.labels.push("");
    window.graphFoodAvailable.data.labels.push("");

    window.graphAverageAge.data.datasets.forEach(function(dataset) {
        dataset.data.push(averageage);
    });
    window.graphActorsSize.data.datasets.forEach(function(dataset) {
        dataset.data.push(ActorsSize);
    });
    window.graphFoodAvailable.data.datasets.forEach(function(dataset) {
        dataset.data.push(foodavailable);
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
    while(window.graphFoodAvailable.data.labels.length > parseInt(Graphsichtfeld.value)) {
        window.graphFoodAvailable.data.labels.splice(0, 1); // remove the label first

        window.graphFoodAvailable.data.datasets.forEach(function (dataset) {
            dataset.data.splice(0,1);
        });
    }
    window.graphAverageAge.update();
    window.graphActorsSize.update();
    window.graphFoodAvailable.update();


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




    infodraw();

    drawcount++;
    requestAnimationFrame(draw);
}

document.body.onload = startShow;
Chart.defaults.global.elements.point.radius = 0;
var config_averageage = {
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
var config_actorssize={
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
var config_foodavailable={
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

//PARAMS
var simulationSpeedParam;
var movespeed;
var movecost;
var rotspeed;
var rotcost;
var eatcost;
var eatspeed;
var costland;
var costwater;
var childage;
var childenergie;


async function startShow() {
    var ctx = document.getElementById('graphAverageAge').getContext('2d');
    var ctx2 = document.getElementById('graphActorsSize').getContext('2d');
    var ctx3 = document.getElementById('graphFoodAvailable').getContext('2d');

    window.graphAverageAge = new Chart(ctx, config_averageage);
    window.graphActorsSize = new Chart(ctx2,config_actorssize);
    window.graphFoodAvailable=new Chart(ctx3, config_foodavailable);

    simulation = document.getElementById('screen');
    infoblock =  document.getElementById("info");
    graphupdatespeed=document.getElementById("graphupdatespeed");
    Graphsichtfeld=document.getElementById("Graphsichtfeld");
    simulationSpeedParam = document.getElementById("simspeed");
    movespeed = document.getElementById("simmovementspeed");
    movecost = document.getElementById("simmovecost");
    rotspeed = document.getElementById("simrotspeed");
    rotcost = document.getElementById("simrotcost");
    eatcost = document.getElementById("simeatcost");
    eatspeed = document.getElementById("simgettingenergie");
    costland = document.getElementById("permcostland");
    costwater = document.getElementById("permcostwater");
    childage = document.getElementById("childage");
    childenergie = document.getElementById("createchildcost");






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

function sendParams() {
console.log("Sending Params...");
var obj = {
  speed:simulationSpeedParam.value,
    type:"param",
  movespeed:movespeed.value,
    movecost:movecost.value,
    rotcost:rotcost.value,
    rotspeed:rotspeed.value,
    eatcost:eatcost.value,
    eatspeed:eatspeed.value,
    childenergie:childenergie.value,
    childage:childage.value,
    costwater:costwater.value,
    costland:costland.value

    }

wsConnection.send(JSON.stringify(obj));
console.log("Sending Successful")
}

function resetSimulation (){

    var obj = {
        type:"reset"
    }
    wsConnection.send(JSON.stringify(obj));

    document.location.reload();
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
                        <canvas id="graphFoodAvailable"></canvas>
                    </div>
                </div>
                <canvas id="screen" width="1200" height="800"></canvas>
                <div id="controll">
                    <h2>Controll Elemente</h2>
                    <h3>Darstellungs Optionen:</h3>
                    <label htmlFor="graphupdatespeed">Graph update Speed</label>
                    <input type="range" id="graphupdatespeed" name="graphupdatespeed" min="1" max="100" defaultValue="1"/>
                    <br></br>
                    <label htmlFor="Graphsichtfeld">Graphsichtfeld</label>
                    <input type="range" id="Graphsichtfeld" name="Graphsichtfeld" min="10" max="1000" defaultValue="200"/>
                    <h3>Simulations Optionen:</h3>
                    <label htmlFor="Simulations Beschleunigung">SPS: </label>
                    <input type="number" id="simspeed" name="Simulations Beschleunigung" min="1" max="1000" defaultValue="30"/>

                    <br/>



                    <label htmlFor="simmovecost">Move Cost: </label>
                    <input type="number" id="simmovecost" name="simmovecost" min="1" max="1000" defaultValue="5"/>
                    <br/>
                    <label htmlFor="simmovementspeed">Geschwindkeit: </label>
                    <input type="number" id="simmovementspeed" name="simmovementspeed" min="1" max="1000" defaultValue="5"/>

                    <br/>

                    <label htmlFor="simrotcost">Rot Cost: </label>
                    <input type="number" id="simrotcost" name="simrotcost" min="1" max="1000" defaultValue="3"/>
                    <br/>
                    <label htmlFor="simrotspeed">Rot-Geschwindkeit: </label>
                    <input type="number" id="simrotspeed" name="simrotspeed" min="1" max="1000" defaultValue="2"/>

                    <br/>

                    <label htmlFor="simeatcost">Eat Cost: </label>
                    <input type="number" id="simeatcost" name="simeatcost" min="1" max="1000" defaultValue="1"/>
                    <br/>
                    <label htmlFor="simgettingenergie">Essens-Energie-Gewinn: </label>
                    <input type="number" id="simgettingenergie" name="simgettingenergie" min="1" max="1000" defaultValue="50"/>
                    <br/>

                    <label htmlFor="permcostland">Land Cost: </label>
                    <input type="number" id="permcostland" name="permcostland" min="1" max="1000" defaultValue="0.04"/>
                    <br/>
                    <label htmlFor="permcostwater">Water-Cost: </label>
                    <input type="number" id="permcostwater" name="permcostwater" min="1" max="1000" defaultValue="3"/>

                    <br/>

                    <label htmlFor="createchildcost">Child Cost: </label>
                    <input type="number" id="createchildcost" name="createchildcost" min="1" max="1000" defaultValue="400"/>
                    <br/>
                    <label htmlFor="childage">Create Child Age: </label>
                    <input type="number" id="childage" name="childage" min="1" max="1000" defaultValue="6"/>



                    <br/>


                    <button onClick={sendParams} id={"send"}>Apply</button>
                    <button onClick={resetSimulation} id={"reset"}>Reset</button>

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