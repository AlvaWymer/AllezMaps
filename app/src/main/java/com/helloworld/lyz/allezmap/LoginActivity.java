package com.helloworld.lyz.allezmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created at 2017/1/11 20:11
 *登录页面
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: LoginActivity
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private Button mLoginButton;
    private Button mForgetButton;
    private TextInputLayout mTextInputLayoutName;
    private TextInputLayout mTextInputLayoutPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.login_bar_title);


        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，
        actionBar.setDisplayShowHomeEnabled(true);

        mTextInputLayoutName = (TextInputLayout) findViewById(R.id.Login_text_input_layout_name);
        mTextInputLayoutPswd = (TextInputLayout) findViewById(R.id.Login_text_input_layout_password);

        mEditTextName = (EditText) findViewById(R.id.login_editText_name);
        mTextInputLayoutName.setErrorEnabled(true);
        mEditTextPassword = (EditText) findViewById(R.id.login_editText_password);
        mTextInputLayoutPswd.setErrorEnabled(true);

        mLoginButton = (Button) findViewById(R.id.login_button_login);
        mLoginButton.setOnClickListener(this);

        mForgetButton=(Button)findViewById(R.id.login_button_find);
        mForgetButton.setOnClickListener(this)

        ;
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkName(s.toString().trim(), false);
            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkPswd(s.toString().trim(), false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    //对应的menu目录下面的login.xml文件中的item   选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
//                break;
                return true;
//            case R.id.action_settings:
//                Toast.makeText(this, "点击了查找按钮", Toast.LENGTH_SHORT).show();
//                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button_login) {
            hideKeyBoard();
//            String textnameString=mEditTextName.getText().
            if (!checkName(mEditTextName.getText().toString().trim(), true))
                return;
            if (!checkPswd(mEditTextPassword.getText().toString().trim(), true))
                return;


            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            finish();
        }else if (v.getId() == R.id.login_button_find) {
            Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkPswd(CharSequence pswd, boolean isLogin) {
        if (TextUtils.isEmpty(pswd)) {
            if (isLogin) {
                mTextInputLayoutPswd.setError(" ");
                heightToast(4, "checkpassword");
                return false;
            }
        } else {
            mTextInputLayoutPswd.setError(null);
        }
        return true;
    }

    private boolean checkName(CharSequence name, boolean isLogin) {
        if (TextUtils.isEmpty(name)) {
            if (isLogin) {
                mTextInputLayoutName.setError(" ");
                //第一个参数是屏占比
                heightToast(9, "checkname");

                return false;
            }
        } else {
            mTextInputLayoutName.setError(null);
        }
        return true;
    }

    //控制显示Toast 在屏幕位置的方法
    //第一个参数，屏占比，第二个参数识别是哪个edittext  需要提示用户输入
    private void heightToast(int number, String check) {
        Toast toast ;
        Display display = getWindowManager().getDefaultDisplay();
        // 获取屏幕高度
        int height = display.getHeight();
        if (check.equals("checkname")) {

            toast = Toast.makeText(this, R.string.login_error_name, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, height / number);

        } else  {
            toast = Toast.makeText(this, R.string.login_error_pass, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, height / number);
        }

        toast.show();


    }

    private void hideKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public void doThis(MenuItem item) {
        Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show();
    }
}
