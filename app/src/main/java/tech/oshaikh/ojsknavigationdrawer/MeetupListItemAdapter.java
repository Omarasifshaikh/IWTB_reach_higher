package tech.oshaikh.ojsknavigationdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by moseslee on 11/22/15.
 */
public class MeetupListItemAdapter extends RecyclerView.Adapter<MeetupListItemAdapter.ViewHolder> {

    interface ListItemClickListener {
        void onItemClicked(int position);
    }

    private ArrayList<String> itemList;
    private ArrayList<String> listOfUrl;
    private ListItemClickListener listener;
    private Context parentContext;
    private int expandedPosition = -1;

    //Extend this abstract class
    public class ViewHolder extends RecyclerView.ViewHolder{
        /*
         * Each data item is just a string
         */
        public TextView itemNameView;
        public TextView expandedView;
        public LinearLayout expandedLayout;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                     * When an item is clicked by the user, a listener interface
                     * is called. Since a ViewHolder can represent different
                     * items over its lifetime, method getAdapterPosition() is
                     * used to determine the current position that this
                     * ViewHolder represents.
                     */
                    listener.onItemClicked(getAdapterPosition());
                    expandCard(getAdapterPosition());
                }
            });
            itemNameView = (TextView) v.findViewById(R.id.info_text);
            expandedView = (TextView) v.findViewById(R.id.info_text_expanded);
            expandedLayout = (LinearLayout) v.findViewById(R.id.expanded_layout);

        }
    }

    public MeetupListItemAdapter(ArrayList<String> itemList, ArrayList<String> listOfUrl,ListItemClickListener listener, Context parent) {
        this.itemList = itemList;
        this.listener = listener;
        this.parentContext = parent;
        this.listOfUrl = listOfUrl;
    }

    @Override
    public MeetupListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*
         * Create a new ViewHolder by inflating a layout-resource.
         */
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetup_cardview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
         * This method will be called whenever a ViewHolder should be populated
         * for a particular item. The data that the ViewHolder will show will be
         * retrieved from the underlying data model (list of strings in this
         * case).
         */
        holder.itemNameView.setText(itemList.get(position));

        if(expandedPosition == position){
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.expandedView.setText(listOfUrl.get(position));
        } else {
            holder.expandedLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void expandCard(int pos){

        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }

        if(expandedPosition == pos) {
            expandedPosition = -1;
        } else {
            expandedPosition = pos;
        }

        notifyItemChanged(expandedPosition);
    }

}
