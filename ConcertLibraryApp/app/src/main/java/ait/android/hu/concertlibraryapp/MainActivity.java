package ait.android.hu.concertlibraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPersonalDiary = (Button) findViewById(R.id.btnPersonalLibrary);
        btnPersonalDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPersonalDiary = new Intent();
                startPersonalDiary.setClass(MainActivity.this, PersonalEntries.class);
                startActivity(startPersonalDiary);
            }
        });

        Button btnAllEntries = (Button) findViewById(R.id.btnAllEntries);
        btnAllEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startAllEntries = new Intent();
                startAllEntries.setClass(MainActivity.this, AllEntriesActivity.class);
                startActivity(startAllEntries);
            }
        });
    }
}
