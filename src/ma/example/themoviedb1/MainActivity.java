package ma.example.themoviedb1;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask;
import ma.example.themoviedb1.adapter.GridViewAdapter;
import ma.example.themoviedb1.adapter.NavDrawerListAdapter;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.model.NavDrawerItem;

public class MainActivity extends ActionBarActivity implements FetchMovieTask.AsyncDrawerItemSelected{

	private final String TAG = getClass().getSimpleName();
	private DrawerLayout mDrawerLayout;
	private ListView mNavDrawerListView;
	String navDrawerTitles[];
	private NavDrawerListAdapter mNavDrawerListAdapter;
	private ActionBarDrawerToggle mDrawerToggle;
	public static CharSequence mTitle = "Popular";
	private GridViewAdapter mGridViewAdapter = null;
	private LinearLayout layoutProgress = null;
	public static String CURRENT_URL_PATH = Constantes.POPULAR_PATH;
	private FetchMovieTask mFetchMovieTaskCallback = null;
	
	private final int PAGE = 1;
	private final int POPULAR = 0;
	private final int TOP_RATED = 1;
	private final int UPCOMING = 2;
	private final int NOW_PLAYING = 3;
	private final int FAVORITES = 4;
	private final int WATCHLIST = 5;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new MainFragment()).commit();
		}
		setTitle(mTitle);
		
//		mGridViewAdapter = new GridViewAdapter(getApplicationContext(), new ArrayList<Movies>());
		mNavDrawerListView = (ListView) findViewById(R.id.nav_drawer_listview);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		navDrawerTitles = getResources().getStringArray(R.array.nav_drawer_titles);
		TypedArray navDrawerIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < navDrawerTitles.length; i++) {
			NavDrawerItem Item = new NavDrawerItem(navDrawerIcons.getResourceId(i, -1), navDrawerTitles[i]);
			navDrawerItems.add(Item);
		}
		mNavDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		// Recycle the typed array
		navDrawerIcons.recycle();
		mNavDrawerListView.setAdapter(mNavDrawerListAdapter);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.string.app_name,
				R.string.app_name) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				 getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				 getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// mDrawerLayout.openDrawer(Gravity.LEFT);
		Log.v("Nav adapter ", mNavDrawerListAdapter.getCount() + "");
		// enabling action bar app icon and behaving it as toggle button
		ActionBar actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_menu);
		actionBar.setDisplayShowHomeEnabled(true);

		mNavDrawerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
				// update selected item and title, then close the drawer
				mNavDrawerListView.setItemChecked(position, true);
				mNavDrawerListView.setSelection(position);

				setTitle(navDrawerTitles[position]);
				mDrawerLayout.closeDrawer(mNavDrawerListView);
				fetchData(position);

			}
		});

	}

	
	private void fetchData(int position){
		MainFragment mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.frame_container);
		layoutProgress = (LinearLayout) mainFragment.getView().findViewById(R.id.layout_pgb);
		
		mGridViewAdapter = mainFragment.getmGridViewAdapter();
		mGridViewAdapter.clear();
		layoutProgress.setVisibility(View.VISIBLE);
		mFetchMovieTaskCallback = new FetchMovieTask();
		mFetchMovieTaskCallback.delegateItemDrawer = this;
		
		switch (position) {
		case POPULAR :
			 CURRENT_URL_PATH = Constantes.POPULAR_PATH;
			 mFetchMovieTaskCallback.execute(
					 Utility.buildSelectedNavigationDrawerUrl(PAGE, Constantes.POPULAR_PATH));
			break;

		case TOP_RATED :
			CURRENT_URL_PATH = Constantes.TOP_RATED_PATH;
			mFetchMovieTaskCallback.execute(
					Utility.buildSelectedNavigationDrawerUrl(PAGE, Constantes.TOP_RATED_PATH));
			break;
			
		case UPCOMING :
			CURRENT_URL_PATH = Constantes.UPCOMING_PATH;
			mFetchMovieTaskCallback.execute(
					Utility.buildSelectedNavigationDrawerUrl(PAGE, Constantes.UPCOMING_PATH));
			break;
			
		case NOW_PLAYING :
			CURRENT_URL_PATH = Constantes.NOW_PLAYING_PATH;
			mFetchMovieTaskCallback.execute(
					Utility.buildSelectedNavigationDrawerUrl(PAGE, Constantes.NOW_PLAYING_PATH));
			break;
			
		case FAVORITES :
			break;
			
		case WATCHLIST :
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);	    
	    
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
//        searchView.setOnQueryTextListener(new OnQueryTextListener() {
//			
//			@Override
//			public boolean onQueryTextSubmit(String query) {
//				getSupportActionBar().setTitle(query);
//				return false;
//			}
//			
//			@Override
//			public boolean onQueryTextChange(String newText) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
        return super.onCreateOptionsMenu(menu);	   
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// else if(id == R.id.action_top_rated){
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		super.setTitle(title);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onItemDrawerSelected(Movies[] movies) {

		mGridViewAdapter.add(Arrays.asList(movies));
		Log.v(TAG, mGridViewAdapter.getCount()+"");
		layoutProgress.setVisibility(View.GONE);
				
	}

}
