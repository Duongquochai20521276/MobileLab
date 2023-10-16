package com.example.lab06_bai03;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_message_key";
    public static final String AUTO_START_ACTIVITY = "auto_start_activity";

    @Override
    public void onReceive(Context context, Intent intent) {
    String queryString = "Are you OK?".toLowerCase();
        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            Object[] pdus = (Object[])  bundle.get(SMS_EXTRA);
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++){
                if(Build.VERSION.SDK_INT >= 23){
                    messages[i] =  SmsMessage.createFromPdu((byte[]) pdus[i],"");
                }
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            // Create ArrayList of OriginatingAddress
            ArrayList<String> addresses = new ArrayList<>();
            for (SmsMessage message:messages){
                if (message.getMessageBody().toLowerCase().contains(queryString)){
                    addresses.add(message.getOriginatingAddress());
                    Toast.makeText(context, message.getMessageBody()  , Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, message.getMessageBody() , Toast.LENGTH_LONG).show();
                }
            }
            if (addresses.size() > 0){
                if(!MainActivity.isRunning){
                    //Start MainActivity if it stopped

//                    Intent iMainActivity = new Intent(context, MainActivity.class);
//                    iMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
//                    context.startActivity(iMainActivity);

                    Intent iMainActivity = new Intent(AUTO_START_ACTIVITY,null,context, MainActivity.class);
                    iMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    iMainActivity.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    context.startActivity(iMainActivity);

//                    // Send Message
//                    Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
//                    iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
//                    context.sendBroadcast(iForwardBroadcastReceiver);
//                    Toast.makeText(context, "Sent when inactive" + " address[0]:" + addresses.get(0) , Toast.LENGTH_LONG).show();


                }
                else {
                    Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                    iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    context.sendBroadcast(iForwardBroadcastReceiver);
//                    Toast.makeText(context, "Sent when active" + " address[0]:" + addresses.get(0) , Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
