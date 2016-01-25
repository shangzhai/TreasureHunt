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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CicleHire extends Activity {
	Spinner mySpinner;
	ArrayAdapter<String> adapter;
	List<String> list;
	List<Cicle> results;
	List<Map<String, Object>> listitems;
	ListView myListview;
	TextView time;
	OnItemSelectedListener l=new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			listitems=new ArrayList<Map<String,Object>>();
			Map<String, Object> item=new HashMap<String, Object>();
			item.put("title", "Name");
			item.put("content", results.get(position).getname());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Installed");
			if(Boolean.valueOf(results.get(position).getinstalled()).booleanValue())
				item.put("content", "Yes");
			else
				item.put("content", "No");
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Locked");
			if(Boolean.valueOf(results.get(position).getlocked()).booleanValue())
				item.put("content", "Yes");
			else
				item.put("content", "No");
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Available bikes");
			item.put("content", results.get(position).getbikes());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Empty docks");
			item.put("content", results.get(position).getEmptyDocks());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Total docks");
			item.put("content", results.get(position).getDocks());
			listitems.add(item);
			
			if(listitems!=null)
			{SimpleAdapter listitemadapter=new SimpleAdapter(CicleHire.this, listitems, R.layout.list_items_lines,
					new String[]{"title","content"}, new int[]{R.id.textView1,R.id.textView2});
			
			myListview.setAdapter(listitemadapter);
			
			
			
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cicle_hire);
		mySpinner=(Spinner)findViewById(R.id.site);
		myListview=(ListView)findViewById(R.id.listView01);
		time=(TextView)findViewById(R.id.textView2);
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					results=readxml("http://www.tfl.gov.uk/tfl/syndication/feeds/cycle-hire/livecyclehireupdates.xml");
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
		list=new ArrayList<String>();
		for(int i=0;i<results.size();i++)
			list.add(results.get(i).getname());
		    adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    mySpinner.setAdapter(adapter);
		    mySpinner.setOnItemSelectedListener(l);
		    SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy h:mm a");
			time.setText("Updated at "+date.format(new Date()));
	}
	public List<Cicle> readxml(String path) throws Exception{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		InputStream in=conn.getInputStream();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		
		List<Cicle>cicles=null;
		Cicle cicle=null;
		while(eventType!= XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				cicles=new ArrayList<Cicle>();
				break;
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				if("station".equals(nodename))
					cicle=new Cicle();
				else if(cicle!=null){
					if("name".equals(nodename))
						cicle.setname(parser.nextText().trim());
					else if("lat".equals(nodename))
						cicle.setlt(parser.nextText().trim());
					else if("long".equals(nodename))
						cicle.setlg(parser.nextText().trim());
					else if("installed".equals(nodename))
						cicle.setinstalled(parser.nextText().trim());
					else if("locked".equals(nodename))
						cicle.setlocked(parser.nextText().trim());
					else if("nbBikes".equals(nodename))
						cicle.setbikes(parser.nextText().trim());
					else if("nbEmptyDocks".equals(nodename))
						cicle.setEmptyDocks(parser.nextText().trim());
					else if("nbDocks".equals(nodename))
						cicle.setDocks(parser.nextText().trim());
				}
				break;
			case XmlPullParser.END_TAG:
				if("station".equals(parser.getName().trim())&&cicle!=null)
				{
					cicles.add(cicle);
					cicle=null;
				}
				break;
			}
			eventType=parser.next();
		}
		return cicles;
	}
}
