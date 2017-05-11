package jp.ac.jec.a16cm0209.android202;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nguyenhiep on 4/27/2017 AD.
 */

public class ReadRSSAdapter extends ArrayAdapter<ReadRSS> {

    public ReadRSSAdapter(Context context, int resource, List<ReadRSS> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.layout_listview, null);
        }
        ReadRSS p = getItem(position);
        if (p != null) {

            TextView txtTitle = (TextView) view.findViewById(R.id.textViewTitle);
            txtTitle.setText(p.title);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(p.image).into(imageView);

        }
        return view;
    }

}
