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
public class GridViewAdapter extends BaseAdapter {

	public static class ViewHolder {
		public final ImageView imagePoster;
		public final TextView titlePoster;

		public ViewHolder(View view) {
			imagePoster = (ImageView) view.findViewById(R.id.poster_image);
			titlePoster = (TextView) view.findViewById(R.id.title_poster);
		}
	}

	private Context mContext;
	private List<Movies> listMovies;
	//private RelativeLayout.LayoutParams mImageViewLayoutParams;

	public GridViewAdapter(Context context, List<Movies> listMovies) {
		mContext = context;
		this.listMovies = listMovies;
		//mImageViewLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	/**
	 * 
	 * @param newMovies list of movie to add 
	 * to adapter
	 */
	public void add(List<Movies> newMovies){
		listMovies.addAll(newMovies);
		notifyDataSetChanged();
	}
	
	public void clear(){
		listMovies.clear();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return listMovies.size();
	}

	@Override
	public Movies getItem(int position) {
		return listMovies.get(position);
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
			convertView = inflater.inflate(R.layout.poster_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.imagePoster.setLayoutParams(
				new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		
		Picasso.with(mContext).load(listMovies.get(position).getPosterName())
				.placeholder(R.drawable.no_poster)
				// .resize(185, 278)
				.into(viewHolder.imagePoster);
		viewHolder.titlePoster.setText(listMovies.get(position).getTitle());
		
		return convertView;
	}
	
	// create a new ImageView for each item referenced by the Adapter
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ImageView imageView;
	// if (convertView == null) {
	// // if it's not recycled, initialize some attributes
	// imageView = new ImageView(mContext);
	//
	// imageView.setLayoutParams(new GridView.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	// imageView.setAdjustViewBounds(true);
	//// imageView.setMaxHeight(100);
	//// imageView.setMaxWidth(100);
	//
	// //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	// //imageView.setPadding(8, 8, 8, 8);
	// //imageView.
	// } else {
	// imageView = (ImageView) convertView;
	// }
	// Picasso.with(mContext).load(mImagesUrl.get(position)).into(imageView);;
	// return imageView;
	// }
	

}
