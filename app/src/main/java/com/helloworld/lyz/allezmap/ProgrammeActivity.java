package com.helloworld.lyz.allezmap;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloworld.lyz.allezmap.util.CheckNetUtil;
import com.helloworld.lyz.allezmap.util.PreferenceUtil;
import com.helloworld.lyz.allezmap.util.SendMailUtil;
import com.helloworld.lyz.allezmap.util.ShareUtil;

import butterknife.ButterKnife;

/**
 * Created at 2017/1/11 20:24
 *   0  是没登陆
 *   1  已经登陆
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: ProgrammeActivity   程序主页面
 */

public class ProgrammeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mImageView;
    private Dialog mDialog;
    private long mExitTime;

    private MenuItem menuItem;
    private NavigationView navigationView;

    private boolean isNight;
    private SharedPreferences sp;


    // 双击返回按钮 退出应用的时间
    private static final long SIGNOUT_DELAY_MILLIS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //用来判断日间夜间模式
        sp = getSharedPreferences("loonggg", this.MODE_PRIVATE);


        setContentView(R.layout.activity_programme);

        init();
        //----------------------------------------------------------------------------------
//        //保存用户登陆状态
//        PreferenceUtil.commitString("userstatus", "1");
//        Toast.makeText(ProgrammeActivity.this, PreferenceUtil.getString("userstatus","")+"---------", Toast.LENGTH_LONG).show();
        //----------------------------------------------------------------------------------
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.program_activity_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //取消navigation的bar滑动效果
        disableNavigationViewScrollbars(navigationView);


        navigationView.setNavigationItemSelectedListener(this);

        mImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivAvatar);
        // mImageView.setOnClickListener(this);

        /**
         * 头像监听
         */
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgrammeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.programme, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {








        int id = item.getItemId();
        String string = null;
        switch (id) {
            case R.id.nav_me:
                string = "我";
                break;
            case R.id.nav_about:
                string = "关于";
                Intent intent = new Intent(ProgrammeActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_langage:
                string = "语言";

                langage_set();
                break;
            case R.id.nav_share:
                string = "一键分享";
                share();

                break;
            case R.id.nav_message:
                string = "私信";
                break;
            case R.id.nav_night:
                string = "夜间模式";

                //夜间模式
                changetheme();
                break;
            case R.id.nav_exit:
                string = "退出";
                //设置item隐藏
//                ItemVisible.setvisible(navigationView);


//                navigationView = (NavigationView) findViewById(R.id.nav_view);
//                MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_exit);
//                menuItem.setVisible(false);    // true 为显示，false 为隐藏

                break;
            case R.id.nav_setting:
                string = "设置";
                break;
            case R.id.nav_suggestion:
                string = "意见反馈";
                //启动邮件客户端
                sendmail();
                break;
            case R.id.nav_theme:
                string = "主题风格";
//                zhuti(string);
                break;
        }
        if (!TextUtils.isEmpty(string)) {
            Toast.makeText(ProgrammeActivity.this, "你点击了" + string, Toast.LENGTH_LONG).show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.program_activity_drawer_layout);
        if (string.equals("夜间模式")) {
            //醉了--------------
        } else {

            drawer.closeDrawer(GravityCompat.START);//控制navigation view 的显示与否的
        }

        //根据当前模式显示不同的  日间模式或者夜间模式。失败
//        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_night);
//        menuItem.setTitle("======");
        return true;

    }

    public boolean changetheme() {
        isNight = sp.getBoolean("night", false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            sp.edit().putBoolean("night", false).commit();


        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            sp.edit().putBoolean("night", true).commit();
        }
        recreate();

        return true;
    }

    //分享
    private void share() {
        //       注意！！:首先我们知道setText接收的是字符串类型，但它也支持资源索引方式赋值。所以我们直接用索引的方式也是可以显示字符串的。
//
//                当你在索引的后面加上其他字符串或别的值的时候，索引就会被setText解读为一串数字。
//
//                这时我们可以通过这个方法将字符串的资源索引转为String类型的字符串，这样再在后面加东西，就不会被setText解读为数字。
//
//                getResources().getString(R.string.show);
//                1
//                1
//                所以刚才的代码应该修改为：
//
//                textview.setText（getResources().getString(R.string.show) + "Hello"）;
//                1
//                1
//                这样两个字符串都能正常显示了。

        String share_select_title = getResources().getString(R.string.share_select_title);
        String share_title = getResources().getString(R.string.share_title);
        String share_content = getResources().getString(R.string.share_content);

        ShareUtil.share(this, share_select_title, share_title, share_content);
    }

    //发送邮件
    private void sendmail() {

        String texttitle = getResources().getString(R.string.send_text_title);
        String body = getResources().getString(R.string.send_text_text);
        String clienttitle = getResources().getString(R.string.send_client_title);

        SendMailUtil.sendmail(this,texttitle,body,clienttitle);

    }

    public void langage_set() {
        Toast.makeText(ProgrammeActivity.this, "语言dialog", Toast.LENGTH_LONG).show();
        if (mDialog == null) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.dialog_select_lanuage, null);

            TextView english = (TextView) layout.findViewById(R.id.dialog_select_english);
            TextView chinese = (TextView) layout.findViewById(R.id.dialog_select_chinese);
            TextView french = (TextView) layout.findViewById(R.id.dialog_select_french);

            mDialog = new Dialog(ProgrammeActivity.this, R.style.Custom_Dialog_Theme);
            mDialog.setCanceledOnTouchOutside(false);


            english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了english", Toast.LENGTH_LONG).show();
                    switchLanguage("en");

                    draw();
                }
            });
            chinese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了中文", Toast.LENGTH_LONG).show();
                    switchLanguage("zh");

                    draw();
                }
            });
            french.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProgrammeActivity.this, "你点击了法语", Toast.LENGTH_LONG).show();
                    switchLanguage("fr");
                    draw();
                }
            });

            mDialog.setContentView(layout);
        }
        mDialog.show();


    }

    //当选择语言之后，重新绘制activity
    public void draw() {
        //更新语言后，destroy当前页面，重新绘制
        finish();
        Intent intent = new Intent(ProgrammeActivity.this, ProgrammeActivity.class);
        startActivity(intent);
    }


