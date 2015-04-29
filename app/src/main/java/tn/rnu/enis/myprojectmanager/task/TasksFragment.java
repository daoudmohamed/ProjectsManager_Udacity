package tn.rnu.enis.myprojectmanager.task;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import tn.rnu.enis.myprojectmanager.project.AddProject;
import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class TasksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TaskAdapter mTaskAdapter ;
    private ListView mListView ;

    private static String project_id ;

    private static final int TASKS_LOADER = 0;

    public static final String[] TASK_COLUMNS = {
            Contract.Task.TABLE_NAME + "." + Contract.Project._ID,
            Contract.Task.TASK_NAME,
            Contract.Task.TASK_DESCRIPTION,
            Contract.Task.TASK_DATE,
            Contract.Task.TASK_STATUS
    };

    public static final int COL_TASK_ID = 0;
    public static final int COL_TASK_NAME = 1;
    public static final int COL_TASK_DESCRIPTION = 2;
    public static final int COL_TASK_DATE = 3;
    public static final int COL_TASK_STATUS = 4;

    public TasksFragment setProject_id(String project_id) {
        TasksFragment.project_id = project_id;
        return this ;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mTaskAdapter = new TaskAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.tasksfragment, container, false);

        setHasOptionsMenu(true);

        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mListView.setAdapter(mTaskAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                Intent i = new Intent(getActivity(),ShowTask.class);

                i.putExtra(Contract.REF,cursor.getString(0));


                startActivity(i);

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TASKS_LOADER, null, this);
        FloatingActionButton flot = (FloatingActionButton) getView().findViewById(R.id.fab);
        flot.attachToListView(mListView);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),AddTask.class);
                i.putExtra(Contract.REF,project_id);
                startActivity(i);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = Contract.Project._ID + " DESC";

        return new CursorLoader(getActivity(),
                Contract.Task.CONTENT_URI,
                TASK_COLUMNS,
                Contract.Task.TASK_PROJECT+"= ?",
                new String[]{project_id},
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mTaskAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTaskAdapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inf) {
        inf.inflate(R.menu.delmenu, menu);
        super.onCreateOptionsMenu(menu, inf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Delete) {
            getActivity().getContentResolver().delete(Contract.Project.CONTENT_URI,Contract.Task._ID+"= ?",new String[]{project_id});
            getActivity().finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
