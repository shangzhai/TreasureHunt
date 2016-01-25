package com.example.treasurehunt;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Weather extends Activity {
String lat="",lon="",set="",rise="",weather="",icon="",temp="",humidity="",pressure="",windspead="",winddirection="",time="";




String name;
List<Map<String, Object>> listitems;
ListView myListview;
EditText t1,t2,t3;
TextView tv1,tv2,tv3,r1,r2,r3,r4,r5,r6;
ImageView img;
Button b1;
OnClickListener l=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url="";
				if(!"".equals(t1.getText().toString()))
				url="http://api.openweathermap.org/data/2.5/weather?q="+t1.getText().toString()+",uk&mode=xml";
				else if(!"".equals(t3.getText().toString())&&!"".equals(t2.getText().toString()))
					url="http://api.openweathermap.org/data/2.5/weather?lat="+t3.getText().toString()+"&lon="+t2.getText().toString()+"&mode=xml";
				try {
					read(url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		one.start();
		try {
			one.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv1.setText(name);
		tv2.setText(weather);
		tv3.setText(new BigDecimal((Double.parseDouble(temp)-273.15)+"").setScale(0, BigDecimal.ROUND_HALF_UP)+"буC");
		img.setImageResource(getResources().getIdentifier(icon, "drawable",  getPackageName()));
		r1.setText(windspead+"\n"+winddirection);
		r2.setText(pressure);
		r3.setText(humidity);
		String[] s1=rise.split("T");
		String[] s2=set.split("T");
		String[] s3=time.split("T");
		SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1=null,d2=null,d3=null;
		try {
			d1=df.parse(s1[1]);
			d2=df.parse(s2[1]);
			d3=df2.parse(s3[0]+" "+s3[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = new GregorianCalendar();
        calendar.setTime(d1);
        calendar.add(calendar.HOUR_OF_DAY,1);
		r4.setText(df.format(calendar.getTime()));
		calendar.setTime(d2);
        calendar.add(calendar.HOUR_OF_DAY,1);
		r5.setText(df.format(calendar.getTime()));
		calendar.setTime(d3);
        calendar.add(calendar.HOUR_OF_DAY,1);
		r6.setText(df2.format(calendar.getTime()));
	}
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		myListview=(ListView)findViewById(R.id.listView01);
		listitems=new ArrayList<Map<String,Object>>();
		t1=(EditText)findViewById(R.id.location);
		t2=(EditText)findViewById(R.id.lon);
		t3=(EditText)findViewById(R.id.lat);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
		img=(ImageView)findViewById(R.id.con);
		r1=(TextView)findViewById(R.id.wind);
		r2=(TextView)findViewById(R.id.Pressure);
		r3=(TextView)findViewById(R.id.Humidity);
		r4=(TextView)findViewById(R.id.Sunrise);
		r5=(TextView)findViewById(R.id.Sunset);
		r6=(TextView)findViewById(R.id.Lastupdate);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(l);
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null)
		{   if(bundle.containsKey("location"))
			{t1.setText(bundle.getString("location"));
			b1.performClick();}
		else
		{
			t2.setText(bundle.getString("lng"));
			t3.setText(bundle.getString("lat"));
			b1.performClick();
		}
			
		}
	}
	
	public void read(String path) throws Exception{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		InputStream in=conn.getInputStream();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		while(eventType!= XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				if("city".equals(nodename))
					name=parser.getAttributeValue(null, "name");
				else if("coord".equals(nodename))
				{lat=parser.getAttributeValue(null, "lat");
				lon=parser.getAttributeValue(null, "lon");}
				else if("sun".equals(nodename))
				{
					set=parser.getAttributeValue(null, "set");
					rise=parser.getAttributeValue(null, "rise");
				}
				else if("temperature".equals(nodename))
					temp=parser.getAttributeValue(null, "value");
				else if("humidity".equals(nodename))
					humidity=parser.getAttributeValue(null, "value")+parser.getAttributeValue(null, "unit");
				else if("pressure".equals(nodename))
					pressure=parser.getAttributeValue(null, "value")+parser.getAttributeValue(null, "unit");
				else if("speed".equals(nodename))
					windspead=parser.getAttributeValue(null, "name")+" "+parser.getAttributeValue(null, "value")+" m/s";
				else if("direction".equals(nodename))
					winddirection=parser.getAttributeValue(null, "name")+" "+parser.getAttributeValue(null, "value")+"бу";
				else if("weather".equals(nodename))
				{
					weather=parser.getAttributeValue(null, "value");
					icon=parser.getAttributeValue(null, "icon");
					icon=icon.substring(2, 3)+icon.substring(0, 2);
				}
				else if("lastupdate".equals(nodename))
					time=parser.getAttributeValue(null, "value");
			}
			eventType=parser.next();
		}
	}
}
