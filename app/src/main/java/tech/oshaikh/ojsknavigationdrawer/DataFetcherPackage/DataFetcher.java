package tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage;

import org.json.JSONObject;
import android.os.AsyncTask;


/**
 * Created by moseslee on 12/1/15.
 */
public abstract class DataFetcher extends AsyncTask<String, Integer, JSONObject> {
    protected int responseCode = 0;
    protected QueryDataInterface ref;

    //These methods are abstract from AsyncTask
    protected abstract JSONObject doInBackground(String... params);
    protected abstract void onPostExecute(JSONObject result);
    protected abstract void onProgressUpdate(Integer... progress);

    //Internal interface for classes that need to be notified of the data set
    public interface QueryDataInterface{
        void finishedParsingResults();
        void updateProgressBar(int p);
    }
}
