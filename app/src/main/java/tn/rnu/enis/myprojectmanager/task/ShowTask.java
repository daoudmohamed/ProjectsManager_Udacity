package tn.rnu.enis.myprojectmanager.task;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.project.AddProject;

/**
 * Created by Mohamed on 29/04/2015.
 */
public class ShowTask extends AppCompatActivity {

    TextView title, description, date, status;
    String id ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtask);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        date = (TextView) findViewById(R.id.date);
        status = (TextView) findViewById(R.id.status);

        id = getIntent().getStringExtra(Contract.REF);

        FloatingActionButton flot = (FloatingActionButton) findViewById(R.id.fab);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UpdateTask.class);
                i.putExtra(Contract.REF,id);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor c = getContentResolver().query(Contract.Task.buildTASKUri(Long.parseLong(id)),TasksFragment.TASK_COLUMNS,null,null,null);

        c.moveToFirst();

        title.setText(c.getString(TasksFragment.COL_TASK_NAME));
        description.setText(c.getString(TasksFragment.COL_TASK_DESCRIPTION));
        date.setText(c.getString(TasksFragment.COL_TASK_DATE));
        status.setText(c.getString(TasksFragment.COL_TASK_STATUS));

        getSupportActionBar().setTitle(c.getString(TasksFragment.COL_TASK_NAME));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Delete) {
            getContentResolver().delete(Contract.Task.CONTENT_URI,Contract.Task._ID+"= ?",new String[]{this.id});
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
