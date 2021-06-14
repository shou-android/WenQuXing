package com.example.wenquxing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wenquxing.ai.Ctable.CtableActivity;
import com.example.wenquxing.ai.ProgressActivity;
import com.google.android.material.navigation.NavigationView;

public class demoActivity extends AppCompatActivity {

    private NavigationView navView;//导航视图
    private DrawerLayout drawerLayout;//滑动菜单
    //private DrawerLayout drawerLayout;//滑动菜单

    public static void show(Context context) {
        context.startActivity(new Intent(context, demoActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Button ReminderTest = findViewById(R.id.ReminderTest);
        Button work = findViewById(R.id.work);
        ReminderTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(demoActivity.this, Reminder.class);
                startActivity(intent);
            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(demoActivity.this,NewEvent.class);
                startActivity(intent);
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //工具栏按钮点击
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        ///-------------
        navView = findViewById(R.id.nav_view);
        //获取头部视图
        View headerView = navView.getHeaderView(0);
        //头像点击
        headerView.findViewById(R.id.iv_avatar).setOnClickListener(v -> showMsg("头像"));
        /*****/
        //导航菜单点击
        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_friend:
                    ProgressActivity.show(this);
                    break;
                case R.id.item_wallet:
                    CtableActivity.show(this);
                    break;
                case R.id.item_location:
                    showMsg("位置");
                    break;
                case R.id.item_phone:
                    showMsg("电话");
                    break;
                case R.id.item_email:
                    showMsg("邮箱");
                    break;
                case R.id.item_share:
                    showMsg("分享");
                    break;
                case R.id.item_send:
                    showMsg("发送");
                    break;
                default:
                    break;
            }
            //关闭滑动菜单
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }
    /**
     * 透明状态栏
     */
    private void transparentStatusBar() {
        //改变状态栏颜色为透明
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    /**
     * Toast提示
     * @param msg 内容
     */
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();



    }

}
