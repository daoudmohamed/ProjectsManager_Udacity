package tn.rnu.enis.myprojectmanager.task;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.material.widget.FloatingEditText;
import com.material.widget.PaperButton;
import com.material.widget.Switch;

import java.util.Calendar;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;
import tn.rnu.enis.myprojectmanager.utils.MyDate;

/**
 * Created by Mohamed on 29/04/2015.
 */
public class UpdateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FloatingEditText mTask_name, mTask_detail;
    private Spinner mSpinner;
    private PaperButton mUpdate;
    private String mDate_Text;
    private boolean with_date ;

    private String mId;
    private AppCompatActivity mActivity = this ;

    private Switch mDate;

    private DatePickerDialog DATE_PICKER_DIALOG ;

    private static final Calendar CALENDAR = Calendar.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udpastetask);

        mDate = (Switch) findViewById(R.id.switch1);


        mId = getIntent().getStringExtra(Contract.REF);

        getSupportActionBar().setTitle(getString(R.string.edit_task));

        mTask_name = (FloatingEditText) findViewById(R.id.task_name);
        mTask_detail = (FloatingEditText) findViewById(R.id.task_detail);


        mSpinner = (Spinner) findViewById(R.id.spinner);
        mUpdate = (PaperButton) findViewById(R.id.submit);

        Cursor c = getContentResolver().query(Contract.Task.buildTASKUri(Long.parseLong(mId)),TasksFragment.TASK_COLUMNS,null,null,null);

        c.moveToFirst();

        mTask_name.setText(c.getString(TasksFragment.COL_TASK_NAME));
        mTask_detail.setText(c.getString(TasksFragment.COL_TASK_DESCRIPTION));
        mDate.setChecked(((c.getString(TasksFragment.COL_TASK_DATE) == null) || c.getString(TasksFragment.COL_TASK_DATE).equals("") ? (false) : (true)));
        if(mDate.isChecked()) {
            MyDate date = new MyDate(c.getString(TasksFragment.COL_TASK_DATE));
            DATE_PICKER_DIALOG = DatePickerDialog.newInstance(this,date.getYear(), date.getMouth(), date.getDay(), false);
        }else{
            DATE_PICKER_DIALOG = DatePickerDialog.newInstance(this, CALENDAR.get(Calendar.YEAR), CALENDAR.get(Calendar.MONTH), CALENDAR.get(Calendar.DAY_OF_MONTH), false);

        }




        switch (c.getString(TasksFragment.COL_TASK_STATUS)){
            case Contract.Status.WAITING :
                mSpinner.setSelection(0);
                break;
            case Contract.Status.DOING :
                mSpinner.setSelection(1);
                break;
            case Contract.Status.BLOCKED :
                mSpinner.setSelection(2);
                break;
            case Contract.Status.DONE :
                mSpinner.setSelection(3);
                break;
        }

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                values.put(Contract.Task.TASK_NAME, mTask_name.getText().toString());
                values.put(Contract.Task.TASK_DESCRIPTION, mTask_detail.getText().toString());
                values.put(Contract.Task.TASK_DATE, (with_date)?(mDate_Text):(""));
                values.put(Contract.Task.TASK_STATUS, mSpinner.getSelectedItem().toString());
                new update().execute(values);
            }
        });

        mDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                with_date = b;
                if (b) {
                    DATE_PICKER_DIALOG.show(getSupportFragmentManager(), "lll");
                }
            }
        });
        if(mDate.isChecked()) DATE_PICKER_DIALOG.show(getSupportFragmentManager(), "lll") ;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int day, int month, int year) {
            mDate_Text = day + "/" + (month + 1) + "/" + year;
    }

    class update extends AsyncTask<ContentValues,Void,Integer>{

        @Override
        protected Integer doInBackground(ContentValues... contentValueses) {
            return getContentResolver().update(Contract.Task.CONTENT_URI, contentValueses[0], Contract.Task._ID + "= ?", new String[]{mId});
        }

        @Override
        protected void onPostExecute(Integer num) {
            super.onPostExecute(num);
            if(num>0) {
                Toast.makeText(mActivity,"Task Updated",Toast.LENGTH_SHORT).show();
                mActivity.finish();
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
            }
                else Toast.makeText(mActivity,"Error while Updating",Toast.LENGTH_SHORT).show();
        }
    }
}
