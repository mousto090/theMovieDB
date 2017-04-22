package ma.example.themoviedb1;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.util.Log;


public class Utility {

	/**
	 * construit le path complet de l'image
	 * @param imgName Nom de l'image avec l'extension
	 * @return
	 */
	public static String builtUrlImage(String imgName, String size) {
		Uri builtUri = Uri.parse(Constantes.BASE_URL_IMAGE);
		builtUri = Uri.withAppendedPath(builtUri, size);
		builtUri = Uri.withAppendedPath(builtUri, imgName);

		return builtUri.buildUpon().build().toString();
	}
	/**
	 * 
	 * @param page page number to fetch
	 * @param typePath type of infos to fetch (POPULAR, TOP_RATED, UPCOMING or NOW_PALYING)
	 * @return the full formated url
	 */
	
	public static String buildSelectedNavigationDrawerUrl(int page, String typePath) {
//		Exemple of returne URL
//		http://api.themoviedb.org/3/movie/popular?sort_by=popularity&api_key=***&page=1
		Uri builtUri = Uri.parse(Constantes.BASE_URL_MOVIES);
		builtUri = Uri.withAppendedPath(builtUri, typePath).buildUpon()
				.appendQueryParameter(Constantes.SORT_BY_PARAM, Constantes.SORT_BY_VALUE)
				.appendQueryParameter(Constantes.API_KEY_PARAM, Constantes.KEY)
				.appendQueryParameter(Constantes.PAGE_PARAM, Integer.toString(page)).build();
		return builtUri.toString();
	}
	
}
