package tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage;

import android.os.AsyncTask;

import org.json.JSONArray;


/**
 * Created by moseslee on 12/1/15.
 */
public abstract class DataFetcherSet extends AsyncTask<String, Integer, JSONArray> {
    protected int responseCode = 0;
    protected QueryDataInterface ref;

    //These methods are abstract from AsyncTask
    protected abstract JSONArray doInBackground(String... params);
    protected abstract void onPostExecute(JSONArray result);
    protected abstract void onProgressUpdate(Integer... progress);

    //Internal interface for classes that need to be notified of the data set
    public interface QueryDataInterface{
        void finishedParsingResults();
        void updateProgressBar(int p);
    }
}
