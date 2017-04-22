package ma.example.themoviedb1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import ma.example.themoviedb1.ActorInfosFragment;
import ma.example.themoviedb1.ActorRelatedMoviesFragment;

public class ActorTabPagerAdapter extends FragmentPagerAdapter{

	private final String tabTitles[] = {"BIOGRAPHY", "MOVIES"};
	public ActorTabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:
			return new ActorInfosFragment();
	
		case 1 :
			return new ActorRelatedMoviesFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return tabTitles.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Log.v("Position", position+"");
		Log.v("Title", tabTitles[position]);
		return tabTitles[position];
	}
}
