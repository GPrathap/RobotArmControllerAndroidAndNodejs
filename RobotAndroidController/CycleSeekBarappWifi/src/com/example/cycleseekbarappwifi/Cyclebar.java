package com.example.cycleseekbarappwifi;



import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
public class Cyclebar extends View  {
	
	public Cyclebar(Context context, AttributeSet attrs,int defstyle) {
		super(context, attrs,defstyle);
		mcontext =context;
		initDrawable();
		// TODO Auto-generated constructor stub
	}
	public Cyclebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext =context;
		initDrawable();
		// TODO Auto-generated constructor stub
	}
	public Cyclebar(Context context) {
		super(context);
		mcontext =context;
		initDrawable();
		// TODO Auto-generated constructor stub
	}
	private Context mcontext;
	private OnSeekChangeListener mseeklistener;
	private Paint criclecolor;
	private Paint innercolor;
	private Paint circleRing;
	private int angle=0;
	private int startangle=160;
	private int bawidth=8;
	private int width;
	private int height;
	private int maxprocess=100;
	private int process;
	private int progresspersent;
	private float innercricle;
	private float outerradius;
	private float cx;
	private float cy;
	private float left;
	private float right;
	private float top;
	private float buttom;
	private float dx;
	private float dy;
	private float startpointx;
	private float startpointy;
	private float markpointx;
	private float markpointy;
	private float adjustmentfactor =3;
	public Bitmap progressmark;
	private Bitmap proogressPressed;
	private boolean IS_PRESSED=false;
	private boolean CANSEL_FROM_ANGLE=false;
	private RectF rect=new RectF();
	{
		
		
		mseeklistener = new OnSeekChangeListener(){
	    @Override
	    public void onProgressChange(Cyclebar view,int newprogress){}
		
	};
	Random rnd = new Random(); 
	int color1 = Color.argb(255,(int)(this.getProcess()+rnd.nextInt(100)),(int)(this.getProcess()+rnd.nextInt(200)) ,(int)(this.getProcess()+rnd.nextInt(50)) );   
	//view.setBackgroundColor(color);
	criclecolor = new Paint();
	innercolor = new Paint();
	circleRing = new Paint();
	criclecolor.setColor(Color.parseColor("#ff3be5"));
	innercolor.setColor(color1);
	criclecolor.setColor(Color.GRAY);
	
	criclecolor.setAntiAlias(true);
	innercolor.setAntiAlias(true);
	circleRing.setAntiAlias(true);
	
	circleRing.setStrokeWidth(5);
	innercolor.setStrokeWidth(5);
	criclecolor.setStrokeWidth(5);
	criclecolor.setStyle(Paint.Style.FILL);
	}
	public void initDrawable(){
		progressmark = BitmapFactory.decodeResource(mcontext.getResources(),R.drawable.scrubber_control_normal_holo);
		proogressPressed = BitmapFactory.decodeResource(mcontext.getResources(),R.drawable.scrubber_control_pressed_holo);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width=150;
		height=150;
		int size = (width>height)?height:width;
		cx=width/2;
		cy=height/2;
		outerradius=size/2-5;
		innercricle=outerradius-bawidth-10;
		left = cx-outerradius;
		right=cx+outerradius;
		top=cy-outerradius;
		buttom=cy+outerradius;
		startpointx=cy+outerradius;
		startpointy=cy-outerradius;
		markpointx=startpointx;
		markpointy=startpointy;
		rect.set(left,top,right,buttom);
		
		
	}
	@Override
	protected void onDraw(Canvas canvas){
		dx=getXFromAngle();
		dy=getYFromAngle();
		canvas.drawCircle(cx, cy, outerradius,circleRing);
		canvas.drawArc(rect, startangle,angle,true, criclecolor);
		canvas.drawCircle(cx, cy, innercricle,innercolor);
		drawMarkerAtProgress(canvas);
		super.onDraw(canvas);
	}
