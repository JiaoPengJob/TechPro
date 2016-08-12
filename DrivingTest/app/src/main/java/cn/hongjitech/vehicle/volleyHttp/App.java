package cn.hongjitech.vehicle.volleyHttp;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bugtags.library.Bugtags;

/**
 * 创建初始Application
 */
public class App extends Application {

    public static final String TAG = "App";
    public RequestQueue mRequestQueue;//请求队列
    private ImageLoader mImageLoader;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        try {
            //初始化BugTags
            Bugtags.start("d2c8767593a09875001bd3eb99eb844e", this, Bugtags.BTGInvocationEventNone);
        } catch (Exception e) {
            Log.e("BugTags", e.toString());
        }
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new MyImageCache());
        }
        return this.mImageLoader;
    }

    public <T> void addRequest(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);

    }

    public <T> void addRequest(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
