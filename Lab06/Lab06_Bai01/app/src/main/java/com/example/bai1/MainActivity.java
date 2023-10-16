package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;

    public void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();
        TextView tvContent = (TextView) findViewById(R.id.tv_content);

        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        String sms = "";
        SmsMessage smsMsg;
        for (int i = 0; i < messages.length; i++) {
            if (Build.VERSION.SDK_INT >= 23) {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i],"");
            }
            smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);

            String msgBody = smsMsg.getMessageBody();

            String address = smsMsg.getDisplayOriginatingAddress();

            sms += address + ":\n" + msgBody + "\n";
        }

        tvContent.setText(sms);
    }

    private void initBroadcastReceiver() {
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (broadcastReceiver == null) initBroadcastReceiver();

        registerReceiver(broadcastReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);

        initBroadcastReceiver();
    }
}