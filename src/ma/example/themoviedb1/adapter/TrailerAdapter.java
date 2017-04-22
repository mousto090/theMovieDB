package ma.example.themoviedb1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ma.example.themoviedb1.R;

public class TrailerAdapter extends BaseAdapter{
	Context mContext;
	String trailerUrl[];
	
	public static class ViewHolder {
		
		public final TextView tvTrailer;
		
		public ViewHolder(View view) {
			tvTrailer = (TextView) view.findViewById(R.id.tv_trailer);
		}
	}
	public TrailerAdapter(Context context, String[] trailerUrl) {
		mContext = context;
		this.trailerUrl = trailerUrl;
	}
	@Override
	public int getCount() {
		return trailerUrl.length;
	}

	@Override
	public String getItem(int position) {
		return trailerUrl[position];
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
			convertView = inflater.inflate(R.layout.trailer_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String trailerStr = mContext.getString(R.string.format_movie_trailer, position + 1);
		viewHolder.tvTrailer.setText(trailerStr);
		
		return convertView;
	}

}
