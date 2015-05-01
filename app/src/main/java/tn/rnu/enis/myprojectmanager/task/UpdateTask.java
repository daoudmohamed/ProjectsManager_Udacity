package tn.rnu.enis.myprojectmanager.task;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.material.widget.FloatingEditText;
import com.material.widget.PaperButton;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 29/04/2015.
 */
public class UpdateTask extends AppCompatActivity {

    FloatingEditText task_name , task_detail , task_date ;
    Spinner spinner ;
    PaperButton b ;

    String id ;
    AppCompatActivity activity = this ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udpastetask);

        id = getIntent().getStringExtra(Contract.REF);

        getSupportActionBar().setTitle(getString(R.string.edit_task));

        task_name = (FloatingEditText) findViewById(R.id.task_name);
        task_detail = (FloatingEditText) findViewById(R.id.task_detail);
        task_date = (FloatingEditText) findViewById(R.id.task_date);

        spinner = (Spinner) findViewById(R.id.spinner);
        b = (PaperButton) findViewById(R.id.submit);

        Cursor c = getContentResolver().query(Contract.Task.buildTASKUri(Long.parseLong(id)),TasksFragment.TASK_COLUMNS,null,null,null);

        c.moveToFirst();

        task_name.setText(c.getString(TasksFragment.COL_TASK_NAME));
        task_detail.setText(c.getString(TasksFragment.COL_TASK_DESCRIPTION));
        task_date.setText(c.getString(TasksFragment.COL_TASK_DATE));

        switch (c.getString(TasksFragment.COL_TASK_STATUS)){
            case Contract.Status.WAITING :
                spinner.setSelection(0);
                break;
            case Contract.Status.DOING :
                spinner.setSelection(1);
                break;
            case Contract.Status.BLOCKED :
                spinner.setSelection(2);
                break;
            case Contract.Status.DONE :
                spinner.setSelection(3);
                break;
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                values.put(Contract.Task.TASK_NAME,task_name.getText().toString());
                values.put(Contract.Task.TASK_DESCRIPTION,task_detail.getText().toString());
                values.put(Contract.Task.TASK_DATE,task_date.getText().toString());
                values.put(Contract.Task.TASK_STATUS,spinner.getSelectedItem().toString());

                int i = getContentResolver().update(Contract.Task.CONTENT_URI,values,Contract.Task._ID+"= ?",new String[]{id});

                activity.finish();

            }
        });



    }
}
