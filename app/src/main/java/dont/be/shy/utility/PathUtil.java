package dont.be.shy.utility;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by zhonglz on 16/7/30.
 */
public class PathUtil {
    private final static String TAG = PathUtil.class.getSimpleName();

    //目录名
    public final static String BASE_DIR = "dont.be.shy";
    public final static String TEMP_DIR = "temp";
    public final static String DATABASE_DIR = "database";
    public final static String LOG_DIR = "log";
    public final static String IMAGECACHE_DIR = "imgcache";


    // /cache
    public static String getCachePath(){
        return Environment.getDownloadCacheDirectory().getPath();
    }
    // /data/data
    public static String getDataPath(){
        return Environment.getDataDirectory().getPath() + "/data";
    }
    // /mnt/sdcard/
    public static String getSdcardPath(){
        return Environment.getExternalStorageDirectory().getPath();
    }
    // /mnt/sdcard/Pictures
    public static String getPicturesPath(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    }
    // /system
    public static String getSystemPath(){
        return Environment.getRootDirectory().getPath();
    }

	/**
	 * 本地根目录
	 * 有SD卡时: /mnt/sdcard/{@link #BASE_DIR}/
	 * 无SD卡时：/data/data/{@link #BASE_DIR}/
	*/

    public static String getBaseDir(){
        String sdBaseDir = getSdcardPath();
        if(TextUtils.isEmpty(sdBaseDir)){

        }else{
            sdBaseDir += "/" + BASE_DIR;
            boolean bRet = makeDir(sdBaseDir);

            if(bRet){
                return sdBaseDir + "/";
            }
        }

        String dtBaseDir = getDataPath();
        if(dtBaseDir == null || dtBaseDir.isEmpty()){
            return "";
        }else{
            dtBaseDir += "/" + BASE_DIR;
            boolean bRet = makeDir(dtBaseDir);

            if(bRet){
                return dtBaseDir + "/";
            }
        }

        return "";
    }

    /**
     *  本地临时存放目录
     *  有SD卡时, /mnt/sdcard/{@link #BASE_DIR}/temp/
     *  无SD卡时, /data/data/{@link #BASE_DIR}/temp/
     *
     */
    public static String getTempDir(){
        String tempDir = getBaseDir();
        if(tempDir.isEmpty()){
            return "";
        }

        tempDir += TEMP_DIR;
        boolean bRet = makeDir(tempDir);
        if(bRet){
            return tempDir + "/";
        }

        return "";
    }

    /**
     *  数据库目录
     *  有SD卡时, /mnt/sdcard/{@link #BASE_DIR}/database/
     *  无SD卡时, /data/data/{@link #BASE_DIR}/database/
     *
     */
    public static String getDatabaseDir(){
        String dbDir = getBaseDir();
        if(dbDir.isEmpty()){
            Logger.e(TAG, "get database dir failed...for base dir is empty");
            return "";
        }

        dbDir += DATABASE_DIR;
        boolean bRet = makeDir(dbDir);
        if(bRet){
            return dbDir + "/";
        }

        Logger.e(TAG, "make head dir failed...");

        return "";
    }

    //崩溃日志路径
    public static String getLogDir(){
        String logDir = getBaseDir();
        if(logDir.isEmpty()){
            Logger.e(TAG, "get log dir failed...for base dir is empty");
            return "";
        }

        logDir += LOG_DIR;
        boolean bRet = makeDir(logDir);
        if(bRet){
            return logDir + "/";
        }

        Logger.e(TAG, "make log dir failed...");

        return "";
    }

    //图片缓存路径
    public static String getImageCacheDir() {
        String imgCacheDir = getBaseDir();
        if (imgCacheDir.isEmpty()) {
            Logger.e(TAG, "get image cache dir failed...for base dir is empty");
            return "";
        }

        imgCacheDir += IMAGECACHE_DIR;
        boolean bRet = makeDir(imgCacheDir);
        if (bRet) {
            return imgCacheDir + "/";
        }

        Logger.e(TAG, "make image cache dir failed...");

        return "";
    }

    //创建目录
    public static boolean makeDir(String name){
        if(name == null || name.isEmpty()){
            return false;
        }

        File dir = new File(name);
        if(dir.exists()){
            return true;
        }

        boolean bRet = dir.mkdir();

        return bRet;
    }
}
