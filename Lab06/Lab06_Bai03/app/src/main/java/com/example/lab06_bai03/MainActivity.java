package com.example.lab06_bai03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private ReentrantLock reentrantLock;
    private Switch swAutoResponseGood;
    private Switch swAutoResponseBad;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    private SharedPreferences.Editor editorGood;
    private SharedPreferences.Editor editorBad;
    private SharedPreferences sharedPreferencesGood;
    private SharedPreferences sharedPreferencesBad;
    private final String AUTO_RESPONSE_GOOD = "auto_response_good";
    private final String AUTO_RESPONSE_BAD = "auto_response_bad";

    private static final int MY_PERMISSIONS_REQUEST_MULTIPLE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[] { Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS },
                MY_PERMISSIONS_REQUEST_MULTIPLE);

        findViewsByIds();
        initVariables();
        handleOnClickListenner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_MULTIPLE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG", "MY_PERMISSIONS_REQUEST_MULTIPLE --> YES");
            } else {
                Log.i("TAG", "MY_PERMISSIONS_REQUEST_MULTIPLE --> NO");
            }

        }

    }

    private void findViewsByIds() {
        swAutoResponseGood = (Switch) findViewById(R.id.sw_auto_response_good);

        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        lvMessages = (ListView) findViewById(R.id.lv_messages);
        btnSafe = (Button) findViewById(R.id.btn_safe);
        btnMayday = (Button) findViewById(R.id.btn_mayday);
    }

    private void respond(String to, String response) {
        reentrantLock.lock();
        requesters.remove(to);
        adapter.notifyDataSetChanged();
        reentrantLock.unlock();
        // Get information for sending
//        Intent intent = getIntent();
//        String phoneNumber = intent.getStringExtra("phoneNumber");
//        String message = intent.getStringExtra("message");
//        Toast.makeText(this,"phoneNumber:" +  phoneNumber +" message" +  message, Toast.LENGTH_LONG).show();
//        if (phoneNumber != null && message != null) {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//        }
//        else {
//
//        }

        // Send The Message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to, null, response, null, null);

    }

    public void respond(boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;
        ArrayList<String> requesterCopy = (ArrayList<String>) requesters.clone();
        for (String to : requesterCopy)
            respond(to, outputString);
    }

    public void processReceiveAddresss(ArrayList<String> addresses) {
        for (int i = 0; i < addresses.size(); i++) {
            if (!requesters.contains(addresses.get(i))) {
                reentrantLock.lock();
                requesters.add(addresses.get(i));
                adapter.notifyDataSetChanged();
                reentrantLock.unlock();
            }
            if (swAutoResponseGood.isChecked())
                respond(true);
            else if(swAutoResponseBad.isChecked()){
                respond(false);
            }
        }
    }

    private void handleOnClickListenner() {

        btnSafe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond(true);
            }
        });
        btnMayday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond(false);
            }
        });
        swAutoResponseGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && !swAutoResponseBad.isChecked())
                    llButtons.setVisibility(View.VISIBLE);
                else
                {
                    llButtons.setVisibility(View.GONE);
                    swAutoResponseBad.setChecked(false);
                    editorBad.putBoolean(AUTO_RESPONSE_BAD, !isChecked);
                    // Save auto response setting
                    editorGood.putBoolean(AUTO_RESPONSE_GOOD, isChecked);
                    editorGood.commit();
                    editorBad.commit();
                }
                swAutoResponseGood.setChecked(isChecked);


            }
        });
        swAutoResponseBad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && !swAutoResponseGood.isChecked())
                    llButtons.setVisibility(View.VISIBLE);
                else
                {
                    llButtons.setVisibility(View.GONE);
                    swAutoResponseGood.setChecked(false);
                    editorGood.putBoolean(AUTO_RESPONSE_GOOD, !isChecked);
                    editorBad.putBoolean(AUTO_RESPONSE_BAD, isChecked);
                    editorGood.commit();
                    editorBad.commit();
                }
                swAutoResponseBad.setChecked(isChecked);
                // Save auto response setting


            }
        });
    }

    private void initBroadcastReceiver() {
        // Default Process (maybe)
        Intent intent = getIntent();
        if (intent != null){
            Toast.makeText(this, intent.getAction() , Toast.LENGTH_LONG).show();
            if (intent.getAction() == SmsReceiver.AUTO_START_ACTIVITY){
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
//            Toast.makeText(this, "i'm in initBroadcastReceiver" , Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "addresses is null? " + String.valueOf(addresses) , Toast.LENGTH_LONG).show();
                if (addresses != null) {
                    processReceiveAddresss(addresses);
//                Toast.makeText(this, "Get something from intent" + " address[0]:" + addresses.get(0) , Toast.LENGTH_LONG).show();
                }
            }
        }


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
//                Toast.makeText(mContext, "Calling initBroadcastReceiver onReceive", Toast.LENGTH_LONG).show();
//                Toast.makeText(this, "Get something from intent" + " address[0]:" + addresses.get(0) , Toast.LENGTH_LONG).show();
                // Process these addresses
                processReceiveAddresss(addresses);
            }

        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        // UnregisterReceiver
        unregisterReceiver(broadcastReceiver);
    }

    private void initVariables() {
        sharedPreferencesGood = getPreferences(MODE_PRIVATE);
        sharedPreferencesBad = getPreferences(MODE_PRIVATE);
        editorGood = sharedPreferencesGood.edit();
        editorBad = sharedPreferencesBad.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        // Load auto response setting
        boolean autoResponseGood = sharedPreferencesGood.getBoolean(AUTO_RESPONSE_GOOD, false);
        boolean autoResponseBad = sharedPreferencesBad.getBoolean(AUTO_RESPONSE_BAD, false);
        swAutoResponseGood.setChecked(autoResponseGood);
        swAutoResponseBad.setChecked(autoResponseBad);
        if (autoResponseGood || autoResponseBad)
            llButtons.setVisibility(View.GONE);

        initBroadcastReceiver();
        // Send addresses to broadcast receiver
        Intent intent = new Intent(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        intent.putExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY, requesters);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        // Make sure broadcastReceiver was inited
        if (broadcastReceiver == null)
            initBroadcastReceiver();
        // RegisterReceiver
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter);
    }

}