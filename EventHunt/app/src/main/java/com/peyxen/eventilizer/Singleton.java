package com.peyxen.eventilizer;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Pci on 10/9/2015.
 */
public class Singleton {
    RequestQueue mRequestQueue;

    ImageLoader imageLoader;
    private static Singleton mInstance = null;

    public Singleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
