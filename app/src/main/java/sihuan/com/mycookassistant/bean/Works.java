package sihuan.com.mycookassistant.bean;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-17.
 */
@AVClassName("Works")
public class Works extends AVObject{
    private String title;
    private String step;
    private String description;
    private AVUser user;
    private AVFile image;
    private String className;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public String getStep() {
        return getString("step");
    }

    public void setStep(String step) {
       put("step",step);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description",description);
    }

    public AVUser getUser() {
        return getAVUser("owner");
    }

    public void setUser(AVUser user) {
        put("owner",user);
    }

    public AVFile getImage() {
        return getAVFile("image");
    }

    public void setImage(AVFile image) {
        put("image",image);
    }

    public Works() {
        super();
    }

    public Works(String theClassName) {
        super();
        AVUtils.checkClassName(theClassName);
        this.className = theClassName;
    }
    public Works(Parcel in){
        super(in);
    }
    public static final Creator CREATOR = AVObjectCreator.instance;


}
