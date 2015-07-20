# openFramework-Android-Recording
The files needed to get recording of an ofFbo in openFrameworks via MediaCodec 

These are the various files involved to get real time recording of an ofFbo 
passing the GLUint of the fbo's texture to android sdk MediaCodec.

To use it, with android 0.8.4 android version, you have to:

- add the classes RenderSrfTex, InputSurface, VideoParam and MyRecorder to your project
- change the ofAndroidWindow.java class with the one from this git, or merge
  it manually
- to start the recording put somewhere in your ofActivity :

	ofApp.mGLView.prepareRec(getTexID());
    ofApp.mGLView.startVideo();

  and to stop it:

	ofApp.mGLView.stopVideo();

- add to your ofApp.cpp this jni function:

  JNIEXPORT jint Java_cc_openframeworks_YOURAPPNAME_OFActivity_getTexID(
            JNIEnv * env, jobject obj)
    {
        ofLog() << "SENDING TEXID TO JAVA: " << texID;
        return texID;
    }

- then assign to texID the textureID of the ofFbo you want to record, like:
  texID = fbo.getTextureReference().getTextureData().textureID;


Hope that this can help somebody who needs to record real time clips on Android,
for me understanding all of this was one of the hard times ever X).





TODO 

- get rid of the VideoParam class 



CREDITS 

- long life to OF, and thanks to Arturo Castro that pointed me in studying the way in which OF 
  sends textures to java in ofVideoPlayer.cpp android version

- the classes are from this super useful example: https://github.com/MorihiroSoft/Android_MediaCodecTest

- and finally thanks to fadden (http://www.fadden.com/), the biggest source of MediaCodec related info, 
  for all the great articles and examples
