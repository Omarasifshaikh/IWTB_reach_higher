package tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LearnDataFetcher extends DataFetcher {

    private int responseCode = 0;
    private ArrayList<String> listOfNames;
    private ArrayList<String> listOfUrl;
    private String category;

    private String requestURL = "https://api.coursera.org/api/courses.v1?q=search&query=";

    public LearnDataFetcher(ArrayList<String> listOfNames, ArrayList<String> listOfUrl,
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
}
