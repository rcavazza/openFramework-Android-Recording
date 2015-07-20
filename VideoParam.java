/*
 * Copyright (C) 2013 MorihiroSoft
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.openframeworks;

import android.graphics.ImageFormat;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Environment;
import android.util.Log;

public class VideoParam {
    private static final boolean DEBUG = true;
    private static final String TAG = "VideoParam";

    //---------------------------------------------------------------------
    // CONSTANTS
    //---------------------------------------------------------------------
    private static final int    VIDEO_W = 800;
    private static final int    VIDEO_H = 804;
    private static final int    FPS     = 30;
    private static final String MIME    = "video/avc";
    private static final int    BPS     = 4*1024*1024;
    private static final int    IFI     = 5;
    private static final String SDCARD  = Environment.getExternalStorageDirectory().getPath();
    private static final String OUTPUT  = SDCARD + "/video.mp4";

    //---------------------------------------------------------------------
    // MEMBERS
    //---------------------------------------------------------------------
//    public final int     mCameraId;
//    public final boolean mFacingFront;
//    public final Size    mSize;
//    public final int[]   mFpsRange;
    public final String  mMime;
    public final int     mBps    = BPS;
    public final int     mIfi    = IFI;
    public final String  mOutput = OUTPUT;

    //---------------------------------------------------------------------
    // SINGLETON
    //---------------------------------------------------------------------
    private static volatile VideoParam sInstance = null;
    private static final    Object     sSyncObj  = new Object();

    public static VideoParam getInstance() {
        if (sInstance == null) {
            synchronized (sSyncObj) {
                if (sInstance == null) {
                    sInstance = new VideoParam();
                }
            }
        }
        return sInstance;
    }

    private VideoParam() {
        // Id

        // MIME
        String mime = null;
        int codec_num = MediaCodecList.getCodecCount();
        for (int i=0; i<codec_num; i++) {
            MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
            if (info.isEncoder()) {
                if (DEBUG) Log.d(TAG, "Codec: "+info.getName());
                final String[] mimes = info.getSupportedTypes();
                for (String m : mimes) {
                    if (DEBUG) Log.d(TAG, "MIME: "+m);
                    if (MIME.equals(m)) {
                        mime = m;
                    }
                }
            }
        }
        if (mime == null) {
            throw new UnsupportedOperationException(
                    String.format("Not support MIME: %s",MIME));
        }
        mMime = mime;
        Log.i(TAG, "mMime = "+mMime);
    }

    //---------------------------------------------------------------------
    // PUBLIC METHODS
    //---------------------------------------------------------------------
    public int getMaxFps() {
        return 25;
//        return mFpsRange[Camera.Parameters.PREVIEW_FPS_MAX_INDEX] / 1000;
    }
}
