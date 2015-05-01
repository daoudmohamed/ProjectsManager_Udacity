package tn.rnu.enis.myprojectmanager.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.TasksActivity;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.task.TasksFragment;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class ProjectsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ProjectAdapter mProjectAdapter ;
    private ListView mListView ;


    private static final int PROJECT_LOADER = 0;

    private static final String[] PROJECT_COLUMNS = {
            Contract.Project.TABLE_NAME + "." + Contract.Project._ID,
            Contract.Project.PROJECT_NAME,
            Contract.Project.PROJECT_DESCRIPTION,
            Contract.Project.PROJECT_DATE,
    };

    public static final int COL_PROJECT_ID = 0;
    public static final int COL_PROJECT_NAME = 1;
    public static final int COL_PROJECT_DESCRIPTION = 2;
    public static final int COL_PROJECT_DATE = 3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mProjectAdapter = new ProjectAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.projectsfragment, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list_view);
        mListView.setAdapter(mProjectAdapter);

        mListView.setEmptyView(rootView.findViewById(R.id.notfound));

        mListView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(PROJECT_LOADER, null, this);
        FloatingActionButton flot = (FloatingActionButton) getView().findViewById(R.id.fab);
        flot.attachToListView(mListView);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),AddProject.class);
                startActivity(i);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = Contract.Project._ID + " DESC";

        return new CursorLoader(getActivity(),
                Contract.Project.CONTENT_URI,
                PROJECT_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mProjectAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProjectAdapter.swapCursor(null);
    }


}
