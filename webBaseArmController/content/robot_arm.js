var camera, scene, renderer;
var cameraControls, effectController,effectControllerSocketIo;
var clock = new THREE.Clock();
var gridX = true;
var gridY = false;
var gridZ = false;
var axes = true;
var ground = true;
var arm, forearm,torus,frontArm,handLeft,handRight;
effectControllerSocketIo = {
		'uy': 70.0,
		'uz': -15.0,

		'fy': 10.0,
		'fz': 60.0,
		'gz': 15.0
	};
	
function map(a,b,c,d,N)
{
    return  c+((N-a)*(d-c))/(b-a);
}

var socket = io.connect('ws://localhost:8080'),
		output = document.getElementById('output'),
		send = document.getElementById('send');
		
		send.addEventListener('click', function () {
		var msg = document.getElementById('msg').value;
			socket.send(msg);
			
		});
		socket.on('message', function (data) {
			//output.innerHTML += logStr('Recieved', data.data1);
			//setRobotArmParameters(data.data1,data.data2,data.data3,data.data4,data.data5);
			//alert(data.data1);
			effectControllerSocketIo.uy=parseInt(map(0,80,-180,180,data.data1));
			effectControllerSocketIo.uz=parseInt(map(0,80,-45,45,data.data2));
			effectControllerSocketIo.fz=parseInt(map(0,80,-90,100,data.data3));
			effectControllerSocketIo.fy=parseInt(map(0,80,-105,105,data.data4));
			effectControllerSocketIo.gz=parseInt(map(0,80,2,15,data.data5));
			//effectControllerSocketIo.gz=parseInt(map(1,76,2,15,data.data5));
		});
		


function fillScene() {
	scene = new THREE.Scene();
	scene.fog = new THREE.Fog( 0x808080, 2000, 4000 );

	// LIGHTS
	var ambientLight = new THREE.AmbientLight( 0x222222 );

	var light = new THREE.DirectionalLight( 0xFFFFFF, 1.0 );
	light.position.set( 200, 400, 500 );
	
	var light2 = new THREE.DirectionalLight( 0xFFFFFF, 1.0 );
	light2.position.set( -500, 250, -200 );

	scene.add(ambientLight);
	scene.add(light);
	scene.add(light2);
	Coordinates.drawGround({size:10000});		
	
	
	
	var robotBaseMaterial = new THREE.MeshPhongMaterial( { color: 0x6E23BB, specular: 0x6E23BB, shininess: 20 } );
	var robotForearmMaterial = new THREE.MeshPhongMaterial( { color: 0xF4C154, specular: 0xF4C154, shininess: 100 } );
	var robotUpperArmMaterial = new THREE.MeshPhongMaterial( { color: 0x95E4FB, specular: 0x95E4FB, shininess: 100 } );
	var baseP = new THREE.Mesh( new THREE.CubeGeometry( 100, 60, 100 ),robotUpperArmMaterial);
	scene.add(baseP);
	torus = new THREE.Object3D();
	var baseLength=40;
	addBasePart(torus,baseLength,robotForearmMaterial);
	//torus.rotation.x = 90 * Math.PI/180;

	var handLength = 38;
	
	handLeft = new THREE.Object3D();
	createRobotGrabber( handLeft, -handLength, robotForearmMaterial );

	handRight = new THREE.Object3D();
	createRobotGrabber( handRight, handLength, robotForearmMaterial );
	

	frontArm = new THREE.Object3D();
	var frontLength=40;
  	createRobotFrontArm(frontArm,frontLength, robotForearmMaterial );
	
	forearm = new THREE.Object3D();
	var faLength = 80;

	createRobotExtender( forearm, faLength, robotForearmMaterial );

	arm = new THREE.Object3D();
	var uaLength = 80;	
	createRobotExtender( arm, uaLength, robotForearmMaterial );
	
	
	handLeft.position.y = frontLength+38;
	handRight.position.y=frontLength;
	handRight.position.z=15;
	handLeft.position.z=-15;
	frontArm.position.y=faLength;
	forearm.position.y = uaLength;
	arm.position.y=baseLength/2;
	torus.position.y=30;

	frontArm.add(handLeft);
	frontArm.add(handRight);
	forearm.add(frontArm);	
	arm.add(forearm);
	torus.add(arm);
	//scene.add(frontArm);
	scene.add( torus );
	//scene.add( arm );
}
function createRobotGrabber( part, length, material )
{
	var box = new THREE.Mesh(
		new THREE.CubeGeometry( 30, Math.abs(length), 4 ), material );
	box.position.y = length/2;
	part.add( box );
}
function createRobotFrontArm(part,length,material){
	var cylinder = new THREE.Mesh(new THREE.CylinderGeometry(15,15,40,32),material);
	cylinder.rotation.x=90*Math.PI/180;
	part.add(cylinder);
	var cube = new THREE.Mesh(new THREE.CubeGeometry(30,40,30),material);
	cube.position.y=length/2;
	part.add(cube);
	var cylinder = new THREE.Mesh(new THREE.CylinderGeometry(15,15,40,32),material);
	cylinder.rotation.x=90*Math.PI/180;
	cylinder.position.y=cube.position.y+20;
	part.add(cylinder);
}
function addBasePart(part,length,material){
	var base = new THREE.Mesh( new THREE.CylinderGeometry( length, length, 10, 32 ), material);
	part.add(base);
}
function createRobotExtender( part, length, material )
{
	var cylinder = new THREE.Mesh( 
		new THREE.CylinderGeometry( 15, 15, 40, 32 ), material );
		cylinder.rotation.x = 90 * Math.PI/180;
	part.add( cylinder );

	var i;
	for ( i = 0; i < 4; i++ )
	{
		var box = new THREE.Mesh( 
			new THREE.CubeGeometry( 16, length, 4 ), material );
		box.position.x = (i < 2) ? -8 : 8;
		box.position.y = length/2;
		box.position.z = (i%2) ? -8 : 8;
		part.add( box );
	}
	
	cylinder = new THREE.Mesh( 
		new THREE.CylinderGeometry( 15, 15, 40, 32 ), material );
	cylinder.rotation.x = 90 * Math.PI/180;
	cylinder.position.y = length;
	part.add( cylinder );
}

