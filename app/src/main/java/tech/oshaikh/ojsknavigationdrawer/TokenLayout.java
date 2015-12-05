package tech.oshaikh.ojsknavigationdrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TokenLayout extends LinearLayout {

    public TokenLayout(Context context) {
        super(context);
    }

    public TokenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        //TODO - Fix close_x drawable issue
        super.setSelected(selected);

        TextView v = (TextView)findViewById(R.id.name);
        if (selected) {
            v.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.close_x, 0);
        } else {
            v.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
    }
}
