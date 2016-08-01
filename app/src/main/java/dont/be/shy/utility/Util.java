package dont.be.shy.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import dont.be.shy.TheApp;

/**
 * Created by zhonglz on 16/7/30.
 */
public class Util {
    private final static String TAG = Util.class.getSimpleName();

    private static int gMsgId = 1;
    private static int gTimerId = 1;
    private static long gSeq = 1;
    private static int gEvtId = 1;

    public static int getScreenWithInPix(){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）

        return width;
    }

    public static int getScreenHeightInPix(){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;// 屏幕高度（像素）

        return height;
    }

    public static int getScreenWithInDp(){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width/density);//屏幕宽度(dp)

        return screenWidth;
    }

    public static int getScreenHeightInDp(){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height= dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenHeight = (int)(height/density);//屏幕高度(dp)

        return screenHeight;
    }

    public static int pix2Dp(int pix){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        return (int)(pix/density);
    }

    public static int dp2Pix(int dp){
        WindowManager wm = (WindowManager) TheApp.getIns().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        return (int)(dp * density);
    }

    public static int getTimerId(){
        return gTimerId++;
    }

    public static int getMsgId(){
        return gMsgId++;
    }

    public static long getSeq(){
        return gSeq++;
    }

    public static int getEvtId(){
        return gEvtId++;
    }

    //随机数
    public static int getRandomNum(){
        return Double.valueOf(Math.random() * 100000).intValue();
    }

    //当前时间戳，到秒
    public static long getCurrentTS(){
        return System.currentTimeMillis()/1000;
    }

    /**
     * long类型时间格式化
     */
    public static String convertToTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(time * 1000);

        return df.format(date);
    }

    //到年月日
    public static String convertToTimeDay(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date(time * 1000);

        return df.format(date);
    }

    //时：分
    public static String convertToTimeHM(long time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(time * 1000);

        return df.format(date);
    }

    public static String getLogName(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static String getLogBegin(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static String convertToTimeForOSS(long time) {
        SimpleDateFormat df = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Date date = new Date(time * 1000);
        return df.format(date);
    }

	/*
	 *  经纬度转为长整型, 乘/除以1000,000
    */

    public static long convertLongLatitude(double num){
        long ret = Double.valueOf(num * 1000000).longValue();
        return ret;
    }

    public static double convertBackLongLatitude(long num){
        double ret = 0;
        if(num > 0){
            ret = num * 1.0/1000000;
        }
        return ret;
    }

    /**
     * 计算两个日期型的时间相差多少时间
     * @param startDate  开始日期，秒级
     * @param endDate    结束日期，秒级
     * @return
     */
    public static String twoTimeDistance(long startDate, long endDate) {
        if(startDate <= 0){
            return "";
        }

        long timeLong = Math.abs((endDate - startDate) * 1000);

        if (timeLong < 60 * 1000)
//			return timeLong / 1000 + "秒前";
            return "刚刚";
        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24 * 1000) {
            timeLong = timeLong / 60 / 60 / 1000;
            if(timeLong > 3){
                SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String hourTime = sdfHour.format(startDate * 1000);
                SimpleDateFormat sdfDay = new SimpleDateFormat("dd", Locale.getDefault());
                String dayTime = sdfDay.format(startDate * 1000);
                String today = sdfDay.format(getCurrentTS() * 1000);
                if(dayTime.equals(today)){
                    return "今天 " + hourTime;
                }else{
                    return "昨天 " + hourTime;
                }

            }else{
                return timeLong + "小时前";
            }
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24;
            SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String hourTime = sdfHour.format(startDate * 1000);
            if(timeLong == 1){
                return "昨天 " + hourTime;
            }else if(timeLong == 2){
                return "前天 " + hourTime;
            }else{
                return timeLong + "天前 ";
            }
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
            return timeLong + "周前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.format(startDate * 1000);
//			return "很久以前";
        }
    }

    public static String convertLeftTime(long lefttime) {
        long timeLong = lefttime * 1000;

        if (timeLong < 60 * 1000)
            return "即将进站";
        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60;
            return "约" + timeLong + "分钟";
        } else{
            timeLong = timeLong / 60 / 60 / 1000;
            return "约" + timeLong + "小时";
        }
    }

    //和现在时间对比，超过5分钟则消息列表里显示时间
    public static boolean isShowMsgTime(long preMsgTime, long msgTime){
        long timeLong = Math.abs((msgTime - preMsgTime));

        return timeLong >= 5 * 60;
    }

    public static boolean isRunningForeground ()
    {
        Context context = TheApp.getIns();
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))
        {
            return true ;
        }

        return false ;
    }

    public static void setInputShow(boolean bShow, View view) {
        Context context = TheApp.getIns();
        if (bShow) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getSplitClassName(String className){
        if(className == null || className.isEmpty()){
            return "";
        }
        int lastIndex = className.lastIndexOf(".");
        String splitName = className.substring(lastIndex + 1, className.length());
        return splitName;
    }

    //获取系统版本
    public static String getPhoneOSVersion(){
        return android.os.Build.VERSION.RELEASE;
    }

    //获取手机型号
    public static String getPhoneModel(){
        return android.os.Build.MODEL;
    }

    //手机SDK版本
    public static int getPhoneSDK(){
        return android.os.Build.VERSION.SDK_INT;
    }

    //手机厂商
    public static String getPhoneManufacture(){
        return android.os.Build.MANUFACTURER;
    }

    //CPU类型
    public static String getCpuType(){
        return android.os.Build.CPU_ABI2;
    }

    //获取手机类型  0表示windows，1表示安卓，2表示iphone
    public static short getPhoneType(){
        return 1;
    }

    //获取versionCode
    public static int getLocalVersionCode()//获取版本号
    {
        Context context = TheApp.getIns();
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(pi == null){
            return 0;
        }
        return pi.versionCode;
    }

    //获取versionName
    public static String getLocalVersionName()// 获取版本名
    {
        Context context = TheApp.getIns();
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (pi == null) {
            return "0.0";
        }
        return pi.versionName;
    }

    public static void writeDataToFile(String path, byte[] data, boolean bAppend) {
        if(data == null || data.length <= 0){
            return;
        }
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            if (file.exists() && !bAppend) {
                file.delete();
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fos = new FileOutputStream(file, true);
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取网络类型
    //1:mobile 2:wifi 0:error
    public static int getNetType(){
        Context ctx = TheApp.getIns();
        if(ctx == null){
            return 0;
        }

        ConnectivityManager cmgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        if(info == null){
            return 0;
        }

        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
            return 1;
        }else if(info.getType() == ConnectivityManager.TYPE_WIFI){
            return 2;
        }

        return 0;
    }

    //判断网络连接是否可用
    public static boolean isNetworkConnected(){
        Context ctx = TheApp.getIns();
        if(ctx == null){
            return false;
        }

        ConnectivityManager cmgr = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        if(info == null){
            return false;
        }

        return info.isAvailable();
    }

    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard(String content){
        if(content == null || content.isEmpty()){
            return false;
        }
        Context ctx = TheApp.getIns();
        if(ctx == null){
            return false;
        }

        ClipboardManager cmb = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);

        return true;
    }

    //是否有定位权限
    public static boolean checkLocationPerssion(){
        Context ctx = TheApp.getIns();
        PackageManager pm = ctx.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION", "packageName"));

        return permission;
    }

    //是否有悬浮框权限
    public static boolean checkSystemAlertPerssion() {
        Context ctx = TheApp.getIns();
        PackageManager pm = ctx.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm
                .checkPermission("android.permission.SYSTEM_ALERT_WINDOW", ctx.getPackageName()));
        return permission;
    }

    //是否有相机功能
    public static boolean checkCamera() {
        Context ctx = TheApp.getIns();
        PackageManager pm = ctx.getPackageManager();
        boolean bCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);

        return bCamera;
    }

    //是否打开gps
    public static boolean checkGPSState(){
        Context ctx = TheApp.getIns();
        LocationManager lmgr = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);

        return lmgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //强制打开GPS
    public static void forceOpenGPS(){
        Context ctx = TheApp.getIns();
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try{
            PendingIntent.getBroadcast(ctx, 0, gpsIntent, 0).send();
        }catch(CanceledException e){
            e.printStackTrace();
        }
    }

    //WIFI列表
    //信号强度：0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线，一般Wifi已断则值为-200
    public static void getWifiList(List<String> wifiList){
        Context ctx = TheApp.getIns();
        if(wifiList == null || ctx == null){
            return;
        }

        WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wf = wm.getScanResults();
        if(wf == null){
            return;
        }

        HashMap<String, Integer> mapWf = new HashMap<String, Integer>();
        for(int i = 0; i < wf.size(); ++i){
            ScanResult result = wf.get(i);
            mapWf.put(result.BSSID, result.level);
        }

        List<Map.Entry<String, Integer>> sortWf = new ArrayList<Map.Entry<String,Integer>>(mapWf.entrySet());
        if(sortWf == null || sortWf.size() <= 0){
            return;
        }
        //按level排序
        Collections.sort(sortWf, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> lhs,
                               Entry<String, Integer> rhs) {
                // TODO Auto-generated method stub
                return (rhs.getValue() - lhs.getValue());
            }

        });

        for(int i = 0; i < sortWf.size() && i < 10; ++i){
            wifiList.add(sortWf.get(i).getKey());
        }

    }

    //获取本机wifi的mac地址
    public static String getWifiMac(){
        Context ctx = TheApp.getIns();
        if(ctx == null){
            return "";
        }

        WifiManager wifi = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    //用设备ID生成蓝牙名称，取ID的前17位，包括符号‘-’，格式为8-4-3
    public static String getBtName(){
        String devId = getDeviceUuid();
        if(devId == null || devId.isEmpty()){
            return "";
        }
        if(devId.length() < 17){
            return "";
        }
        String subId = devId.substring(0, 17);

        return subId;
    }

    //获取设备uuid
    public static String getDeviceUuid(){
        Context ctx = TheApp.getIns();
        if(ctx == null){
            return "";
        }
        TelephonyManager mgr = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String devId = mgr.getDeviceId();
        if(devId == null){
            devId = "";
        }
        String serialNum = mgr.getSimSerialNumber();
        if(serialNum == null){
            serialNum = "";
        }
        String androidId = android.provider.Settings.Secure.getString(ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if(androidId == null){
            androidId = "";
        }
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)devId.hashCode() << 32) | serialNum.hashCode());
        String uuid = deviceUuid.toString();
        return uuid;
    }

    private static long lastClickTime = 0;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
