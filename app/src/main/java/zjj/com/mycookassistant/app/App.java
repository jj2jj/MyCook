package zjj.com.mycookassistant.app;

import android.app.Activity;
import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashSet;
import java.util.Set;

import im.fir.sdk.FIR;
//import tw.chainsea.com.voice.iflytek.RobotManager;
import zjj.com.mycookassistant.bean.Works;

/**
 * sihuan.com.mycookassistant.App
 * Created by sihuan on 2016/10/25.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;
    public static RefWatcher refWatcher;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
        instance = this;
        refWatcher = LeakCanary.install(this);
        AVObject.registerSubclass(Works.class);

        // 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
//        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5860dce8"+ SpeechConstant.FORCE_LOGIN +"=true");

        AVOSCloud.initialize(this, "OPby9zNsW96iR4YbCHVRLl4g-gzGzoHsz", "vsSTWVYCXsGub6Pnkd0goGTN");
        AVOSCloud.setDebugLogEnabled(true);

//        RobotManager.getInstance().initTTSEngine(getApplicationContext());
    }

    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
