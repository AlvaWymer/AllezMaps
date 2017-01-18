package com.helloworld.lyz.allezmap;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.helloworld.lyz.allezmap.util.CheckEmailTel;

import static com.helloworld.lyz.allezmap.R.id.forget_button_retour;

/**
 * Created by paul on 2017/1/15.
 * 找回密码
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: ForgetActivity
 */
public class ForgetActivity extends BaseActivity implements View.OnClickListener {

    private Button mButtonEntre;
    private Button mButtonRetour;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextTel;

//    private TextInputLayout mTextInputLayoutName;
//    private TextInputLayout mTextInputLayoutEmail;
//    private TextInputLayout mTextInputLayoutTel;

    private Toast toast ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.forget_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.forget_bar_title);

        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，
        actionBar.setDisplayShowHomeEnabled(true);

        //确定按钮
        mButtonEntre = (Button) findViewById(R.id.forget_button_entre);
        mButtonEntre.setOnClickListener(this);
        //重新登录按钮
        mButtonRetour = (Button) findViewById(R.id.forget_button_retour);
        mButtonRetour.setOnClickListener(this);


//        mTextInputLayoutName = (TextInputLayout) findViewById(R.id.forget_text_input_layout_name);
//        mTextInputLayoutEmail = (TextInputLayout) findViewById(R.id.forget_text_input_layout_email);
//        mTextInputLayoutTel = (TextInputLayout) findViewById(R.id.forget_text_input_layout_tel);


        mEditTextName = (EditText) findViewById(R.id.forget_editText_name);
//        mTextInputLayoutName.setErrorEnabled(true);

        mEditTextEmail = (EditText) findViewById(R.id.forget_editText_email);
//        mTextInputLayoutEmail.setErrorEnabled(true);

        mEditTextTel = (EditText) findViewById(R.id.forget_editText_tel);
//        mTextInputLayoutTel.setErrorEnabled(true);



    }

    //返回上一级菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forget_button_entre) {

            CheckEmailTel checkEmailTel = new CheckEmailTel();
            Boolean checkemail = checkEmailTel.checkEmail(mEditTextEmail
                    .getText().toString());

//            Boolean checketel = checkEmailTel.isMobileNO(mEditTextTel
//                    .getText().toString());

            //检查输入的字符串
            if(TextUtils.isEmpty(mEditTextName.getText().toString().trim())){

                //第一个参数是屏占比
                heightToast(9, "checkname");

            }else if(TextUtils.isEmpty(mEditTextEmail.getText().toString().trim())){

                //第一个参数是屏占比
                heightToast(5, "checkemail");
            } else if(TextUtils.isEmpty(mEditTextTel.getText().toString().trim())){
                //第一个参数是屏占比
                heightToast(4, "checktel");


            }
            else if (checkemail == false){
                toast = Toast.makeText(this, R.string.forget_error_email_format, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, getWindowManager().getDefaultDisplay().getHeight() / 4);
                toast.show();
            }


            //当点击确定按钮的时候，发送消息到远程服务器。。。

        }
        if (v.getId() == forget_button_retour) {

            finish();

        }
    }

    //控制显示Toast 在屏幕位置的方法
    //第一个参数，屏占比，第二个参数识别是哪个edittext  需要提示用户输入
    private void heightToast(int number, String checkname) {

        Display display = getWindowManager().getDefaultDisplay();
        // 获取屏幕高度
        int height = display.getHeight();
        if (checkname.equals("checkname")) {

            toast = Toast.makeText(this, R.string.forget_error_name, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, height / number);

        } else
        if(checkname.equals("checkemail")) {
            toast = Toast.makeText(this, R.string.forget_error_email, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, height / number);
        }else {
            toast = Toast.makeText(this, R.string.forget_error_tel, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, height / number);


        }

        toast.show();


    }
}
