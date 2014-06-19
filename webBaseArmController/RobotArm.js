var mimeTypes={
		'.js':'text/javascript',
		'.html':'text/html',
		'.css':'text/css'
        }
		var currentThread;
		var http = require('http'),
    		fs = require('fs'),
    		path = require('path');
		
		var HTTP_OK = 200,
    		HTTP_ERR_UNKNOWN = 500,
    		HTTP_ERR_NOT_FOUND = 404;
		var plainHttpServer=http.createServer(function (req, res) {
    				var filepath =path.basename(decodeURI(req.url))||'test.html';
        			var f = 'content/'+filepath;
				console.log(filepath);
    				fs.exists(f, function (con) {
       					 if(f){
						fs.readFile(f,function(err,data){
							if(err){res.writeHead(500);res.end('Server error');return;}

						var header = {'Content-type':mimeTypes[path.extname(filepath)]};
						res.writeHead(200,header);
						res.end(data);
						});
						return ;
						}
					res.writeHead(404);
					res.end();
            			});
        			
    	
		}).listen(8080);
	
var io = require('socket.io').listen(plainHttpServer);
	io.set('origins', ['localhost:8080', '127.0.0.1:8080']) ;
	

var os=require('os');
var net=require('net');
var networkInterfaces=os.networkInterfaces();
var port = 8888;
var count = 1;

function callback_server_connection(socket){
    var remoteAddress = socket.remoteAddress;
    var remotePort = socket.remotePort;
    socket.setNoDelay(true);
    console.log("connected: ", remoteAddress, " : ", remotePort);
    
    var msg = 'Hello ' + remoteAddress + ' : ' +  remotePort + '\r\n'
        + "You are #" + count + '\r\n';
    count++;

    socket.end(msg);
	var a =new Array();
    var finalA = new Array();
    socket.on('data', function (data) {
       console.log(data.toString());
			a=data.toString();
			//console.log(a[0]);
			a=a.replace(/\[/g,"").replace(/\]/g,"").split(",");
			
		for(i=0;i<a.length;i++){
			
			finalA[i]=parseInt(a[i]);
			//console.log(final[i]);
		}
	   io.sockets.on('connection', function (socket) {
			//var start=0;
			//socket.on('message', function (msg) {
				//console.log('--------------------------------',msg);
				//if (msg === 'Hello') {
					//start=1;
						setInterval(function(){
							socket.emit('message',{data1:finalA[1],data2:finalA[2],data3:finalA[3],data4:finalA[4],data5:finalA[5]});	
							console.log("--->"+finalA[2]);
						},200);
					
				//}	
				//socket.emit('message',{data1:'70',data2:'12',data3:'6',data4:'50',data5:'10'});	
			//});
			
		
	    });
		
    });
    
    socket.on('end', function () {
        console.log("ended: ", remoteAddress, " : ", remotePort);
    });
}



console.log("node.js net server is waiting:");
for (var interface in networkInterfaces) {

    networkInterfaces[interface].forEach(function(details){
        
        if ((details.family=='IPv4') && !details.internal) {
            console.log(interface, details.address);  
        }
    });
}

console.log("port: ", port);

var netServer = net.createServer(callback_server_connection);
netServer.listen(port);