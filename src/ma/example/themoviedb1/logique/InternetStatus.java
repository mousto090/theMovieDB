package ma.example.themoviedb1.logique;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetStatus {
	
		
	public static boolean isConnected(Context context){
		
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);
		if(null != connectivity){
			NetworkInfo[] info = connectivity.getAllNetworkInfo(); 
			if(null != info){
				for (int i = 0; i < info.length; i++) {
					if(info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
}
