package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        //Add image programmatically to Linear Layout
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();

        String activeUsername = intent.getStringExtra("username");

        //Set title to active username
        setTitle(activeUsername + "'s Feed");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", activeUsername);//Get the data of active username
        query.orderByDescending("createdAt");//this is the date and we want to show the newest first

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null){

                    if (list.size() > 0){

                        for (ParseObject object : list){

                            ParseFile file = (ParseFile) object.get("image");//image is the field

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {

                                    if (e == null && bytes != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                        //We're creating new image view instead of getting/finding as linear layout above
                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                        ));

                                        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.instagram));//That was for testing and it showed one image from our drawable folder
                                        imageView.setImageBitmap(bitmap);//Set the bitmap that we got from parse here

                                        linearLayout.addView(imageView);

                                    }

                                }
                            });
                        }
                    }
                }

            }
        });





    }
}
