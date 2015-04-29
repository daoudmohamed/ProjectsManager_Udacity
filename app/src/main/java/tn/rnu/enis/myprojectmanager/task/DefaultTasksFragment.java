package tn.rnu.enis.myprojectmanager.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tn.rnu.enis.myprojectmanager.R;

/**
 * Created by Mohamed on 29/04/2015.
 */
public class DefaultTasksFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.defaulttaskfagment,container,false);
    }
}


