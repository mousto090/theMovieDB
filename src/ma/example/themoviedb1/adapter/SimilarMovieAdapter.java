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
import ma.example.themoviedb1.model.Movies;

/**
 * Adapter for GridView items
 * @author Diallo
 *
 */
public class SimilarMovieAdapter extends BaseAdapter {

	public static class ViewHolder {
		public final ImageView imagePoster;
		public final TextView titlePoster;

		public ViewHolder(View view) {
			imagePoster = (ImageView) view.findViewById(R.id.poster_image);
			titlePoster = (TextView) view.findViewById(R.id.title_poster);
		}
	}

	private Context mContext;
	private List<Movies> listSimilarMovies;

	public SimilarMovieAdapter(Context context, List<Movies> similarMovies) {
		mContext = context;
		listSimilarMovies = similarMovies;
	}

	/**
	 * 
	 * @param newMovies list of movie to add 
	 * to adapter
	 */
	public void add(List<Movies> newMovies){
		listSimilarMovies.addAll(newMovies);
		notifyDataSetChanged();
	}
	
	public void clear(){
		listSimilarMovies.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return listSimilarMovies.size();
	}

	@Override
	public Movies getItem(int position) {
		return listSimilarMovies.get(position);
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
			convertView = inflater.inflate(R.layout.similar_movie_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imagePoster.setLayoutParams(
				new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		
		Picasso.with(mContext).load(listSimilarMovies.get(position).getPosterName())
				.placeholder(R.drawable.no_poster)
				.error(R.drawable.no_poster)
//				.resize(100, 100)
				.into(viewHolder.imagePoster);
		viewHolder.titlePoster.setText(listSimilarMovies.get(position).getTitle());
		
		return convertView;
	}
	
		

}
