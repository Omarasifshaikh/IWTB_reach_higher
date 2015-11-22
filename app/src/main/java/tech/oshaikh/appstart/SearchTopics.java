package tech.oshaikh.appstart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tech.oshaikh.appstart.common.ActivityBase;
import tech.oshaikh.appstart.model.Topic;

public class SearchTopics extends ActivityBase {


    private AutoCompleteTextView mAutocompleteView;

    private TextView mPlaceDetailsText;

    private TextView mPlaceDetailsAttribution;

    private List<Topic> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_search_topics);

        // Retrieve the AutoCompleteTextView that will display Topic suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places);



        //Load the raw JSON as a string
        String raw_jason = loadJSONFromAsset();

        //Parse the string and create a list of Topic Objects
        topicList = TopicJSONParser.parseFile(raw_jason);
        Log.d(TAG, "Done with parsing...");
        Log.d(TAG,topicList.get(1).toString());
        Log.d(TAG,topicList.get(12).toString());
        Log.d(TAG,topicList.get(13).toString());
        Log.d(TAG,topicList.get(3830).toString());



        //Build a string array of just the names
        String[] arrayTopicNames = new String[topicList.size()];
        int index = 0;
        for (Topic element : topicList) {
            arrayTopicNames[index] = topicList.get(index).getName();
            index++;
        }

        //create a String adapter to suggest the auto complete form
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, arrayTopicNames);

        mAutocompleteView.setAdapter(adapter);



        // Set up the 'clear text' button that clears the text in the autocomplete view
        Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutocompleteView.setText("");
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("topics.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
