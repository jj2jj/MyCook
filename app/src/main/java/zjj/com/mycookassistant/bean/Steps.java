package zjj.com.mycookassistant.bean;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-28.
 */

public class Steps{
    private String steps;

    public Steps(String steps){
        this.steps = steps;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }


    @Override
    public String toString() {
        return "Steps{" +
                "steps='" + steps + '\'' +
                '}';
    }

}
