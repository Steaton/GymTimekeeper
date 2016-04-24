package one.thing.well.gymtimekeeper.display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import one.thing.well.gymtimekeeper.R;

public class ArrayAdapterForSessionsFragment extends ArrayAdapter {

    public ArrayAdapterForSessionsFragment(Context context, String[][] sessionData) {
        super(context, R.layout.session_layout, sessionData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.session_layout, parent, false);

        TextView dayOfMonthView = (TextView) view.findViewById(R.id.dayOfMonth);
        TextView monthView = (TextView) view.findViewById(R.id.month);
        TextView timePeriodView = (TextView) view.findViewById(R.id.timePeriod);
        TextView durationView = (TextView) view.findViewById(R.id.duration);
        String[] input = (String[]) getItem(position);

        dayOfMonthView.setText(input[0]);
        monthView.setText(input[1]);
        timePeriodView.setText(input[2]);
        durationView.setText(input[3]);

        return view;
    }
}
