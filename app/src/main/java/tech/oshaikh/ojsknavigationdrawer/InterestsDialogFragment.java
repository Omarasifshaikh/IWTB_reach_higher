package tech.oshaikh.ojsknavigationdrawer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.tokenautocomplete.TokenCompleteTextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tech.oshaikh.ojsknavigationdrawer.model.Topic;

/**
 * Created by omar on 11/29/15.
 */
public class InterestsDialogFragment extends DialogFragment implements TokenCompleteTextView.TokenListener {
    public static final String PREFS_NAME = "MyPrefsFile";
    private List<Topic> topicList;
    private static final String TAG = "Interests Dialog";
    private AutoCompleteTextView mAutocompleteView;
    //Set<String> selectedTopics = new HashSet<String>(Arrays.asList("xxx", "vvv"));
    Set<String> selectedTopics = new HashSet<String>();

    TopicsCompletionView completionView;
    Topic[] topics;
    ArrayAdapter<Topic> adapter;

    static InterestsDialogFragment newInstance() {
        return new InterestsDialogFragment();
    }

    public InterestsDialogFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_interests, null);
        builder.setView(view);

        builder.setTitle("Select Interests");


        //mAutocompleteView = (AutoCompleteTextView)
        //      view.findViewById(R.id.autocomplete_interests);



        //Load the raw JSON as a string
        String raw_jason = loadJSONFromAsset();

        //Parse the string and create a list of Topic Objects
        topicList = TopicJSONParser.parseFile(raw_jason);
        Log.d(TAG, "Done with parsing...");
        Log.d(TAG,topicList.get(1).toString());
        Log.d(TAG,topicList.get(12).toString());
        Log.d(TAG,topicList.get(13).toString());
        Log.d(TAG,topicList.get(3830).toString());


        ArrayAdapter<Topic> adapter = new ArrayAdapter<>(getContext(),
                      android.R.layout.simple_dropdown_item_1line, topicList);

        /*
        //Build a string array of just the names
        String[] arrayTopicNames = new String[topicList.size()];
        int index = 0;
        for (Topic element : topicList) {
            arrayTopicNames[index] = topicList.get(index).getName();
            index++;
        }
        */

        //create a String adapter to suggest the complete form
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
          //      android.R.layout.simple_dropdown_item_1line, arrayTopicNames);



/*
        mAutocompleteView.setAdapter(adapter);
        // Set up the 'clear text' button that clears the text in the autocomplete view
        Button clearButton = (Button) view.findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutocompleteView.setText("");
            }
        });

*/
    /*
        topics = new Topic[]{
                new Topic(0, "testTopic1"),
                new Topic(1, "testTopic2"),
                new Topic(2, "testTopic3"),
                new Topic(3, "testTopic4"),
                new Topic(4, "testTopic5"),
                new Topic(5, "testTopic6")
        };
        */
        //adapter = new ArrayAdapter<Topic>(this.getContext(), android.R.layout.simple_dropdown_item_1line, topics);
        /*
        adapter = new FilteredArrayAdapter<Topic>(this.getContext(), R.layout.topic_layout, topics) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.topic_layout, parent, false);
                }

                Topic t = getItem(position);
                ((TextView)convertView.findViewById(R.id.name)).setText(t.getId());
                ((TextView)convertView.findViewById(R.id.email)).setText(t.getName());

                return convertView;
            }

            @Override
            protected boolean keepObject(Topic t, String mask) {
                mask = mask.toLowerCase();
                return t.getId().toLowerCase().startsWith(mask) || t.getName().toLowerCase().startsWith(mask);
            }
        };
        */

        completionView = (TopicsCompletionView)view.findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);




        SharedPreferences settings = this.getContext().getSharedPreferences(PREFS_NAME, 0);
        //boolean silent = settings.getBoolean("silentMode", false);
        //this.getContext().setSilent(silent);


        //SharedPreferences pref = this.getContext().getSharedPreferences("careerApp", Context.MODE_PRIVATE);
        selectedTopics = settings.getStringSet("topicsSet",null);



/*
        //Abhijits <code></code>
        SharedPreferences mValuePrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mValuePrefs.edit();
        editor.clear();
        editor.putInt("seekbar", 80); // dont hardcode
        editor.apply();

*/

        //PreferenceManager.getDefaultSharedPreferences(this.getContext()).getStringSet("topicsSet",selectedTopics);


        //PRINT SET DEBUGGGG
        for (Iterator<String> it = selectedTopics.iterator(); it.hasNext(); ) {
            String f = it.next();
            Log.d("SET",f);
        }

        if (savedInstanceState == null) {
            //TODO - load stored interests here
            //completionView.setPrefix("Interests: ");
            /*
            completionView.addObject(topics[0]);
            completionView.addObject(topics[1]);
            */
            //completionView.addObject(new Topic(0,selectedTopics.));

            //PRINT SET DEBUGGGG
            for (Iterator<String> it = selectedTopics.iterator(); it.hasNext(); ) {
                String f = it.next();
                Log.d("SET",f);
            }

            for (Iterator<String> it = selectedTopics.iterator(); it.hasNext(); ) {
                String f = it.next();
                /*
                if (f.equals(new Foo("Hello")))
                    System.out.println("foo found");
                    */
                completionView.addObject(new Topic(0,f));

            }

        }




        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button



            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public String loadJSONFromAsset() {
    String json;
    try {

        InputStream is = getContext().getAssets().open("topics.json");

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

    @Override
    public void onTokenAdded(Object token) {

        //TODO  -  add string to shared prefernces here...
        Log.d("general", "Token Added");
        //PRINT SET DEBUGGGG
        for (Iterator<String> it = selectedTopics.iterator(); it.hasNext(); ) {
            String f = it.next();
            Log.d("SET",f);
        }

        selectedTopics.add(token.toString());

        /*
        SharedPreferences pref = this.getContext().getSharedPreferences("careerApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("topicsSet", selectedTopics);
        editor.commit();
*/



        //PreferenceManager.getDefaultSharedPreferences(this.getContext()).edit().putString("MYLABEL", "myStringToSave").commit();

        //PreferenceManager.getDefaultSharedPreferences(this.getContext()).edit().putStringSet("topicsSet",selectedTopics).apply();
    }

    @Override
    public void onTokenRemoved(Object token) {

        SharedPreferences settings = this.getContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        selectedTopics.remove(token.toString());
        editor.putStringSet("topicsSet",null);
        editor.putStringSet("topicsSet",selectedTopics);
        //editor.putBoolean("silentMode", mSilentMode);

        // Commit the edits!
        editor.apply();
    }

}
