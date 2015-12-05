package tech.oshaikh.ojsknavigationdrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tech.oshaikh.ojsknavigationdrawer.model.Topic;


/**
 * Created by omar on 11/21/15.
 */
public class TopicJSONParser {
    //TODO - Build the newer gson parser
    private static final String TAG = "appstart";
    public static List<Topic> parseFile(String content){
        try{
            JSONObject o1 = new JSONObject(content);
            String con1 = o1.getString("data");
            JSONArray ar = new JSONArray(con1);
            List<Topic> libraryEntryList = new ArrayList<>();
            int length = ar.length();
            int j=0;
            for (int i = 0; i < length; i++) {
                JSONObject obj = ar.getJSONObject(j);
                Topic topic = new Topic();
                topic.setId(Integer.parseInt(obj.getString("id").substring(1,obj.getString("id").length()-1)));
                topic.setName(obj.getString("topic").substring(2,obj.getString("topic").length()-2).replace("\\/", "/"));
                if(!obj.isNull("id")) {
                    libraryEntryList.add(topic);
                    j++;
                }
                else{
                    topic = null;
                    i--;
                    length--;
                    j++;
                }
            }
            return libraryEntryList;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
