package com.myexample.googlemapsapp;
import java.util.Random;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity {
	private ClusterManager<MyItem> mClusterManager;
	private GoogleMap map;
	
	public class MyItem implements ClusterItem {
	    private final LatLng mPosition;

	    public MyItem(double lat, double lng) {
	        mPosition = new LatLng(lat, lng);
	    }

	    @Override
	    public LatLng getPosition() {
	        return mPosition;
	    }
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setUpMapIfNeeded() {
        if (map != null) {
            return;
        }
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if (map != null) {
        	mClusterManager = new ClusterManager<MyItem>(this, map);
           map.setOnCameraChangeListener(mClusterManager);
            map.setOnMarkerClickListener(mClusterManager);
            
        	int latMin = 43;
            int latMax = 50;
            int lonMin = 90;
            int lonMax = 97;
            
            for (int i = 0; i <= 14000; i ++) {
            	Random lat = new Random();
            	int lat1 = lat.nextInt(latMax - latMin + 1) + latMin;
            	Random lon = new Random();
            	int lon1 = (lon.nextInt(lonMax - lonMin + 1) + lonMin)*-1;
            	MyItem offsetItem = new MyItem(lat1, lon1);
            	mClusterManager.addItem(offsetItem);
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(44.1667, -94.0000), 6));
        }
        
    }
 
}