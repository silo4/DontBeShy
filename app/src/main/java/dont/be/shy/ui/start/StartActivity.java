package dont.be.shy.ui.start;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import dont.be.shy.R;
import dont.be.shy.TheApp;
import dont.be.shy.event.BaseEvent;
import dont.be.shy.event.EventId;
import dont.be.shy.ui.base.BaseActivity;
import dont.be.shy.ui.home.HomeActivity;
import dont.be.shy.utility.Logger;

public class StartActivity extends BaseActivity {

    private final static String TAG = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TheApp.getIns().addActivity(this);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void finishSelf() {
        super.finishSelf();
        TheApp.getIns().removeActivity(this);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(BaseEvent evt){
        switch (evt.eventId){
            case EventId.SYS_EVENTID_LOAD_MODULES_FINISH:{
                Logger.i(TAG, "SYS_EVENTID_LOAD_MODULES_FINISH");
                startActivity(new Intent(this, HomeActivity.class));
                finishSelf();
            }
            break;
            default:break;
        }
    }
}
