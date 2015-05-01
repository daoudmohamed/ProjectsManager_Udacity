package tn.rnu.enis.myprojectmanager.pagers;

/**
 * Created by Mohamed on 01/05/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.material.widget.TabIndicator;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.task.TasksFragment;

public class TaskPager extends FragmentPagerAdapter implements TabIndicator.TabTextProvider {

    protected static final String[] CONTENT = new String[]{Contract.Status.WAITING,
            Contract.Status.DOING, Contract.Status.BLOCKED, Contract.Status.DONE};

    private int mCount = CONTENT.length;

    private String project_id;

    public TaskPager(FragmentManager fragmentManager, String project_id) {
        super(fragmentManager);
        this.project_id = project_id;
    }

    public void setProject_id(String p){
        project_id = p ;
    }

    @Override
    public Fragment getItem(int position) {

        Log.i("jjjj", "iiiiiiiii");

        Bundle bundle = new Bundle();
        bundle.putString(Contract.REF, project_id);
        TasksFragment fragInfo = new TasksFragment();

        if (position == 0) {
            bundle.putString(Contract.NAME, Contract.Status.WAITING);
            fragInfo.setArguments(bundle);

            return fragInfo;
        }
        if (position == 1) {
            bundle.putString(Contract.NAME, Contract.Status.DOING);
            fragInfo.setArguments(bundle);

            return fragInfo;
        } else {
            if (position == 2) {
                bundle.putString(Contract.NAME, Contract.Status.BLOCKED);
                fragInfo.setArguments(bundle);

                return fragInfo;
            } else {
                bundle.putString(Contract.NAME, Contract.Status.DONE);
                fragInfo.setArguments(bundle);

                return fragInfo;
            }
        }
    }

    @Override
    public String getText(int position) {
        return TaskPager.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getCount() {
        return mCount;
    }

}
