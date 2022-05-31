//package com.example.myapplication;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.example.myapplication.dao.Record;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//public class JsonAdapter {
//    private String path;
//
//    public static void main(String[] args) throws IOException {
//        JsonAdapter jsonAdapter = new JsonAdapter();
//        jsonAdapter.createJsonFile();
//    }
//
//    public JsonAdapter(){
//        path = "assets/easy.json";
//        try {
////            InputStream is = this.getAssets().open("test.json");//eclipse
//            InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/" + ".json");//android studio
//            BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuilder builder = new StringBuilder();
//            while ((line = bufr.readLine()) != null) {
//                builder.append(line);
//            }
//            is.close();
//            bufr.close();
//
//            try {
//                JSONObject root = new JSONObject(builder.toString());
//                Log.d("info","cat=" + root.getString("cat"));
//                JSONArray array = root.getJSONArray("languages");
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject lan = array.getJSONObject(i);
//                    Log.d("info","-----------------------");
//                    Log.d("info","id=" + lan.getInt("id"));
//                    Log.d("info","ide=" + lan.getString("ide"));
//                    Log.d("info","name=" + lan.getString("name"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void createJsonFile() throws IOException {
//        File file = new File(path);
//
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//        try{
//            JSONObject root = new JSONObject();
//            JSONArray players = new JSONArray();
//
//            JSONArray player = new JSONArray();
//            JSONObject username = new JSONObject();
//            username.put("username", "hhh");
//            JSONObject credits = new JSONObject();
//            credits.put("credits", 500);
//
//            JSONArray records = new JSONArray();
//            JSONObject record1 = new JSONObject();
//            record1.put("score", 200);
//            record1.put("time", "2022-05-30 15:11");
//            JSONObject record2 = new JSONObject();
//            record2.put("score", 250);
//            record2.put("time", "2022-05-30 15:01");
//            records.put(record1);
//            records.put(record2);
//
//            player.put(username);
//            player.put(credits);
//            player.put(records);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    public void createJsonFromRecord(List<Record> records) throws IOException {
//        File file = new File(path);
//
//        if (file.exists()) { // 如果已存在,删除旧文件
//            file.delete();
//        }
//        file.createNewFile();
//
//        for(Record record : records){
//
//        }
//    }
//}
