package com.example.a16033774.firebaseinventoryapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView lvitem;
    private ArrayList<Item> alItem;
    private ArrayAdapter<Item> aaItem;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference itemListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvitem = (ListView)findViewById(R.id.listViewItem);
        alItem = new ArrayList<Item>();
        aaItem = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, alItem);
        lvitem.setAdapter(aaItem);

        firebaseDatabase = FirebaseDatabase.getInstance();
        itemListRef = firebaseDatabase.getReference("itemList");

        itemListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildAdded()");
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    item.setId(dataSnapshot.getKey());

                    alItem.add(item);
                    aaItem.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildChanged()");

                String selectedId = dataSnapshot.getKey();
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    for (int i = 0; i < alItem.size(); i++) {
                        if (alItem.get(i).getId().equals(selectedId)) {
                            item.setId(selectedId);
                            alItem.set(i, item);
                            break;
                        }
                    }
                    aaItem.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("MainActivity", "onChildRemoved()");
                String selectedId = dataSnapshot.getKey();
                for(int i= 0; i < alItem.size(); i++) {
                    if (alItem.get(i).getId().equals(selectedId)) {
                        alItem.remove(i);
                        break;
                    }
                }
                aaItem.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("MainActivity", "onChildMoved()");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Database error occurred", databaseError.toException());
            }
        });

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = alItem.get(i);  // Get the selected Student
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra("Item", item);
                startActivityForResult(intent, 1);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.addItem) {

            Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
