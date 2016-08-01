package dont.be.shy.ui.home;

import android.os.Bundle;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import butterknife.OnClick;
import dont.be.shy.R;
import dont.be.shy.TheApp;
import dont.be.shy.ui.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    private final static String TAG = HomeActivity.class.getSimpleName();
    private final static long DOUBLE_CLICK_BACK_DURATION = 600;//毫秒

    private long mFirstClickBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TheApp.getIns().addActivity(this);
        setContentView(R.layout.activity_home);
    }

    @OnClick({R.id.live})
    void onClickLive(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        long curTime = System.currentTimeMillis();

        if (curTime - mFirstClickBackTime > DOUBLE_CLICK_BACK_DURATION){
            mFirstClickBackTime = curTime;
            SuperActivityToast.create(this, getString(R.string.home_exit), Style.DURATION_SHORT)
                    .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                    .setAnimations(Style.ANIMATIONS_POP)
                    .show();
        }else {
            finishSelf();
        }
    }

    @Override
    protected void finishSelf() {
        super.finishSelf();

        TheApp.getIns().removeActivity(this);
        finish();

        TheApp.getIns().exit();
    }
}
