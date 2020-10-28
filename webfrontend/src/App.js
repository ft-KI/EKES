import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route} from "react-router-dom"
import Communication from "./communication";
import React from "react";

var world;
var actors;
var simulation;
var context;

var worldwidth;
var Tilesize;
var worldheight;
var worldfield;

var wsConnection = new WebSocket('ws://localhost:8080/evodata');
wsConnection.onopen = function () {
};
wsConnection.onerror = function (error) {
};
wsConnection.onmessage = async function (e) {

    // console.log(e.data);

    if (JSON.parse(e.data).type === "world") {
        world = JSON.parse(e.data);
        worldfield = world.world;
    } else if (JSON.parse(e.data).type === "creatures") {
        actors = JSON.parse(JSON.parse(e.data).kreatures);

    }

};

var XpositionInCanvas = 0;
var YpositionInCanvas = 0;
var worldheightinpx = 0;
var worldwidthinpx = 0;

function draw() {
    try {


        Tilesize = world.tilesize;
        worldheight = world.height;
        worldwidth = world.width;

        worldheightinpx = Tilesize * worldheight;
        worldwidthinpx = Tilesize * worldwidth;
        context.fillStyle = '#14002b'

        context.fillRect(0, 0, 1500, 1000);

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
    requestAnimationFrame(draw);

}

document.body.onload = startShow;

async function startShow() {

    simulation = document.getElementById('screen');
    context = simulation.getContext('2d');
    context.linewidth = 10;

    context.scale(0.80, 0.80);

    document.getElementById("info").innerHTML = "<h1>Sollen mal infos sein</h1>";


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
            context.scale(1.01, 1.01);
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
                </div>
                <canvas id="screen" width="1200" height="800"></canvas>
                <div id="controll">
                    <p>controllelemte</p>
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