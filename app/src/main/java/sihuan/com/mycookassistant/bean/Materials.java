package sihuan.com.mycookassistant.bean;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-24.
 */

public class Materials {
    private String material;//用料

    public Materials(String material, String dosages) {
        this.material = material;
        this.dosages = dosages;
    }



    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    private String dosages;//用量

    public String getDosages() {
        return dosages;
    }

    public void setDosages(String dosages) {
        this.dosages = dosages;
    }

    @Override
    public String toString() {
        return "Materials{" +
                "dosages='" + dosages + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
