package dont.be.shy.modules;

import org.greenrobot.eventbus.EventBus;

import dont.be.shy.event.BaseEvent;
import dont.be.shy.event.EventId;
import dont.be.shy.utility.Logger;

/**
 * Created by zhonglz on 16/7/29.
 */
public class BootModule extends BaseModule {
    private final static String TAG = BootModule.class.getSimpleName();

    public BootModule(String moduleName) {
        super(moduleName);
    }

    public BootModule(String moduleName, ModuleInitCallback callback) {
        super(moduleName, callback);
    }

    @Override
    public void init() {
        Logger.i(TAG, "init [%s]", mModuleName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
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
