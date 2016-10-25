package sihuan.com.mycookassistant.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * sihuan.com.mycookassistant.net
 * Created by sihuan on 2016/10/25.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
