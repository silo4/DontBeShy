package dont.be.shy.modules;

import android.text.TextUtils;

import dont.be.shy.utility.Logger;

/**
 * Created by zhonglz on 16/7/29.
 */
public abstract class BaseModule {
    private final static String TAG = BaseModule.class.getSimpleName();
    String mModuleName;
    ModuleInitCallback mInitCallback;

    public BaseModule(String moduleName){
        assert (!TextUtils.isEmpty(moduleName));
        this.mModuleName = moduleName;
    }

    public BaseModule(String moduleName, ModuleInitCallback callback){
        assert (!TextUtils.isEmpty(moduleName));
        this.mModuleName = moduleName;
        this.mInitCallback = callback;
    }

    public abstract void init();
    public abstract void uninit();

    public void setInitCallback(ModuleInitCallback callback){
        this.mInitCallback = callback;
    }

    public void finishInit(){
        if (mInitCallback != null){
            Logger.i(TAG, "init [%s] complete", mModuleName);
            mInitCallback.onInited(mModuleName);
        }
    }
}
