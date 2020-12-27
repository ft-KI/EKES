var scene;
var camera;
var renderer;
export var worldmesh;
export var actormesh;


export const width = 1200;
export const height = 800;

const color = new THREE.Color();
const matrix = new THREE.Matrix4();


export function register() {

    scene = new THREE.Scene();
    const aspect = window.innerWidth / window.innerHeight;
    
    camera = new THREE.OrthographicCamera(width / - 2, width / 2, height / 2, height / - 2, 1, 1000);
    var canvasElm = document.getElementById('screen');
    renderer = new THREE.WebGLRenderer( { canvas: canvasElm });
    renderer.setClearColor(new THREE.Color('#14002b'),1);


   
    camera.position.z = 5;
       

    var material = new THREE.MeshBasicMaterial( { color: 0xFFFFFF } );
    const geometry = new THREE.PlaneBufferGeometry(width/150,height/100,1); //0.045,50
    worldmesh = new THREE.InstancedMesh( geometry, material, 15000 );
 
    var actormaterial = new THREE.MeshBasicMaterial({ color: 0xFFFFFF } );
    const actorgeometry = new THREE.CircleBufferGeometry( 3.1, 20 );
    actormesh = new THREE.InstancedMesh(actorgeometry,actormaterial,1200);

    actormesh.instanceMatrix.setUsage( THREE.DynamicDrawUsage ); 

var i=0;
for(var x=0;x<150;x++) {
    
    for(var y=0;y<100;y++) {
        
       // worldtils[x][y] = new THREE.Mesh( geometry, material );
       matrix.setPosition( -width/2+(width/150)/2+x*width/150, -height/2+(height/100)/2+y*height/100, 1 );

       worldmesh.setMatrixAt( i, matrix );
       worldmesh.setColorAt( i, color );
     


       i++;

       // worldtils[x][y].position.x = -width/2+(width/150)/2+x*width/150;
       // worldtils[x][y].position.y = -height/2+(height/100)/2+y*height/100;
       // scene.add(worldtils[x][y]);
    }
}

for(var z=0;z<1200;z++) {

    matrix.setPosition( 1000,1000, 1 );

    actormesh.setMatrixAt(z, matrix );
    actormesh.setColorAt( z, color );

}

scene.add(actormesh);
scene.add(worldmesh);



    

}



export function animate() {

   

    renderer.render( scene, camera );
}
