package one.thing.well.gymtimekeeper.display.mainPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import one.thing.well.gymtimekeeper.R;

public class FirstPage extends android.support.v4.app.Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    public static FirstPage newInstance() {
        FirstPage fragment = new FirstPage();
        return fragment;
    }

    public FirstPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.first_page, container, false);
        return rootView;
    }
}








