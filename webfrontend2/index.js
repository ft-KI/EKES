
import {config_foodavailable, config_actorssize, config_averageage} from "./chartconfig.js"
import {register,animate,worldmesh} from "./wordRendering.js"

//Darstellung
var infoblock;
var graphupdatespeed;
var Graphsichtfeld;
const color = new THREE.Color();

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
var eatadmission;
var drawcount=0;

//infos
var ActorsSize;
var Timeinyears;
var averageage;
var foodavailable;



window.onload = function(){
    start();
};

function rendering() {

    if(parseInt(graphupdatespeed.value) % drawcount) {
        updateGraphs();
    }
    infodraw();
    animate();

    drawcount++;
    requestAnimationFrame(rendering);
}


function start() {
    loadInfoDisplay();

    register();
    initWS();

    rendering();
}


function loadInfoDisplay() {

    var ctx = document.getElementById('graphAverageAge').getContext('2d');
    var ctx2 = document.getElementById('graphActorsSize').getContext('2d');
    var ctx3 = document.getElementById('graphFoodAvailable').getContext('2d');

   window.graphAverageAge = new Chart(ctx, config_averageage);
    window.graphActorsSize = new Chart(ctx2,config_actorssize);
    window.graphFoodAvailable=new Chart(ctx3, config_foodavailable);

    document.getElementById("reset").onclick = function() {resetSimulation()};
    document.getElementById("send").onclick = function() {sendParams()};
 console.log( document.getElementById("reset").onclick);
   // simulation = document.getElementById('screen');
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
    eatadmission = document.getElementById("eatadmission");
}


function infodraw(){
    var infos;


    infos="<h2>Info Daten</h2>"
    infos=infos+"<br>ActorsSize: "+ActorsSize+"</br>";
    infos=infos+"<br>Timeinyears: "+Timeinyears+"</br>";
    infos=infos+"<br>averageage: "+averageage+"</br>";
    infos=infos+"<br>foodavailable: "+foodavailable+"</br>";



    infoblock.innerHTML = infos;

}

async function updateGraphs(){
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





var rawInfos
//Connections
var wsConnection;
function initWS() {
     wsConnection = new WebSocket('ws://localhost:8080/evodata');
wsConnection.onopen = function () {
};
wsConnection.onerror = function (error) {
};

wsConnection.onmessage = async function (e) {


    if (JSON.parse(e.data).type === "world") {
        window.world = JSON.parse(e.data);
        window.worldfield = window.world.world;
        updateWorld();
    } else if (JSON.parse(e.data).type === "creatures") {
        window.actors = JSON.parse(JSON.parse(e.data).kreatures);
    }else if(JSON.parse(e.data).type==="info"){
        rawInfos = JSON.parse(e.data);
        ActorsSize=rawInfos.ActorsSize;
        Timeinyears=rawInfos.Timeinyears;
        averageage=rawInfos.averageage;
        foodavailable=rawInfos.foodavailable;

    }


};

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
        costland:costland.value,
        eatadmission:eatadmission.value
    
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


     async function updateWorld() {

        var i =0;

        for (var x = 0; x < window.world.width; x++) {
            for (var y = 0; y < window.world.height; y++) {
                if (window.worldfield[x][y] === -1) {
                    worldmesh.setColorAt(i,color.setHex( 0x14002b));
                }else{
                    worldmesh.setColorAt(i,color.setHex( rgbToHex(Math.floor((1 - window.worldfield[x][y]) * 255),191,15)));
                }

                i++;

            }
        }

        //worldtils[x][y]
        //console.log(window.worldfield);

        worldmesh.instanceColor.needsUpdate = true;
    }
    function rgbToHex(r, g, b) {
        return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
    }

    function componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }
