package com.example.lab6;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextView recordsTotal;
    private TextView foundRecords;
    private TextInputEditText searchName;
    private Button personAddButton;

    private AppDatabase db;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordsTotal = this.findViewById(R.id.recordsTotal);
        foundRecords = this.findViewById(R.id.foundRecords);
        searchName = (TextInputEditText) this.findViewById(R.id.searchName);
        personAddButton = this.findViewById(R.id.personAddButton);

        db = AppDatabase.createInstance(this);
        personDao = db.personDao();

        updateRecordsTotal();
        updateFoundedRecords();

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        boolean isPersonCreated = data.getBooleanExtra("isPersonCreated", true);
                        if(isPersonCreated){
                            updateRecordsTotal();
                            updateFoundedRecords();
                        }
                    }
                });

        setSearchNameChangedListener();
        setPersonAddClickListener(this, AddRecordActivity.class, launcher);
    }

    private void updateRecordsTotal(){
        recordsTotal.setText(String.valueOf(personDao.getQuantityPersons()));
    }

    private void updateFoundedRecords(){
        String prefix = searchName.getText().toString();
        if(prefix.isEmpty()){
            foundRecords.setText("0");
        } else {
            foundRecords.setText(String.valueOf(personDao.getQuantityPersonsWithNameOrSurnameStartWith(prefix)));
        }
    }

    private void setSearchNameChangedListener(){
        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateFoundedRecords();
            }
        });
    }

    private void setPersonAddClickListener(AppCompatActivity from, Class to, ActivityResultLauncher<Intent> launcher){
        personAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(from, to);
                launcher.launch(intent);
            }
        });
    }
}