package com.helloworld.lyz.allezmap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toast.makeText(LoginActivity.this, "LoginActivity", Toast.LENGTH_LONG).show();

        Button button = (Button) findViewById(R.id.but_login);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDialog == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.dialog_select_lanuage,null);
                    TextView english = (TextView) layout.findViewById(R.id.select_english);
                    TextView chinese = (TextView) layout.findViewById(R.id.select_chinese);
                    mDialog = new Dialog(LoginActivity.this, R.style.Custom_Dialog_Theme);
                    mDialog.setCanceledOnTouchOutside(false);
                    english.setOnClickListener(LoginActivity.this);
                    chinese.setOnClickListener(LoginActivity.this);
                    mDialog.setContentView(layout);
                }
                mDialog.show();
            }
        });
    }



    @Override
    protected void onStart() {
        NetWorkStatus();
        super.onStart();
    }


    // 判断网络连接
    public void NetWorkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.getActiveNetworkInfo();


        boolean netSataus = true;
        if (connectivityManager.getActiveNetworkInfo() != null) {
            netSataus = connectivityManager.getActiveNetworkInfo().isAvailable();
        } else {
            AlertDialog.Builder aler_builder = new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher).setTitle(R.string.login_network_title)
                    .setMessage(R.string.login_network_message);
            aler_builder.setPositiveButton(R.string.login_network_setting, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
					/* Intent mIntent = new Intent("/"); */
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */

                    Intent intent = null;
                    /**
                     * 判断手机系统的版本！如果API大于10 就是3.0+
                     * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                     */
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        intent = new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                    } else {
                        intent = new Intent();
                        ComponentName component = new ComponentName(
                                "com.android.settings",
                                "com.android.settings.WirelessSettings");
                        intent.setComponent(component);
                        intent.setAction("android.intent.action.VIEW");
                    }
                    startActivity(intent);
                }
            }).setNeutralButton(R.string.login_network_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }
    }
    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        switch (v.getId()) {
            case R.id.select_english:
                switchLanguage("en");
                break;
            case R.id.select_chinese:
                switchLanguage("zh");
                break;

            default:
                break;
        }

        //更新语言后，destroy当前页面，重新绘制
        finish();
        Intent it = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(it);
    }

}
