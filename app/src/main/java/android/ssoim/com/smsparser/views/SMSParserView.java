package android.ssoim.com.smsparser.views;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.ssoim.com.smsparser.R;
import android.ssoim.com.smsparser.services.SMSHijackParseService;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SMSParserView extends AppCompatActivity {

    public SMSHijackParseService mSMSHijackParseService;
    private Button hijackBtn = null;
    private Button storedParseBtn = null;
    private TextView smsText = null;

    public static SMSParserView smsParserActivity;
    public static SMSParserView instance() {
        return smsParserActivity;
    }



    public void updateSMSText(String authStr) {
        smsText.setText(authStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsparer_view);

        init();

        requestSmsPermission();

    }

    private void init() {
        hijackBtn = (Button) findViewById(R.id.sms_hijact_parse_btn);
        storedParseBtn = (Button) findViewById(R.id.sms_stored_parse_btn);

        smsText = (TextView) findViewById(R.id.sms_parse_text);

        mSMSHijackParseService = new SMSHijackParseService();

        requestSmsPermission();

        setButton();


    }

    private void addSMSReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        registerReceiver(mSMSHijackParseService, intentFilter);
    }

    private void startParseSMSService() {
        Intent mServiceIntent = new Intent(this, StoredSMSParseService.class);
        this.startService(mServiceIntent);

    }


    private void setButton() {
        hijackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSMSReceiver();
            }
        });

        storedParseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestHijackSmsPermission();
            }
        });

    }


    private void requestSmsPermission() {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

    }

    private void requestHijackSmsPermission() {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);

        } else {
            startParseSMSService();
        }
    }

}
