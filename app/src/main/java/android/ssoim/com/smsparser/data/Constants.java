package android.ssoim.com.smsparser.data;

/**
 * Created by renewinspirit on 2017. 6. 20..
 */

public class Constants {

    public static String TAG = "Code Artist";
    public static String SMS_CONTENT_HEADER= "[Web발신]";
    public static String BROKERAGE_TEL_NUM = "015776000";
//    public static String SMS_CONTENT_HEADER= "*YOUR SPECIFIC STRING*";

    public static String SMS_PARSE_HEADER_FIELD= "content://sms/inbox";
    public static String SMS_PARSE_HEADER_FORMAT= "content://sms/{0}/addr";
    public static String SMS_PARSE_PART_FIELD = "content://sms/part";

    public static String NOTIFICATION_TITLE = "SMS Parsing Done";
    public static String NOTIFICATION_DESC = "SMS Parsing is completed.";
}
