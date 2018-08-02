package com.example.a16033774.firebaseinventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddItemActivity extends AppCompatActivity {
    private static final String TAG = "AddItemActivity";

    private EditText etName, etCost;
    private Button btnAdd;

    // TODO: Task 1 - Declare Firebase variables
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference itemListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        etName = (EditText)findViewById(R.id.editTextName);
        etCost = (EditText)findViewById(R.id.editTexCost);
        btnAdd = (Button)findViewById(R.id.buttonAdd);

        // TODO: Task 2: Get Firebase database instance and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        itemListRef = firebaseDatabase.getReference("/itemList");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 3: Add student to database and go back to main screen
                String name = etName.getText().toString();
                Double price = Double.parseDouble(etCost.getText().toString());
                Item item = new Item(name, price);

                itemListRef.push().setValue(item);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);


            }
        });

    }


}


