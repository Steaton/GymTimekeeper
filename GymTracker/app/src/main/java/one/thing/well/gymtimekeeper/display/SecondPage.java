package one.thing.well.gymtimekeeper.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import one.thing.well.gymtimekeeper.R;

public class SecondPage extends android.support.v4.app.Fragment {


    public  static SecondPage newInstance(){

        SecondPage fragment = new SecondPage();

        return fragment;
    }


    public SecondPage(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.second_page,container,false);

        return rootView;
    }

}
