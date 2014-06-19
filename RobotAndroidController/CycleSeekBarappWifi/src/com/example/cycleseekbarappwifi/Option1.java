package com.example.cycleseekbarappwifi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;



public class Option1 extends Activity{
	private Socket socket;

	private static final int SERVERPORT = 8888;
	private static final String SERVER_IP = "169.254.55.126";
	String  mfileoutputsream;
	private static final byte TARGET_PIN =0x01;
	TextView connectedstate;
 
 
	Cyclebar cricularSeekbar,cricularSeekbar1,cricularSeekbar2,cricularSeekbar3,cricularSeekbar4;
	TextView textview,textview1,textview2,textview3,textview4;
    EditText getIpAddress;
    int[] databuffer;
    public String IPAddress;
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criclemain);
		mfileoutputsream="data";
		databuffer=new int[6];
		databuffer[0]=TARGET_PIN;
		
		new Thread(new ClientThread()).start();
		
	
		connectedstate = (TextView)findViewById(R.id.servo31);
		connectedstate.setText(setConnectionStatus(true));
		
        cricularSeekbar =(Cyclebar)findViewById(R.id.servos1);
		textview = (TextView)findViewById(R.id.servo1);
		cricularSeekbar.setMaxProcess(100);
		cricularSeekbar.setProcess(100);
		cricularSeekbar.invalidate();
		
		cricularSeekbar1 =(Cyclebar)findViewById(R.id.servos2);
		textview1 = (TextView)findViewById(R.id.servo2);
		cricularSeekbar1.setMaxProcess(100);
		cricularSeekbar1.setProcess(100);
		cricularSeekbar1.invalidate();
		
		cricularSeekbar2 =(Cyclebar)findViewById(R.id.servos3);
		textview2 = (TextView)findViewById(R.id.servo3);
		cricularSeekbar2.setMaxProcess(100);
		cricularSeekbar2.setProcess(100);
		cricularSeekbar2.invalidate();
		
		cricularSeekbar3 =(Cyclebar)findViewById(R.id.servos4);
		textview3 = (TextView)findViewById(R.id.servo4);
		cricularSeekbar3.setMaxProcess(100);
		cricularSeekbar3.setProcess(100);
		cricularSeekbar3.invalidate();
		
		cricularSeekbar4 =(Cyclebar)findViewById(R.id.servos5);
		textview4 = (TextView)findViewById(R.id.servo5);
		cricularSeekbar4.setMaxProcess(100);
		cricularSeekbar4.setProcess(100);
		cricularSeekbar4.invalidate();
		
		
		
		cricularSeekbar.setSeekBarChangeListener(new OnSeekChangeListener(){
			@Override
			public void onProgressChange(Cyclebar view, int newprogress) {
				
		    	
		    	databuffer[1]=(int)newprogress;
		      
		    	try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(Arrays.toString(databuffer));
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textview.setText(Integer.toString(newprogress));}
			});
	    cricularSeekbar1.setSeekBarChangeListener(new OnSeekChangeListener(){
	    	@Override
			public void onProgressChange(Cyclebar view, int newprogress) {
	    		databuffer[2]=(int)newprogress;
	    		try {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(Arrays.toString(databuffer));
				
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textview1.setText(Integer.toString(newprogress));}
			});
	    cricularSeekbar2.setSeekBarChangeListener(new OnSeekChangeListener(){
			@Override
			public void onProgressChange(Cyclebar view, int newprogress) {
				databuffer[3]=(int)newprogress;
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(Arrays.toString(databuffer));
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textview2.setText(Integer.toString(newprogress));}
			});
	    cricularSeekbar3.setSeekBarChangeListener(new OnSeekChangeListener(){
	    	@Override
			public void onProgressChange(Cyclebar view, int newprogress) {
	    		databuffer[4]=(byte)newprogress;
	    		try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(Arrays.toString(databuffer));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textview3.setText(Integer.toString(newprogress));}
			});
	    cricularSeekbar4.setSeekBarChangeListener(new OnSeekChangeListener(){
			@Override
			public void onProgressChange(Cyclebar view, int newprogress) {
				databuffer[5]=(byte)newprogress;
				try {
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
					out.println(Arrays.toString(databuffer));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textview4.setText(Integer.toString(newprogress));}
			});
	   
		
	}
	public String setConnectionStatus(boolean connected) {
    	if(connected==true){
    		return "Connected";
    	}
    	return  "Disconnected";
    }
	@Override
    public void onResume() {
        super.onResume();
        
    }
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
    }
	
    @Override
    public void onDestroy() {
        super.onDestroy();
      
    }
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	class ClientThread implements Runnable {

		@Override
		public void run() {
			
			try {
				InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

				socket = new Socket(serverAddr, SERVERPORT);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
}