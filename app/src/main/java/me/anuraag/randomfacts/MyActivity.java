package me.anuraag.randomfacts;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;


public class MyActivity extends Activity {
    private TextView title,body;
    private Button newFact;
    private ArrayList<String> facts = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Read in the file
        InputStream is = this.getResources().openRawResource(R.raw.facts);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            while ((readLine = br.readLine()) != null) {
                facts.add(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                int factNumber = (int)(Math.random() * 2101) + 1;
                if(facts.get(factNumber).isEmpty())
                    factNumber++;
                if(facts.get(factNumber).isEmpty())
                    factNumber++;
                title.setText("Swag Fact #" + factNumber);
                body.setText(facts.get(factNumber));
                createNotif("Fact of the day", facts.get(factNumber));
                Log.i("work it", facts.get(factNumber));
            }
        }, 1000);
//        fil Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                if(facts.get(factNumber).isEmpty())
//                    factNumber++;
//                if(facts.get(factNumber).isEmpty())
//                    factNumber++;
//                createNotif("Fact of the day",facts.get(factNumber) );
//            }
//        });

        //Initialize all the UI Stuff
        title = (TextView)findViewById(R.id.head);
        body = (TextView)findViewById(R.id.textView2);
        newFact = (Button)findViewById(R.id.button);
        createFact();
        newFact.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFact();
            }
        });







    }

    public void createFact(){
        int factNumber = (int)(Math.random() * 2101) + 1;
        if(facts.get(factNumber).isEmpty())
            factNumber++;
        if(facts.get(factNumber).isEmpty())
            factNumber++;
        displayFact(factNumber);
    }

    public void displayFact(int factnumber){

        title.setText("Swag Fact #" + factnumber);
        body.setText(facts.get(factnumber));

    }
    public void createNotif(String title, String text){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);
        Intent resultIntent = new Intent(this, MyActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MyActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );//        mBuilder.set(resultIntent);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(0, mBuilder.build());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
}
