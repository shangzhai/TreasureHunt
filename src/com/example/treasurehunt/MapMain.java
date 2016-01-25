package com.example.treasurehunt;


import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;




import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;





public class MapMain extends Activity {
	private Button startbtn;
    private EditText lngedt, latedt;
    private RadioGroup viewtype;
    GoogleMap myMap;
    private CameraPosition camera;
    
 
    private LocationManager locManager;
    private Location location;
    private String bpro;
    ImageButton local;
    List<LatLng> list;
	double latend = 0.0;
	double lngend = 0.0;
	
	String end = "";
	String searchlng="-0.1412303";
	String searchlat="51.5153503";
	
	private ProgressDialog prd;
	private Handler myhandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
									
			case 1:
				
                prd.dismiss();
				
		
                myMap.clear();
				markMysition();
				
				
				
				myMap.addMarker(new MarkerOptions().position(
						new LatLng(latend, lngend)));
				
				
				
				
				int length=list.size() - 1;
				for (int i = 0; i < length; i++) {
					LatLng src = list.get(i);
					LatLng dest = list.get(i + 1);
					
					myMap.addPolyline(new PolylineOptions()
							.add(src,dest)
							.width(4).color(Color.GREEN));
				}

				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_main);
		myMap=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		startbtn = (Button) findViewById(R.id.btn_loc);
	  
		lngedt = (EditText) findViewById(R.id.edt_lng);
		latedt = (EditText) findViewById(R.id.edt_lat);
	    
	    viewtype = (RadioGroup) findViewById(R.id.rg_mapType);
	    local = (ImageButton) findViewById(R.id.local);
		local.setImageResource(android.R.drawable.ic_menu_myplaces);
		local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeview(location);

			}

		});
		init();
	
		changeview(location);
		startbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				searchlng = lngedt.getText().toString().trim();
				searchlat = latedt.getText().toString().trim();
				
				if(searchlng.equals("") || searchlat.equals("")){
				    Toast.makeText(getApplicationContext(), "Please enter valid longitude and latitude."
				, Toast.LENGTH_LONG).show();
				    
				    changeview(location);
				}
				else{
					Location locopy=new Location(bpro);
				    locopy.setLongitude(Double.parseDouble(searchlng));
				    
				    locopy.setLatitude(Double.parseDouble(searchlat));
				    
				    changeview(locopy);
				}
				
			}
		});
		viewtype.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
				case R.id.rb_nomal:
					myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				    break;
				case R.id.rb_satellite:
					myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				    break;
				        }
				
			}
		});
		
		
		
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null)
		{
			latedt.setText(bundle.getString("lat"));
			lngedt.setText(bundle.getString("lng"));
			startbtn.performClick();
			
		}

	}
	private LocationListener l=new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			location=locManager.getLastKnownLocation(provider);
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			changeview(null);
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			MapMain.this.location=location;
			changeview(location);
			
		}
	};
	private void init() {
	    
	    locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
	    
	    bpro = locManager.getBestProvider(new Criteria(), false);
	    Log.e("zs", bpro);
	    locManager.requestLocationUpdates(bpro, 3*1000, 8, l);
	    location = locManager.getLastKnownLocation(bpro);
	    if(location==null)
	    	{Log.i("zs", "notreal");
	    	Location locopy=new Location(bpro);
		    locopy.setLongitude(-0.1412303);
		    
		    locopy.setLatitude(51.5153503);
		    location=locopy;
	    	
	    	}
	   
	}
	
	
	
	private void changeview(Location location){
		myMap.clear();
	    
	    
	    double lon = 0;
	    double lat = 0;
	    if(location != null){
	
	lon = location.getLongitude();
	
	lat = location.getLatitude();
	    }
	   
	    myMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
	    		.fromResource(android.R.drawable.ic_menu_mylocation)));
	    
	    camera = new CameraPosition(new LatLng(lat, lon), 17, 30, 0);

	    myMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
	    }
	void markMysition() {
		myMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
	
	}
	
	private void getroute() {

		
		StringBuilder all = new StringBuilder();
		String path = "http://maps.google.com/maps/api/directions/json?origin="
				+ location.getLatitude() + "," + location.getLongitude() + "&destination=" + latend + "," + lngend
				+ "&sensor=true&mode=driving";
		
		
		

		try {
	
			URL url=new URL(path);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			InputStream in=conn.getInputStream();
			
		
			int b;
			while ((b = in.read()) != -1) {
				all.append((char) b);
			}
			
			JSONObject jsonObject = new JSONObject(all.toString());
			
			String points = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");
			
			
			list = decodePoly(points);
			

		} catch (Exception e) {

		}
		
	}
	
	
		private void startroute() {
			LayoutInflater factory = LayoutInflater.from(MapMain.this);
			
			final View view = factory.inflate(R.layout.maptable, null);
			final EditText left=(EditText)view.findViewById(R.id.lon);
			final EditText right=(EditText)view.findViewById(R.id.lat);
			left.setText(searchlng);
			right.setText(searchlat);
			new AlertDialog.Builder(MapMain.this)
					.setIcon(android.R.drawable.ic_menu_edit).setTitle("Get route")
					.setView(view).setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							dialog.dismiss();
							
							
							
							
							EditText en = (EditText) view.findViewById(R.id.end);
							
							
							
							
							end = en.getText().toString();
							
							
							
							
							prd = ProgressDialog.show(MapMain.this,
									"Getting the route", "Please wait¡­¡­", true);

							

							new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(end!=null&&!"".equals(end))
										parseaddress(end);
									else
									{
										lngend=Double.parseDouble(left.getText().toString());
										latend=Double.parseDouble(right.getText().toString());
										
									}
									getroute();
									
									myhandler.sendEmptyMessage(1);
								}
							}).start();

						}
					}).setNegativeButton("Cancle", null).show();
			
		}
		
		
		private void parseaddress(String end) {
			String path = "http://maps.google.com/maps/api/geocode/json?address="
					+ end + "&sensor=false";
			StringBuilder all = new StringBuilder();
			try {

				
						
				URL url=new URL(path);
				HttpURLConnection conn=(HttpURLConnection)url.openConnection();
				InputStream in=conn.getInputStream();

				int b;
				while ((b = in.read()) != -1) {
					all.append((char) b);
				}
				JSONObject jsonObject= new JSONObject(all.toString());
				lngend = jsonObject.getJSONArray("results").getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lng");

				latend = jsonObject.getJSONArray("results").getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lat");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

			
		}

		
		/* Code taken from:
		 * Title: Decoding Polylines from Google Maps Direction API with Java
		 * Author: Jeffrey Sambells
		 * Date: 27/05/2010
		 * Availability: http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
		 * */
		private List<LatLng> decodePoly(String encoded) {
			List<LatLng> poly = new ArrayList<LatLng>();
			int index = 0, len = encoded.length();
			int lat = 0, lng = 0;

			while (index < len) {
				int b, shift = 0, result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lat += dlat;

				shift = 0;
				result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lng += dlng;

				LatLng p = new LatLng((lat / 1E5), lng / 1E5);
				poly.add(p);
			}
			return poly;
		}
		
		
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, Menu.FIRST + 1, 1, "Get route");
			menu.add(0, Menu.FIRST + 2, 2, "Check weather");
			menu.add(0, Menu.FIRST + 3, 3, "Exit");
			return true;
		}

		
		public boolean onOptionsItemSelected(MenuItem item) {

			switch (item.getItemId()) {
			case Menu.FIRST + 1:
				
				startroute();
				break;
			case Menu.FIRST + 2:
				changetoweather();
			    break;
			case Menu.FIRST + 3:
				locManager.removeUpdates(l);
				this.finish();
				break;

			}
			return true;
		}
		
		void changetoweather(){
			Intent intent=new Intent(MapMain.this,Weather.class);
			Bundle bundle=new Bundle();
			bundle.putString("lng", searchlng);
			bundle.putString("lat", searchlat);
			intent.putExtras(bundle);
			startActivity(intent);
			MapMain.this.finish();
		}

}
