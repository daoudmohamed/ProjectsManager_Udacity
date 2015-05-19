package tn.rnu.enis.myprojectmanager.task;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import me.drakeet.materialdialog.MaterialDialog;
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
        getMenuInflater().inflate(R.menu.deltask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Delete_Task) {

            final MaterialDialog mMaterialDialog = new MaterialDialog(ShowTask.this);
            mMaterialDialog.setTitle("Info");

            mMaterialDialog
                    .setMessage("Do you really want to remove this task ?")
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getContentResolver().delete(Contract.Task.CONTENT_URI, Contract.Task._ID + "= ?", new String[]{mId});
                            finish();
                            overridePendingTransition(R.anim.open_main, R.anim.close_next);
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
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }


}
