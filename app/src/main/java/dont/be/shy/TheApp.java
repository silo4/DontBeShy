package dont.be.shy;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import java.util.ArrayList;
import java.util.List;

import dont.be.shy.modules.ModuleManager;
import dont.be.shy.utility.Logger;

/**
 * Created by zhonglz on 16/7/29.
 */
public class TheApp extends MultiDexApplication{
    private final static String TAG = TheApp.class.getSimpleName();

    private static TheApp sIns;
    private List<Activity> mActivityList = null;
    private Activity mCurrentActivity = null;

    private ModuleManager mModuleManager;

    @Override
    public void onCreate() {
        super.onCreate();

        sIns = this;

        mModuleManager = new ModuleManager();

        mModuleManager.loadModules();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        mModuleManager.unloadModules();
    }

    public ModuleManager getModuleManager(){
        return mModuleManager;
    }

    public static TheApp getIns(){
        return sIns;
    }

    public void addActivity(Activity act){
        if(mActivityList == null){
            mActivityList = new ArrayList<>();
        }

        mCurrentActivity = act;
        Logger.i(TAG, "add activity [%s]", act.getClass().getSimpleName());
        mActivityList.add(act);
    }

    public void removeActivity(Activity act){
        if(mActivityList == null || act == null){
            return;
        }

        int pos = -1;
        for(int i = 0; i < mActivityList.size(); ++i){
            Activity temp = mActivityList.get(i);
            if(temp.equals(act)){
//                Logger.i(TAG, "found activity [%d : %s]", i, temp.getClass().getName());
                temp = null;
                pos = i;
                break;
            }
        }

        if(pos != -1){
            Logger.i(TAG,"remove activity [%d]", pos);
            mActivityList.remove(pos);
        }
    }

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public void exit(){
        if(mActivityList != null){
            for(int i = 0; i < mActivityList.size(); ++i){
                Activity act = mActivityList.get(i);
                if(act != null){
                    Logger.i(TAG, "finish class [%d : %s]", i, act.getClass().getSimpleName());
                    act.finish();
                }

                act = null;
            }
        }
        Logger.e(TAG, "app exit !!!!");
        System.exit(0);
    }
}
