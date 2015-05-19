package tn.rnu.enis.myprojectmanager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import me.drakeet.materialdialog.MaterialDialog;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.project.ProjectsFragment;
import tn.rnu.enis.myprojectmanager.task.DefaultTasksFragment;
import tn.rnu.enis.myprojectmanager.task.TasksFragment;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.weather_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, new DefaultTasksFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportFragmentManager().beginTransaction().add(R.id.project_list, new ProjectsFragment()).commit();
        }

        getSupportActionBar().setElevation(0.5f);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Delete_all_Project) {

            final MaterialDialog mMaterialDialog = new MaterialDialog(MainActivity.this);
            mMaterialDialog.setTitle("Info");

            mMaterialDialog
                    .setMessage("Do you really want to remove all your projects ?")
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContentResolver().delete(Contract.Project.CONTENT_URI, null, null);
                            Toast.makeText(getApplicationContext(), getString(R.string.all_projects_deleted), Toast.LENGTH_SHORT).show();
                            mMaterialDialog.dismiss();
                        }
                    });

            mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });

            mMaterialDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        String id = cursor.getString(ProjectsFragment.COL_PROJECT_ID);

        if (mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putString(Contract.REF, id);
            TasksFragment tasks = new TasksFragment();
            tasks.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container,tasks , "detail")
                    .commit();
        } else {

            Intent i = new Intent(getApplicationContext(), TasksActivity.class);
            i.putExtra(Contract.REF, id);
            i.putExtra(Contract.NAME, cursor.getString(ProjectsFragment.COL_PROJECT_NAME));

            startActivity(i);
        }

    }
}
