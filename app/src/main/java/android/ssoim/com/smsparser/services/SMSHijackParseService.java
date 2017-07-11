package android.ssoim.com.smsparser.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.ssoim.com.smsparser.data.Constants;
import android.ssoim.com.smsparser.views.SMSParserView;
import android.telephony.SmsMessage;
import android.util.Log;


/**
 * Created by getaim on 2016. 11. 9..
 */

public class SMSHijackParseService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {

                Object[] pdus = (Object[]) bundle.get("pdus");
                
                for (int i = 0; i < pdus.length; i++) {
                    byte[] pdu = (byte[]) pdus[i];
                    SmsMessage sms = SmsMessage.createFromPdu(pdu);
                    String text = sms.getDisplayMessageBody();

                    Log.d("SMS", text);

                    // SMS Parsing
                    if(text.contains(Constants.SMS_CONTENT_HEADER)) { // YOUR STRING

                        SMSParserView inst = SMSParserView.instance();
                        inst.updateSMSText(text);
                    }

                }
            } else {
               
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
