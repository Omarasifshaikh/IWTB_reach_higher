package tech.oshaikh.ojsknavigationdrawer;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Jason Burmark on 11/24/15.
 */
public class LearnDataFetcher extends AsyncTask <String, Integer, JSONObject> {

    private int responseCode = 0;
    private ArrayList<String> listOfNames;
    private ArrayList<String> listOfUrl;
    private QueryDataInterface ref;
    private String category;

    private String requestURL = "https://api.coursera.org/api/courses.v1?q=search&query=";

    LearnDataFetcher(ArrayList<String> listOfNames, ArrayList<String> listOfUrl,
                     QueryDataInterface ref, String category) {
        this.listOfNames = listOfNames;
        this.listOfUrl = listOfUrl;
        this.ref = ref;
        this.category = category;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection urlConnection;
        URL url;
        JSONObject results = null;

        try {
            url = new URL(requestURL + category);
            urlConnection = (HttpURLConnection) url.openConnection();

            String recievingText = "";
            String strLn;
            StringBuilder sb = new StringBuilder();

            responseCode = urlConnection.getResponseCode();

            BufferedReader input =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 16384);

            while((strLn = input.readLine())!= null){
                sb.append(strLn);
            }

            input.close();
            recievingText = sb.toString();

            results = new JSONObject(recievingText);
        } catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Returningresults", "asynctask");
        return results;
    }

    protected void onProgressUpdate(Integer... progress) {
        ref.updateProgressBar(progress[0]);
    }


    //Parse the results
    protected void onPostExecute(JSONObject result) {
        Log.d("Got results", "postExec");
        try {
            JSONArray results = result.getJSONArray("elements");

            for (int i = 0; i < results.length(); i++){
                listOfNames.add(results.getJSONObject(i).getString("name"));
                if(results.getJSONObject(i).has("previewLink")){
                    listOfUrl.add(results.getJSONObject(i).getString("previewLink"));
                } else {
                    listOfUrl.add("");
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

            listOfNames.clear();
            listOfUrl.clear();
        } finally {
            ref.finishedParsingResults();
        }
    }

    //Internal interface for classes that need to be notified of the data set
    interface QueryDataInterface{
        void finishedParsingResults();
        void updateProgressBar(int p);
    }
}
