package katherine.codegame;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init tablayout & viewPager
        initViewPager();
        Intent intent = getIntent();
        if(intent!=null){
            int position = intent.getIntExtra("position",0);
            viewPager.setCurrentItem(position);
        }

    }
    private void initViewPager(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //create adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //get string tab name
        String tab_name_1 = getResources().getString(R.string.tab_name_1);
        String tab_name_2 = getResources().getString(R.string.tab_name_2);
        //add fragment to adapter
        adapter.addFragment(new FragmentOne(), tab_name_1);
        adapter.addFragment(new FragmentTwo(), tab_name_2);
        //set adapter to viewpager
        viewPager.setAdapter(adapter);
        //set tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

    }
    /*
       Class  ViewPager Adapter
    */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
