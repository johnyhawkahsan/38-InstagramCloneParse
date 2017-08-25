package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User Feed");

        final ArrayList<String> usernames = new ArrayList<String>();

        final ListView userListView = (ListView) findViewById(R.id.userListView);

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
