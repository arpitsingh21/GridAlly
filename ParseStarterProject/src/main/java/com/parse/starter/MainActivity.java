/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.mvc.imagepicker.ImagePicker;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
int count=0;
    private static final int RESULT_LOAD_IMG = 1;
Button log,signup;
  EditText email,pass,cell;
    CircleImageView imageView;
ProgressBar progressBar,mProgressBar;

    String imgDecodableString;
    RelativeLayout rl;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

mProgressBar=(ProgressBar)findViewById(R.id.google_music_dices);
      imageView = (CircleImageView) findViewById(R.id.image_view);
      ImagePicker.setMinQuality(600, 600);
      if(ParseUser.getCurrentUser()!=null)

      {
          Intent i = new Intent(getApplicationContext(), HomeActivity.class);
          startActivity(i);

          finish();
      }

      log=(Button)findViewById(R.id.button);
      rl=(RelativeLayout)findViewById(R.id.relative);
      rl.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              InputMethodManager im=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
              im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
          }
      });
    signup=(Button)findViewById(R.id.button2);
    email=(EditText)findViewById(R.id.email);
    pass=(EditText)findViewById(R.id.password);
    cell=(EditText)findViewById(R.id.cell);
      cell.setVisibility(View.INVISIBLE);
      log.setOnClickListener(new View.OnClickListener() {


          @Override
          public void onClick(View view) {
//              mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(getApplicationContext()).build());

              //CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
             // progressView.startAnimation();
              if(email.getText().toString().matches("")||pass.getText().toString().matches("")) {
                  Toast.makeText(MainActivity.this, "Email and password Required", Toast.LENGTH_SHORT).show();
              }
              else {

                  log.setClickable(false);
                  String s = email.getText().toString();
                  String s1 = pass.getText().toString();

                  final ProgressDialog progress = new ProgressDialog(MainActivity.this);
                  progress.setMessage("Signing In....");
                  progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                  progress.setIndeterminate(true);
progress.show();






                  ParseUser.logInInBackground(s, s1, new LogInCallback() {
    @Override
    public void done(ParseUser user, ParseException e) {
        if(user!=null) {
//mProgressDialog.dismiss();
progress.dismiss();
            Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);


        }
    else
            Toast.makeText(MainActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
    log.setClickable(true);

    }
});
               //   progressView.stopAnimation();
              }
          }
      });
      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              count++;
             if(count==1)
             {
                 cell.setVisibility(View.VISIBLE);
             }
              if(count==2)
              {
                if(imgDecodableString==null) {
                    String s = email.getText().toString();
                    String s1 = pass.getText().toString();
                    String s2 = cell.getText().toString();
                    ParseUser pu = new ParseUser();
                    pu.setEmail(s2);
                    pu.setPassword(s1);
                    pu.setUsername(s);

                    pu.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {
                                Toast.makeText(MainActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                  else
                {


                    Bitmap bit=BitmapFactory
                            .decodeFile(imgDecodableString);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] image = stream.toByteArray();

                    final String s = email.getText().toString();
                    String s1 = pass.getText().toString();
                    String s2 = cell.getText().toString();
                    ParseUser pu = new ParseUser();
                    pu.setEmail(s2);
                    pu.setPassword(s1);
                 // pu.put("ProfilePicture",image);
                    pu.setUsername(s);

                    pu.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseFile file=new ParseFile("dp.png",image);

                            file.saveInBackground();

                            // Create a New Class called "ImageUpload"
                            // in Parse
                            final ParseObject imgupload = new ParseObject(
                                    "ProfilePicture");

                            // Create a column named "ImageName" and set
                            // the string
                            imgupload.put("username",
                                    s);

                            // Create a column named "ImageFile" and
                            // insert the image
                            imgupload.put("Picture", file);

                            // Create the class and the columns
                            imgupload.saveInBackground();


                            if (e == null) {
                                Toast.makeText(MainActivity.this, "Signup Successful with image", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

                  count=0;
                  cell.setVisibility(View.INVISIBLE);
              }

          }
      });

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.image_view);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    public void onPickImage(View view) {

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

}