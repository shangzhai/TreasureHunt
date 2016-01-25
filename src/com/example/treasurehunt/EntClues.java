package com.example.treasurehunt;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class EntClues extends Activity {
	EditText t;
	ImageView img1,img2;
	Button b1,b2;
	TextView t1,t2;
	String [] s;
	int n=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ent_clues);
		t=(EditText)findViewById(R.id.editText1);
		img1=(ImageView)findViewById(R.id.imgView1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		s=getResources().getStringArray(R.array.fun);
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
				Intent intent=new Intent(EntClues.this,MapMain.class);
				Bundle bundle=new Bundle();
				bundle.putString("lng", "-0.114719");
				bundle.putString("lat", "51.506553");
				intent.putExtras(bundle);
				startActivity(intent);
				EntClues.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ent_clues, menu);
		return true;
	}
	
	public void puzzle(){
		String answer=t.getText().toString();
		switch(n){
		case 0:
			if("interstellar".equals(answer)||"Interstellar".equals(answer))
			{   n++;
			img1.setImageResource(R.drawable.q1);
			t1.setText(s[n]);
			t2.setText("");
			t.setText("");
		    }
		else{
			t2.setText("Oops, your answer is not correct!");
		}
		
	    break;
	    
		case 1:
			if("05/07/2012".equals(answer)){
				 n++;
					img1.setImageResource(R.drawable.q2);
					t1.setText(s[n]);
					t2.setText("");
					t.setText("");
				
				
			}
			else{
				t2.setText("Oops, your answer is not correct!");
			}
		break;
		
		case 2:
			if("Rosamund Pike".equals(answer)||"rosamund pike".equals(answer))
			{
				 n++;
					img1.setImageResource(R.drawable.q3);
					t1.setText(s[n]);
					t2.setText("");
					t.setText("");
				
			}
			else{
				t2.setText("Oops, your answer is not correct!");
			}
		break;
		case 3:
			if("17".equals(answer))
			{n++;
			img1.setVisibility(View.GONE);
			t.setVisibility(View.GONE);
			t1.setText(s[n]);
			t2.setVisibility(View.GONE);
			b1.setText("Find other clues!");
			b2.setVisibility(View.VISIBLE);
			RelativeLayout fc=(RelativeLayout)findViewById(R.id.entclues);
			img2=new ImageView(this);
			
			img2.setId(1);
			RelativeLayout.LayoutParams p= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			p.addRule(RelativeLayout.CENTER_VERTICAL);
			p.addRule(RelativeLayout.CENTER_HORIZONTAL);
			qrcode();
			fc.addView(img2, p);
			RelativeLayout.LayoutParams pp=(RelativeLayout.LayoutParams)b1.getLayoutParams();
			RelativeLayout.LayoutParams ppp=(RelativeLayout.LayoutParams)b2.getLayoutParams();
			pp.addRule(RelativeLayout.BELOW,1);
			ppp.addRule(RelativeLayout.BELOW,1);
			pp.setMargins(0, 100, 0, 0);
			ppp.setMargins(0, 20, 0, 0);
			b1.setLayoutParams(pp);
			b2.setLayoutParams(ppp);
			break;
			
			}
			else{
				t2.setText("Oops, your answer is not correct!");
			}
		
		default:
			Intent intent=new Intent(EntClues.this,ChooseClues.class);
			startActivity(intent);
			EntClues.this.finish();
		
		}
		
		
	}
	

	
	public void qrcode(){
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		String str = "Free Film"+df.format(new Date());   
		BitMatrix mat=null;
    	try {
			mat= new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
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
