package tech.oshaikh.ojsknavigationdrawer.DataFetcherPackage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class LearnDataFetcherSet extends DataFetcherSet {

    private int responseCode = 0;
    private ArrayList<String> listOfNames;
    private ArrayList<String> listOfUrl;
    private Set<String> categories;

    private String requestURL = "https://api.coursera.org/api/courses.v1?q=search&query=";

    public LearnDataFetcherSet(ArrayList<String> listOfNames, ArrayList<String> listOfUrl,
                               QueryDataInterface ref, Set<String> categories) {
        this.listOfNames = listOfNames;
        this.listOfUrl = listOfUrl;
        this.ref = ref;
        this.categories = categories;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        HttpURLConnection urlConnection;
        URL url;
        JSONArray jsonArray = null;
        JSONObject results = null;

        try {

            for (Iterator<String> it = categories.iterator(); it.hasNext(); ) {
                String f = it.next();
                Log.d("SET", f);

                url = new URL(requestURL + it);
                Log.d("lols","Running url " + url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();

                String recievingText = "";
                String strLn;
                StringBuilder sb = new StringBuilder();

                responseCode = urlConnection.getResponseCode();

                BufferedReader input =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 16384);

                while ((strLn = input.readLine()) != null) {
                    sb.append(strLn);
                }

                input.close();
                recievingText = sb.toString();

                results = new JSONObject(recievingText);

                jsonArray.put(results);
                
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Returningresults", "asynctask");


        return jsonArray;
    }

    protected void onProgressUpdate(Integer... progress) {
        ref.updateProgressBar(progress[0]);
    }


    //Parse the results
    protected void onPostExecute(JSONArray resultsArray) {
        Log.d("Got results", "postExec");
        int j=1;
        try {
                while(j<resultsArray.length()){

                JSONObject obj = resultsArray.getJSONObject(j);
            JSONArray results = obj.getJSONArray("elements");

            for (int i = 0; i < results.length(); i++){
                listOfNames.add(results.getJSONObject(i).getString("name"));
                if(results.getJSONObject(i).has("previewLink")){
                    listOfUrl.add(results.getJSONObject(i).getString("previewLink"));
                } else {
                    listOfUrl.add("");
                }
            }
                j++;
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
