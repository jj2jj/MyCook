package zjj.com.mycookassistant.bean;

public class MyClassification {

    private String title; // 标题
    private String img; // 图片url
    private int width; // 宽
    private int height; // 高

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MyClassification{" +
                "height=" + height +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", width=" + width +
                '}';
    }
}
