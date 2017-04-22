package ma.example.themoviedb1;

import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ma.example.themoviedb1.MoviesAsyncTask.FetchActorInfos;
import ma.example.themoviedb1.MoviesAsyncTask.FetchActorInfos.AsyncActorData;
import ma.example.themoviedb1.adapter.ActorAdapter;
import ma.example.themoviedb1.model.Actor;

public class ActorInfosFragment extends Fragment implements AsyncActorData {

	private final String TAG = getClass().getSimpleName();
	private final int DEFAULT_ACTOR_ID = 0; 
	private FetchActorInfos mFetchActorInfosCallback;
	private ImageView mActorPhoto = null;
	private TextView mActorName = null,
					 mActorBirthday = null,
					 mActorBirthplace = null,
					 mActorBiography = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_actor_infos, container, false);
		mActorPhoto = (ImageView) rootView.findViewById(R.id.actor_photo);
		mActorName = (TextView) rootView.findViewById(R.id.tv_actor_name);
		mActorBirthday = (TextView) rootView.findViewById(R.id.tv_actor_birthday);
		mActorBirthplace = (TextView) rootView.findViewById(R.id.tv_actor_birthplace);
		mActorBiography = (TextView) rootView.findViewById(R.id.tv_actor_biograhy);		
		
		
		Intent intent = getActivity().getIntent();
		if (null != intent && intent.hasExtra(DetailFragment.ID_SELECTED_ACTOR)) {
			int actorID = intent.getIntExtra(DetailFragment.ID_SELECTED_ACTOR, DEFAULT_ACTOR_ID);

			mFetchActorInfosCallback = new FetchActorInfos();
			mFetchActorInfosCallback.delegateActorData = this;
			mFetchActorInfosCallback.execute(actorID);

//			layoutProgress.setVisibility(View.VISIBLE);
		}
		return rootView;
	}
	@Override
	public void onLoadActorDataFinish(Actor result) {
		if(null == result){
			return;
		}
		Log.v(TAG, result.toString());
		//set actor info
		
		Picasso.with(getActivity()).load(result.getProfilePath())
				.error(R.drawable.no_profile)
				.placeholder(R.drawable.no_profile)
				.into(mActorPhoto);
		mActorName.setText(result.getName());
		mActorBirthday.setText(result.getBirthday());
		mActorBirthplace.setText(result.getBirthplace());
		mActorBiography.setText(result.getBiography());
		
	}
}
