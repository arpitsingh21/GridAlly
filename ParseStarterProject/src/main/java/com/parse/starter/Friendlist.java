package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.provider.BasicImageButtonsCardProvider;

import com.dexafree.materialList.view.MaterialListView;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Friendlist extends Fragment {

    RecyclerviewAdapter adapter;
    RecyclerView recyclerView;
    private static List<Model> demoData;
    //final  Bitmap []bitmaps= new Bitmap[100];
   Bitmap bitmap;
    public Friendlist() {



        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.listview, container,
                false);
        recyclerView = (RecyclerView)view.findViewById(R.id.myList);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        demoData = new ArrayList<Model>();


        ParseQuery<ParseObject> query= ParseQuery.getQuery("ProfilePicture");
       query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0) {
                        final int count=0;


                        //String [] array =new String[objects.size()];
                        for (ParseObject user : objects) {


try {
    final ParseFile pf = user.getParseFile("Picture");

    String str=pf.getUrl();
if(pf.getUrl().matches(ParseUser.getCurrentUser().getUsername()))
{
    Picasso.with(getActivity()).load(str).into(new HomeActivity().User);

}

    Model model = new Model("Active 1 hr ago",user.getString("username"),str);


    demoData.add(model);


    // array[count]=user.getUsername();
//                            Toast.makeText(getActivity(),user.getUsername()+""+objects.size(), Toast.LENGTH_SHORT).show();
}catch (NullPointerException n)
{

    n.printStackTrace();
}//count++;
                        }
  //                      ArrayAdapter<String> arr= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,array);
                        adapter = new RecyclerviewAdapter(demoData,getActivity());
                        recyclerView.setAdapter(adapter);


//                        setListAdapter(arr);


                    }
                }
                else {

                    if (e.getMessage().matches("i/o failure")) {
                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
















        return view;
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

}
