package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.dummy.DummyContent;
import com.parse.starter.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>

 * interface.
 */
public class Feeds extends Fragment {
    private ArrayList<String> images;
    private ArrayList<String> text;
    //    private ProgressDialog pDialog;
    private FeedsAdapter mAdapter;
    private RecyclerView recyclerView;



    public Feeds() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container,
                false);

        recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view2);
//final ImageView im =(ImageView)view.findViewById(R.id.new1);

        images = new ArrayList<>();
text=new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        images.add("http://ec2-13-126-36-210.ap-south-1.compute.amazonaws.com/parse/files/714c61afd4a124fbc19bc26d55e3cb10a514adfb/94c1cc0affc91ea39cd180718f89a731_dp.png");
        text.add("this is url");

        ParseQuery<ParseUser> query=ParseUser.getQuery();
        try {
            int n=query.count();
            Toast.makeText(getActivity(), n+"", Toast.LENGTH_SHORT).show();
query.findInBackground(new FindCallback<ParseUser>() {
    @Override
    public void done(List<ParseUser> objects, ParseException e) {
if(e==null) {

    if(objects.size()>0) {
        for (final ParseUser user : objects) {

            ParseQuery<ParseObject> query=new ParseQuery<ParseObject>(user.getUsername());
            // query.addAscendingOrder("updatedAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null){

                        if(objects.size()>0)
                        {
                            try {
                                for (ParseObject object : objects) {
                                    ParseFile file = (ParseFile) object.getParseFile("Picture");
                                    String st = file.getUrl();
            //                         Toast.makeText(getActivity(), st + "", Toast.LENGTH_SHORT).show();
                                    images.add(st);
                                     text.add(user.getUsername());

                                }



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

        }
        mAdapter = new FeedsAdapter(getActivity().getApplicationContext(), images,text);
        recyclerView.setAdapter(mAdapter);


    }
    }



    }
});


        } catch (ParseException e) {
            e.printStackTrace();
        }


       // mAdapter = new FeedsAdapter(getActivity().getApplicationContext(), images,text);
        //recyclerView.setAdapter(mAdapter);



        return view;
    }
}
