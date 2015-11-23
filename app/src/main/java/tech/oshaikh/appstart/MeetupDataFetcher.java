package tech.oshaikh.appstart;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;
/**
 * Created by moseslee on 11/23/15.
 */
public class MeetupDataFetcher extends AsyncTask <String, Integer, JSONObject> {

    int responseCode = 0;
    ArrayList<String> listOfNames;
    ArrayList<String> listOfUrl;
    QueryDataInterface ref;

//    public static final String AUTH_URL = "https://secure.meetup.com/oauth2/authorize";
//    public static final String TOKEN_URL = "https://secure.meetup.com/oauth2/access";
//    public static final String REDIRECT_URI = "your.redirect.com";
//    public static final String CONSUMER_KEY = "1c0gijl0b0r2e2rn7o2tdjm6oc";
//    public static final String CONSUMER_SECRET = "rl7sqql9mrdu9jbild3ebk3a36";

    MeetupDataFetcher(ArrayList<String> listOfNames, ArrayList<String> listOfUrl, QueryDataInterface ref) {
        /*
        USING THE OAUTH PROTOCOL SHOULD BE THE PREFERRED WAY
        However at the moment we will just use a single user API key
         */
//        OAuthClientRequest request = null;
//        try {
//            request = OAuthClientRequest.authorizationLocation(
//                    AUTH_URL).setClientId(
//                    CONSUMER_KEY).setRedirectURI(
//                    REDIRECT_URI).buildQueryMessage();
//        } catch (OAuthSystemException e) {
//            Log.d( "OAuth request failed", "Activity");
//        }

        this.listOfNames = listOfNames;
        this.listOfUrl = listOfUrl;
        this.ref = ref;
    }
    /*This is a temporary place holder due to the fact that I am not familiar with
     meetup api's. I just want to see if I can submit a request, get a JSON, and parse
     the JSON and populate the list. THIS WILL CHANGE IN THE FUTURE
     */
    private String requestURL = "https://api.meetup.com/topics?key=575e2b7f347f591d39574a3f593c&search=tech";
            //"https://api.meetup.com/find/groups?key=575e2b7f347f591d39574a3f593c&zip=11211&radius=1&category=25&order=members";

    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection urlConnection;
        URL url;
        JSONObject results = null;

        try {
            url = new URL(requestURL);
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

    //Parse the results
    protected void onPostExecute(JSONObject result) {
        Log.d("Got results", "postExec");
        try {
            JSONArray results = result.getJSONArray("results");

            for (int i = 0; i < results.length(); i++){
                listOfNames.add(results.getJSONObject(i).getString("name"));
                listOfUrl.add(results.getJSONObject(i).getString("link"));
            }
            Log.d(results.getJSONObject(0).getString("name"), "JSON OBJECT");
            ref.finishedParsingResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Internal interface for classes that need to be notified of the data set
    interface QueryDataInterface{
        public void finishedParsingResults();
    }
}
