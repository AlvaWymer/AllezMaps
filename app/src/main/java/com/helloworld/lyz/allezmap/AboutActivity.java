package com.helloworld.lyz.allezmap;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

public class AboutActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        webView = (WebView) findViewById(R.id.about_webview);
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.about_title);

        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，
        actionBar.setDisplayShowHomeEnabled(true);

        addlangage();
    }

    //根据当前的语言环境加载不同的url页面
    private void addlangage() {
// 根据语言加载不同的页面，实现多语言适配
        if (getResources().getConfiguration().locale.getLanguage().equals("zh")) {
            webView.loadUrl("file:////android_asset/index_zh.html");
        } else {
            webView.loadUrl("file:////android_asset/index_fr.html");
        }
    }


    //对应的menu目录下面的login.xml文件中的item   选择事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
