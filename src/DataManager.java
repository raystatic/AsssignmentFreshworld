import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.Key;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class DataManager {

    private JSONArray jsonArray;
    private Long DEFAULT_TIMEOUT = Long.valueOf("0");
    private FileWriter fileWriter;
    private String KeyStoreFileName = "assignment_db.json";

    public DataManager(){
        jsonArray = new JSONArray();
    }


    public void delete(String key){
        File f = new File(KeyStoreFileName);

        JSONParser jsonParser = new JSONParser();
        try {
            if (f.exists()){
                Object obj = jsonParser.parse(new FileReader(f));
                jsonArray = (JSONArray) obj;
                for (Object o : jsonArray) {
                    JSONObject json = (JSONObject) o;
                    if (json.containsKey(key)) {
                        JSONObject jo = (JSONObject) json.get(key);
                        Long timeout = (Long) jo.get("timeout");

                        if (timeout!=0){
                            if (System.currentTimeMillis() < timeout){
                                jsonArray.remove(json);
                                writeJsonArrayToFile(jsonArray);
                                System.out.println("Success: "+key+" is deleted");
                            }else{
                                System.out.println("Error: Time to live of "+key+" has expired");
                            }
                        }else{
                            jsonArray.remove(json);
                            writeJsonArrayToFile(jsonArray);
                            System.out.println("Success: "+key+" is deleted");
                        }

                        return;
                    }
                }

                System.out.println("Error: Key does not exist");


            }else{
                System.out.println("Error: File does not exist");
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public void read(String key){
        File f = new File(KeyStoreFileName);

        JSONParser jsonParser = new JSONParser();
        try {
            if (f.exists()){
                Object obj = jsonParser.parse(new FileReader(f));
                jsonArray = (JSONArray) obj;
                for (Object o : jsonArray) {
                    JSONObject json = (JSONObject) o;
                    if (json.containsKey(key)) {
                        JSONObject jo = (JSONObject) json.get(key);
                        String value = (String) jo.get("value");
                        Long timeout = (Long) jo.get("timeout");

                        if (timeout != 0){
                            if (System.currentTimeMillis() < timeout){
                                System.out.println("Success: "+key+":"+value);
                            }else{
                                System.out.println("Error: Time to live of "+key+" has expired");
                            }
                        }else{
                            System.out.println("Success: "+key+":"+value);
                        }

                        return;
                    }
                }

                System.out.println("Error: Key does not exist");


            }else{
                System.out.println("Error: File does not exist");
            }
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

    }

    public void create(String key, String value, Long timeout){
        JSONObject jo = new JSONObject();
        jo.put("value",value);
        jo.put("timeout",System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeout));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key,jo);

        File f = new File(KeyStoreFileName);

        JSONParser parser =  new JSONParser();

        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            if (json.containsKey(key)) {
                System.out.println("Error: Key Already exists!");
                return;
            }
        }

        try{
            if (f.exists()){
                Object obj = parser.parse(new FileReader(f));
                jsonArray = (JSONArray) obj;

                for (Object o : jsonArray) {
                    JSONObject json = (JSONObject) o;
                    if (json.containsKey(key)) {
                        System.out.println("Error: Key Already exists!");
                        return;
                    }
                }

                if (f.length() >= 1020 * 1024 * 1024){
                    System.out.println("Error: Memory size exceeded!");
                    return;
                }

            }
            jsonArray.add(jsonObject);
            writeJsonArrayToFile(jsonArray);
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
    }

    public void create(String key, String value){
        JSONObject jo = new JSONObject();
        jo.put("value",value);
        jo.put("timeout",DEFAULT_TIMEOUT);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key,jo);

        File f = new File(KeyStoreFileName);

        JSONParser parser =  new JSONParser();

        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            if (json.containsKey(key)) {
                System.out.println("Error: Key Already exists!");
                return;
            }
        }

        try{
            if (f.exists()){
                Object obj = parser.parse(new FileReader(f));
                jsonArray = (JSONArray) obj;

                for (Object o : jsonArray) {
                    JSONObject json = (JSONObject) o;
                    if (json.containsKey(key)) {
                        System.out.println("Error: Key Already exists!");
                        return;
                    }
                }

                if (f.length() >= 1020 * 1024 * 1024){
                    System.out.println("Error: Memory size exceeded!");
                    return;
                }

            }
            jsonArray.add(jsonObject);
            writeJsonArrayToFile(jsonArray);
        } catch (ParseException | IOException e){
            e.printStackTrace();
        }
    }

    private void writeJsonArrayToFile(JSONArray jsonArray){
        try {
            fileWriter = new FileWriter(KeyStoreFileName);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
