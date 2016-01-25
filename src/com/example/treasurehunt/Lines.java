package com.example.treasurehunt;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Lines extends Activity {
	List<Map<String, Object>> listitems;
	ListView myListview;
	List<String> lines;
	List<String> status;
	List<String> details;
	List<Line> cls;
	boolean isFrist=true;
	TextView time;
	Button b1,b2;
	SimpleAdapter listitemadapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lines);
		myListview=(ListView)findViewById(R.id.listView01);
		listitems=new ArrayList<Map<String,Object>>();
		time=(TextView)findViewById(R.id.textView2);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		
		
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					readxml("http://cloud.tfl.gov.uk/TrackerNet/LineStatus");
					cls=readcolor("https://data.tfl.gov.uk/tfl/syndication/feeds/TubeThisWeekend_v1.xml");
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
		
		for(int i=0; i<lines.size();i++){
			Map<String, Object> item=new HashMap<String, Object>();
			item.put("name", lines.get(i));
			item.put("status", status.get(i));
			listitems.add(item);
			
		}
		
		
		if(listitems!=null)
		{listitemadapter=new SimpleAdapter(Lines.this, listitems, R.layout.list_items_lines,
				new String[]{"name","status"}, new int[]{R.id.textView1,R.id.textView2});
		
		myListview.setAdapter(listitemadapter);
		
		
		
		}
	
			
		
		myListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if("Good Service".equals(status.get(position)))
					Toast.makeText(Lines.this, lines.get(position)+" line is operating normally.", Toast.LENGTH_LONG).show();
				else
				Toast.makeText(Lines.this, details.get(position), Toast.LENGTH_LONG).show();
				
			}
		});
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Thread one = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							readxml("http://cloud.tfl.gov.uk/TrackerNet/LineStatus");
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
				listitems.clear();
				for(int i=0; i<lines.size();i++){
					Map<String, Object> item=new HashMap<String, Object>();
					item.put("name", lines.get(i));
					item.put("status", status.get(i));
					listitems.add(item);
					
				}
				
				listitemadapter.notifyDataSetChanged();
				SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
				time.setText("Updated at "+date.format(new Date()));
				
				
			}
		});
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					readxml("local");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listitems.clear();
				for(int i=0; i<lines.size();i++){
					Map<String, Object> item=new HashMap<String, Object>();
					item.put("name", lines.get(i));
					item.put("status", status.get(i));
					listitems.add(item);
					
				}
				
				listitemadapter.notifyDataSetChanged();
				SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
				time.setText("Updated at "+date.format(new Date()));
				
			}
		});
		SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
		time.setText("Updated at "+date.format(new Date()));
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus&&isFrist)
		{for(int i=0;i<lines.size();i++)
		{	TextView t=(TextView)myListview.getChildAt(i).findViewById(R.id.textView1);
		    for(int j=0;j<cls.size();j++)
		    {
		    	if("H'smith & City".equals(cls.get(j).getname())&&"Hammersmith and City".equals(lines.get(i)))
		    		{t.setBackgroundColor(Long.valueOf("FF"+cls.get(j).getbgcolor(),16).intValue());
		    		t.setTextColor(Long.valueOf("FF"+cls.get(j).getcolor(),16).intValue());
		    		}
		    	else if("Waterloo & City".equals(cls.get(j).getname())&&"Waterloo and City".equals(lines.get(i)))
		    		{t.setBackgroundColor(Long.valueOf("FF"+cls.get(j).getbgcolor(),16).intValue());
		    		t.setTextColor(Long.valueOf("FF"+cls.get(j).getcolor(),16).intValue());}
		    	else if(cls.get(j).getname().equals(lines.get(i)))
		    		{t.setBackgroundColor(Long.valueOf("FF"+cls.get(j).getbgcolor(),16).intValue());
		    		if("Circle".equals(lines.get(i)))
		    			t.setTextColor(Long.valueOf("FF"+cls.get(j).getcolor(),16).intValue());
		    		else
		    		t.setTextColor(Long.valueOf("FFFFFFFF",16).intValue());}
		    }
		
		}
		isFrist=false;
			
		}
	}

	public void readxml(String path) throws Exception{
		InputStream in;
		if("local".equals(path))
			in= getResources().getAssets().open("LineStatus.xml");
		else{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		in=conn.getInputStream();}
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		lines=new ArrayList<String>();
		status=new ArrayList<String>();
		details=new ArrayList<String>();
		String beforename="";
		while(eventType!= XmlPullParser.END_DOCUMENT){
			
			switch(eventType){
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				if("LineStatus".equals(nodename))
					details.add(parser.getAttributeValue(null, "StatusDetails"));
				else if("Line".equals(nodename))
					lines.add(parser.getAttributeValue(null,"Name"));
				else if("Status".equals(nodename)&&"Line".equals(beforename))
					status.add(parser.getAttributeValue(null,"Description"));
				beforename=nodename;
			    break;
			
			}
			
			eventType=parser.next();
		}
		
		
	}
	
	public List<Line> readcolor(String path) throws Exception{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		InputStream in=conn.getInputStream();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		
		List<Line>colors=null;
		Line color=null;
		while(eventType!= XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				colors=new ArrayList<Line>();
				break;
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				if("Line".equals(nodename))
					color=new Line();
				else if(color!=null){
					if("Name".equals(nodename))
						color.setname(parser.nextText().trim());
					else if("Colour".equals(nodename)&&parser.getDepth()==4)
						color.setcolor(parser.nextText().trim());
					else if("BgColour".equals(nodename)&&parser.getDepth()==4)
						color.setbgcolor(parser.nextText().trim());
					
						
					
					
				}
			break;
			case XmlPullParser.END_TAG:
				if("Line".equals(parser.getName().trim())&&color!=null)
				{
					colors.add(color);
					color=null;
				}
				break;
			}
			eventType=parser.next();
		}
		return colors;
	}
}
