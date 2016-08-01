package dont.be.shy.ui.av;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dont.be.shy.R;
import dont.be.shy.ui.base.BaseActivity;
import dont.be.shy.utility.Logger;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class VideoSettingActivity extends BaseActivity implements SurfaceHolder.Callback {
    private final static String TAG = VideoSettingActivity.class.getSimpleName();

    @BindView(R.id.svVideo)
    SurfaceView mSurfaceview;  // SurfaceView对象：(视图组件)视频显示

    private SurfaceHolder mSurfaceHolder = null;  // SurfaceHolder对象：(抽象接口)SurfaceView支持类
    private Camera mCamera = null;     // Camera对象，相机预览
    private boolean bIfPreview;
    private int mPreviewWidth = 800;
    private int mPreviewHeight = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_setting);

        ButterKnife.bind(this);

        initSurfaceView();
    }

    private void initSurfaceView(){
        mSurfaceHolder = mSurfaceview.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    private void initCamera(){
        Logger.i(TAG, "going into initCamera");
        if (bIfPreview) {
            mCamera.stopPreview();//stopCamera();
        }
        if (null != mCamera) {
            try {
                /* Camera Service settings*/
                Camera.Parameters parameters = mCamera.getParameters();
                // parameters.setFlashMode("off"); // 无闪光灯
                parameters.setPictureFormat(PixelFormat.JPEG); //Sets the image format for picture 设定相片格式为JPEG，默认为NV21
                parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP); //Sets the image format for preview picture，默认为NV21
                /*【ImageFormat】JPEG/NV16(YCrCb format，used for Video)/NV21(YCrCb format，used for Image)/RGB_565/YUY2/YU12*/

                // 【调试】获取caera支持的PictrueSize，看看能否设置？？
                List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();
                List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
                List<Integer> previewFormats = mCamera.getParameters().getSupportedPreviewFormats();
                List<Integer> previewFrameRates = mCamera.getParameters().getSupportedPreviewFrameRates();
                Logger.i(TAG + "initCamera", "cyy support parameters is ");
                Camera.Size psize = null;
                for (int i = 0; i < pictureSizes.size(); i++) {
                    psize = pictureSizes.get(i);
                    Logger.i(TAG + "initCamera", "PictrueSize,width: " + psize.width + " height" + psize.height);
                }
                for (int i = 0; i < previewSizes.size(); i++) {
                    psize = previewSizes.get(i);
                    Logger.i(TAG + "initCamera", "PreviewSize,width: " + psize.width + " height" + psize.height);
                }
                Integer pf = null;
                for (int i = 0; i < previewFormats.size(); i++) {
                    pf = previewFormats.get(i);
                    Logger.i(TAG + "initCamera", "previewformates:" + pf);
                }

                // 设置拍照和预览图片大小
//                parameters.setPictureSize(640, 480); //指定拍照图片的大小
                parameters.setPreviewSize(mPreviewWidth, mPreviewHeight); // 指定preview的大小
                //这两个属性 如果这两个属性设置的和真实手机的不一样时，就会报错

                // 横竖屏镜头自动调整
                if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    parameters.set("orientation", "portrait"); //
                    parameters.set("rotation", 90); // 镜头角度转90度（默认摄像头是横拍）
                    mCamera.setDisplayOrientation(90); // 在2.2以上可以使用
                } else// 如果是横屏
                {
                    parameters.set("orientation", "landscape"); //
                    mCamera.setDisplayOrientation(0); // 在2.2以上可以使用
                }

                /* 视频流编码处理 */
                //添加对视频流处理函数
//                mCamera.setPreviewCallback(new encoderVideo(mCamera.getParameters().getPreviewSize().width,
//                        　　　　mCamera.getParameters().getPreviewSize().height,(ImageView) findViewById(R.id.ImageView2)));//①原生yuv420sp视频存储方式
//                　　mCamera.setPreviewCallback(new encoderH264(mCamera.getParameters().getPreviewSize().width,
//                        　　　　mCamera.getParameters().getPreviewSize().height)); //②x264编码方式
//                　　 mCamera.setPreviewCallback(mJpegPreviewCallback);  //③JPEG压缩方式

                // 设定配置参数并开启预览
                mCamera.setParameters(parameters); // 将Camera.Parameters设定予Camera
                mCamera.startPreview(); // 打开预览画面
                bIfPreview = true;

                // 【调试】设置后的图片大小和预览大小以及帧率
                Camera.Size csize = mCamera.getParameters().getPreviewSize();
                mPreviewHeight = csize.height; //
                mPreviewWidth = csize.width;
                Logger.i(TAG + "initCamera", "after setting, previewSize:width: " + csize.width + " height: " + csize.height);
                csize = mCamera.getParameters().getPictureSize();
                Logger.i(TAG + "initCamera", "after setting, pictruesize:width: " + csize.width + " height: " + csize.height);
                Logger.i(TAG + "initCamera", "after setting, previewformate is " + mCamera.getParameters().getPreviewFormat());
                Logger.i(TAG + "initCamera", "after setting, previewframetate is " + mCamera.getParameters().getPreviewFrameRate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        SuperActivityToast.create(this, "show camera", Style.DURATION_SHORT)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                .setAnimations(Style.ANIMATIONS_SCALE)
                .show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        SuperActivityToast.create(this, "showRationaleForCamera", Style.DURATION_SHORT)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                .setAnimations(Style.ANIMATIONS_SCALE)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        SuperActivityToast.create(this, "showDeniedForCamera", Style.DURATION_SHORT)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                .setAnimations(Style.ANIMATIONS_SCALE)
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        SuperActivityToast.create(this, "showNeverAskForCamera", Style.DURATION_SHORT)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                .setAnimations(Style.ANIMATIONS_SCALE)
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishSelf();
    }

    @Override
    protected void finishSelf() {
        super.finishSelf();
        finish();
    }


    // SurfaceView启动时/初次实例化，预览界面被创建时，该方法被调用。
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        mCamera = Camera.open();// 开启摄像头（2.3版本后支持多摄像头,需传入参数）
        try
        {
            Logger.i(TAG, "SurfaceHolder.Callback：surface Created");
            mCamera.setPreviewDisplay(mSurfaceHolder);//set the surface to be used for live preview

        } catch (Exception ex)
        {
            if(null != mCamera)
            {
                mCamera.release();
                mCamera = null;
            }
            Logger.i(TAG, ex.getMessage());
        }
    }

    // 当SurfaceView/预览界面的格式和大小发生改变时，该方法被调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Logger.i(TAG, "SurfaceHolder.Callback：Surface Changed");
        //mPreviewHeight = height;
        //mPreviewWidth = width;
        initCamera();
    }

    // SurfaceView销毁时，该方法被调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.i(TAG, "SurfaceHolder.Callback：Surface Destroyed");
        if(null != mCamera)
        {
            mCamera.setPreviewCallback(null); //！！这个必须在前，不然退出出错
            mCamera.stopPreview();
            bIfPreview = false;
            mCamera.release();
            mCamera = null;
        }
    }
}