public void drawMarkerAtProgress(Canvas canvas){
	if(IS_PRESSED){
		canvas.drawBitmap(progressmark, dx,dy,null);
		
	}else{
		canvas.drawBitmap(progressmark,dx,dy,null);
	}
}
	public float getXFromAngle(){
		int size1=progressmark.getWidth();
		int size2=proogressPressed.getWidth();
		int adjust =(size1>size2)?size1:size2;
		
		 float x= markpointx-(adjust/2);
		 return x;
	}
	public float getYFromAngle(){
		int size=progressmark.getHeight();
		int size3=proogressPressed.getHeight();
		int adjust1 =(size>size3)?size:size3;
		
		 float y= markpointy-(adjust1/2);
		 return y;
		
	}
	public int getAngle(){
		return angle;
		
	}
	public void setAngle(int angle){
		this.angle=angle;
		float donePercent = (((float)this.angle)/360)*100;
		float progress=(donePercent/100)*getMaxPregress();
		setProgressPersent(Math.round(donePercent));
		CANSEL_FROM_ANGLE=true;
		setProcess(Math.round(progress));
	}
	public OnSeekChangeListener getSeekBarChangeListener(){
		return mseeklistener;
	}
	public int getBarWidth(){
		return bawidth;
		
	}	
	public void setBarWidth(int barwidth){
		this.bawidth=barwidth;
		
	}
	public int getMaxPregress(){
		return maxprocess;
	}
	public void setMaxProcess(int maxprocess){
		this.maxprocess=maxprocess;
	}
	public int getProcess(){
		return process;
	}
	public void setProcess(int progress){
		
		if(this.process!=progress){
			this.process=progress;
			if(!CANSEL_FROM_ANGLE){
				int newPersent=(this.process/this.maxprocess)*100;
				int newangl=(newPersent/100)*360;
				this.setAngle(newangl);
				this.setProgressPersent(newangl);
				//Toast.makeText(getContext()," ----------> "+progress, Toast.LENGTH_LONG).show();
				
			}
			mseeklistener.onProgressChange(this,this.getProcess());
			CANSEL_FROM_ANGLE=false;
		}
	}
	public int getProgressPersent(){
		return progresspersent;
	}
	public void setProgressPersent(int progresspersent){
		this.progresspersent=progresspersent;
		
	}
	public void setRingBackgroundColor(int color){
		circleRing.setColor(color);
	}
	public void setBackgroundColor(int color){
		innercolor.setColor(color);
		
		
	}
	public void setProgressColor(int color){
		criclecolor.setColor(color);
	}
	public boolean onTouchEvent(MotionEvent event){
		float x=event.getX();
		float y=event.getY();
		if(x<=70&&y<=70){
			
			return false;
			//Toast.makeText(getBaseline(), "Out of bound", Toast.LENGTH_SHORT).show();
		}
		boolean up =false;
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				moved(x,y,up);break;
			case MotionEvent.ACTION_MOVE:
				moved(x,y,up);break;
			case MotionEvent.ACTION_UP:
				up=true;
				moved(x,y,up);
				break;
		}
		return true;
		}
	private void moved(float x,float y,boolean up){
		float distance = (float)Math.sqrt(Math.pow((x-cx),2)+Math.pow((y-cy),2));
		if(distance<outerradius+adjustmentfactor && distance >innercricle-adjustmentfactor && !up){
			IS_PRESSED=true;
			markpointx=x;
			markpointy=y;
			float degree=(float)((float)((Math.toDegrees(Math.atan2(x-cx, cy-y))+360))%360.0);
			if(degree<0){degree+=2*Math.PI;}
			setAngle(Math.round(degree));
			invalidate();
			
		}else{
			IS_PRESSED=false;
			invalidate();
		}
	}
	public float getAdjustmentFactor(){
		return this.adjustmentfactor;
		
	}
	public void setAdjustmentFactor(float ajust){
		this.adjustmentfactor=ajust;
		
	}
	public void setSeekBarChangeListener(OnSeekChangeListener onseekchangelistener){
		mseeklistener=onseekchangelistener;
	}
	
	}
	
	
	

	



