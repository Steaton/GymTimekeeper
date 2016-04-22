package one.thing.well.gymtimekeeper.display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import one.thing.well.gymtimekeeper.R;

/**
 * Created by zeko on 22/04/2016.
 */
public class ArrayAdapterForSessionsFragment extends ArrayAdapter {


    public ArrayAdapterForSessionsFragment(Context context, String[] resource) {
        super(context, R.layout.css_for_the_sessions_fragment_list_view,resource);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.css_for_the_sessions_fragment_list_view, parent, false);

        TextView dateView = (TextView) view.findViewById(R.id.Date);
        TextView startTimeView = (TextView) view.findViewById(R.id.StartTime);
        TextView endTimeView = (TextView) view.findViewById(R.id.EndTime);
        TextView durationView = (TextView) view.findViewById(R.id.Duration);
        String[] input = getItem(position).toString().split(",");


        dateView.setText(input[0]);
        startTimeView.setText(input[1]);
        endTimeView.setText(input[2]);
        durationView.setText(input[3]);





        return view;
    }
}
