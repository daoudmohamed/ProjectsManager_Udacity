package tn.rnu.enis.myprojectmanager.task;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
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
public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    PaperButton b ;
    FloatingEditText nametext ;
    FloatingEditText detail ;
    String date_text ;
    AppCompatActivity activity = this ;
    private String id_project ;

    Switch date ;

    final Calendar calendar = Calendar.getInstance();
    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);



    boolean with_date ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);

        date = (Switch) findViewById(R.id.switch1);

        date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                with_date = b ;
                if(b){
                    datePickerDialog.show(getSupportFragmentManager(), "lll");
                }
            }
        });

        id_project = getIntent().getStringExtra(Contract.REF);

        nametext = ((FloatingEditText)findViewById(R.id.task_name));
        detail = (FloatingEditText) findViewById(R.id.task_detail);

        b = (PaperButton) findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues vals = new ContentValues();

                boolean v = false ;
                String name = nametext.getText().toString();
                String description = detail.getText().toString();

                if(name.isEmpty()) {
                    nametext.setValidateResult(false,getString(R.string.fill_field));
                    v=true;
                }

                if(v) return ;
                vals.put(Contract.Task.TASK_NAME,name);
                vals.put(Contract.Task.TASK_DESCRIPTION,description);
                if (with_date)vals.put(Contract.Project.PROJECT_DATE,date_text);
                vals.put(Contract.Task.TASK_PROJECT,id_project);
                vals.put(Contract.Task.TASK_STATUS,Contract.Status.WAITING);

                Uri a = getContentResolver().insert(Contract.Task.CONTENT_URI,vals);
                Toast.makeText(getApplicationContext(),getString(R.string.task_add),Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int day, int month, int year) {
        date_text =  day + "/" +  month+1 + "/" + year ;
    }
}