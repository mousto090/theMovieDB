package ma.example.themoviedb1.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ma.example.themoviedb1.R;
import ma.example.themoviedb1.model.Actor;


/**
 * Adapter for cast items
 * @author Diallo
 *
 */
public class ActorAdapter extends BaseAdapter {

	public static class ViewHolder {
		public final ImageView actorPhoto;
		public final TextView actorName;

		public ViewHolder(View view) {
			actorPhoto = (ImageView) view.findViewById(R.id.actor_photo);
			actorName = (TextView) view.findViewById(R.id.actor_name);
		}
	}

	private Context mContext;
	private List<Actor> listActor;

	public ActorAdapter(Context context, List<Actor> actors) {
		mContext = context;
		listActor = actors;
	}

	/**
	 * 
	 * @param newActor list of cast to add 
	 * to adapter
	 */
	public void add(List<Actor> newActor){
		listActor.addAll(newActor);
		notifyDataSetChanged();
	}
	
	public void clear(){
		listActor.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return listActor.size();
	}

	@Override
	public Actor getItem(int position) {
		return listActor.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.cast_movie_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.actorPhoto.setLayoutParams(
				new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		
		Picasso.with(mContext).load(listActor.get(position).getProfilePath())
				.placeholder(R.drawable.no_profile)
				.error(R.drawable.no_profile)
//				.resize(100, 100)
				.into(viewHolder.actorPhoto);
		viewHolder.actorName.setText(listActor.get(position).getName());
		
		return convertView;
	}
	
		

}
