package tech.oshaikh.ojsknavigationdrawer.ListPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.oshaikh.ojsknavigationdrawer.R;

/**
 * Created by moseslee on 12/1/15.
 */
public abstract class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public interface ListItemClickListener {
        void onItemClicked(int position);
    }

    protected ListItemClickListener listener;
    protected ArrayList<String> itemList;
    protected ArrayList<String> listOfUrl;
    protected Context parentContext;
    protected int expandedPosition = -1;

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

    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    protected void expandCard(int pos){
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
