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
import android.widget.LinearLayout;
import android.widget.TextView;
import ma.example.themoviedb1.R;
import ma.example.themoviedb1.model.Movies;

/**
 * Adapter for GridView items
 * @author Diallo
 *
 */
public class ActorRelatedMovieAdapter extends BaseAdapter {

	public static class ViewHolder {
		public final ImageView moviePoster;
		public final TextView movieTitle;
		public final TextView movieReleaseDate;
		public final TextView movieRate;
		public final TextView movieVoteCount;

		public ViewHolder(View view) {
			moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
			movieTitle = (TextView) view.findViewById(R.id.tv_movie_title);
			movieReleaseDate = (TextView) view.findViewById(R.id.tv_movie_relase_date);
			movieRate = (TextView) view.findViewById(R.id.tv_movie_rate);
			movieVoteCount = (TextView) view.findViewById(R.id.tv_movie_vote_count);
		}
	}

	private Context mContext;
	private List<Movies> listRelatedMovies;

	public ActorRelatedMovieAdapter(Context context, List<Movies> similarMovies) {
		mContext = context;
		listRelatedMovies = similarMovies;
	}

	/**
	 * 
	 * @param newMovies list of movie to add 
	 * to adapter
	 */
	public void add(List<Movies> newMovies){
		listRelatedMovies.addAll(newMovies);
		notifyDataSetChanged();
	}
	
	public void clear(){
		listRelatedMovies.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return listRelatedMovies.size();
	}

	@Override
	public Movies getItem(int position) {
		return listRelatedMovies.get(position);
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
			convertView = inflater.inflate(R.layout.actor_related_movies_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Picasso.with(mContext).load(listRelatedMovies.get(position).getPosterName())
				.placeholder(R.drawable.no_poster)
				.error(R.drawable.no_poster)
				.into(viewHolder.moviePoster);
		Movies movie = listRelatedMovies.get(position);
		String Votecount = mContext.getResources().getString(R.string.format_movie_vote_count, movie.getVoteCount());
		viewHolder.movieTitle.setText(movie.getTitle());
		viewHolder.movieReleaseDate.setText(movie.getReleaseYear());
		viewHolder.movieRate.setText(Double.toString(movie.getRating()));
		viewHolder.movieVoteCount.setText(Votecount);
		return convertView;
	}
	
		

}
