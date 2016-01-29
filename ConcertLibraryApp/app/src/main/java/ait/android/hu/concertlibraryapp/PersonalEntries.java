package ait.android.hu.concertlibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import ait.android.hu.concertlibraryapp.adapter.ConcertRecyclerAdapter;
import ait.android.hu.concertlibraryapp.data.ConcertEntry;

public class PersonalEntries extends AppCompatActivity {

    private ConcertRecyclerAdapter entriesAdapter;
    public static final int REQUEST_NEW_ENTRY = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_entries);

        List<ConcertEntry> concertList = new ArrayList<ConcertEntry>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        entriesAdapter = new ConcertRecyclerAdapter(concertList);
//        entriesAdapter = new ConcertRecyclerAdapter(concertList);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(entriesAdapter);


        ParseQuery<ConcertEntry> query = ParseQuery.getQuery(ConcertEntry.class);
        query.findInBackground(new FindCallback<ConcertEntry>() {
            @Override
            public void done(List<ConcertEntry> results, ParseException e) {
                for (ConcertEntry entry : results) {
                    System.out.println(" currenf irst: " + ParseUser.getCurrentUser().getUsername());
                    System.out.println("entry user name here: " + entry.getNameUser());
                    if (entry.getNameUser().equals(ParseUser.getCurrentUser().getUsername())){
                        System.out.println("we have a mathc");
                        entriesAdapter.addConcertEntry(entry);
                    }

                }
            }
        });


        FloatingActionButton fabAddItem = (FloatingActionButton) findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateEntryActivity();
            }
        });
    }


    private void showCreateEntryActivity() {
        Intent intentStart = new Intent(PersonalEntries.this, CreateEntryActivity.class);
        startActivityForResult(intentStart, REQUEST_NEW_ENTRY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_ENTRY) {
                    ParseQuery<ConcertEntry> query = ParseQuery.getQuery(ConcertEntry.class);

                    query.findInBackground(new FindCallback<ConcertEntry>() {
                        @Override
                        public void done(List<ConcertEntry> results, ParseException e) {

                            int last = results.size();

                            ConcertEntry entry = results.get(last - 1);
                            System.out.println("here" + entry.getBandName());
                            entriesAdapter.addConcertEntry(entry);
                        }

                    });
//                    ConcertEntry entry = (ConcertEntry) data.getSerializableExtra(
//                            CreateEntryActivity.KEY_BAND_NAME);
//                    entriesAdapter.addConcertEntry(entry);
//                    System.out.println("succdess");
                }
                break;
            case RESULT_CANCELED:
                System.out.println("failure");
                break;
        }
    }

}
