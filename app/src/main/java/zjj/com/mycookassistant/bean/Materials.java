package zjj.com.mycookassistant.bean;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-24.
 */

public class Materials {
    @Override
    public String toString() {
        return "Materials{" +
                "food='" + food + '\'' +
                ", portion='" + portion + '\'' +
                '}';
    }

    public Materials(String food, String portion) {
        this.food = food;
        this.portion = portion;
    }

    private String food;//用料

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    private String portion;//用量

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }


}
