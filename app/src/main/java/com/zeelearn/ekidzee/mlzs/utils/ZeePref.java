package com.zeelearn.ekidzee.mlzs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class ZeePref {

    private static final String TAG = "zeepref";
    public static final String KEY_PREF_VALUE = "zeepref";

    protected static final String KEY_USER_ID ="userid";
    protected static final String KEY_USER_NAME ="username";
    protected static final String KEY_USER_TYPE ="app_usertype";
    protected static final String KEY_USER_TYPE_NAME ="usertype_name";
    protected static final String KEY_DEVICE_ID ="deviceid";
    protected static final String KEY_FCM_TOKEN ="fcm_token";


    protected static final String KEY_UID ="uid";
    protected static final String KEY_DISPLAY_NAME ="display_name";
    protected static final String KEY_USER_CODE ="usercode";
    protected static final String KEY_USER_LAST_lOGIN ="last_login";
    protected static final String KEY_CONMTACT_PERSON ="contact_person";
    protected static final String KEY_EMAIL_ID ="mail";
    protected static final String KEY_MOBILE_NO ="mobile_no";
    protected static final String KEY_ZONE_CODE ="zone_code";
    protected static final String KEY_CAN_EDIT ="can_edit";
    protected static final String KEY_IS_EXTERNAL_USER ="is_external_user";
    protected static final String KEY_REF_FRAN_ID ="ref_fran_id";
    protected static final String KEY_M_USER_ID = "m_user_id";
    protected static final String KEY_IS_VALID_LOGIN = "validlogin";
    protected static final String KEY_APP_ID = "appid";


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(KEY_PREF_VALUE, Context.MODE_PRIVATE);
    }

    private static void putLong(Context context, String key, long value) {
        getPreferences(context).edit().putLong(key, value).commit();
    }

    private static long getLong(Context context, String key, long value) {
        return getPreferences(context).getLong(key, value);
    }

    private static void putString(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).commit();
    }

    private static String getString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    private static int getInt(Context context, String key) {
        return getPreferences(context).getInt(KEY_PREF_VALUE + key, 0);
    }

    private static void putInt(Context context, String key, int value) {
        getPreferences(context).edit().putInt(KEY_PREF_VALUE + key, value).commit();
    }

    private static boolean getBoolean(Context context, String key, boolean def) {
        return getPreferences(context).getBoolean(KEY_PREF_VALUE + key, def);
    }


    private static void putBoolean(Context context, String key, boolean value)
    {
        getPreferences(context).edit().putBoolean(KEY_PREF_VALUE + key, value).commit();
    }
    private static String getStringPrefrence(Context context, String type) {
        return getString(context, KEY_PREF_VALUE + type, "");
    }

    private static void setStringPrefrence(Context context, String type, String value) {
        putString(context, KEY_PREF_VALUE + type, value);
    }

    private static void removeValue(Context context, String key)
    {
        getPreferences(context).edit().remove(KEY_PREF_VALUE + key).commit();
    }

    public static void clear(Context context) {
        getPreferences(context).edit().clear().commit();
    }


    public static void setUserId(Context context,int userId){
            putInt(context, KEY_USER_ID, userId);
    }



    public static int getUserId(Context context){
        return getInt(context,KEY_USER_ID);
    }

    public static void setAppId(Context context,int id){
        putInt(context, KEY_APP_ID, id);
    }



    public static int getAppId(Context context){
        return getInt(context,KEY_APP_ID);
    }

    public static void setDeviceId(Context context,String deviceid){
        if(!TextUtils.isEmpty(deviceid)) {
            setStringPrefrence(context, KEY_DEVICE_ID, deviceid);
        }
    }

    public static String getDeviceId(Context context){
        return getStringPrefrence(context,KEY_DEVICE_ID);
    }

    public static void setUserName(Context context,String userName){
        if(!TextUtils.isEmpty(userName)) {
            setStringPrefrence(context, KEY_USER_NAME, userName);
        }
    }

    public static String getUserName(Context context){
        return getStringPrefrence(context,KEY_USER_NAME);
    }

    public static void setUserType(Context context,String userType){
        if(!TextUtils.isEmpty(userType)) {
            setStringPrefrence(context, KEY_USER_TYPE, userType);
        }
    }
    public static String getUserType(Context context){
        return getStringPrefrence(context,KEY_USER_TYPE);
    }

    public static void setUserTypeName(Context context,String userType){
        if(!TextUtils.isEmpty(userType)) {
            setStringPrefrence(context, KEY_USER_TYPE_NAME, userType);
        }
    }
    public static String getUserTypeName(Context context){
        return getStringPrefrence(context,KEY_USER_TYPE_NAME);
    }

    public static void setUID(Context context,int uid){
        putInt(context,KEY_UID,uid);
    }
    public static int getUID(Context context){
        return getInt(context,KEY_UID);
    }

    public static void setUserCode(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_USER_CODE, value);
        }
    }
    public static String getUuserCode(Context context){
        return getStringPrefrence(context,KEY_USER_CODE);
    }

    public static void setDisplayName(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_DISPLAY_NAME, value);
        }
    }
    public static String getDisplayName(Context context){
        return getStringPrefrence(context,KEY_DISPLAY_NAME);
    }

    public static void setContactPerson(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_CONMTACT_PERSON, value);
        }
    }
    public static String getContactPerson(Context context){
        return getStringPrefrence(context,KEY_CONMTACT_PERSON);
    }
    public static void setEmailId(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_EMAIL_ID, value);
        }
    }
    public static String getEmailId(Context context){
        return getStringPrefrence(context,KEY_EMAIL_ID);
    }

    public static void setMobileNo(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_MOBILE_NO, value);
        }
    }
    public static String getMobileNo(Context context){
        return getStringPrefrence(context,KEY_MOBILE_NO);
    }

    public static void setZoneCode(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_ZONE_CODE, value);
        }
    }
    public static String getZoneCode(Context context){
        return getStringPrefrence(context,KEY_ZONE_CODE);
    }

    public static void setFCMToken(Context context,String value){
        if(!TextUtils.isEmpty(value)) {
            setStringPrefrence(context, KEY_FCM_TOKEN, value);
        }
    }
    public static String getToken(Context context){
        return getStringPrefrence(context,KEY_FCM_TOKEN);
    }

    public static void setCanEdit(Context context,boolean flag){
        putBoolean(context,KEY_CAN_EDIT,flag);
    }
    public static boolean canEdit(Context context){
        return getBoolean(context,KEY_CAN_EDIT,false);
    }

    public static void setExternalUser(Context context,boolean flag){
        putBoolean(context,KEY_IS_EXTERNAL_USER,flag);
    }
    public static boolean isExternalUser(Context context){
        return getBoolean(context,KEY_IS_EXTERNAL_USER,false);
    }

    public static void setRefFranid(Context context,int value){
        putInt(context,KEY_REF_FRAN_ID,value);
    }
    public static int getRefFranid(Context context){
        return getInt(context,KEY_REF_FRAN_ID);
    }

    public static void logOut(Context context){
        setStringPrefrence(context,KEY_USER_ID,"");
        setStringPrefrence(context,KEY_USER_TYPE,"");
        setStringPrefrence(context,KEY_USER_NAME,"");
    }
}
