package ma.example.themoviedb1;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieTask.AsyncDrawerItemSelected;
import ma.example.themoviedb1.adapter.ActorRelatedMovieAdapter;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.utility.EndlessScrollListener;

public class ActorRelatedMoviesFragment extends Fragment implements AsyncDrawerItemSelected{

	private final String TAG = getClass().getSimpleName();
	private ListView mListViewActorRelatedMovies = null;
	private ActorRelatedMovieAdapter mActorRelatedMovieAdapter = null;
	private FetchMovieTask mFetchMovieTaskCallback = null;
	private int idActor = 0;
	private final int PAGE = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_actor_related_movies, container, false);
		mListViewActorRelatedMovies = (ListView) rootView.findViewById(
				R.id.listView_actor_related_movies);
		mActorRelatedMovieAdapter = new ActorRelatedMovieAdapter(getActivity(),
				new ArrayList<Movies>());
		mListViewActorRelatedMovies.setAdapter(mActorRelatedMovieAdapter);
		Intent intent = getActivity().getIntent();
		if (null != intent && intent.hasExtra(DetailFragment.ID_SELECTED_ACTOR)) {
			idActor = intent.getIntExtra(DetailFragment.ID_SELECTED_ACTOR, 0);
			fetchActorRelatedMovies(PAGE, idActor);
			
		}
		// listener for endless scrolling so we can fetch data
		mListViewActorRelatedMovies.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				fetchActorRelatedMovies(page, idActor);
				
			}
		});
		
		//listener for list item click
		mListViewActorRelatedMovies.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
				
				Movies movie = (Movies) adapter.getItemAtPosition(position);
				startDetailActivityIntent(movie);
			}
		});
		return rootView;
	}
	
	private void startDetailActivityIntent(Movies movie){
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra(MainFragment.MOVIE_ID, movie.getId());
		DetailActivity.mTitle = movie.getTitle();
		startActivity(intent);
	}
	
	/**
	 * 
	 * @param page current page to fetch
	 * @param idActor id of actor to fetch related movies
	 * @return full formated URL
	 */
	private String buildActorRelatedMoviesUrl(int page, int idActor){
		Uri builtUri = Uri.parse(Constantes.BASE_URL_API);
		builtUri = Uri.withAppendedPath(builtUri, Constantes.DISCOVER_PATH);
		builtUri = Uri.withAppendedPath(builtUri, Constantes.MOVIE_PATH).buildUpon()
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY)
				.appendQueryParameter(Constantes.WITH_ACTOR_PARAM, Integer.toString(idActor))
				.appendQueryParameter(Constantes.PAGE_PARAM, Integer.toString(page)).build();
		return builtUri.toString();
	}
	
	private void fetchActorRelatedMovies(int page, int actorId){
		mFetchMovieTaskCallback = new FetchMovieTask();
		mFetchMovieTaskCallback.delegateItemDrawer = this;
		mFetchMovieTaskCallback.execute(buildActorRelatedMoviesUrl(page, actorId));
	}
	@Override
	public void onItemDrawerSelected(Movies[] movies) {
		if(null == movies){
			return;
		}
		mActorRelatedMovieAdapter.add(Arrays.asList(movies));
	}
}
