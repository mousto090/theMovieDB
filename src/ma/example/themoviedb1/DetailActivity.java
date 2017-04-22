package ma.example.themoviedb1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

public class DetailActivity extends ActionBarActivity{

	public static CharSequence mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, new DetailFragment())
                    .commit();
        }
        getSupportActionBar().setTitle(mTitle);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		mTitle = title;
	}
	
}
