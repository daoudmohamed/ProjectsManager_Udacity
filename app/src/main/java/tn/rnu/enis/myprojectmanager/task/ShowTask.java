package tn.rnu.enis.myprojectmanager.task;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 29/04/2015.
 */
public class ShowTask extends AppCompatActivity {

    private TextView mTitle, mDescription, mDate, mStatus;
    private String mId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtask);
        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
        mDate = (TextView) findViewById(R.id.date);
        mStatus = (TextView) findViewById(R.id.status);

        mId = getIntent().getStringExtra(Contract.REF);

        FloatingActionButton flot = (FloatingActionButton) findViewById(R.id.fab);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UpdateTask.class);
                i.putExtra(Contract.REF, mId);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor c = getContentResolver().query(Contract.Task.buildTASKUri(Long.parseLong(mId)),TasksFragment.TASK_COLUMNS,null,null,null);

        c.moveToFirst();

        mTitle.setText(c.getString(TasksFragment.COL_TASK_NAME));
        mDescription.setText(c.getString(TasksFragment.COL_TASK_DESCRIPTION));
        mDate.setText(c.getString(TasksFragment.COL_TASK_DATE));
        mStatus.setText(c.getString(TasksFragment.COL_TASK_STATUS));

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
            getContentResolver().delete(Contract.Task.CONTENT_URI,Contract.Task._ID+"= ?",new String[]{this.mId});
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
