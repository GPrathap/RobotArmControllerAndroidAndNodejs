var os=require('os');
var net=require('net');
var networkInterfaces=os.networkInterfaces();
var port = 8888;
var count = 1;
var a =new Array();
function callback_server_connection(socket){
    var remoteAddress = socket.remoteAddress;
    var remotePort = socket.remotePort;
    socket.setNoDelay(true);
    console.log("connected: ", remoteAddress, " : ", remotePort);
    
    var msg = 'Hello ' + remoteAddress + ' : ' +  remotePort + '\r\n'
        + "You are #" + count + '\r\n';
    count++;

    socket.end(msg);
    
    socket.on('data', function (data) {
       console.log(data.toString());
	    
		a=data.toString();
		//console.log(a[0]);
        a=a.replace(/\[/g,"").replace(/\]/g,"").split(",");
		var final = new Array();
		for(i=0;i<a.length;i++){
			
			final[i]=parseInt(a[i]);
			//console.log(final[i]);
		}
		
    });
    
    socket.on('end', function () {
        console.log("ended: ", remoteAddress, " : ", remotePort);
    });
}



console.log("node.js net server is waiting:");

console.log("port: ", port);

var netServer = net.createServer(callback_server_connection);
netServer.listen(port);