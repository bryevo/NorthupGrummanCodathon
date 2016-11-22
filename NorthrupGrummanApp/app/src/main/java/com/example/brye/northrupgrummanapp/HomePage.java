package com.example.brye.northrupgrummanapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Locale;

public class HomePage extends AppCompatActivity implements GeoTask.Geo,LocationListener{

    Button btn_get;
    String str_from,str_to;

    public Button button;
    public TextView loc;
    public LocationManager locationManager;
    public List<Address> addresses;
    public Geocoder geocoder;

    public static double lon = -117.0719;
    public static double lat = 32.7757;
    public static String name;
    public TextView locResults;

    //Initialize the Edit Directions button
    public void init() {
        button = (Button) findViewById(R.id.btn_newDirection);
        loc = (TextView) findViewById(R.id.loc);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, EditDirections.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        locResults = (TextView) findViewById(R.id.listBox);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, this);


        Notify.newNotification("Update Alert", "Traffic Conditions: Clear", this);
    }

    @Override
    public void setDouble(String result) {
        String res[] = result.split(",");
        Double min = Double.parseDouble(res[0])/60;
        Double dist = Double.parseDouble(res[1])/1000;
        //tv_result1.setText("Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        //tv_result2.setText("Distance= " + dist + " kilometers");

        Notify.newNotification(name, min + " mins away", this);
        locResults.setText(name + "Distance= " + dist + " kilometers" + "\n" + min + " mins away");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Notify.newNotification("ayy","lmao",this);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        loc.setText("Current Location: " + address + ", " + city + ", " + state + ", " + country + " "+ postalCode + knownName +
        "\n"+ "Latitude:" + location.getLatitude()+", Longitude:" + location.getLongitude());
        String or = location.getLatitude()+","+location.getLongitude();
        String dest = lat+","+lon;

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+or+"&destinations="+dest+"&key=AIzaSyBcx3w9EN8P0GySufslLpXNTwK8uRitjsE";
        Log.d("ayy",url);
        Log.d("abb",url);

        new GeoTask(HomePage.this).execute(url);
    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}
