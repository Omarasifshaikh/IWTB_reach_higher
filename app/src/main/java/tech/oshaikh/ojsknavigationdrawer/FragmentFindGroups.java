package tech.oshaikh.ojsknavigationdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage.*;
import tech.oshaikh.ojsknavigationdrawer.ListPackage.ListAdapter;
import tech.oshaikh.ojsknavigationdrawer.ListPackage.MeetupListItemAdapter;

public class FragmentFindGroups extends Fragment
        implements ListAdapter.ListItemClickListener,
                DataFetcher.QueryDataInterface {


    private ArrayList<String> meetupList;
    private ArrayList<String> urlList;
    private RecyclerView.Adapter listAdapter;
    private AutoCompleteTextView categoryText;
    private ProgressBar searchProgress;
    private String category = "";
    private Context _context;
    private String query;
    public static final String PREFS_NAME = "MyPrefsFile";
    Set<String> selectedTopics = new HashSet<String>();


    public FragmentFindGroups() {
        // empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        meetupList = new ArrayList<>();
        urlList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_do, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _context = this.getContext();
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.meetUpListView);

        listView.setHasFixedSize(true);
        //Sets the view vertically
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
/*
        categoryText = (AutoCompleteTextView) view.findViewById(R.id.category_text);
        categoryText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                populateMenuList();
                                            }
                                        }
        );*/
        SharedPreferences settings = this.getContext().getSharedPreferences(PREFS_NAME, 0);
        selectedTopics = settings.getStringSet("topicsSet",null);

        //PRINT SET DEBUGGGG
        for (Iterator<String> it = selectedTopics.iterator(); it.hasNext(); ) {
            String f = it.next();


            category = category + " " + f;
            Log.d("SET",f);
            Log.d("category is now: ",category);
        }

        try {
            query = URLEncoder.encode(category, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("the encoded string", query);

        searchProgress = (ProgressBar) view.findViewById(R.id.searchProgress);
        searchProgress.setVisibility(View.GONE);

        listAdapter = new MeetupListItemAdapter(meetupList, urlList,this, _context);
        listView.setAdapter(listAdapter);
        populateMenuList();
        StandardUtilities.showSoftKeyboard(this.getActivity());

    }

    public void populateMenuList() {
        /*
        //Querying meet up
        String prevString = category;
        category = categoryText.getText().toString();

        //If its nothing or the same topic, dont do anything
        if (category.length() == 0 || prevString.equals(category))
            return;
        */
        searchProgress.setVisibility(View.VISIBLE);
        meetupList.clear();
        urlList.clear();
        MeetupDataFetcher md = new MeetupDataFetcher(meetupList, urlList, this, query);
        md.execute();
    }
    @Override
    public void onItemClicked(int position) {
        Log.d("List Item Clicked", "Meetup");
        //CardView card = new CardView();
    }

    //For the interface after aync task
    @Override
    public void finishedParsingResults() {
        searchProgress.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
        StandardUtilities.hideSoftKeyboard(this.getActivity());
    }

    @Override
    public void updateProgressBar(int p) { searchProgress.setProgress(p); }
}
