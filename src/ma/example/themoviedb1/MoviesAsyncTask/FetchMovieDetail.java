package ma.example.themoviedb1.MoviesAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.Utility;
import ma.example.themoviedb1.logique.ConnectionHttp;
import ma.example.themoviedb1.model.Actor;
import ma.example.themoviedb1.model.Comments;
import ma.example.themoviedb1.model.Movies;
/**
 * Fetch date for a movie
 * @author Diallo
 *
 */
public class FetchMovieDetail extends AsyncTask<Integer, Void, Movies> {

	private String TAG = getClass().getSimpleName();

	public AsyncMovieDetail delegateMovieData = null;

	public interface AsyncMovieDetail {
		public void onLoadMovieDetailFinish(Movies result);
	}

		
	private String buildDetailMovieUrl(int id) {

		Uri builtUri = Uri.parse(Constantes.BASE_URL_MOVIES);
		builtUri = Uri.withAppendedPath(builtUri, Integer.toString(id)).buildUpon()
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY)
				.appendQueryParameter(Constantes.MOVIE_DETAIL_PARAM, Constantes.MOVIE_DETAIL).build();

		return builtUri.toString();
	}
	

	private Movies getDetailFromJson(String movieJsonStr) throws JSONException {
		// Columns to extract from JSON STRING
		
		//columns movie detail
		final String ID = "id";
		final String TITLE = "original_title";
		final String POSTER_PATH = "poster_path";
		final String RELEASE_DATE = "release_date";
		final String DUREE = "runtime";
		final String RATING = "vote_average";
		final String OVERVIEW = "overview";
		final String VOTE_COUNT = "vote_count";
		
		//columns movie backdrop
		final String IMAGES = "images";
		final String BACKDROPS = "backdrops";
		final String BACKDROP_NAME = "file_path";
		int nb_file = 5; // Number of poster to extract
		
		//columns reviews
		final String RESULT = "results";
		final String REVIEWS = "reviews";
		final String AUTHOR = "author";
		final String COMMENT = "content";
		
		//columns trailer
		final String VIDEOS = "videos";
		final String KEY = "key"; //shortest url of the trailer
		Movies result;

		//colums genres
		final String GENRES = "genres";
		final String NAME = "name";
		
		//columns similar Movies
		final String SIMILAR = "similar";
		//plus ID, TITLE and POSTER_PATH
		//columns cast (actor)
		final String CREDITS = "credits";
		final String CAST = "cast";
//		final String CAST_ID = "cast_id";
		//plus ID
		final String PROFILE_PATH = "profile_path";
		final String CAST_NAME = "name";
		
		JSONObject oneMovie = new JSONObject(movieJsonStr);
		
		//Extract movie detail
		int id = oneMovie.getInt(ID);
		String title = oneMovie.getString(TITLE);
		String posterPath = oneMovie.getString(POSTER_PATH);
		String releaseDate = oneMovie.getString(RELEASE_DATE);
		int duree = oneMovie.getString(DUREE) == "null" ? 0 : oneMovie.getInt(DUREE);
		double rating = oneMovie.getDouble(RATING);
		String overview = oneMovie.getString(OVERVIEW);
		int vote_count = oneMovie.getString(VOTE_COUNT) == "null" ? 0 : oneMovie.getInt(VOTE_COUNT);
		
		//Extract backdrops
		JSONObject imagesObject = oneMovie.getJSONObject(IMAGES);
		JSONArray backdropsArray = imagesObject.getJSONArray(BACKDROPS);
		int nbBackdrops = backdropsArray.length();
		nb_file = nbBackdrops < nb_file? nbBackdrops : nb_file;
		String arrayBackdrops[] = new String[nb_file];
		
		for (int i = 0; i < nb_file; i++) {
			arrayBackdrops[i] = backdropsArray.getJSONObject(i).getString(BACKDROP_NAME);
		}
		
		//Extract reviews
		JSONObject reviewsObject = oneMovie.getJSONObject(REVIEWS);
		JSONArray reviewsArray = reviewsObject.getJSONArray(RESULT);
		int nbReviews = reviewsArray.length();
		Comments comment[] = new Comments[nbReviews];
		for (int i = 0; i < nbReviews; i++) {
			JSONObject reviewObject = reviewsArray.getJSONObject(i);
			String authorName = reviewObject.getString(AUTHOR);
			String review = reviewObject.getString(COMMENT);
			comment[i] = new Comments(authorName, review);
		}
		
		//Extract trailers
		JSONObject videosObject = oneMovie.getJSONObject(VIDEOS);
		JSONArray videosArray = videosObject.getJSONArray(RESULT);
		int nbVideos = videosArray.length();
		String arrayVideos[] = new String[nbVideos]; 
		for (int i = 0; i < nbVideos; i++) {
			arrayVideos[i] = videosArray.getJSONObject(i).getString(KEY);
		}
		
		//Extract genres
		JSONArray genresArray = oneMovie.getJSONArray(GENRES);
		int nbGenres = genresArray.length();
		String arrayGenres[] = new String[nbGenres];
		for (int i = 0; i < nbGenres; i++) {
			arrayGenres[i] = genresArray.getJSONObject(i).getString(NAME);
		}
		
		//Extract cast infos
		JSONObject castInfosObject = oneMovie.getJSONObject(CREDITS);
		JSONArray castArray = castInfosObject.getJSONArray(CAST);
		int nbCast = castArray.length();
		Actor arrayActor[] = new Actor[nbCast];
		for (int i = 0; i < nbCast; i++) {
			JSONObject oneCast = castArray.getJSONObject(i);
			int idCast = oneCast.getInt(ID);
			String name = oneCast.getString(CAST_NAME);
			String profilePath = Utility.builtUrlImage(oneCast.getString(PROFILE_PATH), Constantes.IMAGE_SIZE_W154);
			arrayActor[i] = new Actor(idCast, name, profilePath);
			
		}
		
		//Extract similar movies
		JSONObject similarObject = oneMovie.getJSONObject(SIMILAR);
		JSONArray resultArray = similarObject.getJSONArray(RESULT);
		int nbSimilar = resultArray.length();
		Movies arraySimilarMovie[] = new Movies[nbSimilar];
		for (int i = 0; i < nbSimilar; i++) {
			JSONObject movie = resultArray.getJSONObject(i);
			int  movieId = movie.getInt(ID);
			String movieTitle = movie.getString(TITLE);
			String moviePoster = Utility.builtUrlImage(movie.getString(POSTER_PATH), Constantes.IMAGE_SIZE_W154);
			arraySimilarMovie[i] = new Movies(movieId, movieTitle, moviePoster);
		}
		
		result = new Movies(id, title, Utility.builtUrlImage(posterPath, Constantes.IMAGE_SIZE_W342), releaseDate,
							duree, rating, vote_count, overview, arrayBackdrops, comment, arrayVideos, 
							arrayGenres, arrayActor, arraySimilarMovie);
		return result;
	}

	@Override
	protected Movies doInBackground(Integer... params) {
		if (params.length == 0) {
			return null;
		}

		ConnectionHttp conn = new ConnectionHttp(buildDetailMovieUrl(params[0]));
		String jsonStr = conn.readStream(conn.getConnection());

		try {
			return getDetailFromJson(jsonStr);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {

			conn.disconnect();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Movies result) {
		super.onPostExecute(result);
		delegateMovieData.onLoadMovieDetailFinish(result);
	}

}
