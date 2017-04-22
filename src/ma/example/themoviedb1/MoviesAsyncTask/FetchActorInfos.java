package ma.example.themoviedb1.MoviesAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.Utility;
import ma.example.themoviedb1.logique.ConnectionHttp;
import ma.example.themoviedb1.model.Actor;
import ma.example.themoviedb1.model.Movies;
/**
 * Fetch date for a movie
 * @author Diallo
 *
 */
public class FetchActorInfos extends AsyncTask<Integer, Void, Actor> {

	private String TAG = getClass().getSimpleName();

	public AsyncActorData delegateActorData = null;

	public interface AsyncActorData {
		public void onLoadActorDataFinish(Actor result);
	}

	private String buildActorUrl(int id) {

		Uri builtUri = Uri.parse(Constantes.BASE_URL_PERSON_INFOS);
		builtUri = Uri.withAppendedPath(builtUri, Integer.toString(id)).buildUpon()
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY).build();

		return builtUri.toString();
	}

	private Actor getDataFromJson(String actorJsonString) throws JSONException {
		// colonnes a extraire
		final String ID = "id";
		final String NAME = "name";
		final String BIRTHDAY = "birthday";
		final String BIRTHPLACE = "place_of_birth";
		final String PROFILE_PATH = "profile_path";
		final String BIOGRAPGHY = "biography";
		Actor result;

		JSONObject actor = new JSONObject(actorJsonString);

		int id = actor.getInt(ID);
		String name = actor.getString(NAME);
		String birthday = actor.getString(BIRTHDAY);
		String birthplace = actor.getString(BIRTHPLACE);
		String profilePath = actor.getString(PROFILE_PATH);
		String biography = actor.getString(BIOGRAPGHY);

		
		result = new Actor(id, name, Utility.builtUrlImage(profilePath, Constantes.IMAGE_SIZE_W342), 
				birthday, birthplace, biography);
		return result;
	}

	@Override
	protected Actor doInBackground(Integer... params) {
		if (params.length == 0) {
			return null;
		}

		ConnectionHttp conn = new ConnectionHttp(buildActorUrl(params[0]));
		String jsonStr = conn.readStream(conn.getConnection());

		try {
			return getDataFromJson(jsonStr);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {

			conn.disconnect();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Actor result) {
		super.onPostExecute(result);
		delegateActorData.onLoadActorDataFinish(result);
	}

}
