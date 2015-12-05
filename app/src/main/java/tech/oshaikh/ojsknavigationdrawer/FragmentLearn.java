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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage.LearnDataFetcherSet;
import tech.oshaikh.ojsknavigationdrawer.ListPackage.LearnListItemAdapter;


public class FragmentLearn extends Fragment implements LearnListItemAdapter.ListItemClickListener , LearnDataFetcherSet.QueryDataInterface {

    private ArrayList<String> tutorialList;
    private ArrayList<String> urlList;
    public static final String PREFS_NAME = "MyPrefsFile";
    private RecyclerView.Adapter listAdapter;
    private AutoCompleteTextView categoryText;
    private ProgressBar searchProgress;
    Set<String> selectedTopics = new HashSet<String>();
    private String category = "";

    private Context _context;

    public FragmentLearn() {
        // empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tutorialList = new ArrayList<>();
        urlList = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _context = this.getContext();

        SharedPreferences settings = this.getContext().getSharedPreferences(PREFS_NAME, 0);
        selectedTopics = settings.getStringSet("topicsSet",null);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.learnListView);
        listView.setHasFixedSize(true);
        //Sets the view vertically
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
/*
        categoryText = (AutoCompleteTextView) view.findViewById(R.id.learn_category_text);
        categoryText.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                populateMenuList();
                                            }
                                        }
        );
*/

        searchProgress = (ProgressBar) view.findViewById(R.id.learnSearchProgress);
        searchProgress.setVisibility(View.GONE);

        listAdapter = new LearnListItemAdapter(tutorialList, urlList, this, _context);
        listView.setAdapter(listAdapter);
        populateMenuList();
        StandardUtilities.showSoftKeyboard(this.getActivity());
    }

    public void populateMenuList() {

        //Querying
        /*
        String prevSearch = category;
        category = categoryText.getText().toString();

        if(category.length() == 0 || prevSearch.equals(category))
            return;
            */

        searchProgress.setVisibility(View.VISIBLE);
        tutorialList.clear();
        urlList.clear();
        //LearnDataFetcher md = new LearnDataFetcher(tutorialList, urlList, this, category);
        LearnDataFetcherSet mdSet = new LearnDataFetcherSet(tutorialList, urlList, this, selectedTopics);
        mdSet.execute();
    }

    @Override
    public void onItemClicked(int position) {
        Log.d("List Item Clicked", "Learn");
    }

    @Override
    public void finishedParsingResults() {

        searchProgress.setVisibility(View.GONE);
        listAdapter.notifyDataSetChanged();
        StandardUtilities.hideSoftKeyboard(this.getActivity());
    }

    @Override
    public void updateProgressBar(int p) { searchProgress.setProgress(p); }
}