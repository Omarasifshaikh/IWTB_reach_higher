package tech.oshaikh.ojsknavigationdrawer;

import android.content.Context;
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

import java.util.ArrayList;


public class FragmentDo extends Fragment implements MeetupListItemAdapter.ListItemClickListener, MeetupDataFetcher.QueryDataInterface {


    private ArrayList<String> meetupList;
    private ArrayList<String> urlList;
    private RecyclerView.Adapter listAdapter;
    private AutoCompleteTextView categoryText;
    private ProgressBar searchProgress;
    private String category;

    private Context _context;


    public FragmentDo() {
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

        categoryText = (AutoCompleteTextView) view.findViewById(R.id.category_text);
        categoryText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                populateMenuList();
                                            }
                                        }
        );

        searchProgress = (ProgressBar) view.findViewById(R.id.searchProgress);
        searchProgress.setVisibility(View.GONE);

        listAdapter = new MeetupListItemAdapter(meetupList, urlList,this, _context);
        listView.setAdapter(listAdapter);

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
    @Override
    public void onItemClicked(int position) {
        Log.d("List Item Clicked", "Meetup");
        //CardView card = new CardView();
    }

    @Override
    public void finishedParsingResults() {
        searchProgress.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateProgressBar(int p) { searchProgress.setProgress(p); }
}
