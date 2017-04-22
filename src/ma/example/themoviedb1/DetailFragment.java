package ma.example.themoviedb1;

import java.util.Arrays;
import java.util.List;

import org.lucasr.twowayview.TwoWayView;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieDetail;
import ma.example.themoviedb1.MoviesAsyncTask.FetchMovieDetail.AsyncMovieDetail;
import ma.example.themoviedb1.adapter.ActorAdapter;
import ma.example.themoviedb1.adapter.CommentAdapter;
import ma.example.themoviedb1.adapter.SimilarMovieAdapter;
import ma.example.themoviedb1.adapter.TrailerAdapter;
import ma.example.themoviedb1.adapter.ViewFlipperAdapter;
import ma.example.themoviedb1.logique.DAOMovies;
import ma.example.themoviedb1.model.Actor;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.utility.NestedListView;

public class DetailFragment extends Fragment implements AsyncMovieDetail {

	private final String TAG = getClass().getSimpleName();
	public static final String ID_SELECTED_ACTOR = "id"; 

	private TextView orignialTtitle = null,
					 releaseDate = null,
					 runtime = null,
					 vote = null,
					 synopsis = null,
					 tvTrailer = null,
					 tvComment = null,
					 genres = null;
	private CheckBox mCheckboxFavorite = null,
					 mCheckboxWatchlist = null;
	private ImageView poster = null;
	private AdapterViewFlipper backdropAdapterViewFlipper = null;
	private LinearLayout layoutProgress = null;
	private NestedListView listViewTrailer = null;
	private NestedListView listViewComment = null;
	private TwoWayView listViewCast = null;
	private TwoWayView listViewSimilarMovie = null;
	private ScrollView detailScrollView = null;
	
	private final String TMDb_SHARE_HASHTAG = "#TMDb";
	private String mShareString;
	private ShareActionProvider mShareActionProvider = null;

	// id du film MAD_MAX Fury Road
	private int defaultMovieId = 76341;

	private FetchMovieDetail mFetchMovieDetailCallback = null;
	
	private Movies mSelectedMovie;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

		backdropAdapterViewFlipper = (AdapterViewFlipper) rootView.findViewById(R.id.backdrop_view_flipper);
		poster = (ImageView) rootView.findViewById(R.id.movie_poster);
		orignialTtitle = (TextView) rootView.findViewById(R.id.tv_original_title);
		releaseDate = (TextView) rootView.findViewById(R.id.tv_release_date);
		runtime = (TextView) rootView.findViewById(R.id.tv_runtime);
		vote = (TextView) rootView.findViewById(R.id.tv_rating);
		synopsis = (TextView) rootView.findViewById(R.id.movie_synopsis);
		genres = (TextView) rootView.findViewById(R.id.tv_genres);
		mCheckboxFavorite = (CheckBox) rootView.findViewById(R.id.checkbox_favorite);
		mCheckboxWatchlist = (CheckBox) rootView.findViewById(R.id.checkbox_watchlist);
		tvComment = (TextView) rootView.findViewById(R.id.tv_comment);
		tvTrailer = (TextView) rootView.findViewById(R.id.tv_trailer);
		listViewComment = (NestedListView) rootView.findViewById(R.id.listView_comment);
		listViewTrailer = (NestedListView) rootView.findViewById(R.id.listView_trailer);
		listViewCast = (TwoWayView) rootView.findViewById(R.id.listview_cast_movie);
		listViewSimilarMovie = (TwoWayView) rootView.findViewById(R.id.listview_similar_movie);
		layoutProgress = (LinearLayout) rootView.findViewById(R.id.layout_pgb);
		detailScrollView = (ScrollView) rootView.findViewById(R.id.detail_scrollView);

