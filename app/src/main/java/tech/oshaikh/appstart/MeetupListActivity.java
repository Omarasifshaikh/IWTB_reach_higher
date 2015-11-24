package tech.oshaikh.appstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import tech.oshaikh.appstart.common.ActivityBase;


/**
 * Created by moseslee on 11/22/15.
 */
public class MeetupListActivity extends ActivityBase
        implements MeetupListItemAdapter.ListItemClickListener, MeetupDataFetcher.QueryDataInterface {
    private ArrayList<String> meetupList;
    private ArrayList<String> urlList;
    private RecyclerView.Adapter listAdapter;
    private AutoCompleteTextView categoryText;
    private ProgressBar searchProgress;
    private String category;

    private Intent _intent;
    private Context _context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        _intent = getIntent();
        _context = getApplicationContext();
        meetupList = new ArrayList<>();
        urlList = new ArrayList<>();

        //ALWAYS SET CONTENT VIEW
        setContentView(R.layout.meetup_layout);
        RecyclerView listView = (RecyclerView) findViewById(R.id.meetUpListView);
        listView.setHasFixedSize(true);
        //Sets the view vertically
        listView.setLayoutManager(new LinearLayoutManager(this));

        categoryText = (AutoCompleteTextView) findViewById(R.id.category_text);
        categoryText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                populateMenuList();
                                            }
                                        }
        );

        searchProgress = (ProgressBar) findViewById(R.id.searchProgress);
        searchProgress.setVisibility(View.GONE);

        listAdapter = new MeetupListItemAdapter(meetupList, urlList,this, _context);
        listView.setAdapter(listAdapter);
    }

    //For the list
    @Override
    public void onItemClicked(int position) {

        Log.d("List Item Clicked", "Meetup");
        //CardView card = new CardView();
    }

    //Clean up
    public void finishActivity() {
        //do something here before finishing if needed
        finish();
    }

    //NEEDS TO BE DECLARED IF NOT MAIN ACTIVITY
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED, _intent);
        finishActivity();
    }

    public void populateMenuList() {
        //Querying meet up
        searchProgress.setVisibility(View.VISIBLE);
        category = categoryText.getText().toString();
        meetupList.clear();
        urlList.clear();
        MeetupDataFetcher md = new MeetupDataFetcher(meetupList, urlList, this, category);
        md.execute();
    }

    //For the meetup interface
    public void finishedParsingResults(){
        searchProgress.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
    }

    public void updateProgressBar(int p){
        searchProgress.setProgress(p);
    }
}
