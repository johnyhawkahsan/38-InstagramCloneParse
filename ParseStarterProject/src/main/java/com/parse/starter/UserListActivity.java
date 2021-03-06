package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {


    //This method will launch external intent to select an image
    public void getPhoto(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }

    //When user selects the image, this method defines what to do with the selected data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);//Convert data to Bitmap data

                Log.i("Photo", "Received");//If log shows this, this means image data is successful

                //In order to send it in ParseObject form, it needs to be translated to ByteArray
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);//Compress using png format

                byte[] byteArray = stream.toByteArray();//Convert byteArray stream data to byteArray

                ParseFile file = new ParseFile("image.png", byteArray);//In order to get a ParseFile, we need to pass byteArray, that's why we need byteArray in the first place

                ParseObject object = new ParseObject("Image");//Create class images

                object.put("image", file);//Save file(parseobject)

                object.put("username", ParseUser.getCurrentUser().getUsername());//Save username of the user who's posting the image as well

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){

                            Toast.makeText(UserListActivity.this, "Image Shared!", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(UserListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    //Create menu after creating directory "menu" in resources and add share_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);//share_menu is the name of the menu in menu resource directory

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //When item id is equal to share button
        if (item.getItemId() == R.id.share){

            //Because check for permission is added in and after Android M Marshmallow API 23
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }

            } else {

                getPhoto();

            }

            //If user clicks logout, log him out of parse and start main activity
        } else if (item.getItemId() == R.id.logout){

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    //When user grants permission to user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //If user grants permission on the first run, instantly launch getPhoto method to select image.
                getPhoto();

            }

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Feed");

        final ArrayList<String> usernames = new ArrayList<String>();

        final ListView userListView = (ListView) findViewById(R.id.userListView);

        //When clicked on list item username, redirect to user feed
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);

                intent.putExtra("username", usernames.get(position));//Send clicked username in intent put extra

                startActivity(intent);

            }
        });

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);

        userListView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());//Don't display current user in the list

        query.addAscendingOrder("username");

        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseUser user : objects) {

                            usernames.add(user.getUsername());

                        }

                        arrayAdapter.notifyDataSetChanged();
                    }

                } else {

                    e.printStackTrace();

                }

            }
        });


    }


}
