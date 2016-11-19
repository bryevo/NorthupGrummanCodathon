package com.example.brye.northrupgrummanapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import static com.example.brye.northrupgrummanapp.R.id.textView_result1;
import static com.example.brye.northrupgrummanapp.R.id.url;

public class HomePage extends AppCompatActivity implements GeoTask.Geo,LocationListener{

    EditText edttxt_from,edttxt_to;
    Button btn_get;
    String str_from,str_to;
    TextView tv_result1,tv_result2;

    public Button button;
    public TextView loc;
    public LocationManager locationManager;

    public static double lon = -117.0719;
    public static double lat = 32.7757;
    public static String name;

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
        edttxt_from= (EditText) findViewById(R.id.editText_from);
        edttxt_to= (EditText) findViewById(R.id.editText_to);
        btn_get= (Button) findViewById(R.id.button_get);
        tv_result1= (TextView) findViewById(textView_result1);
        tv_result2=(TextView) findViewById(R.id.textView_result2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);


        Notify.newNotification("Update Alert", "Traffic: Severe Accident", this);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_from=edttxt_from.getText().toString();
                str_to=edttxt_to.getText().toString();


            }
        });
    }

    @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        //tv_result1.setText("Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        //tv_result2.setText("Distance= " + dist + " kilometers");

        Notify.newNotification(name, min+" min away", this);
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
        loc.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        String or = location.getLongitude()+","+location.getLatitude();
        String dest = lon+","+lat;

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
