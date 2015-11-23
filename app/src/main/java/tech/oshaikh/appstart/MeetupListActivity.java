package tech.oshaikh.appstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import tech.oshaikh.appstart.common.ActivityBase;


/**
 * Created by moseslee on 11/22/15.
 */
public class MeetupListActivity extends ActivityBase
        implements MeetupListItemAdapter.ListItemClickListener {
    private ArrayList<String> meetupList;
    private RecyclerView.Adapter listAdapter;

    private Intent _intent;
    private Context _context;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        _intent = getIntent();
        _context = getApplicationContext();
        meetupList = new ArrayList<>();

        //ALWAYS SET CONTENT VIEW
        setContentView(R.layout.meetup_layout);
        RecyclerView listView = (RecyclerView) findViewById(R.id.meetUpListView);
        listView.setHasFixedSize(true);
        //Sets the view vertically
        listView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new MeetupListItemAdapter(meetupList, this, _context);
        listView.setAdapter(listAdapter);

        //Just for now.
        addFakeItems();
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

    //This is just to add fake items to the list
    private void addFakeItems(){
        meetupList.add("Hackathon");
        meetupList.add("Computer");
        meetupList.add("Drink and Code");
        meetupList.add("Code and Code");
        meetupList.add("Code and Drink");
        meetupList.add("Biology");
        meetupList.add("Golf and Meet");
        meetupList.add("Best and Meet");
        meetupList.add("Omar's Astronomy");
        meetupList.add("Jason's HPC");
    }
}