		Intent intent = getActivity().getIntent();
		if (null != intent && intent.hasExtra(MainFragment.MOVIE_ID)) {
			int movieId = intent.getIntExtra(MainFragment.MOVIE_ID, defaultMovieId);
//			DetailActivity.mTitle = intent.getStringExtra(MainFragment.MOVIE_TITLE);
			mFetchMovieDetailCallback = new FetchMovieDetail();
			mFetchMovieDetailCallback.delegateMovieData = this;
			mFetchMovieDetailCallback.execute(movieId);

			layoutProgress.setVisibility(View.VISIBLE);
		}
		
		
		
		mCheckboxFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				manageFavoriteAndWatchList(view);
				
			}
		});
		
		mCheckboxWatchlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				manageFavoriteAndWatchList(view);
				
			}
		});
				
		return rootView;
	}

	private void manageFavoriteAndWatchList(View view){
		
		String typeList="";
		
		DAOMovies daoMovies = new DAOMovies(getActivity());
		
		switch (view.getId()) {
		case R.id.checkbox_favorite:
			typeList = Constantes.TYPE_FAVORITE;
			break;
		case R.id.checkbox_watchlist :
			typeList = Constantes.TYPE_WATCHLIST;
			break;
		}
		
		if(((CheckBox)view).isChecked()){
			daoMovies.insertMovie(mSelectedMovie, typeList);			
		}
		
		if(!((CheckBox)view).isChecked()){
			daoMovies.delete(mSelectedMovie, typeList);
		}
		daoMovies.closeConnexion();
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.detail_fragment_menu, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);
        
        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(menuItem);
        
     // Attach an intent to this ShareActionProvider.  You can update this at any time,
     // like when the user selects a new piece of data they might like to share.
        
        if(null != mShareActionProvider){
        	mShareActionProvider.setShareIntent(createShareMovieIntent());
        }else {
            Log.d(TAG, "Share Action Provider is null?");
        }
        
	}
	
	private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mShareString + TMDb_SHARE_HASHTAG);
        return shareIntent;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onLoadMovieDetailFinish(Movies result) {
		layoutProgress.setVisibility(View.GONE);
		if (null == result) {
			return;
		}
		// check before trying to get string from resources
		if (null == getActivity()) {
			return;
		}
		// save movie detail for later use when insert favorite or watchlist
		//	to SQLite DATABASE
		mSelectedMovie = result;
		//make URL to share with action share
		String trailerUrl[] = result.getTrailer();
		if(trailerUrl.length > 0){
			//construct youtube url for the trailer
			mShareString = Constantes.BASE_URL_YOUTUBE + trailerUrl[0];
		}else{
			String posterUrl = result.getPosterName();
			if(null != posterUrl){
				//construct image url for the poster
				mShareString = Utility.builtUrlImage(posterUrl, Constantes.IMAGE_SIZE_W342);
			}
		}
		//update share intent with the new string mShareString 
		if (mShareActionProvider != null) { 
             mShareActionProvider.setShareIntent(createShareMovieIntent());
         }
		// Begin setting data on views

		String duree = getResources().getString(R.string.format_movie_length, result.getDuree());
		String rating = getResources().getString(R.string.format_movie_vote, result.getRating());
		
		orignialTtitle.setText(result.getTitle());
		releaseDate.setText(result.getReleaseDate());
		runtime.setText(duree);
		vote.setText(rating);
		synopsis.setText(result.getOverview());
		genres.setText(result.getGenres());
		Picasso.with(getActivity()).load(Utility.builtUrlImage(result.getPosterName(), Constantes.IMAGE_SIZE_W185))
				.fit().centerCrop().error(R.drawable.no_poster).placeholder(R.drawable.no_poster).into(poster);

		
		DAOMovies daoMovies = new DAOMovies(getActivity());
		List<Movies> favoritesMovies =  daoMovies.getListMoviesByType(Constantes.TYPE_FAVORITE);
		List<Movies> watchlistMovies =  daoMovies.getListMoviesByType(Constantes.TYPE_WATCHLIST);
		mCheckboxFavorite.setChecked(result.containsMovie(favoritesMovies));
		mCheckboxWatchlist.setChecked(result.containsMovie(watchlistMovies));
		daoMovies.closeConnexion();
		
		// load backdrops images
		ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter(getActivity(), result);
		backdropAdapterViewFlipper.setAdapter(viewFlipperAdapter);
		backdropAdapterViewFlipper.setFlipInterval(5000);
		backdropAdapterViewFlipper.startFlipping();

		if (viewFlipperAdapter.getCount() == 0) {
			backdropAdapterViewFlipper.setVisibility(View.GONE);
			
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)detailScrollView.getLayoutParams();
			params.setMargins(0, 110, 0, 0);
			detailScrollView.setLayoutParams(params);
		}

		// load movie cast
		ActorAdapter actorAdapter = new ActorAdapter(getActivity(), Arrays.asList(result.getActor()));
		listViewCast.setAdapter(actorAdapter);

		// load similar Movies
		SimilarMovieAdapter similarMovieAdapter = new SimilarMovieAdapter(getActivity(),
				Arrays.asList(result.getSimilarMovies()));
		listViewSimilarMovie.setAdapter(similarMovieAdapter);

		// load trailer to listView
		TrailerAdapter trailerAdapter = new TrailerAdapter(getActivity(), result.getTrailer());
		listViewTrailer.setAdapter(trailerAdapter);

		// load comment to listView
		CommentAdapter commentAdapter = new CommentAdapter(getActivity(), Arrays.asList(result.getReview()));
		listViewComment.setAdapter(commentAdapter);

		// ============= Begin setting listener on views ===============//

		listViewTrailer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
				String trailerUrl = adapter.getItemAtPosition(position).toString();
				startPlayingTrailer(trailerUrl);

			}
		});
		
		
		listViewCast.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {
				Log.v(TAG, adapter.getItemAtPosition(position).toString());
				Actor selectedActor = (Actor)adapter.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(), ActorActivity.class);
				intent.putExtra(ID_SELECTED_ACTOR, selectedActor.getId());
				startActivity(intent);
				
			}
		});

		listViewSimilarMovie.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {

				Movies selectedMovie = (Movies)adapter.getItemAtPosition(position);
				DetailActivity.mTitle = selectedMovie.getTitle();
				Log.v(TAG, selectedMovie.getTitle());
				Log.v(TAG, adapter.getItemAtPosition(position).toString());
				Intent intentMovieDetail = new Intent(getActivity(), DetailActivity.class);
				intentMovieDetail.putExtra(MainFragment.MOVIE_ID, selectedMovie.getId());
				startActivity(intentMovieDetail);
			}
		});

		tvTrailer.setOnClickListener(onClickTextViewCommentTrailer);
		tvComment.setOnClickListener(onClickTextViewCommentTrailer);

		Log.v(TAG, viewFlipperAdapter.getCount() + " adapter ViewFlipper");
		Log.v(TAG, result.toString());
		layoutProgress.setVisibility(View.GONE);
	}

	private void startPlayingTrailer(String trailerUrl) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri youtubeUri = Uri.parse(Constantes.BASE_URL_YOUTUBE + trailerUrl);
		intent.setData(youtubeUri);
		Intent chooserIntent = Intent.createChooser(intent, getActivity().getString(R.string.intent_chooser_text));

		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(chooserIntent);
		}
	}

	/**
	 * Listener for TextViews Comments and Trailers
	 */
	private OnClickListener onClickTextViewCommentTrailer = new OnClickListener() {

		@Override
		public void onClick(View view) {

			NestedListView listView = null;
			switch (view.getId()) {
			case R.id.tv_trailer:
				listView = listViewTrailer;
				break;

			case R.id.tv_comment:
				listView = listViewComment;
				break;
			}
			switch (listView.getVisibility()) {
			case View.VISIBLE:
				listView.setVisibility(View.GONE);
				((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
				break;

			case View.GONE:
				listView.setVisibility(View.VISIBLE);
				((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
				break;
			}
		}
	};

}
