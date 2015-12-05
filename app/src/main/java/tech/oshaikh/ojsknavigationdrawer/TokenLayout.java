package tech.oshaikh.ojsknavigationdrawer;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
            Drawable drawable = getResources().getDrawable(R.drawable.close_x);
            drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.35),
                    (int)(drawable.getIntrinsicHeight()*0.35));

            //ScaleDrawable sd = new ScaleDrawable(drawable, 0, scaleWidth, scaleHeight);
            //v.setCompoundDrawablesWithIntrinsicBounds(0,0, drawable, 0);
            v.setCompoundDrawables(null,null, drawable, null);
        } else {
            v.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
    }
}