//    public void zhuti(String string) {
//        Toast.makeText(ProgrammeActivity.this, "你点击了--方法" + string, Toast.LENGTH_LONG).show();
//
//    }

    //首次启动时  检测网络状态
    @Override
    protected void onStart() {
        CheckNetUtil.NetWorkStatus(ProgrammeActivity.this);

        //----------------------------------------------------------------------------------
        //全局变量   如果用户已经登陆，那么状态码为1，如果没登录，那么状态码为0，根据用户登陆的状态，来实现
        //程序主页面的权限控制
        String usersatus= PreferenceUtil.getString("userstatus","");
        if(usersatus.equals("1")){
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_exit);
            menuItem.setVisible(false);    // true 为显示，false 为隐藏

//            BaseActivity bas=new BaseActivity();
//            bas.onFingerprintClick(ProgrammeActivity.this);
        }

        //----------------------------------------------------------------------------------


//        if(usersatus.equals("1")){
//            Toast.makeText(this, "状态为1", Toast.LENGTH_SHORT).show();//不能删除
//        }
        super.onStart();
    }



    //取消navigation的bar滑动效果
    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    // 实现了再按一次退出功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > SIGNOUT_DELAY_MILLIS) {
                Object mHelperUtils;
                Toast.makeText(this, R.string.programm_many_times_close, Toast.LENGTH_SHORT).show();//不能删除
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.Button03:
//                Intent intent = new Intent(mainActivity.this, fristActivity.class);
//                intent.putExtra("data", "mainActivity");
//                startActivity(intent);
//                break;
//            case R.id.Button02:
//                Intent intent = new Intent(mainActivity.this, loginActivity.class);
//                intent.putExtra("data", "mainActivity");
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//        if(v.getId()==R.id.ivAvatar){
//            Intent intent = new Intent(this,Login2Activity.class);
//            startActivity(intent);
//        }
    }


//    @OnClick(R.id.snackbar)
//    void goToSnackbar(){
//        startActivity(new Intent(this,SnackBarActivity.class));
//    }
}
