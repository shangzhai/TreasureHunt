package com.example.treasurehunt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.WriterException;  
import com.google.zxing.common.BitMatrix;  

public class FoodClues extends Activity {
	EditText t;
	ImageView img1;
	ImageView img2;
	Button b1,b2;
	TextView t1,t2,t3;
	String [] s;
	int n=0;
	int k=10;
	String name;
	Timer time=new Timer();
	
	TimerTask task=new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			k--;
			Message m=new Message();
			m.what=1;
			h.sendMessage(m);
			
		}
	};

	Handler h=new Handler(){
		public void handleMessage(Message msg){  
			super.handleMessage(msg); 
			switch (msg.what)
			{
			case 1:
			if(k>=0)
			{ t3.setText(k+" seconds remaining");
		          }
			else
				{
				Intent intent=new Intent(FoodClues.this,ChooseClues.class);
			startActivity(intent);
			time.cancel();
			}}
			             
			         }

		
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_clues);
		t=(EditText)findViewById(R.id.editText1);
		img1=(ImageView)findViewById(R.id.imgView1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		s=getResources().getStringArray(R.array.food);
		b2.setVisibility(View.INVISIBLE);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				puzzle();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(FoodClues.this,MapMain.class);
				Bundle bundle=new Bundle();
				bundle.putString("lng", "-0.140495");
				bundle.putString("lat", "51.522081");
				intent.putExtras(bundle);
				startActivity(intent);
				FoodClues.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_clues, menu);
		return true;
	}
	
	public void puzzle(){
		String answer=t.getText().toString();
		switch(n){
		case 0:
			if("FISH BONE".equals(answer)||"FISHBONE".equals(answer)||"fish bone".equals(answer)||"fishbone".equals(answer))
			{
				n++;
				img1.setImageResource(R.drawable.q1);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
				
			}
			else
				t2.setText("Oops, your answer is not correct!");
			break;
		case 1:
			if("japan".equals(answer)||"Japan".equals(answer)){
				n++;
				img1.setImageResource(R.drawable.q2);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
			}
			else
				t2.setText("Oops, your answer is not correct!");
			break;
		case 2:
			if("14.8".equals(answer)||"14.80".equals(answer)||("14.8"+"\u00A3").equals(answer)||("14.80"+"\u00A3").equals(answer))
			{
				n++;
				img1.setImageResource(R.drawable.q3);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
			}
			else
				t2.setText("Oops, your answer is not correct!");
			break;
		case 3:
			if(Integer.parseInt(answer)>=5&&Integer.parseInt(answer)<=15)
			{
				n++;
				img1.setImageResource(R.drawable.q4);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
				t3=new TextView(this);
				t3.setTextColor(getResources().getColor(R.color.red));
				RelativeLayout fc=(RelativeLayout)findViewById(R.id.foodclues);
				
				RelativeLayout.LayoutParams p= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				p.addRule(RelativeLayout.CENTER_HORIZONTAL);
				p.addRule(RelativeLayout.BELOW,R.id.textView2);
				fc.addView(t3,p);
				time.schedule(task, 1000, 1000);
				
			}
			else
				t2.setText("Oops, your answer is not correct!");
			break;
		case 4:
			if(Integer.parseInt(answer)==2||"two".equals(answer))
			{n++;
			time.cancel();
			img1.setImageResource(R.drawable.q5);
			t1.setText(s[n]);
			t2.setText("");
			t.setText("");
			t3.setText("");
			
			}
			else
				t2.setText("Oops, your answer is not correct!");
			break;
		case 5:
			n++;
			name=t.getText().toString();
			img1.setVisibility(View.GONE);
			t.setVisibility(View.GONE);
			t1.setText("Congratulations! Here is a bar code which is worth a free meal in ASSA restaurant! Just show it to any waiters and scan it, then there you go! ");
			t2.setVisibility(View.GONE);
			t3.setVisibility(View.GONE);
			b1.setText("Find other clues!");
			b2.setVisibility(View.VISIBLE);
			RelativeLayout fc=(RelativeLayout)findViewById(R.id.foodclues);
			img2=new ImageView(this);
			
			img2.setId(1);
			RelativeLayout.LayoutParams p= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.CENTER_VERTICAL);
			p.addRule(RelativeLayout.CENTER_HORIZONTAL);
			barcode();
			fc.addView(img2, p);
			RelativeLayout.LayoutParams pp=(RelativeLayout.LayoutParams)b1.getLayoutParams();
			RelativeLayout.LayoutParams ppp=(RelativeLayout.LayoutParams)b2.getLayoutParams();
			pp.addRule(RelativeLayout.BELOW,1);
			ppp.addRule(RelativeLayout.BELOW,1);
			b1.setLayoutParams(pp);
			b2.setLayoutParams(ppp);
			break;
			
			default:
				Intent intent=new Intent(FoodClues.this,ChooseClues.class);
				startActivity(intent);
				FoodClues.this.finish();
			
		
		}
		
		
	}
	

    
    public void barcode(){
    	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
    	String str = name+df.format(new Date());  
    	BitMatrix mat=null;
    	try {
			mat= new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, 500, 200);
		} catch (WriterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if(mat!=null){
    		int width=mat.getWidth();
    		int height=mat.getHeight();
    		Bitmap bit=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    		for(int i=0;i<height;i++)
    			for(int j=0;j<width;j++)
    				if(mat.get(j, i))
    					bit.setPixel(j, i, getResources().getColor(R.color.black));
    		
    		img2.setImageBitmap(bit);
    	}
    	
    	
      
    }

}
