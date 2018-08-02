package com.example.a16033774.firebaseinventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemDetailActivity extends AppCompatActivity {
    private static final String TAG = "itemDetailActivity";

    private EditText etName, etCost;
    private Button btnUpdate, btnDelete;

    private Item item;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference itemListRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        etName = (EditText) findViewById(R.id.editTextNameUpdate);
        etCost = (EditText) findViewById(R.id.editTexCostUpdate);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);

        firebaseDatabase = FirebaseDatabase.getInstance();
        itemListRef = firebaseDatabase.getReference("/itemList");

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");

        //Display Student details as retrieved from the intent

        String phonename = item.getPhoneName();
        String phoneprice = String.valueOf(item.getPrice());
        etName.setText(phonename);
        etCost.setText(phoneprice);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Student record based on input given
                String id = item.getId();
                item.setId(null);
                item.setPhoneName(etName.getText().toString());
                item.setPrice(Double.parseDouble(etCost.getText().toString()));

                itemListRef.child(id).setValue(item);

                Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete Student record based on student id
                itemListRef.child(item.getId()).removeValue();

                Toast.makeText(getApplicationContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK);
                finish();

            }
        });
    }
}
