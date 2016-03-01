package com.example.hewlett_packard.stopwatchapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //Number of seconds displayed on the stopwatch
    private int milliseconds=0;
    private int mils, secs, minutes, hours;
    //Is the stopwatch running?
    private boolean running;
    private Button start_btn;
    private Button stop_btn;
    private Button lap_Btn;

    private ArrayList<String> lapList;
    private ArrayAdapter<String> lapListAdapter;
    private ListView lapListView;

    private TextView timeView;
    private int countLapList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null){
            milliseconds=savedInstanceState.getInt("milliseconds");
            running=savedInstanceState.getBoolean("running");
            lapList=savedInstanceState.getStringArrayList("laps");
            countLapList=savedInstanceState.getInt("contador");

        }


        lapList=new ArrayList<>();




        start_btn=(Button) findViewById(R.id.start_button);
        stop_btn=(Button) findViewById(R.id.stop_button);
        lap_Btn=(Button)findViewById(R.id.lap_btn);



        timeView= (TextView)findViewById(R.id.time_view);

        lapListView= (ListView) findViewById(R.id.listView);
        lapListAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,lapList);
        lapListView.setAdapter(lapListAdapter);

        runTimer();




    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view){




        if(start_btn.getText().equals("Start")){
            start_btn.setText("Reset");
            running=true;
        }
        else if(start_btn.getText().equals("Reset")){
            start_btn.setText("Start");
            stop_btn.setText("Stop");
            running=false;
            onClickReset();
            clearLapList();

        }


    }



    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop (View view){


        if((stop_btn.getText().equals("Stop"))&&(running)){
            stop_btn.setText("Resume");
            running=false;
        }
        else if(stop_btn.getText().equals("Resume")){
            stop_btn.setText("Stop");
            running=true;

        }



    }

    public void setLapList() {


        lapList.add(countLapList+".- "+timeView.getText().toString());
                lapListAdapter.notifyDataSetChanged();
        countLapList++;

    }

    public void clearLapList() {
        lapListAdapter.clear();
        lapListAdapter.notifyDataSetChanged();
        countLapList=0;

    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(){



        milliseconds=0;
        mils=0;
        secs=0;
        minutes=0;
        hours=0;


    }

    //Sets the number of seconds on the timer.
    private void runTimer(){


        final Handler handler= new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (running)
                    milliseconds++;

                mils=milliseconds%10;
                secs=milliseconds%600/10;
                minutes=(milliseconds%36000)/600;
                hours=milliseconds/36000;



                String time=String.format("%01d:%02d:%02d.%01d",hours,minutes,secs,mils);
                timeView.setText(time);

                handler.postDelayed(this,100);


            }
        });







    }
    //To allow the user to rotate the screen without recreating the app
    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        savedInstanceState.putInt("milliseconds",milliseconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putStringArrayList("laps", lapList);
        savedInstanceState.putInt("contador",countLapList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickLap(View view){

       if(start_btn.getText().equals("Reset"))
        setLapList();
    }



}
