package demo.lqs.com.myselectview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import demo.lqs.com.myselectviewlibrary.MySelectView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPage = (ViewPager) findViewById(R.id.viewpage);
        viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new Fragment();
            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Title" + position + "@" + "sub" + position;
            }
        });


        MySelectView selectView = (MySelectView) findViewById(R.id.selectview);

        selectView.setViewPage(viewPage);
    }
}
