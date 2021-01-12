
import {config_foodavailable, config_actorssize, config_averageage} from "./chartconfig.js"
import {register,animate,worldmesh,actormesh,height,width} from "./wordRendering.js"

//Darstellung
var infoblock;
var graphupdatespeed;
var Graphsichtfeld;
const color = new THREE.Color();
const actorcolor = new THREE.Color();
const actormatrix = new THREE.Matrix4();
const matrix = new THREE.Matrix4();

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
var mutation_neurons;
var mutation_percentage;
var stopby;

//infos
var ActorsSize;
var Timeinyears;
var averageage;
var foodavailable;
var lastyears_ActorsSize;
var lastyears_averageage;



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
   // simulation = document.getElementById('screen');
    window.simset = document.getElementById("simsets");
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
    mutation_percentage = document.getElementById("mutation_percentage");
    mutation_neurons = document.getElementById("mutation_neurons");
    stopby = document.getElementById("stopby")
}


function infodraw(){
    var infos;


    infos="<h2>Info Daten</h2>"
    infos=infos+"<br>Menge an Kreaturen: "+ActorsSize+"</br>";
    infos=infos+"<br>Jahre: "+Timeinyears+"</br>";
    infos=infos+"<br>Durchschnittsalter: "+averageage+"</br>";
    infos=infos+"<br>Fressensdurschnitt: "+foodavailable+"</br>";
    infos=infos+"<br>Letze 25 Jahre Kreaturen: "+lastyears_ActorsSize+"</br>";
    infos=infos+"<br>Letze 25 Jahre Durchschnittsalter: "+lastyears_averageage+"</br>";


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
     wsConnection = new WebSocket('ws://212.227.211.145:8080/evodata');
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
        updateActors();
    }else if(JSON.parse(e.data).type==="info"){
        rawInfos = JSON.parse(e.data);
        ActorsSize=rawInfos.ActorsSize;
        Timeinyears=rawInfos.Timeinyears;
        averageage=rawInfos.averageage;
        foodavailable=rawInfos.foodavailable;
        lastyears_ActorsSize = rawInfos.lastyears_ActorsSize;
        lastyears_averageage = rawInfos.lastyears_averageage

    }else if(JSON.parse(e.data).type==="biparam") {
        console.log("apply")
        const recived = JSON.parse(e.data);
        simulationSpeedParam.value =recived.speed
        movespeed.value = recived.movespeed
        movecost.value = recived.movecost
        rotcost.value = recived.rotcost
        rotspeed.value = recived.rotspeed
        eatcost.value = recived.eatcost
        eatspeed.value = recived.eatspeed
        childenergie.value = recived.childenergie
        childage.value = recived.childage
        costwater.value = recived.costwater
        costland.value = recived.costland
        eatadmission.value = recived.eatadmission
        mutation_percentage.value = recived.mutation_percentage
        mutation_neurons.value = recived.mutation_neurons
        stopby.value = recived.stopby
        window.changePermissions = recived.permission;


        if(window.changePermissions!==0)  {
            window.simset.style.display = 'block';
        }else{
            window.simset.style.display = 'none';
        }

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
        eatadmission:eatadmission.value,
        mutation_percentage:mutation_percentage.value,
        mutation_neurons:mutation_neurons.value,
        stopby:stopby.value

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


     async function updateActors() {
        const actorSize = window.actors.length;
         //actormesh.count =actorSize;
    
        for(var i=0;i<actorSize;i++) {

        
         //actormesh
         //console.log(actormesh);
         actormatrix.setPosition((-width/2)+window.actors[i].x*0.8,(-height/2+1)+window.actors[i].y*0.8,1);
        //actormatrix.setPosition( -500,-396, 1 );
        actormesh.setMatrixAt( i, actormatrix );
        
     }

     for(var i=ActorsSize;i<1200;i++) {

        actormatrix.setPosition(1000,1000, 1 );
        actormesh.setMatrixAt( i, actormatrix );

     }
     
     actormesh.instanceMatrix.needsUpdate = true;
     actormesh.updateMatrix();

     for(var i=0;i<actorSize;i++) {

     if (window.actors[i].gen <= 10) {
        actormesh.setColorAt(i,color.setHex(rgbToHex(Math.floor((1 - window.actors[i].gen / 10) * 255),Math.floor(window.actors[i].gen / 10 * 255),0)));
      } else {
        actormesh.setColorAt(i,color.setHex( 0xffffff)); 
      }

    }
     actormesh.instanceColor.needsUpdate = true;
     }



    function rgbToHex(r, g, b) {
        return "0x" + componentToHex(r) + componentToHex(g) + componentToHex(b);
    }

    function componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }
