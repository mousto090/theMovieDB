package ma.example.themoviedb1;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask;
import ma.example.themoviedb1.adapter.GridViewAdapter;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.utility.EndlessScrollListener;

/**
 * A placeholder fragment pour MainActivity.
 */
public class MainFragment extends Fragment implements FetchMovieTask.AsyncDrawerItemSelected{
	private final String TAG = getClass().getSimpleName();
	private GridView mMovieGridView = null;
	private GridViewAdapter mGridViewAdapter = null;
	private LinearLayout layoutProgress = null;
	private final int PAGE = 1;
	public static final String MOVIE_ID = "id";
	public static final String MOVIE_TITLE = "title";
	private CharSequence mActivityTitle="Popular";
	private FetchMovieTask mFetchMovieTaskCallback = null;
	
	public MainFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setHasOptionsMenu(true);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		mMovieGridView = (GridView) rootView.findViewById(R.id.gridView);
		layoutProgress = (LinearLayout) rootView.findViewById(R.id.layout_pgb);
		mGridViewAdapter = new GridViewAdapter(getActivity(), new ArrayList<Movies>());
		mMovieGridView.setAdapter(mGridViewAdapter);
		mMovieGridView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				fetchMovies(page);
			}
		});

		mMovieGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
//				mActivityTitle = getActivity().getTitle();
//				Intent intent = new Intent(getActivity(), DetailActivity.class);
				Movies movie = (Movies) adapter.getItemAtPosition(position);
//				intent.putExtra(MOVIE_ID, movie.getId());
//				DetailActivity.mTitle = movie.getTitle();
//				startActivity(intent);
				startDetailActivityIntent(movie);
			}
		});
		
		layoutProgress.setVisibility(View.VISIBLE);

		return rootView;
	}
	
	private void startDetailActivityIntent(Movies movie){
		mActivityTitle = getActivity().getTitle();
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra(MOVIE_ID, movie.getId());
		DetailActivity.mTitle = movie.getTitle();
		startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "On resume");
		fetchMovies(PAGE);
		MainActivity.mTitle = mActivityTitle;
		
	}
		
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
				
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

//		int id = item.getItemId();
//		if (id == R.id.action_popular) {
//			mGridViewAdapter.clear();
//			new FetchMovieTask().execute(buildPopularUrl(page));
//			return true;
//		} else if (id == R.id.action_top_rated) {
//
//			mGridViewAdapter.clear();
//			new FetchMovieTask().execute(buildTopRatedUrl(page));
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
	
	private void fetchMovies(int page){
		mFetchMovieTaskCallback = new FetchMovieTask();
		mFetchMovieTaskCallback.delegateItemDrawer = this;
		mFetchMovieTaskCallback.execute(
				Utility.buildSelectedNavigationDrawerUrl(page, MainActivity.CURRENT_URL_PATH));
	}

	public GridViewAdapter getmGridViewAdapter() {
		return mGridViewAdapter;
	}
	@Override
	public void onItemDrawerSelected(Movies[] movies) {
		if(null == movies){
			return;
		}
		if(layoutProgress.getVisibility() == View.VISIBLE){
			layoutProgress.setVisibility(View.GONE);
		}
		mGridViewAdapter.add(Arrays.asList(movies));
	}
}
