package com.helloworld.lyz.allezmap.util;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.helloworld.lyz.allezmap.R;

/**
 * Created by paul on 2017/1/15.
 */

public class ItemVisible {
    public static void setvisible(NavigationView navigationView){


//        navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_exit);
        menuItem.setVisible(false);    // true 为显示，false 为隐藏
    }
}
