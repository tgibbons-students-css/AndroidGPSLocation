package gibbons.androidgpslocation;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // code from Googlehttp://developer.android.com/guide/topics/location/strategies.html
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                // Called when a new location is found by the network location provider.
                String locStr = String.format("%s %f:%f (%f meters)", loc.getProvider(),
                        loc.getLatitude(), loc.getLongitude(), loc.getAccuracy());
                TextView tvLoc = (TextView) findViewById(R.id.textView1);
                tvLoc.setText(locStr);
                Log.v("Gibbons",locStr);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.v("Gibbons","location onStatusChanged() called");
            }
            public void onProviderEnabled(String provider) {
                Log.v("Gibbons","location onProviderEnabled() called");
            }
            public void onProviderDisabled(String provider) {
                Log.v("Gibbons","location onProviderDisabled() called");
            }
        };

        // Register the listener with the Location Manager to receive location updates
        Log.v("Gibbons","setting location updates from network provider");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        Log.v("Gibbons","setting location updates from GPS provider");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }


	  public void OnClick (View v) {
		  
		  // Remember to use the gps add the following permission to AndriodManifest.xml
		  // <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
		  // To use the wireless for location add
		  // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
		  // <uses-permission android:name="android.permission.INTERNET" />
		  
		    LocationManager mgr = (LocationManager)getSystemService(LOCATION_SERVICE);
		    Location loc;			// location from GPS or other provider
		    
		  // Retrieve a list of location providers that have fine accuracy, no monetary cost, etc
		    Criteria criteria = new Criteria();
		    //criteria.setAccuracy(Criteria.ACCURACY_FINE);
		    //criteria.setCostAllowed(false);
		    String providerName = mgr.getBestProvider(criteria, true);

		    // If no suitable provider is found, null is returned.
		    if (providerName != null) {
		    	Toast.makeText(getApplicationContext(),"No good location provider found, trying GPS by default", Toast.LENGTH_LONG).show();
		    	Log.v("Gibbons", "No good location provider found, trying GPS by default");
		    	loc = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    	//loc = mgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		    } else {
		    	loc = mgr.getLastKnownLocation(providerName);
		    }
		    
		    TextView tvLoc = (TextView) findViewById(R.id.textView1);
		    if (loc == null) {
		      Log.v("Gibbons", "Got null for getLastKnownLocation()");
		      tvLoc.setText("No location found");
		    }
		    else {
		        String locStr = String.format("%s %f:%f (%f meters)", loc.getProvider(),
	    				  loc.getLatitude(), loc.getLongitude(), loc.getAccuracy());
		    	tvLoc.setText(locStr);
		    	Log.v("Gibbons",locStr);
		    }




	  
	  }
		   

}
