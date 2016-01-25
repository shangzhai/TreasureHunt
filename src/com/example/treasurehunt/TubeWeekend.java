package com.example.treasurehunt;

import java.io.InputStream;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TubeWeekend extends Activity {
	
	ListView myListview,myListview2;
	List<Map<String, Object>> listitems,listitems2;
	List<Line> results;
	List<Line> stations;
	TextView time,time2;
	boolean isFrist=true;
    TabHost tabs;
    SimpleAdapter listitemadapter,listitemadapter2;
    OnTabChangeListener l=new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			Thread one=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						results=readxml("https://data.tfl.gov.uk/tfl/syndication/feeds/TubeThisWeekend_v2.xml");
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
			if("tab1".equals(tabId)){
				
				
				listitems.clear();
				for(int i=0; i<results.size();i++){
					Map<String, Object> item=new HashMap<String, Object>();
					if("H'smith & City".equals(results.get(i).getname()))
						item.put("name", "Hammersmith and City");
					else if("Waterloo & City".equals(results.get(i).getname()))
						item.put("name", "Waterloo and City");
					else
					item.put("name", results.get(i).getname());
					item.put("status", results.get(i).getstatus());
					listitems.add(item);
					
				}
				listitemadapter.notifyDataSetChanged();
				SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
				time.setText("Updated at "+date.format(new Date()));
			}
			else if("tab2".equals(tabId)){
				
				listitems2.clear();
				for(int i=0; i<stations.size();i++){
					Map<String, Object> item=new HashMap<String, Object>();
					
					item.put("name", stations.get(i).getname());
					item.put("status", stations.get(i).getstatus());
					listitems2.add(item);
					
				}
				listitemadapter2.notifyDataSetChanged();
				SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
				time2.setText("Updated at "+date.format(new Date()));
				
			}
			
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tube_weekend);
		myListview =(ListView)findViewById(R.id.listView01);
		myListview2 =(ListView)findViewById(R.id.listView02);
		listitems=new ArrayList<Map<String,Object>>();
		listitems2=new ArrayList<Map<String,Object>>();
		time=(TextView)findViewById(R.id.textView2);
		time2=(TextView)findViewById(R.id.textView4);
		tabs=(TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		tabs.addTab(tabs.newTabSpec("tab1").setIndicator("Line this weekend").setContent(R.id.tab1));
		tabs.addTab(tabs.newTabSpec("tab2").setIndicator("Station this weekend").setContent(R.id.tab2));
		tabs.setOnTabChangedListener(l);
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					results=readxml("https://data.tfl.gov.uk/tfl/syndication/feeds/TubeThisWeekend_v2.xml");
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
		
		for(int i=0; i<results.size();i++){
			Map<String, Object> item=new HashMap<String, Object>();
			if("H'smith & City".equals(results.get(i).getname()))
				item.put("name", "Hammersmith and City");
			else if("Waterloo & City".equals(results.get(i).getname()))
				item.put("name", "Waterloo and City");
			else
			item.put("name", results.get(i).getname());
			item.put("status", results.get(i).getstatus());
			listitems.add(item);
			
		}
		
		for(int i=0; i<stations.size();i++){
			Map<String, Object> item=new HashMap<String, Object>();
			
			item.put("name", stations.get(i).getname());
			item.put("status", stations.get(i).getstatus());
			listitems2.add(item);
			
		}
		
		if(listitems!=null)
		{listitemadapter=new SimpleAdapter(TubeWeekend.this, listitems, R.layout.list_items_lines,
				new String[]{"name","status"}, new int[]{R.id.textView1,R.id.textView2});
		
		myListview.setAdapter(listitemadapter);
		
		
		
		}
		
		if(listitems2!=null)
		{listitemadapter2=new SimpleAdapter(TubeWeekend.this, listitems2, R.layout.list_items_lines,
				new String[]{"name","status"}, new int[]{R.id.textView1,R.id.textView2});
		
		myListview2.setAdapter(listitemadapter2);
		
		
		
		}
		
		myListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if("Good Service".equals(results.get(position).getstatus()))
					Toast.makeText(TubeWeekend.this, results.get(position).getname()+" line operates normally this week", Toast.LENGTH_LONG).show();
				else
				Toast.makeText(TubeWeekend.this, results.get(position).getdetails(), Toast.LENGTH_LONG).show();
				
			}
		});
		
		myListview2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(TubeWeekend.this, stations.get(position).getdetails(), Toast.LENGTH_LONG).show();
				
			}
		});
		SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
		time.setText("Updated at "+date.format(new Date()));
		time2.setText("Updated at "+date.format(new Date()));
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus&&isFrist){
			for(int i=0;i<results.size();i++){
				TextView t=(TextView)myListview.getChildAt(i).findViewById(R.id.textView1);
				t.setBackgroundColor(Long.valueOf("FF"+results.get(i).getbgcolor(),16).intValue());
				if("H'smith & City".equals(results.get(i).getname())||"Waterloo & City".equals(results.get(i).getname())||"Circle".equals(results.get(i).getname()))
					t.setTextColor(Long.valueOf("FF"+results.get(i).getcolor(),16).intValue());
				else
					t.setTextColor(Long.valueOf("FFFFFFFF",16).intValue());
				
			}
			isFrist=false;
		}
	}
	
	public List<Line> readxml(String path) throws Exception{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		InputStream in=conn.getInputStream();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		
		List<Line>lines=null;
		Line line=null;
		stations=null;
		Line station=null;
		while(eventType!= XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				lines=new ArrayList<Line>();
				stations=new ArrayList<Line>();
				break;
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				if("Line".equals(nodename))
					line=new Line();
				else if(line!=null){
					if("Name".equals(nodename))
						line.setname(parser.nextText().trim());
					else if("Colour".equals(nodename)&&parser.getDepth()==4)
						line.setcolor(parser.nextText().trim());
					else if("BgColour".equals(nodename)&&parser.getDepth()==4)
						line.setbgcolor(parser.nextText().trim());
					else if("Text".equals(nodename)&&parser.getDepth()==5)
						line.setstatus(parser.nextText().trim());
					else if("Text".equals(nodename)&&parser.getDepth()==6)
						line.setdetails(parser.nextText().trim());
					
						
					
					
				}
				else if("Station".equals(nodename))
					station=new Line();
				else if(station!=null){
					if("Name".equals(nodename))
						station.setname(parser.nextText().trim());
					else if("Text".equals(nodename)&&parser.getDepth()==5)
						station.setstatus(parser.nextText().trim());
					else if("Text".equals(nodename)&&parser.getDepth()==6)
						station.setdetails(parser.nextText().trim());
				}
			break;
			case XmlPullParser.END_TAG:
				if("Line".equals(parser.getName().trim())&&line!=null)
				{
					lines.add(line);
					line=null;
				}
				if("Station".equals(parser.getName().trim())&&station!=null)
				{
					stations.add(station);
					station=null;
				}
				break;
			}
			eventType=parser.next();
		}
		return lines;
	}
	
}
