package dont.be.shy.modules;

import dont.be.shy.utility.Logger;

/**
 * Created by zhonglz on 16/7/29.
 */
public class UserModule extends BaseModule {
    private final static String TAG = UserModule.class.getSimpleName();

    public UserModule(String moduleName) {
        super(moduleName);
    }

    public UserModule(String moduleName, ModuleInitCallback callback) {
        super(moduleName, callback);
    }

    @Override
    public void init() {
        Logger.i(TAG, "init [%s]", mModuleName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finishInit();
            }
        }).start();
    }

    @Override
    public void uninit() {
        Logger.i(TAG, "uninit [%s]", mModuleName);
    }
}
