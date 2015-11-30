package tech.oshaikh.ojsknavigationdrawer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import tech.oshaikh.ojsknavigationdrawer.model.Topic;

/**
 * Created by omar on 11/29/15.
 */
public class InterestsDialogFragment extends DialogFragment {
    private List<Topic> topicList;
    private static final String TAG = "Interests Dialog";
    private AutoCompleteTextView mAutocompleteView;

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


        mAutocompleteView = (AutoCompleteTextView)
                view.findViewById(R.id.autocomplete_interests);

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
        Log.d(TAG,"sorted the list");


        //create a String adapter to suggest the complete form
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, arrayTopicNames);
        Log.d(TAG,"Setting Adapter");
        mAutocompleteView.setAdapter(adapter);
        // Set up the 'clear text' button that clears the text in the autocomplete view
        Button clearButton = (Button) view.findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutocompleteView.setText("");
            }
        });






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

}
