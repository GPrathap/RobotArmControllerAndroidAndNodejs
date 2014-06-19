(function () {
	var socket = io.connect('ws://localhost:8080'),
		output = document.getElementById('output'),
		send = document.getElementById('send');
		function logStr(eventStr, msg) {
				return '<div>' + eventStr + ': ' + msg + '</div>';
		}
		socket.on('connect', function () {
		send.addEventListener('click', function () {
		var msg = document.getElementById('msg').value;
			socket.send(msg);
			output.innerHTML += logStr('Sent', msg);
		});
		socket.on('message', function (msg) {
		output.innerHTML += logStr('Recieved', msg);
		});
	});
	}());
