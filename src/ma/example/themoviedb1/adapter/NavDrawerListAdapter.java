package ma.example.themoviedb1.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ma.example.themoviedb1.R;
import ma.example.themoviedb1.adapter.GridViewAdapter.ViewHolder;
import ma.example.themoviedb1.model.Movies;
import ma.example.themoviedb1.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter{

	public static class ViewHolder {
		public final ImageView icon;
		public final TextView title;

		public ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
		}
	}

	private Context mContext;
	private List<NavDrawerItem> navDrawerItems;

	public NavDrawerListAdapter(Context context, List<NavDrawerItem> navDrawerItem) {
		mContext = context;
		navDrawerItems = navDrawerItem;
	}
	
	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public NavDrawerItem getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		Picasso.with(mContext).load(navDrawerItems.get(position).getIcone())
				// .resize(185, 278)
				.into(viewHolder.icon);
//		viewHolder.icon.setImageResource(R.drawable.ic_launcher);
		viewHolder.title.setText(navDrawerItems.get(position).getTitle());
		
		return convertView;
	}
	
}
