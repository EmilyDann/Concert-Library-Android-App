package ait.android.hu.concertlibraryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ait.android.hu.concertlibraryapp.adapter.ConcertRecyclerAdapter;
import ait.android.hu.concertlibraryapp.data.ConcertEntry;

public class AllEntriesActivity extends AppCompatActivity {

    private ConcertRecyclerAdapter entriesAdapterEveryone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_entries);

        List<ConcertEntry> concertListEveryone = new ArrayList<ConcertEntry>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_everyone);
        entriesAdapterEveryone = new ConcertRecyclerAdapter(concertListEveryone);
//        entriesAdapter = new ConcertRecyclerAdapter(concertList);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(entriesAdapterEveryone);

        ParseQuery<ConcertEntry> query = ParseQuery.getQuery(ConcertEntry.class);

        query.findInBackground(new FindCallback<ConcertEntry>() {
            @Override
            public void done(List<ConcertEntry> results, ParseException e) {
                for (ConcertEntry entry : results) {
                    System.out.println("here" + entry.getBandName());
                    entriesAdapterEveryone.addConcertEntry(entry);
                }
            }
        });
    }
}
