package dont.be.shy.utility;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import dont.be.shy.TheApp;


public class UEHandler implements Thread.UncaughtExceptionHandler{
	private final static String TAG = "UEHandler";

    private Context softApp = null;
    private File fileErrorLog = null;

    public UEHandler(Context app) {
        softApp = app;
        String logDir = PathUtil.getLogDir();
        String filePath = "";
        String fileName = Util.getLogName() + ".log";
        filePath = logDir + fileName;
        Logger.e(TAG, "log path[%s]", filePath);

        fileErrorLog = new File(filePath);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // fetch Excpetion Info

        String info = null;
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;
        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            info = new String(data);
            data = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long threadId = thread.getId();
        // print
        String beginInfo = "////////////  " + Util.getLogBegin() + "////////////\n";
        String phoneInfo = "phone info: \n\n"
                + "[system version:" + Util.getPhoneOSVersion() + "]\n"
                + "[phone model:" + Util.getPhoneModel() + "]\n"
                + "[sdk version:" + Util.getPhoneSDK() + "]\n"
                + "[phone manufacture:" + Util.getPhoneManufacture() + "]\n"
                + "[cpu type:" + Util.getCpuType() + "]\n\n";
        String threadInfo = "thread info:\n\n"
                + "[thread name:" + thread.getName() + "]\n"
                + "[thread id:" + threadId + "]\n"
                + "[thread state:" + thread.getState() + "]\n\n";

        String errInfo = "error info:\n\n" + info + "\n\n";

        Logger.e(TAG, phoneInfo);
        Logger.e(TAG, threadInfo);
        Logger.e(TAG, errInfo);

        String err = beginInfo + phoneInfo + threadInfo + errInfo;

        //写到本地
        write2ErrorLog(fileErrorLog, err);

        ((TheApp)softApp).exit();
    }

    private void write2ErrorLog(File file, String content) {
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                // 清空之前的记录
//				file.delete();
                Logger.e(TAG, "exist log file...");
            } else {
                Logger.e(TAG, "not exist log file, so create it:" + file.getPath());
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            fos = new FileOutputStream(file, true);
            fos.write(content.getBytes());
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
}
