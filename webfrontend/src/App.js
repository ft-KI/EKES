import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from "react-router-dom"
import Communication from "./communication";
import {act} from "@testing-library/react";
import React from "react";

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
    var XpositionInCanvas=0;
    var YpositionInCanvas=0;
    var worldheightinpx=0;
    var worldwidthinpx=0;
function draw() {

    document.getElementById("test").innerHTML="<h1>Sollen mal infos sein</h1>";

    var Tilesize = world.tilesize;
    var worldheight = world.height;
    var worldwidth=world.width;
    worldheightinpx=Tilesize*worldheight;
    worldwidthinpx=Tilesize*worldwidth;
    context.fillStyle = 'black';
    context.fillRect(0,0,4500,3000);

    ////////Draw Tile World////////
    for (var x = 0; x < worldwidth; x++) {
        for (var y = 0; y < worldheight; y++) {
            //console.log(world.tiles[x][y].landType);
            if (world.world[x][y] == -1) {
                context.fillStyle = 'rgb(20,0,43)'
            } else {

                context.fillStyle = 'rgb(' + (1 - world.world[x][y]) * 255 + ',' + 191 + ',' + 15 + ')'
            }
            context.fillRect(x * Tilesize + XpositionInCanvas, YpositionInCanvas+worldheight * Tilesize - y * Tilesize, Tilesize, Tilesize);

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
        context.arc(actors[i].x+XpositionInCanvas, YpositionInCanvas+worldheight*Tilesize-actors[i].y, 4, 0, 2 * Math.PI);
        context.fill();

        var feelersize=actors[i].feelers.length;
        for(var a=0;a<feelersize;a++){

            context.linewidth=10;
            context.beginPath();
            context.moveTo(XpositionInCanvas+actors[i].feelers[a].feelerpos.x, YpositionInCanvas+worldheight*Tilesize-actors[i].feelers[a].feelerpos.y);
            context.lineTo(XpositionInCanvas+actors[i].x,YpositionInCanvas+worldheight*Tilesize-actors[i].y);
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


    var elem = document.querySelector('body'), text = '';
    elem.addEventListener ("keydown", function (event) {
        console.log(event.code);
        if(event.code=="ArrowUp"){
            YpositionInCanvas+=10;
        }
        if(event.code=="ArrowDown"){
            YpositionInCanvas+=-10;
        }
        if(event.code=="ArrowLeft"){
            XpositionInCanvas+=10;
        }
        if(event.code=="ArrowRight"){
            XpositionInCanvas+=-10;
        }
        if(event.key=="a") {
            context.scale(0.9,0.9);
        }
        if(event.key=="q"){
            context.scale(1.01,1.01);
        }
    });

    //var DeinName = prompt("Gib bitte Deinen Namen an.", "Name");
    //document.write("<b>Hallo " + DeinName + "<\/b>");

    requestAnimationFrame(draw);
    //alert("Hier die Simulation");

}


function App() {

    return (
        <div className="App">
            <div id="infos">
                <p id="test"></p>
            </div>
            <canvas id="screen" width="1200" height="800"></canvas>
            <div id="controll">
                <p>controllelemte</p>
            </div>

        </div>
    );

}

export default App;
