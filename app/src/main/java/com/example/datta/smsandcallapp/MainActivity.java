package com.example.datta.smsandcallapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

// give the permissions in manifest file ...
public class MainActivity extends AppCompatActivity {


    EditText et;

    public static final String TAG = "MACVTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.editText);
    }

    //  Calling Purpose.....
    public void callMadu(View v) {
        Log.d(TAG, "--> call Madu");
        String ph = et.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);   // directly will go fa call..
        //Intent callIntent = new Intent(Intent.ACTION_DIAL);// we can change the number during call also
        callIntent.setData(Uri.parse("tel:" + ph));//change the number
        try {
            startActivity(callIntent);
            Toast.makeText(this, "callling to " + ph, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "Excep!!!");
        }
    }

    // Sms -----------------------------

    public void smsMadu(View v) {
        Log.d(TAG, "--> sms Madu");
        String ph = et.getText().toString(); // using implicit intent..  Action view

        /* ---------1st way of sending sms-------------------*/      // using mobile sms app
        Intent in = new Intent(Intent.ACTION_VIEW);
        // prompts only sms-mms clients                             // it will open msg app dats all...
        in.setType("vnd.android-dir/mms-sms");

        // extra fields for number and message respectively
        in.putExtra("address", ph);
        in.putExtra("sms_body", "HELLO ANDROID");
        try {
            startActivity(in);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Your sms has failed...", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
          /* ---------1st way of sending sms ends here-------------------*/


    }
// 2nd way..
    public void useSmsManager(View view) {
        //Getting intent and PendingIntent instance
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pin = PendingIntent.getActivity(this, 0, i, 0);


        //Get the SmsManager instance and call the sendTextMessage method to send message
        ArrayList<String> addresses = new ArrayList<String>();   /// here address means phone number
        addresses.add("8050236951");
        addresses.add("7411499198");
        addresses.add("9481584582");

        for (int k = 0; k < addresses.size(); k++) {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(addresses.get(k), null, "Hey From Android Studio", pin, null);
            Toast.makeText(this, "sending sms to " + addresses, Toast.LENGTH_SHORT).show();
        }
    }
    // 3rd way..........
    public void thirdType(View view)
    {
        String ph = et.getText().toString();
        Uri uri = Uri.parse("smsto:" +ph);Intent in = new Intent(Intent.ACTION_SENDTO, uri);            // // it will open msg app dats all...
        // add the message at the sms_body extra field
        in.putExtra("sms_body", "HelloHAIHELLO");
        try{
            startActivity(in);
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();

    }




    }

   /* You can open each SMS message part by opening different ways.
        Inbox = "content://sms/inbox"
        Failed = "content://sms/failed"
        Queued = "content://sms/queued"
        Sent = "content://sms/sent"
        Draft = "content://sms/draft"
        Outbox = "content://sms/outbox"
        Undelivered = "content://sms/undelivered"
        All = "content://sms/all"
        Conversations = "content://sms/conversations"
        */


    // Searchin
    public void searchMadu(View view) {

        Log.d(TAG, "--->searchSMS");
        Uri uri = Uri.parse("content://sms/inbox"); // very imp
        uri = Uri.parse("content://sms/sent"); // very imp
        Cursor c= getContentResolver().query(uri, null, null, null, null);
        //startManagingCursor(c);             // we can leave this one..

        String[] body = new String[c.getCount()];
        String[] number = new String[c.getCount()];
        Log.d(TAG,"<-------U have these msgs------>");
        if(c.moveToFirst()){
            for(int i=0;i<c.getCount();i++){
                body[i]= c.getString(c.getColumnIndexOrThrow("body")).toString();  // we can use oly getColoumn index or with or throw..
                number[i]=c.getString(c.getColumnIndex("address")).toString();
                Log.d(TAG,"msg--> "+body[i]+" from ---> "+number[i]);
                c.moveToNext();
            }
        }

        c.close();
    }
}
