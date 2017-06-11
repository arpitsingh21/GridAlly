package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.clans.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Upload extends Fragment {
    private String TAG = HomeActivity.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<String> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    Image image;
    private FloatingActionButton mFab;
    int count=1;
    private static final int RESULT_LOAD_IMG = 1;
    //Gallery galleryView;
    ImageView imgView;
    String imgDecodableString="";
  //  Bitmap[] bitmaps;


    //    ImageView selectedImage;
    public Upload() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container,
                false);



        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
//final ImageView im =(ImageView)view.findViewById(R.id.new1);

        images = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


     //   fetchImages();


        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>(ParseUser.getCurrentUser().getUsername());
       // query.addAscendingOrder("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){

                    if(objects.size()>0)
                    {
try {

    Toast.makeText(getActivity(), objects.size() + "", Toast.LENGTH_SHORT).show();

    for (ParseObject object : objects) {
        // Toast.makeText(getActivity(), count + "", Toast.LENGTH_SHORT).show();


        ParseFile file = (ParseFile) object.getParseFile("Picture");

        String st = file.getUrl();


        //  Drawable d =Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png").into(im);

        //
//m.setImageDrawable(drawableTest);
        Toast.makeText(getActivity(), st + "", Toast.LENGTH_SHORT).show();
        images.add(st);

    }
    mAdapter = new GalleryAdapter(getActivity().getApplicationContext(), images);
    recyclerView.setAdapter(mAdapter);
    Toast.makeText(getActivity(), images.size() + "", Toast.LENGTH_SHORT).show();

}
catch (Exception n){
    n.printStackTrace();
}


                    }
                    else
                    {

                    }
                }
            }
        });



       // Toast.makeText(getActivity(),mAdapter.toString()+ "", Toast.LENGTH_SHORT).show();

        return view;
    }

    private void fetchImages() {

//        mAdapter.notifyDataSetChanged();
        //AppController.getInstance().addToRequestQueue(req);
       // Toast.makeText(getActivity(), mAdapter.toString()+"", Toast.LENGTH_SHORT).show();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }


    class GetImage extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        // Get the cursor
        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        Bitmap b= BitmapFactory
                .decodeFile(imgDecodableString);
        final ParseObject imgupload = new ParseObject(
                ParseUser.getCurrentUser().getUsername());

        // Create a column named "ImageName" and set
        // the string
       // imgupload.put("count",
         //       ++count+"");

        // Create a column named "ImageFile" and
        // insert the image
        imgupload.put("Picture",b);

        // Create the class and the columns
        imgupload.saveInBackground();




    }





}









