package tech.oshaikh.ojsknavigationdrawer.ListPackage;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by moseslee on 11/22/15.
 */
public class MeetupListItemAdapter extends ListAdapter {

    public MeetupListItemAdapter(ArrayList<String> itemList, ArrayList<String> listOfUrl,ListItemClickListener listener, Context parent) {
        this.itemList = itemList;
        this.listener = listener;
        this.parentContext = parent;
        this.listOfUrl = listOfUrl;
    }
}
