package cc.openframeworks;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.EGL14;
import android.opengl.EGLSurface;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

public class OFGLSurfaceView extends GLSurfaceView {
//public class OFGLSurfaceView { // extends GLSurfaceView {

    public OFEGLConfigChooser configChooser;

    private MyRecorder  mRecorder = null;

    private int myTexID = -1;

    public void prepareRec(int texID) {

        myTexID = texID;
    }

    public void startVideo() {
        if (mRecorder == null) {
            mRecorder = new MyRecorder();
            mRecorder.prepareEncoder();
            mRenderer.setRecorder(mRecorder, myTexID);
        }
    }

    public void stopVideo() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder = null;
            mRenderer.setRecorder(null, 0);
        }
    }

    public OFGLSurfaceView(Context context) {
        super(context);
        mRenderer = new OFAndroidWindow(getWidth(),getHeight(), this);
        getHolder().setFormat( PixelFormat.OPAQUE );

        if(OFEGLConfigChooser.getGLESVersion()==2){
            Log.i("XXX", "HERE");
            setEGLContextClientVersion(2);
        }
        configChooser = new OFEGLConfigChooser(5,6,5,0,16,0);
        //setEGLConfigChooser(configChooser);
        setRenderer(mRenderer);
    }

	public OFGLSurfaceView(Context context,AttributeSet attributes) {
        super(context,attributes);
        mRenderer = new OFAndroidWindow(getWidth(),getHeight(), this);
        getHolder().setFormat( PixelFormat.OPAQUE );
        if(OFEGLConfigChooser.getGLESVersion()==2){
            Log.i("XXX", "THERE");
            setEGLContextClientVersion(2);
        }
//        OFEGLConfigChooser configChooser = new OFEGLConfigChooser(5,6,5,0,16,0);
        configChooser = new OFEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setEGLConfigChooser(configChooser); // XXX TRICK

        setRenderer(mRenderer);

        //xx
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
    	OFAndroid.onSurfaceDestroyed();
	}

    boolean isSetup(){
    	return mRenderer.isSetup();
    }

    public OFAndroidWindow mRenderer;

    public void setSurface(Surface _surf) {
//        getHolder().getSurface() = surf;
//        surf = _surf;
    }

    public void setup() {
//        mRenderer = new OFAndroidWindow(getWidth(),getHeight());
//        surf.setFormat(PixelFormat.OPAQUE);
//
//        if(OFEGLConfigChooser.getGLESVersion()==2){
//            Log.i("XXX", "HERE");
//            setEGLContextClientVersion(2);
//        }
//        OFEGLConfigChooser configChooser = new OFEGLConfigChooser(5,6,5,0,16,0);
//        //setEGLConfigChooser(configChooser);
//        setRenderer(mRenderer);
    }

    public Surface getSurface() {
        return getHolder().getSurface();
    }

    public EGLSurface getEGLSurface() {
        return EGL14.eglGetCurrentSurface(0);
    }
}
