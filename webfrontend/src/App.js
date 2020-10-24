import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from "react-router-dom"
import Communication from "./communication";
import {act} from "@testing-library/react";

let backend = new Communication("http://localhost:8080");

var world;
var actors;
var simulation;
var context;

async function getWorld() {
    world = JSON.parse( backend.getWorld());
}

async function getActors() {
    actors = JSON.parse(backend.getActors());

}

setInterval(getWorld, 13);
setInterval(getActors, 13);

function draw() {


    var Tilesize = world.tilesize;
    var worldheight = world.height;
    var worldwidth=world.width;
    context.fillStyle = 'black';
    context.fillRect(0,0,4500,3000);

    ////////Draw Tile World////////
    for (var x = 0; x < worldwidth; x++) {
        for (var y = 0; y < worldheight; y++) {
            //console.log(world.tiles[x][y].landType);
            if (world.world[x][y] == -1) {
                context.fillStyle = 'darkblue'
            } else {

                context.fillStyle = 'rgb(' + (1 - world.world[x][y]) * 255 + ',' + 255 + ',' + 0 + ')'
            }
            context.fillRect(x * Tilesize, worldheight * Tilesize - y * Tilesize, Tilesize, Tilesize);

        }
    }
    ////////Draw Kreatures///////////

    context.beginPath();
    var actorsize =actors.length;
    for(var i=0;i<actorsize;i++)  {
        if(actors[i].gen<=10) {
            context.fillStyle = 'rgb(' + (1 - actors[i].gen / 10) * 255 + ',' + actors[i].gen / 10 * 255 + ',0)'
        }else{
            context.fillStyle='white';
        }
        context.arc(actors[i].x, worldheight*Tilesize-actors[i].y, 4, 0, 2 * Math.PI);
        context.fill();

        var feelersize=actors[i].feelers.length;
        for(var a=0;a<feelersize;a++){

            context.linewidth=10;
            context.beginPath();
            context.moveTo(actors[i].feelers[a].feelerpos.x, worldheight*Tilesize-actors[i].feelers[a].feelerpos.y);
            context.lineTo(actors[i].x,worldheight*Tilesize-actors[i].y);
            context.stroke();

            context.closePath();





        }

    }




    requestAnimationFrame(draw);

}



document.body.onload = startShow;

async  function startShow() {


    simulation = document.getElementById('screen');
    context = simulation.getContext('2d');
    context.scale(0.80,0.80);

    requestAnimationFrame(draw);


}


function App() {

    return (
        <div className="App">
            <canvas id="screen" width="1200" height="800"></canvas>
        </div>
    );
}

export default App;
