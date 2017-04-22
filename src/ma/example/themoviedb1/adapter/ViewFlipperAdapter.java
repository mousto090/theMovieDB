package ma.example.themoviedb1.adapter;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ma.example.themoviedb1.Constantes;
import ma.example.themoviedb1.R;
import ma.example.themoviedb1.Utility;
import ma.example.themoviedb1.model.Movies;

public class ViewFlipperAdapter extends BaseAdapter{
	Movies movie;
	Context mContext;
	
	public static class ViewHolder {
		
		public final ImageView backdropImage;
		
		
		public ViewHolder(View view) {
			backdropImage = (ImageView) view.findViewById(R.id.backdrop_image);
			
		}
	}
	public ViewFlipperAdapter(Context context, Movies movie) {
		mContext = context;
		this.movie = movie;
	}
	@Override
	public int getCount() {
		return movie.getBackdrops().length;
	}

	@Override
	public String getItem(int position) {
		String backdrops[] = movie.getBackdrops();
		return backdrops[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.backdrops_flipper, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String backdrops[] = movie.getBackdrops();
		Picasso.with(mContext).load(Utility.builtUrlImage(backdrops[position], Constantes.IMAGE_SIZE_W342))
			.placeholder(R.drawable.no_poster).into(viewHolder.backdropImage);
		
		return convertView;
	}

}
