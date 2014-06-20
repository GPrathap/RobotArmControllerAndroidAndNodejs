package com.example.cycleseekbarappwifi;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView textview;
	EditText getIp;
	public String IPAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getIp= (EditText)findViewById(R.id.getIPaddress);
		
	}
	public void setIPAddress(){
    	IPAddress =getIp.getText().toString();
    	Toast.makeText(getApplicationContext(), IPAddress,Toast.LENGTH_LONG).show();
    }
    public String getIPAddress(){
    	
    	return IPAddress;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onClick(View view){
		setIPAddress();
		Intent intent =new Intent(MainActivity.this,Option1.class);
		intent.putExtra(IPAddress,getIPAddress());
		String packgeName="com.example.cycleseekbarappwifi";
		String className="com.example.cycleseekbarappwifi.Option1";
		intent.setClassName(packgeName,className);
		startActivity(intent);
		
	}
	
	public void onClickAccelerometer(View view){
		Intent intent = new Intent(MainActivity.this,Option1.class);
		intent.putExtra("IPAddress",getIPAddress());
		String packageName = "com.example.cycleseekbarappwifi";
		String className="com.example.cycleseekbarappwifi.Option2";
		intent.setClassName(packageName, className);
		startActivity(intent);
	}
	public void sendDataOverWifi(String ip,String sendData,TextView status){
		Socket socket =null;
		DataOutputStream dataoutputStream =null;
		DataInputStream datainputStream =null;
		try{
			socket = new Socket("10.40.18.108",8888);
			dataoutputStream = new DataOutputStream(socket.getOutputStream());
			
			dataoutputStream.writeUTF("hello");
			datainputStream = new DataInputStream(socket.getInputStream());
			status.setText(datainputStream.readUTF());
			
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

}
