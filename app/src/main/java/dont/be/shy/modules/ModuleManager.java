package dont.be.shy.modules;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import dont.be.shy.event.BaseEvent;
import dont.be.shy.event.EventId;
import dont.be.shy.utility.Logger;

/**
 * Created by zhonglz on 16/7/29.
 */
public class ModuleManager {
    private final static String TAG = ModuleManager.class.getSimpleName();

    private HashMap<String, BaseModule> mMapModules;
    private int initedModulesCount = 0;//初始化模块个数统计,当达到mMapModules.size()个时,说明所有模块初始化完成

    public ModuleManager(){
        mMapModules = new HashMap<>();
    }

    private void register(final Class className, final ModuleInitCallback callback){
        if (className == null){
            Logger.e(TAG, "constructor class is null");
            return;
        }

        try {
            Constructor constructor = className.getConstructor(String.class, ModuleInitCallback.class);
            BaseModule module = (BaseModule)constructor.newInstance(className.getSimpleName(), callback);
            mMapModules.put(module.mModuleName, module);

            module.init();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void unregister(Class className){
        if (className == null){
            Logger.e(TAG, "unregister class name is null");
            return;
        }

        String moduleName = className.getSimpleName();

        BaseModule module = mMapModules.get(moduleName);

        if (module == null){
            Logger.e(TAG, "unregister module is null");
            return;
        }

        module.uninit();
        mMapModules.remove(moduleName);
    }

    public void loadModules(){
        register(BootModule.class, mInitCallback);
        register(UserModule.class, mInitCallback);
    }

    public void unloadModules(){
        unregister(BootModule.class);
        unregister(UserModule.class);
    }

    private ModuleInitCallback mInitCallback = new ModuleInitCallback() {
        @Override
        public void onInited(String moduleName) {
            initedModulesCount ++;
            if (initedModulesCount == mMapModules.size()){
                Logger.i(TAG, "init all modules complete");
                EventBus.getDefault().post(new BaseEvent(EventId.SYS_EVENTID_LOAD_MODULES_FINISH));
            }
        }
    };

}
