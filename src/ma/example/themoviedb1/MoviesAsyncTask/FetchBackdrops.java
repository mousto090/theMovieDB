package ma.example.themoviedb1.MoviesAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.logique.ConnectionHttp;
/**
 * Fetch the backdrops(image de fond) urls 
 * @author Diallo
 *
 */
public class FetchBackdrops extends AsyncTask<Integer, Void, String[]> {

	private String TAG = getClass().getSimpleName();

	public AsyncMovieImageUrl delegateMovieImageUrl = null;
	
	public interface AsyncMovieImageUrl {
		public void onLoadImageUrlFinish(String[] result);
	}
	
	private String[] getBackdropsUrlFromJson(String movieJsonStr) throws JSONException {
		// colonnes a extraire
		final String RESULT = "backdrops";
		final String FILE_NAME = "file_path";
		int nb_file = 5; // nombre de poster a prendre

		JSONObject movieObject = new JSONObject(movieJsonStr);
		JSONArray movieNames = movieObject.getJSONArray(RESULT);
		
		int length = movieNames.length();
		nb_file = length < nb_file? length : nb_file;
		String result[] = new String[nb_file];
		for (int i = 0; i < nb_file; i++) {
			result[i] = movieNames.getJSONObject(i).getString(FILE_NAME);
		}

		return result;
	}
	
	
	private String buildMovieIdImageUrl(int id) {

		Uri builtUri = Uri.parse(Constantes.BASE_URL_MOVIES);
		builtUri = Uri.withAppendedPath(builtUri, Integer.toString(id));
		builtUri = Uri.withAppendedPath(builtUri, Constantes.IMAGE_PATH).buildUpon()
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY).build();

		return builtUri.toString();
	}

	@Override
	protected String[] doInBackground(Integer... params) {
		if (params.length == 0) {
			return null;
		}

		ConnectionHttp conn = new ConnectionHttp(buildMovieIdImageUrl(params[0]));
		String jsonStr = conn.readStream(conn.getConnection());

		try {
			return getBackdropsUrlFromJson(jsonStr);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {

			conn.disconnect();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String[] result) {
		super.onPostExecute(result);
		delegateMovieImageUrl.onLoadImageUrlFinish(result);
	}

}