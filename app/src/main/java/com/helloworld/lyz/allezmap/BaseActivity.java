package com.helloworld.lyz.allezmap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.lyz.allezmap.util.PreferenceUtil;

import java.util.Locale;

/**
 * Created at 2017/1/11 20:09
 * 国际化基础类
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: BaseActivity  国际化基础类
 *
 */

public class BaseActivity extends AppCompatActivity implements SearchView.OnCloseListener{

    public NavigationView navigationView;
//    private Context mContext;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化PreferenceUtil
		PreferenceUtil.init(this);

//       mContext = this;

		//根据上次的语言设置，重新设置语言
//	    switchLanguage(PreferenceUtil.getString("language", "fr"));

        //设置应用语言类型----根据系统设置
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(Locale.getDefault());




        //保存用户登陆状态
        PreferenceUtil.commitString("userstatus", "1");
        Toast.makeText(this, PreferenceUtil.getString("userstatus","")+"---------", Toast.LENGTH_LONG).show();
	}


    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
//            config.locale = Locale.ENGLISH;//本地语言--已过时
            config.setLocale(Locale.ENGLISH);
        } else if (language.equals("fr")){
            config.setLocale(Locale.FRANCE);
        }else{
            config.setLocale(Locale.SIMPLIFIED_CHINESE);
//        	 config.locale = Locale.SIMPLIFIED_CHINESE;// 已过时


        }


        resources.updateConfiguration(config, dm);


        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    protected void onStart() {
        Toast.makeText(this, "baseactivity=============", Toast.LENGTH_LONG).show();

        //----------------------------------------------------------------------------------
        //全局变量   如果用户已经登陆，那么状态码为1，如果没登录，那么状态码为0，根据用户登陆的状态，来实现
        //程序主页面的权限控制
//        String usersatus=PreferenceUtil.getString("userstatus","");
//        if(usersatus.equals("1")){
//            navigationView = (NavigationView) findViewById(R.id.nav_view);
//            MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_exit);
//            menuItem.setVisible(false);    // true 为显示，false 为隐藏
//            Toast.makeText(this, "baseactivity11111111", Toast.LENGTH_LONG).show();
//
//
//            onFingerprintClick();

//        }

        //----------------------------------------------------------------------------------
        super.onStart();
    }
    public  void onFingerprintClick(final Context mContext){

        FingerprintUtil.callFingerPrint(new FingerprintUtil.OnCallBackListenr() {
            AlertDialog dialog;
            @Override
            public void onSupportFailed() {
                showToast(mContext,"当前设备不支持指纹");
            }

            @Override
            public void onInsecurity() {
                showToast(mContext,"当前设备未处于安全保护中");
            }

            @Override
            public void onEnrollFailed() {
                showToast(mContext,"请到设置中设置指纹");
            }

            @Override
            public void onAuthenticationStart() {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.fingerprint_basic,null);
                initView(view);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeMessages(0);
                        FingerprintUtil.cancel();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                showToast(mContext,errString.toString());
                if (dialog != null  &&dialog.isShowing()){
                    dialog.dismiss();
                    handler.removeMessages(0);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                showToast(mContext,"解锁失败");
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                showToast(mContext,helpString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                showToast(mContext,"解锁成功");
                if (dialog != null  &&dialog.isShowing()){
                    dialog.dismiss();
                    handler.removeMessages(0);
                }

            }
        });
    }
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                int i = postion % 5;
                if (i == 0){
                    tv[4].setBackground(null);
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tv[i-1].setBackground(null);
                }
                postion++;
                handler.sendEmptyMessageDelayed(0,100);
            }
        }
    };
    TextView[] tv = new TextView[5];
    private int postion = 0;
    private void initView(View view) {
        postion = 0;
        tv[0] = (TextView) view.findViewById(R.id.tv_1);
        tv[1] = (TextView) view.findViewById(R.id.tv_2);
        tv[2] = (TextView) view.findViewById(R.id.tv_3);
        tv[3] = (TextView) view.findViewById(R.id.tv_4);
        tv[4] = (TextView) view.findViewById(R.id.tv_5);
        handler.sendEmptyMessageDelayed(0,100);
    }


    public void showToast(Context mContext,String name ){
        Toast.makeText(mContext,name,Toast.LENGTH_SHORT).show();
    }

}
