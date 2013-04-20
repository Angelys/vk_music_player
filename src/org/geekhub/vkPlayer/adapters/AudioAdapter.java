package org.geekhub.vkPlayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.perm.kate.api.Audio;
import org.geekhub.vkPlayer.R;

import java.util.ArrayList;


public class AudioAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Audio> values;

    public AudioAdapter(Context context,ArrayList<Audio> values){
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
    }

    public void setData(ArrayList<Audio> data){
        this.values = data;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView =  inflater.inflate(R.layout.row_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.artist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.artist.setText(values.get(position).artist);
        viewHolder.title.setText(values.get(position).title);

        return convertView;
    }

    public static class ViewHolder{
        TextView artist;
        TextView title;
    }
}
