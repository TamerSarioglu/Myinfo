package com.tamersarioglu.myinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.uptimeMillis;
import static java.lang.System.getProperty;

public class GeneralActvity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_actvity);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setTitle("General Info");

            //set back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //Calculate device up time
        long miliSec = uptimeMillis();
        String upTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(miliSec),
                TimeUnit.MILLISECONDS.toMinutes(miliSec) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliSec)),
                TimeUnit.MILLISECONDS.toSeconds(miliSec) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliSec)));

        //arrays of containing information
        titles = new String[]{"Model", "Manufacturer", "Device", "Board", "Hardware", "Brand",
                "Android Version", "OS Name", "API Level", "Bootloader", "Build Number", "Radio Version",
                "Kernal", "Android Runtime", "Up Time"};
        descriptions = new String[]{Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE,
                Build.BRAND, Build.VERSION.RELEASE, Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName(),
                Build.VERSION.SDK_INT + "", Build.BOOTLOADER, Build.FINGERPRINT, Build.getRadioVersion(), getProperty("os.arch"),
        "ART" + getProperty("java.vm.version"), upTime};

        ListView list = findViewById(R.id.generaList);
        //set adapter to our listView
        MyAdapter adapter = new MyAdapter(this,titles,descriptions);
        list.setAdapter(adapter);

    }

    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context c, String[] titles, String[] descriptions){
            super(c, R.layout.tworow, R.id.title, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.tworow,parent,false);
            TextView myTitle = row.findViewById(R.id.title_TextView);
            TextView myDescription = row.findViewById(R.id.description_TextView);

            //set texts to views
            myTitle.setText(titles[position]);
            myDescription.setText(descriptions[position]);

            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //when backbutton in actionbar is clicked, go to activity
        return true;
    }

    //hide searcView from this activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);

        return true;
    }
}
