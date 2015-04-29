package tn.rnu.enis.myprojectmanager.project;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
public class AddProject extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    PaperButton b ;
    FloatingEditText nametext ;
    FloatingEditText detail ;
    String date_text ;
    AppCompatActivity activity = this ;

    Switch date ;

    final Calendar calendar = Calendar.getInstance();
    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);



    boolean with_date ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproject);

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



        nametext = ((FloatingEditText)findViewById(R.id.project_name));
        detail = (FloatingEditText) findViewById(R.id.project_detail);

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
                vals.put(Contract.Project.PROJECT_NAME,name);
                vals.put(Contract.Project.PROJECT_DESCRIPTION,description);
                if (with_date)vals.put(Contract.Project.PROJECT_DATE,date_text);
                Uri a = getContentResolver().insert(Contract.Project.CONTENT_URI,vals);
                Toast.makeText(getApplicationContext(),getString(R.string.project_added),Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        date_text =  day + "/" +  month+1 + "/" + year ;
    }
}
