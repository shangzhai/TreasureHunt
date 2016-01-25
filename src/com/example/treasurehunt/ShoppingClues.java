package com.example.treasurehunt;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.th.mail.*;

public class ShoppingClues extends Activity {
	EditText t;
	ImageView img1;
	Button b1,b2;
	TextView t1,t2;
	String [] s;
	int n=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_clues);
		t=(EditText)findViewById(R.id.editText1);
		img1=(ImageView)findViewById(R.id.imgView1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		s=getResources().getStringArray(R.array.shopping);
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
				Intent intent=new Intent(ShoppingClues.this,MapMain.class);
				startActivity(intent);
				ShoppingClues.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_clues, menu);
		return true;
	}
	public void puzzle(){
		switch(n){
		case 0:
		if("www.marksandspencer.com".equals(t.getText().toString()))
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
			if("35".equals(t.getText().toString())||("35"+"\u00A3").equals(t.getText().toString()))
			{   n++;
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
			if("460".equals(t.getText().toString()))
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
			if("2".equals(t.getText().toString())||"two".equals(t.getText().toString()))
			{
				n++;
				img1.setImageResource(R.drawable.q4);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
			}
			else{
				t2.setText("Oops, your answer is not correct!");
			}
			
		    break;
		case 4:
			if("0333 300 1000".equals(t.getText().toString())||"03333001000".equals(t.getText().toString()))
			{
				n++;
				img1.setImageResource(R.drawable.q5);
				t1.setText(s[n]);
				t2.setText("");
				t.setText("");
			}
			else{
				t2.setText("Oops, your answer is not correct!");
			}
			break;
		case 5:
				
				Pattern regex=Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
				Matcher m=regex.matcher(t.getText().toString());
				if(m.matches())
				{
					n++;
					img1.setVisibility(View.GONE);
					t1.setText(Html.fromHtml("The email has been sent to you <strong><font color=red>successfully!</font></strong><br/>Go to check it and start your shopping journey!"));
					t1.setTextSize(20);
					t2.setText("");
					t.setVisibility(View.INVISIBLE);
					b1.setText("Find other clues!");
					b2.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					sendmail();	
					}
				}).start();
				}
				else
					t2.setText("Email is not correct!");
				break;
				
				default:
					
					Intent intent=new Intent(ShoppingClues.this,ChooseClues.class);
					startActivity(intent);
					ShoppingClues.this.finish();
				
		}
		
		
		
	}
	public void sendmail(){
		OneMail mail = new OneMail();   
		mail.sethost("smtp.qq.com");   
		mail.setport("25");    
		mail.setusername("57625852@qq.com");   
		mail.setpassword("lovewqc1314");
		mail.setsender("57625852@qq.com");   
		mail.setreceiver(t.getText().toString());   
		mail.settitle("Your voucher code");   
		mail.settext("Congratulations!\nYou have now completed the shopping puzzle game! Here is your voucher code:\n20OFF\nHere are the shops which accept this code:\nHarrods\n"+getString(R.string.Harrods)+"\n\nSelfridges & Co\n"+getString(R.string.Selfridges)+"\n\nMarks&Spencer\n"+getString(R.string.MS)+"\n\nTopshop\n"+getString(R.string.Topshop)+"\n\nHennes Mauritz\n"+getString(R.string.HM));   
		
		 
		mail.sendmail();
		

		
		
	}
	
	

}
