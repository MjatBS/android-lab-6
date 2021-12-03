package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class AddRecordActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private TextInputEditText surnameEditText;
    private TextInputEditText commentEditText;
    private Button recordCancelButton;
    private Button recordSaveButton;

    private AppDatabase db;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        nameEditText = this.findViewById(R.id.nameEditText);
        surnameEditText = this.findViewById(R.id.surnameEditText);
        commentEditText = this.findViewById(R.id.commentEditText);
        recordCancelButton = this.findViewById(R.id.recordCancelButton);
        recordSaveButton = this.findViewById(R.id.recordSaveButton);

        db = AppDatabase.getInstance();
        personDao = db.personDao();

        setCancelClickListener(this);
        setSaveClickListener(this);
    }

    private void setCancelClickListener(AppCompatActivity activity) {
        recordCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                activity.setResult(Activity.RESULT_CANCELED, returnIntent);
                activity.finish();
            }
        });
    }

    private void setSaveClickListener(AppCompatActivity activity){
        recordSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                personDao.insert(getCreatedPerson());
                returnIntent.putExtra("isPersonCreated", true);
                activity.setResult(Activity.RESULT_OK, returnIntent);
                activity.finish();
            }
        });
    }

    private Person getCreatedPerson(){
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String comment = commentEditText.getText().toString();
        return new Person(name, surname, comment);
    }
}