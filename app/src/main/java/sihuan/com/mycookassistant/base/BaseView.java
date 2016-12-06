package sihuan.com.mycookassistant.base;

/******************************************
 * 类名称：BaseView
 * 类描述：
 *

 ******************************************/
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError(String msg);
}
