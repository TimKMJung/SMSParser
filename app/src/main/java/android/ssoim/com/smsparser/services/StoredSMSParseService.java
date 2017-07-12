package android.ssoim.com.smsparser.views;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.ssoim.com.smsparser.R;
import android.ssoim.com.smsparser.data.Constants;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by renewinspirit on 2017. 6. 19..
 */

public class StoredSMSParseService extends IntentService {

	public StoredSMSParseService() {
	super("TAG");
}

	public static String parseString;

@Override
protected void onHandleIntent(@Nullable Intent intent) {
	ScanAndParseSMS();
}

	public void ScanAndParseSMS() {

		Uri inboxUri = Uri.parse(Constants.SMS_PARSE_HEADER_FIELD);
		String brAddress = Constants.BROKERAGE_TEL_NUM;

	//	final String[] projection = {"*"};

		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(inboxUri, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
//				do {

	//				Msg msg = new Msg(cursor.getString(cursor.getColumnIndex("_id")));
	//				msg.setAddress(getMmsAddr(msg.getID()));
	//
	//				String add = getMmsAddr(msg.getID());

	//				if (add.contains(brAddress)) {
	//					ParseSMS(msg);
	//				}

//					String from = cursor.getString(cursor.getColumnIndexOrThrow("address"));

				String from = cursor.getString(2).toString();
				Log.d("SMSPARSER", from );

					if(from.contains(Constants.BROKERAGE_TEL_NUM)) {
						Log.d("SMSPARSER", from );


						ParseSMS(from);

					}


			}
		}
	}


	public String getMmsAddr(String id) {
		String sel = new String("msg_id=" + id);
		String uriString = MessageFormat.format(Constants.SMS_PARSE_HEADER_FORMAT, id);
		Uri uri = Uri.parse(uriString);
		Cursor c = getContentResolver().query(uri, null, sel, null, null);
		String name = "";
		while (c.moveToNext()) {
			String t = c.getString(c.getColumnIndex("address"));
			if (!(t.contains("insert")))
				name = name + t + " ";
		}
		c.close();
		return name;
	}


	private void ParseSMS(String txt) {
		parseString = txt;

		SMSParserView inst = SMSParserView.instance();
		inst.updateSMSText(parseString);

		// NOTIFICATION BUILDER
		Intent intent = new Intent(this, SMSParserView.class);
		int requestCode = 0;

		String title = Constants.NOTIFICATION_TITLE;
		String desc = Constants.NOTIFICATION_DESC +  "From : " + parseString;

		PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
		Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.push_icon)
				.setContentTitle(title)
				.setContentText(desc)
				.setAutoCancel(true)
				.setSound(sound)
				.setContentIntent(pendingIntent);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, noBuilder.build());


	}
	
	
}
