package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
ImageView logout;
    String imgDecodableString="";
    CircleImageView User;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    FloatingActionButton mFab;
    static final int RESULT_LOAD_IMG=1;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.friendsi,
            R.drawable.guest
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User=(CircleImageView)findViewById(R.id.User);
        try {


            ParseQuery<ParseObject> query=ParseQuery.getQuery("ProfilePicture");
            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null)
                    {
                        if(objects.size()>0)
                        {

                            for(ParseObject object:objects)
                            {
                                ParseFile file=object.getParseFile("Picture");
                                Picasso.with(HomeActivity.this).load(file.getUrl()).into(User);
//                                Toast.makeText(HomeActivity.this, object.toString()+"", Toast.LENGTH_SHORT).show();

                            }


                        }
                        else{}

                    }
                    else{}


                }
            });


        }
        catch (Exception e)
        {
            // Toast.makeText(HomeActivity.this,e.toString()+"", Toast.LENGTH_LONG).show();
        }


        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 //               Toast.makeText(HomeActivity.this,"Hello", Toast.LENGTH_LONG).show();
            }
        });













        logout=(ImageView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent i= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        finish();
                        final ProgressDialog progress = new ProgressDialog(HomeActivity.this);
                        progress.setMessage("Signing In....");
                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progress.setIndeterminate(true);
                        progress.show();


                        Toast.makeText(HomeActivity.this, "User Logged out Successfully", Toast.LENGTH_SHORT).show();





                    }
                });


            }
        });



       // ParseUser.getCurrentUser().getParseFile("Images");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        mFab = (FloatingActionButton)findViewById(R.id.fab1);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });




        //initdataset();



    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Feeds(),"");
        adapter.addFrag(new Friendlist(), "");
        adapter.addFrag(new Upload(), "");
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
switch (position) {
    case 0: setTitle("Home");

        break;
    case 1: setTitle("Friends");
break;
    case 2 : setTitle("My Uploads");
}
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
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


                Bitmap b=BitmapFactory
                        .decodeFile(imgDecodableString);


                final ParseObject imgupload = new ParseObject(ParseUser.getCurrentUser().getUsername());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] image = stream.toByteArray();
                ParseFile file=new ParseFile("dp.png",image);
                file.saveInBackground();
                imgupload.put("username", ParseUser.getCurrentUser().getUsername());
                imgupload.put("Picture",file);
                imgupload.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                    if(e==null)
                        Toast.makeText(HomeActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Toast.makeText(HomeActivity.this,e.getMessage()+ "", Toast.LENGTH_SHORT).show();
                    }
                    }
                });




            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong"+e.toString(), Toast.LENGTH_LONG)
                    .show();
        }

    }

}
