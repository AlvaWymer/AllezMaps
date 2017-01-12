package com.helloworld.lyz.allezmap;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;

import com.helloworld.lyz.allezmap.util.PreferenceUtil;

import java.util.Locale;
/**
 * Created at 2017/1/11 20:09
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: BaseActivity  国际化基础类
 *
 */

public class BaseActivity extends AppCompatActivity implements SearchView.OnCloseListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化PreferenceUtil
		PreferenceUtil.init(this);

		//根据上次的语言设置，重新设置语言
//	    switchLanguage(PreferenceUtil.getString("language", "fr"));

        //设置应用语言类型----根据系统设置
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(Locale.getDefault());
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
}