function init() {
	
	var canvasWidth = window.innerWidth;
	var canvasHeight = window.innerHeight;
	var canvasRatio = canvasWidth / canvasHeight;

	// RENDERER
	renderer = new THREE.WebGLRenderer( { antialias: true } );
	renderer.gammaInput = true;
	renderer.gammaOutput = true;
	renderer.setSize(canvasWidth, canvasHeight);
	renderer.setClearColorHex( 0xAAAAAA, 1.0 );

	
	$("#canvasOne").append(renderer.domElement);
	// CAMERA
	camera = new THREE.PerspectiveCamera( 30, canvasRatio, 1, 10000 );
	camera.position.set( -510, 240, 100 );
	// CONTROLS
	cameraControls = new THREE.OrbitAndPanControls(camera, renderer.domElement);
	cameraControls.target.set(0,100,0);
	
	fillScene();

}

function animate() {
	window.requestAnimationFrame(animate);
	render();
}

function render() {
	var delta = clock.getDelta();
	cameraControls.update(delta);
	torus.rotation.y = effectControllerSocketIo.uy * Math.PI/180;	// yaw
	arm.rotation.z = effectControllerSocketIo.uz * Math.PI/180;	// roll
	
	frontArm.rotation.z = effectControllerSocketIo.fy * Math.PI/180;	// yaw
	forearm.rotation.z = effectControllerSocketIo.fz * Math.PI/180;	// roll
	handLeft.position.z = -effectControllerSocketIo.gz ;
	handRight.position.z= effectControllerSocketIo.gz;
		
	renderer.render(scene, camera);
}



function setupGui() {

	effectController = {
		uy: 70.0,
		uz: -15.0,

		fy: 10.0,
		fz: 60.0,
		gz: 15.0
	};
	
	var gui = new dat.GUI();
	var armg = gui.addFolder("Arm angles");
	armg.add(effectController, "uy", -180.0, 180.0, 0.025).name("Base");
	armg.add(effectController, "uz", -45.0, 45.0, 0.025).name("Upper arm ");
	armg.add(effectController, "fy", -105.0, 105.0, 0.025).name("Forearm ");
	armg.add(effectController, "fz", -90.0, 100.0, 0.025).name("Forearm ");
	armg.add(effectController, "gz", 2.0, 15.0, 0.025).name("Graber ");
}



init();
setupGui();
animate();






