package dont.be.shy.ctrl;

import android.hardware.Camera;

/**
 * Created by zhonglz on 16/8/2.
 */
public class CameraCtrl {
    private final static String TAG = CameraCtrl.class.getSimpleName();

    private Camera mCamera = null;
    private EnmCamMode mMode = EnmCamMode.MODE_NONE;

    private int mPreviewWidth;
    private int mPreviewHeight;

    public enum EnmCamMode{
        MODE_NONE,
        MODE_PREVIEW,
    }
}
