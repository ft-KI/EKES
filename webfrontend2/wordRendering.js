var scene;
var camera;
var renderer;


const width = 1200;
const height = 800;

export var worldtils = [];
export function register() {

    scene = new THREE.Scene();
    const aspect = window.innerWidth / window.innerHeight;
    
    camera = new THREE.OrthographicCamera(width / - 2, width / 2, height / 2, height / - 2, 1, 1000);
    var canvasElm = document.getElementById('screen');
    renderer = new THREE.WebGLRenderer( { canvas: canvasElm });
    renderer.setClearColor(new THREE.Color('#14002b'),1);


   
    camera.position.z = 5;

    const geometry = new THREE.PlaneGeometry(width/150,height/100,1); //0.045,50


for(var x=0;x<150;x++) {
    worldtils[x] = [];
    for(var y=0;y<100;y++) {
        var material = new THREE.MeshBasicMaterial( { color: 0xFFFFFF } );
        worldtils[x][y] = new THREE.Mesh( geometry, material );
        worldtils[x][y].position.x = -width/2+(width/150)/2+x*width/150;
        worldtils[x][y].position.y = -height/2+(height/100)/2+y*height/100;
        scene.add(worldtils[x][y]);
    }
}



     


}

export function animate() {
    renderer.render( scene, camera );
}
