package com.helloworld.lyz.allezmap;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.helloworld.lyz.allezmap.util.PreferenceUtil;

import java.util.Locale;
/**
 *
 *
 *
 * Created at 2017/1/11 18:24
 * @Version 1.0
 * @author paul (yangnaihua.2008@163.com)
 * class desc:基础类
 */
public class BaseActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化PreferenceUtil
		PreferenceUtil.init(this);
		//根据上次的语言设置，重新设置语言
	    switchLanguage(PreferenceUtil.getString("language", "zh"));
	}
	
	
	protected void switchLanguage(String language) {
		//设置应用语言类型
		Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
       if (language.equals("en")) {
//            config.locale = Locale.ENGLISH;//本地语言
           config.setLocale(Locale.ENGLISH);
        } else {
           config.setLocale(Locale.SIMPLIFIED_CHINESE);
//        	 config.locale = Locale.SIMPLIFIED_CHINESE;
        }

//resources.overrideConfiguration

        resources.updateConfiguration(config, dm);
//        resources.cr


        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }
}
