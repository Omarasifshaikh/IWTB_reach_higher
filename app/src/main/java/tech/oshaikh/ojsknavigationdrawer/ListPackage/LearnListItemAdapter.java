package tech.oshaikh.ojsknavigationdrawer.ListPackage;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Jason Burmark on 11/24/15.
 */
public class LearnListItemAdapter extends ListAdapter {

    public LearnListItemAdapter(ArrayList<String> itemList, ArrayList<String> listOfUrl, ListItemClickListener listener, Context parent) {
        this.itemList = itemList;
        this.listener = listener;
        this.parentContext = parent;
        this.listOfUrl = listOfUrl;
    }
}
