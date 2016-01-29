package ait.android.hu.concertlibraryapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.List;

import ait.android.hu.concertlibraryapp.R;
import ait.android.hu.concertlibraryapp.data.ConcertEntry;

/**
 * Created by emily on 11/22/15.
 */
public class ConcertRecyclerAdapter extends
        RecyclerView.Adapter<ConcertRecyclerAdapter.ViewHolder> {

    public List<ConcertEntry> concertList;

    public ConcertRecyclerAdapter(List<ConcertEntry> concertList) {
        this.concertList = concertList;
    }
    public ConcertRecyclerAdapter(){

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.concert_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(v, viewGroup.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.tvBandName.setText(concertList.get(i).getBandName());
        viewHolder.tvLocation.setText(concertList.get(i).getLocation());
        viewHolder.tvDate.setText(concertList.get(i).getDate());
        viewHolder.tvUserName.setText("User: " + concertList.get(i).getNameUser());

        System.out.println("here rating: " + concertList.get(i).getCurrentRating());

        viewHolder.rbRatings.setRating((float)concertList.get(i).getCurrentRating());



        if (concertList.get(i).isVideoInserted() == true) {
            System.out.println("video has been inserted");
            viewHolder.ivPicture.setImageResource(R.drawable.film);
        }
        System.out.println("no video inserted");


        viewHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConcertEntry entry = concertList.get(i);
                //http://stackoverflow.com/questions/1572107/android-intent-for-playing-video
                if (entry.isVideoInserted() == true) {
                    String video = concertList.get(i).getVideo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video));
                    intent.setDataAndType(Uri.parse(video), "video/*");
                    viewHolder.context.startActivity(intent);
                } else {
                    Toast.makeText(viewHolder.context, "No video for this entry", Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(i, viewHolder);

            }
        });

    }

    @Override
    public int getItemCount() {
        return concertList.size();
    }

    public void addConcertEntry(ConcertEntry entry) {

        concertList.add(entry);
        notifyDataSetChanged();
    }

    public void removeItem(int index, ViewHolder viewHolder) {
        // remove it from the list
        if (concertList.get(index).getNameUser().equals(ParseUser.getCurrentUser().getUsername())) {
            concertList.get(index).deleteInBackground();
            concertList.remove(index);
            notifyDataSetChanged();
        }
        else{
            Toast.makeText(viewHolder.context, "You do not have permission to perform this action.", Toast.LENGTH_SHORT).show();;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBandName;

        public TextView tvLocation;
        public TextView tvDate;
        public Button btnDelete;
        public ImageView ivPicture;
        public RatingBar rbRatings;

        public TextView tvUserName;

        public Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            tvBandName = (TextView) itemView.findViewById(R.id.tvBandName);
            rbRatings = (RatingBar) itemView.findViewById(R.id.rbRatings);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            ivPicture = (ImageView) itemView.findViewById(R.id.ivPicture);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);

            this.context = context;
        }
    }
}
