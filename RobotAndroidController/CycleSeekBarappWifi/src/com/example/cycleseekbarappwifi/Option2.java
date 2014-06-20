package com.example.cycleseekbarappwifi;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;





public class Option2 extends Activity implements SensorEventListener,OnSeekBarChangeListener{
	private Socket socket;
    private static final int SERVERPORT = 8888;
	private static final String SERVER_IP = "169.254.55.126";
	FileInputStream  mfileinputstream;
	EditText getIpAddress;
	private String IPAddress;
	FileOutputStream  mfileoutputsream;
	private static final byte TARGET_PIN =0x02;
	TextView connectedstate;
	int[] databuffer;
	
	boolean mInitialized;
	Sensor maccelerometer;
	SensorManager msensormanager;
	 TextView tx,ty,tz,iv;
	
	 
	TextView angle1,angle2,angle3,angle4,angle5;
	ToggleButton button1,button2,button3,button4,button5;
	SeekBar seekbar1,seekbar2,seekbar3,seekbar4,seekbar5;
	private int [] data =new int[5];
	boolean state1=false,state2=false,state3=false,state4=false,state5=false;
	 Bundle extras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accelerometerxml);
		extras = getIntent().getExtras();
		if (extras != null) {
			IPAddress = extras.getString("IPAddress");
			
		}
		
		new Thread(new ClientThread()).start();
		 msensormanager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		 maccelerometer = msensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		 msensormanager.registerListener(this,maccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
		 databuffer=new int[6];
		for(int i=0;i<6;i++){databuffer[i]=0;}
		databuffer[0]=TARGET_PIN;
	    connectedstate = (TextView)findViewById(R.id.connectedacc);
	        
		 angle1 = (TextView)findViewById(R.id.parent0);
		 angle2 = (TextView)findViewById(R.id.parent1);
		 angle3 = (TextView)findViewById(R.id.parent2);
		 angle4 = (TextView)findViewById(R.id.parent3);
		 angle5 = (TextView)findViewById(R.id.parent4);
		
		 button1 =(ToggleButton)findViewById(R.id.toggleButton1);
		 button2 =(ToggleButton)findViewById(R.id.toggleButton2);
		 button3 =(ToggleButton)findViewById(R.id.toggleButton3);
		 button4 =(ToggleButton)findViewById(R.id.toggleButton4);
		 button5 =(ToggleButton)findViewById(R.id.toggleButton5);
		 
		  seekbar1 =(SeekBar)findViewById(R.id.slider0);
		  seekbar2 =(SeekBar)findViewById(R.id.slider1);
		  seekbar3 =(SeekBar)findViewById(R.id.slider2);
		  seekbar4 =(SeekBar)findViewById(R.id.slider3);
		  seekbar5 =(SeekBar)findViewById(R.id.slider4);
		
		
	}

	public void setIPAddress(String ipAddress) {
		IPAddress=ipAddress;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
		
	}
	public void toggleButtonClick(View view){
		ToggleButton clickedOne =(ToggleButton)view;
		state1=(clickedOne.getId()==R.id.toggleButton1)?true:false;
		state2=(clickedOne.getId()==R.id.toggleButton2)?true:false;
		state3=(clickedOne.getId()==R.id.toggleButton3)?true:false;
		state4=(clickedOne.getId()==R.id.toggleButton4)?true:false;
		state5=(clickedOne.getId()==R.id.toggleButton5)?true:false;
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		
		float x =arg0.values[0];
		float y=arg0.values[1];
		float z=arg0.values[2];
		angle1.setText(Integer.toString(Math.round(((x/9)*60))+60));
		angle2.setText(Integer.toString(Math.round(((y/9)*60))+60));
		angle4.setText(Integer.toString(Math.round(((z/9)*60))+60));
		angle3.setText(Integer.toString(Math.round(((x/9)*60))+60));
		angle5.setText(Integer.toString(Math.round(((y/9)*60))+60));
		
		if(state1==true){
			if((Math.round(((x/9)*60))+60)<=120 &&(Math.round(((x/9)*60))+60)>=0){
					data[0]=Math.round(((x/9)*60))+60;
					seekbar1.setProgress(data[0]);
					databuffer[1]=(byte)data[0];
					try {
						
						PrintWriter out = new PrintWriter(new BufferedWriter(
								new OutputStreamWriter(socket.getOutputStream())),
								true);
						
						out.println(Arrays.toString(databuffer));
						
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
			}
		}
		if(state2==true){
			if((Math.round(((y/9)*60))+60)<=120 &&(Math.round(((y/9)*60))+60)>=0){
				data[1]=Math.round(((y/9)*60))+60;
				seekbar2.setProgress(data[1]);
				databuffer[2]=(byte)data[1];
				try {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					
					out.println(Arrays.toString(databuffer));
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(state3==true){
			if((Math.round(((x/9)*60))+60)<=120 &&(Math.round(((x/9)*60))+60)>=0){
				data[2]=Math.round(((x/9)*60))+60;
				seekbar3.setProgress(data[2]);
				databuffer[3]=(byte)data[2];
				try {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					
					out.println(Arrays.toString(databuffer));
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(state4==true){
			if((Math.round(((z/9)*60))+60)<=120 &&(Math.round(((z/9)*60))+60)>=0){
				data[3]=Math.round(((z/9)*60))+60;
				seekbar4.setProgress(data[3]);
				databuffer[4]=(byte)data[3];
				try {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					
					out.println(Arrays.toString(databuffer));
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(state5==true){
			if((Math.round(((y/9)*60))+60)<=120 &&(Math.round(((y/9)*60))+60)>=0){
				data[4]=Math.round(((y/9)*60))+60;
				seekbar5.setProgress(data[4]);
				databuffer[5]=(byte)data[4];
				try {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					
					out.println(Arrays.toString(databuffer));
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
       
    }
	
	
    @Override
    public void onDestroy() {
        super.onDestroy();
      
    }
   

    public String setConnectionStatus(boolean connected) {
    	if(connected==true){
    		return "Connected";
    	}
    	return  "Disconnected";
    }
    
    
	@Override
	protected void onPause(){
		super.onPause();
		
		
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		if(arg0.getId()==R.id.slider0){
			if(state1==true){
				seekbar1.setProgress(data[0]);}
		}
		if(arg0.getId()==R.id.slider1){
			if(state2==true){
			seekbar2.setProgress(data[1]);}
		}
		if(arg0.getId()==R.id.slider2){
			if(state3==true){
				seekbar3.setProgress(data[2]);}
		}
		if(arg0.getId()==R.id.slider3){
			if(state4==true){
				seekbar4.setProgress(data[3]);}
		}
		if(arg0.getId()==R.id.slider4){
			if(state5==true){
				seekbar5.setProgress(data[4]);}
		}
		return;
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	public String getIPAddress() {
		return IPAddress;
	}
	class ClientThread implements Runnable {

		@Override
		public void run() {
			
			try {
				InetAddress serverAddr = InetAddress.getByName(getIPAddress());

				socket = new Socket(serverAddr, SERVERPORT);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}
