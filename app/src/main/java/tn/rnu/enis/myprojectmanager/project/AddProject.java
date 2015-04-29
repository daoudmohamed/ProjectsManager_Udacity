package tn.rnu.enis.myprojectmanager.project;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.EventLogTags;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.material.widget.FloatingEditText;
import com.material.widget.PaperButton;

import tn.rnu.enis.myprojectmanager.R;
import tn.rnu.enis.myprojectmanager.data.Contract;

/**
 * Created by Mohamed on 28/04/2015.
 */
public class AddProject extends AppCompatActivity {

    PaperButton b ;
    FloatingEditText nametext ;
    FloatingEditText detail ;
    FloatingEditText date_text ;
    AppCompatActivity activity = this ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproject);

        nametext = ((FloatingEditText)findViewById(R.id.project_name));
        detail = (FloatingEditText) findViewById(R.id.project_detail);
        date_text = (FloatingEditText)findViewById(R.id.project_date);

        b = (PaperButton) findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues vals = new ContentValues();

                boolean v = false ;
                String name = nametext.getText().toString();
                String description = detail.getText().toString();
                String date = date_text.getText().toString();

                if(name.isEmpty()) {
                    nametext.setValidateResult(false,getString(R.string.fill_field));
                    v=true;
                }
                if(!date.isEmpty()) {
                    if(!date.matches("((\\d{2}/){2})\\d{4}")) {
                        date_text.setValidateResult(false,getString(R.string.invalid_date));
                        v=true;
                    }
                };
                if(v) return ;
                vals.put(Contract.Project.PROJECT_NAME,name);
                vals.put(Contract.Project.PROJECT_DESCRIPTION,description);
                vals.put(Contract.Project.PROJECT_DATE,date);
                Uri a = getContentResolver().insert(Contract.Project.CONTENT_URI,vals);
                Toast.makeText(getApplicationContext(),getString(R.string.project_added),Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
    }
}
