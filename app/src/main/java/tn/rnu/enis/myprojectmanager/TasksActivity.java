package tn.rnu.enis.myprojectmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.pagers.TaskHolder;
import tn.rnu.enis.myprojectmanager.task.TasksFragment;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class TasksActivity extends AppCompatActivity {

    private static String ID ;
    private static String name ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ID = getIntent().getStringExtra(Contract.REF);
        name = getIntent().getStringExtra(Contract.NAME);

        Bundle bundle = new Bundle();
        bundle.putString(Contract.REF, ID );
        TaskHolder fragInfo = new TaskHolder();
        fragInfo.setArguments(bundle);

        getSupportActionBar().setTitle(name);
        if(savedInstanceState==null) getSupportFragmentManager().beginTransaction().replace(R.id.task_list,fragInfo).commit();

    }
}
