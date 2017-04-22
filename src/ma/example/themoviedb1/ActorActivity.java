package ma.example.themoviedb1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import ma.example.themoviedb1.adapter.ActorTabPagerAdapter;

public class ActorActivity extends ActionBarActivity  implements ActionBar.TabListener, OnPageChangeListener {

	private ViewPager mViewPager;
	private ActionBar mActionBar;
	private ActorTabPagerAdapter mActorTabPagerAdapter;
	private String tabs[] = {"BIOGRAPGHY", "MOVIES"};//tabs title
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actor);
		
		mViewPager = (ViewPager)findViewById(R.id.actor_tabs_pager);
		mActionBar = getSupportActionBar();
		mActorTabPagerAdapter = new ActorTabPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mActorTabPagerAdapter);
		mActionBar.setHomeButtonEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tabName : tabs) {
			Tab tab = mActionBar.newTab();
			tab.setText(tabName);
			tab.setTabListener(this);
			mActionBar.addTab(tab);
		}
		// hides action bar icon and title
		mActionBar.setDisplayShowHomeEnabled(false);  
		mActionBar.setDisplayShowTitleEnabled(false);	
		
//		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99000000")));
		String transparentColor = getResources().getString(R.color.actionbar_bg_transparnent);
		//change tab color
		mActionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(transparentColor)));
		mViewPager.setOnPageChangeListener(this);
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}

	
}
