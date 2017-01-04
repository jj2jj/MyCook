package zjj.com.mycookassistant.bean;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AVUtils;

import java.util.List;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-17.
 */
@AVClassName("Works")

public class Works extends AVObject{
    private String dishestype;

    public String getDishestype() {
        return getString("dishestype");
    }

    public void setDishestype(String dishestype) {
        put("dishestype", dishestype);
    }

    private String title;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    private List<Materials> materials;

    public List getMaterials() {
        return getList("materials");
    }

    public void setMaterials(List<Materials> materials) {
        put("materials",materials);
    }

    private List<Steps> steps;

    public List getSteps() {
        return getList("steps");
    }
    public void setSteps(List<Steps> steps) {
        put("steps",steps);
    }

    private AVUser user;
    public AVUser getUser() {
        return getAVUser("owner");
    }

    public void setUser(AVUser user) {
        put("owner",user);
    }

    private String className;

    private String describe     ;

    public String getDescribe() {
        return getString("describe");
    }

    public void setDescribe(String describe) {
        put("describe", describe);
    }

    private AVFile image;

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
