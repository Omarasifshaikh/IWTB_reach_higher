package tech.oshaikh.ojsknavigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import tech.oshaikh.ojsknavigationdrawer.model.Topic;

public class TopicsCompletionView extends TokenCompleteTextView<Topic> {

    public TopicsCompletionView(Context context) {
        super(context);
    }

    public TopicsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopicsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View getViewForObject(Topic t) {

        LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout)l.inflate(R.layout.topic_token, (ViewGroup) TopicsCompletionView.this.getParent(), false);
        ((TextView)view.findViewById(R.id.name)).setText(t.getName());

        return view;
    }

    @Override
    protected Topic defaultObject(String completionText) {
        return new Topic(5, completionText);
        /*
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new Topic(5, completionText.replace(" ", "") + "@example.com");
        } else {
            return new Topic(5, completionText);
        }
        */
    }
}
