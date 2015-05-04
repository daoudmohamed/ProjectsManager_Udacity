package tn.rnu.enis.myprojectmanager.pagers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.material.widget.TabIndicator;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 01/05/2015.
 */
public class TaskHolder extends Fragment {

    private String mProject_Id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.taskswithcategories,container,false);

        mProject_Id = getArguments().getString(Contract.REF);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);
        TaskPager adapter = new TaskPager(getActivity().getSupportFragmentManager(), mProject_Id);
        viewPager.setAdapter(adapter);

        TabIndicator indicator = (TabIndicator) v.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        return v;
    }


}
