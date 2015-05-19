package tn.rnu.enis.myprojectmanager.task;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.material.widget.FloatingEditText;
import com.material.widget.PaperButton;
import com.material.widget.Switch;

import java.util.Calendar;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private PaperButton mSubmit;
    private FloatingEditText mName_Text;
    private FloatingEditText mDetail;
    private String mDate_Text;
    private AppCompatActivity mActivity = this;
    private String mId_Project;

    private Switch mDate;

    private static final Calendar CALENDAR = Calendar.getInstance();
    private final DatePickerDialog DATE_PICKER_DIALOG = DatePickerDialog.newInstance(this, CALENDAR.get(Calendar.YEAR), CALENDAR.get(Calendar.MONTH), CALENDAR.get(Calendar.DAY_OF_MONTH), false);


    boolean with_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);

        mDate = (Switch) findViewById(R.id.switch1);

        mDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                with_date = b;
                if (b) {
                    DATE_PICKER_DIALOG.show(getSupportFragmentManager(), "lll");
                }
            }
        });

        mId_Project = getIntent().getStringExtra(Contract.REF);

        mName_Text = ((FloatingEditText) findViewById(R.id.task_name));
        mDetail = (FloatingEditText) findViewById(R.id.task_detail);

        mSubmit = (PaperButton) findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues vals = new ContentValues();

                boolean v = false;
                String name = mName_Text.getText().toString();
                String description = mDetail.getText().toString();

                if (name.isEmpty()) {
                    mName_Text.setValidateResult(false, getString(R.string.fill_field));
                    v = true;
                }

                if (v) return;
                vals.put(Contract.Task.TASK_NAME, name);
                vals.put(Contract.Task.TASK_DESCRIPTION, description);
                if (with_date) vals.put(Contract.Project.PROJECT_DATE, mDate_Text);
                vals.put(Contract.Task.TASK_PROJECT, mId_Project);
                vals.put(Contract.Task.TASK_STATUS, Contract.Status.WAITING);

                Uri a = getContentResolver().insert(Contract.Task.CONTENT_URI, vals);
                Toast.makeText(getApplicationContext(), getString(R.string.task_add), Toast.LENGTH_SHORT).show();
                mActivity.finish();
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int day, int month, int year) {
        mDate_Text = day + "/" + (month + 1) + "/" + year;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

}
