package com.example.treasurehunt;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Stations extends Activity {
	
	Spinner mySpinner;
	ArrayAdapter<String> adapter;
	List<String> list;
	ListView myListview1,myListview2;
	List<Station> results;
	List<Map<String, Object>> listitems,listitems2;
	OnItemSelectedListener l=new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			listitems=new ArrayList<Map<String,Object>>();
			listitems2=new ArrayList<Map<String,Object>>();
			
			Map<String, Object> item=new HashMap<String, Object>();
			item.put("title", "Name");
			item.put("content", results.get(position).getname());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			String str="";
			item.put("title", "Serving lines");
			if(results.get(position).getservinglines().size()>0)
			{str=results.get(position).getservinglines().get(0);
			for(int i=1; i<results.get(position).getservinglines().size();i++)
				str=str+", "+results.get(position).getservinglines().get(i);}
			item.put("content", str);
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Zone");
			item.put("content", results.get(position).getzone());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Phone");
			item.put("content", results.get(position).getphone());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Address");
			item.put("content", results.get(position).getaddress());
			listitems.add(item);
			
			item=new HashMap<String, Object>();
			item.put("title", "Coordinate(Click to check on map)");
			item.put("content", results.get(position).getcoordinates());
			listitems.add(item);
			
			for(int i=0;i<results.get(position).getfacilitynames().size();i++){
				item=new HashMap<String, Object>();
				item.put("title", results.get(position).getfacilitynames().get(i));
				item.put("content", results.get(position).getfacilities().get(i));
				listitems2.add(item);
			}
			if(listitems!=null)
			{SimpleAdapter listitemadapter=new SimpleAdapter(Stations.this, listitems, R.layout.list_items_stations,
					new String[]{"title","content"}, new int[]{R.id.textView1,R.id.textView2});
			
			myListview1.setAdapter(listitemadapter);
			
			setListViewHeightBasedOnChildren(myListview1);
			
			}
			if(listitems2!=null){
				SimpleAdapter listitemadapter2=new SimpleAdapter(Stations.this, listitems2, R.layout.list_items_stations,
						new String[]{"title","content"}, new int[]{R.id.textView1,R.id.textView2});
				myListview2.setAdapter(listitemadapter2);
				setListViewHeightBasedOnChildren(myListview2);
				
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
		setContentView(R.layout.activity_stations);
		mySpinner=(Spinner)findViewById(R.id.station);
		myListview1=(ListView)findViewById(R.id.listView01);
		myListview2=(ListView)findViewById(R.id.listView02);
		myListview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position==5)
				{
					TextView cr=(TextView)view.findViewById(R.id.textView2);
					String[] coor=cr.getText().toString().split(",");
					Intent intent=new Intent(Stations.this,MapMain.class);
					Bundle bundle=new Bundle();
					bundle.putString("lng", coor[0]);
					bundle.putString("lat", coor[1]);
					intent.putExtras(bundle);
					startActivity(intent);
					Stations.this.finish();
				}
			}
		});
		Thread one=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
			results=readxml("https://data.tfl.gov.uk/tfl/syndication/feeds/stations-facilities.xml");
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
		
		
		
	}
	
	public List<Station> readxml(String path) throws Exception{
		List<Station> stations=null;
		Station station=null;
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();
		InputStream in=conn.getInputStream();
		XmlPullParser parser=Xml.newPullParser();
		parser.setInput(in, "UTF-8");
		int eventType=parser.getEventType();
		String beforename="";
		while(eventType!= XmlPullParser.END_DOCUMENT){
			switch(eventType){
			case XmlPullParser.START_DOCUMENT:
				stations=new ArrayList<Station>();
				break;
			case XmlPullParser.START_TAG:
				String nodename=parser.getName().trim();
				
				if("station".equals(nodename))
					station=new Station();
				else if(station!=null){
					if("name".equals(nodename)&&"station".equals(beforename))
						station.setname(parser.nextText().trim());
					else if("address".equals(nodename))
						station.setaddress(parser.nextText().trim());
					else if("phone".equals(nodename))
						station.setphone(parser.nextText().trim());
					else if("servingLine".equals(nodename))
						station.setservinglines(parser.nextText().trim());
					else if("zone".equals(nodename))
						station.setzone(parser.nextText().trim());
					else if("facility".equals(nodename)){
						station.setfacilitynames(parser.getAttributeValue(0).trim());
						station.setfacilities(parser.nextText().trim());
						
					}
					else if("coordinates".equals(nodename))
					{String[] coor=parser.nextText().trim().split(",");
						station.setcoordinates(Double.parseDouble(coor[0])+","+Double.parseDouble(coor[1]));
					}
					
					
					
				}
				beforename=nodename;
				break;
				
			case XmlPullParser.END_TAG:
				if("station".equals(parser.getName().trim())&&station!=null)
				{
					stations.add(station);
					station=null;
					
				}
			break;
			
			
			}
			eventType=parser.next();
			
			
		}
		return stations;
		
	}
	
	public void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight();  
            if(i==listAdapter.getCount()-1)
            	totalHeight += listItem.getMeasuredHeight()/2; 
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
        listView.setLayoutParams(params);  
    }  
}
