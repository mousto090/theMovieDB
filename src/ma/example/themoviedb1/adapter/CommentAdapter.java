package ma.example.themoviedb1.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ma.example.themoviedb1.R;
import ma.example.themoviedb1.model.Comments;
import ma.example.themoviedb1.utility.RoundedImage;
/**
 * Adapter for comments(Reviews) on a movies
 * @author Diallo
 *
 */
public class CommentAdapter extends BaseAdapter {

	public class ViewHolder {
		public ImageView icone = null;
		public TextView authorName = null;
		public TextView comment = null;

		public ViewHolder(View view) {
			icone = (ImageView)view.findViewById(R.id.icone_author);
			authorName = (TextView) view.findViewById(R.id.author_name);
			comment = (TextView) view.findViewById(R.id.comment);
		}
	}

	protected Context mContext;
	protected List<Comments> listReviews;

	public CommentAdapter(Context context, List<Comments> listReviews) {
		super();
		this.mContext = context;
		this.listReviews = listReviews;
	}

	@Override
	public int getCount() {
		return listReviews.size();
	}

	@Override
	public Comments getItem(int position) {
		return listReviews.get(position);
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
			convertView = inflater.inflate(R.layout.comment_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Comments comment = listReviews.get(position);
		Picasso.with(mContext).load(R.drawable.ic_profile)
			.transform(new RoundedImage())
			.into(viewHolder.icone);
		viewHolder.authorName.setText(comment.getAuthorName());
		viewHolder.comment.setText(comment.getComment());
		return convertView;
	}

}
