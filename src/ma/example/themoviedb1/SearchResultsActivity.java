package ma.example.themoviedb1;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask.AsyncDrawerItemSelected;
import ma.example.themoviedb1.adapter.ActorRelatedMovieAdapter;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.utility.EndlessScrollListener;

public class SearchResultsActivity extends ActionBarActivity implements AsyncDrawerItemSelected {
	private final String TAG = getClass().getSimpleName();
	private final int FIRST_PAGE = 1;
	private FetchMovieTask mFetchMovieTaskCallback = null;
	private ActorRelatedMovieAdapter mSearchResultAdapter = null;
	private ListView mListViewSearchResult;
	private String mQuerySearchURL;
	private LinearLayout layoutProgress = null;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		Log.v(TAG, "searchResult");
		layoutProgress = (LinearLayout) findViewById(R.id.layout_pgb);
		mListViewSearchResult = (ListView) findViewById(R.id.listView_search_result_movies);
		mSearchResultAdapter = new ActorRelatedMovieAdapter(SearchResultsActivity.this, new ArrayList<Movies>());
		mListViewSearchResult.setAdapter(mSearchResultAdapter);

		// listener for endless scrolling so we can fetch data
		mListViewSearchResult.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				if (null != mQuerySearchURL) {
					executeSearchQuery(page, mQuerySearchURL);
				}

			}
		});

		// listener for list movies click
		mListViewSearchResult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {

				Movies movie = (Movies) adapter.getItemAtPosition(position);
				startDetailActivityIntent(movie);
			}
		});

//		handleIntent(getIntent());
	}
	
	private void startDetailActivityIntent(Movies movie){
		Intent intent = new Intent(SearchResultsActivity.this, DetailActivity.class);
		intent.putExtra(MainFragment.MOVIE_ID, movie.getId());
		DetailActivity.mTitle = movie.getTitle();
		startActivity(intent);
	}


	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data to display result
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// Use this query to display search results
			getSupportActionBar().setTitle(query);
			mQuerySearchURL = query;
			layoutProgress.setVisibility(View.VISIBLE);
			executeSearchQuery(FIRST_PAGE, query);
		}
	}

	private String buildSearchMoviesUrl(int page, String query) {
		Uri builtUri = Uri.parse(Constantes.BASE_URL_SEARCH);
		builtUri = Uri.withAppendedPath(builtUri, Constantes.MOVIE_PATH).buildUpon()
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY)
				.appendQueryParameter(Constantes.SEARCH_QUERY_PARAM, query)
				.appendQueryParameter(Constantes.PAGE_PARAM, Integer.toString(page)).build();
		return builtUri.toString();
	}

	private void executeSearchQuery(int page, String query) {
		mFetchMovieTaskCallback = new FetchMovieTask();
		mFetchMovieTaskCallback.delegateItemDrawer = this;
		mFetchMovieTaskCallback.execute(buildSearchMoviesUrl(page, query));
	}

	@Override
	public void onItemDrawerSelected(Movies[] movies) {
		layoutProgress.setVisibility(View.GONE);
		if (null == movies) {
			return;
		}
		mSearchResultAdapter.add(Arrays.asList(movies));

	}

}
