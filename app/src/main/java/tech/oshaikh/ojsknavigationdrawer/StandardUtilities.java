package tech.oshaikh.ojsknavigationdrawer;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by moseslee on 12/1/15.
 *
 * This is just a utility class to manage some basic system stuff
 */
public class StandardUtilities {
    public  StandardUtilities(){

    }

    //Hide the keyboard when necessary
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //Show the keyboard when neccessary
    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(activity.getCurrentFocus(), 0);
    }
}
