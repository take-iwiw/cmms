package ca.on.conestogac.cmms;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 2016-02-19.
 */
public class Utility {
    public static final String TAG = "@] CMMS";
    public  static final String BR = System.getProperty("line.separator");
    private static final boolean IS_DETAIL = true;

    public static void logDebug(String msg){
        if(Utility.IS_DETAIL){
            StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
            Log.d(TAG
                    + callStack.getFileName() + "#" + callStack.getMethodName() + ":" + callStack.getLineNumber(), msg);
        } else {
            Log.d(TAG, msg);
        }
    }

    public static void logInfo(String msg){
        if(Utility.IS_DETAIL){
            StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
            Log.i(TAG
                    + callStack.getFileName() + "#" + callStack.getMethodName() + ":" + callStack.getLineNumber(), msg);
        } else {
            Log.i(TAG, msg);
        }
    }

    public static void logWarning(String msg){
        if(Utility.IS_DETAIL){
            StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
            Log.w(TAG
                    + callStack.getFileName() + "#" + callStack.getMethodName() + ":" + callStack.getLineNumber(), msg);
        } else {
            Log.w(TAG, msg);
        }
    }

    public static void logError(String msg){
        if(Utility.IS_DETAIL){
            StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
            Log.e(TAG
                    + callStack.getFileName() + "#" + callStack.getMethodName() + ":" + callStack.getLineNumber(), msg);
        } else {
            Log.e(TAG, msg);
        }
    }

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToastDetail(Context context, String msg){

        StackTraceElement callStack = Thread.currentThread().getStackTrace()[3];
        Toast.makeText(context, callStack.getFileName() + "#" + callStack.getMethodName() + ":"+ callStack.getLineNumber()
                + BR + msg, Toast.LENGTH_LONG).show();
    }

    public static String convertDateToString (int year, int month, int day) {
        String strYear = String.format("%04d", year);
        String strMonth = String.format("%02d", month);
        String strDay = String.format("%02d", day);
        return strYear + "/" + strMonth + "/" + strDay;
    }

    public static String convertDateToStringRaw (int year, int month, int day) {
        String strYear = String.format("%04d", year);
        String strMonth = String.format("%02d", month);
        String strDay = String.format("%02d", day);
        return strYear + strMonth + strDay;
    }

    public static String convertDateYYYYMMDDToShow (String orgDate) {
        String str = orgDate.substring(0, 4) + "/" + orgDate.substring(4, 6) + "/" + orgDate.substring(6, 8);
        return str;
    }

    public static boolean saveTextFile (String title, String fileType, String text) {
        String saveDir = Environment.getExternalStorageDirectory().getPath() + "/" + "CMMS";
        File file = new File(saveDir);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Utility.logError(saveDir.toString());
                return false;
            }
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String filename = saveDir + "/" + title + sf.format(cal.getTime()) + "." + fileType;

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filename, true);
            fos.write(text.getBytes());
            fos.close();
        } catch (Exception e) {
            Utility.logError(e.getMessage());
            return false;
        }
        fos = null;
        return true;
    }
}
