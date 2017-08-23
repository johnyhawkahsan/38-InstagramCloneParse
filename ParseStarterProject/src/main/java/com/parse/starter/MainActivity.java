/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());


/*
    //Save data in Parse Server
    ParseObject score = new ParseObject("Score");
    score.put("username", "Ahsan");
    score.put("score", 86);

    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {

        if (e == null){

          Log.i("SaveInBackground", "Successful");

        } else {

          Log.i("SaveInBackground", "Failed. Error: " + e.toString());

        }

      }
    });
*/

/*
    //Retrieve Data from parse
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    query.getInBackground("XNwMTiapPX", new GetCallback<ParseObject>() {
        @Override
        public void done(ParseObject object, ParseException e) {

            if (e == null && object != null){

                //If we want to update the score data, we can simply use this object to update
                object.put("score", 200);
                object.saveInBackground();//This time no call back

                Log.i("username", object.getString("username"));
                Log.i("score", String.valueOf(object.getInt("score")));

            } else {

                Log.i("ObjectRetrieveError", "Failed. Error: " + e.toString());

            }

        }
    });
*/

    //Advance Parse Queries
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");//Score class

      //Add a search criteria
      query.whereEqualTo("username", "ralphie");
      query.setLimit(1);//Return only one result

      query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, ParseException e) {

              //If there is no exception error, means everything worked
              if (e == null){

                Log.i("findInBackground", "Retrieved " + objects.size() + " objects");

                if (objects.size() > 0){

                    //We created ParseObject called object from each item in objects
                    for (ParseObject object : objects){

                        Log.i("findInBackgroundResult", object.toString());
                        Log.i("findInBackgroundStrings", object.getString("username"));
                        Log.i("findInBackgroundStrings", String.valueOf(object.getInt("score")));

                    }

                }
              }
          }
      });



  }

}



























