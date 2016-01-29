package ait.android.hu.concertlibraryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import ait.android.hu.concertlibraryapp.data.ConcertEntry;

/**
 * Created by emily on 11/24/15.
 */
public class CreateEntryActivity extends AppCompatActivity {

    public static final String KEY_BAND_NAME = "KEY_BAND_NAME";
    public static final int REQUEST_NEW_IMAGE = 101;
    private Bitmap thumb;
    private EditText etBandName;
    private EditText etLocation;
    private EditText etDate;
    private RatingBar rbGivenRating;
    private double currentRating;
    private TextView tvVideoInserted;
    private String video;
    private boolean videoInserted;
    private String currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        currentUser = ParseUser.getCurrentUser().getUsername();
        System.out.println("do we have ??" + currentUser);


        etBandName = (EditText) findViewById(R.id.etBandName);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etDate = (EditText) findViewById(R.id.etDate);
        rbGivenRating = (RatingBar) findViewById(R.id.rbGivenRating);




        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (! etBandName.getText().toString().equals("")) {
                    savePlace();
                }
                else{
                    etBandName.setError("You must enter a band name.");
                }
            }
        });

        Button btnInsertVideo = (Button) findViewById(R.id.btnInsertVideo);
        btnInsertVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://stackoverflow.com/questions/4922037/android-let-user-pick-image-or-video-from-gallery
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/* video/*");
                startActivityForResult(pickIntent, REQUEST_NEW_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_NEW_IMAGE) {

                    tvVideoInserted = (TextView) findViewById(R.id.tvVideoInserted);
                    tvVideoInserted.setText("You have successfully inserted a video.");




                    Uri selectedMediaUri = data.getData();

                    if (selectedMediaUri.toString().contains("video")) {
                        videoInserted = true;
                        video = selectedMediaUri.toString();
                        //http://stackoverflow.com/questions/4317665/how-to-get-thumbnail-for-video-in-my-sdcard-android-data-mypackage-files-folder
//                        thumb = ThumbnailUtils.createVideoThumbnail(selectedMediaUri.getPath(),
//                                MediaStore.Images.Thumbnails.MINI_KIND);
//
//                        System.out.println("worked");
                    }
                }
                break;
            case RESULT_CANCELED:
                System.out.println("FAILURE");
                break;
        }
    }

    private void savePlace() {

        ConcertEntry newConcert = new ConcertEntry();
        newConcert.setBandName(etBandName.getText().toString());
        newConcert.setVideo(video);
        newConcert.setVideoInserted(videoInserted);
        newConcert.setLocation(etLocation.getText().toString());
        newConcert.setDate(etDate.getText().toString());
        newConcert.setThumb(thumb);
        newConcert.setNameUser(currentUser);
        newConcert.setRating(rbGivenRating.getRating());
        newConcert.saveInBackground();




        Intent intentResult = new Intent();
        intentResult.putExtra(KEY_BAND_NAME,
                new ConcertEntry(etBandName.getText().toString(), video, videoInserted, etLocation.getText().toString(), etDate.getText().toString(), thumb, currentUser, rbGivenRating.getRating())
        );
        System.out.println("video: "+ video + "video inserted: " + videoInserted);
        setResult(RESULT_OK, intentResult);
        finish();
    }
}
