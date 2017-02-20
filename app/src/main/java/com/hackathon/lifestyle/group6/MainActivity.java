package com.hackathon.lifestyle.group6;

import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, PageFragment.OnFragmentInteractionListener, PageFragment.MyListener {

    private ProgressDialog progressDialog;
    private static int CURR_POS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                try {
                    Thread.sleep(1000);
                } catch (Exception e){}
                finally {
                    progressDialog.hide();
                    Toast t = Toast.makeText(getApplicationContext(), "提案しました！", Toast.LENGTH_SHORT);
                    View v = t.getView();
                    v.setBackgroundColor(Color.rgb(76, 175, 80));
                    t.show();
                }
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,getResources().getStringArray(R.array.month));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //spinner.setAdapter(adapter);

        // スピナーのアイテムが選択された時の動作を設定
        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //スピナー内のアイテムが選択された場合の処理をここに記載
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //スピナーでは呼ばれない模様。ただし消せないので「おまじない」として残す。
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // xmlからTabLayoutの取得
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // xmlからViewPagerを取得
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // ページタイトル配列
        final String[] pageTitle = {"t1", "t2", "t3", "t4", "t5"};

        // 表示Pageに必要な項目を設定
        FragmentPagerAdapter fpadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public PageFragment getItem(int position) {
                return PageFragment.newInstance(position + 1);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return null;
                //return pageTitle[position];
            }

            @Override
            public int getCount() {
                return pageTitle.length;
            }
        };

        // ViewPagerにページを設定
        viewPager.setAdapter(fpadapter);
        viewPager.addOnPageChangeListener(this);

        // ViewPagerをTabLayoutを設定
        tabLayout.setupWithViewPager(viewPager);

        // tablayoutにアイコンをセット
        setIcons(tabLayout);

    }

    @Override
    public void onClickButton() {
        //Toast.makeText(getApplicationContext(), "OKボタンがタップされました！", Toast.LENGTH_SHORT).show();
        sendBasicNotification(getApplicationContext());
    }

    @Override
    public void changeColor(View v) {

    }

    /*
        スマートウォッチへ通知を送る
     */
    public void sendBasicNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("新着メッセージがあります")
                .setContentText("18:02 東西線木場駅三鷹行き乗車")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .addAction(R.mipmap.ic_launcher, "返信", pIntent)
                .addAction(R.mipmap.ic_launcher, "転送", pIntent)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notification);
    }

    /*
        タブの部分にアイコンをセット
     */
    public void setIcons(TabLayout tl) {
        tl.getTabAt(0).setIcon(R.drawable.ic_schedule_white_24dp);
        tl.getTabAt(1).setIcon(R.drawable.ic_gavel_white_24dp);
        tl.getTabAt(2).setIcon(R.drawable.ic_search_white_24dp);
        tl.getTabAt(3).setIcon(R.drawable.ic_format_list_numbered_white_24dp);
        tl.getTabAt(4).setIcon(R.drawable.ic_directions_walk_white_24dp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        CURR_POS = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
