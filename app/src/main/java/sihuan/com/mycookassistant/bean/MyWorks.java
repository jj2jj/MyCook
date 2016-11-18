package sihuan.com.mycookassistant.bean;

import android.widget.Button;
import android.widget.EditText;

/**
 * MyCook
 * Created by Jessica0906zjj on 2016-11-17.
 */

public class MyWorks {

    private EditText mTitleEdit;
    private  EditText mStepEdit;
    private  EditText mDescribeEdit;
    private  Button mSubmitBtn;

    public EditText getmDescribeEdit() {
        return mDescribeEdit;
    }

    public EditText getmTitleEdit() {
        return mTitleEdit;
    }

    public void setmTitleEdit(EditText mTitleEdit) {
        this.mTitleEdit = mTitleEdit;
    }

    public Button getmSubmitBtn() {

        return mSubmitBtn;
    }

    public void setmSubmitBtn(Button mSubmitBtn) {
        this.mSubmitBtn = mSubmitBtn;
    }

    public EditText getmStepEdit() {

        return mStepEdit;
    }

    public void setmStepEdit(EditText mStepEdit) {
        this.mStepEdit = mStepEdit;
    }

    public void setmDescribeEdit(EditText mDescribeEdit) {
        this.mDescribeEdit = mDescribeEdit;

    }

    @Override
    public String toString() {
        return "MyWorks{" +
                "mDescribeEdit=" + mDescribeEdit +
                ", mTitleEdit=" + mTitleEdit +
                ", mStepEdit=" + mStepEdit +
                ", mSubmitBtn=" + mSubmitBtn +
                '}';
    }
}
