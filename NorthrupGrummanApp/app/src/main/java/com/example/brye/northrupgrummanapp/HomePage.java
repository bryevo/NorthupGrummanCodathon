package com.example.brye.northrupgrummanapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements GeoTask.Geo{

    public Button button;
    EditText edttxt_from,edttxt_to;
    Button btn_get;
    String str_from,str_to;
    TextView tv_result1,tv_result2;

    //Initialize the Edit Directions button
    public void init() {
        button = (Button) findViewById(R.id.btn_newDirection);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, EditDirections.class);
                startActivity(intent);
            }
        });
        edttxt_from= (EditText) findViewById(R.id.editText_from);
        edttxt_to= (EditText) findViewById(R.id.editText_to);
        btn_get= (Button) findViewById(R.id.button_get);
        tv_result1= (TextView) findViewById(R.id.textView_result1);
        tv_result2=(TextView) findViewById(R.id.textView_result2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        Notify.newNotification("Update Alert", "Traffic: Severe Accident", this);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_from=edttxt_from.getText().toString();
                str_to=edttxt_to.getText().toString();
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyBMUjResWPXk24xE8R5D7_JVRVu9RA0gEA";
                new GeoTask(HomePage.this).execute(url);

            }
        });
    }

    @Override
    public void setDouble(String result) {
        String res[]=result.split(",");
        Double min=Double.parseDouble(res[0])/60;
        int dist=Integer.parseInt(res[1])/1000;
        tv_result1.setText("Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins");
        tv_result2.setText("Distance= " + dist + " kilometers");

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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
