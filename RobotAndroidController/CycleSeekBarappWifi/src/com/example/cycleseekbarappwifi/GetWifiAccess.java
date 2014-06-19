package com.example.cycleseekbarappwifi;
//This method can be used for device which has android version below 4.0
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetWifiAccess extends Activity {
	EditText textout;
	TextView textIn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textout = (EditText)findViewById(R.id.servo1);
		Button buttonSend = (Button)findViewById(R.id.button1);
		//textIn = (TextView)findViewById(R.id.servo1);
		buttonSend.setOnClickListener(buttonOnClickListener);
	}
	Button.OnClickListener buttonOnClickListener = new Button.OnClickListener(){
		@Override
		public void onClick(View arg0){
			Socket socket =null;
			DataOutputStream dataoutputStream =null;
			DataInputStream datainputStream =null;
			try{
				socket = new Socket("192.168.137.1",8888);
				dataoutputStream = new DataOutputStream(socket.getOutputStream());
				
				dataoutputStream.writeUTF(textout.getText().toString());
				datainputStream = new DataInputStream(socket.getInputStream());
				textIn.setText(datainputStream.readUTF());
				
			}catch(IOException e){
				e.printStackTrace();
			}
			finally{
				if(socket!=null){
					try{
						socket.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				if(dataoutputStream!=null){
					try{
						dataoutputStream.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				if(datainputStream !=null){
					try{
						datainputStream.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	};
	

}

