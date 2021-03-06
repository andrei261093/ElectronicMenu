package com.example.andreiiorga.electronicmenu.asyncTasks;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.ApplicationController;
import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;
import com.example.andreiiorga.electronicmenu.fragments.CategoryFragment;
import com.example.andreiiorga.electronicmenu.models.Category;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by andreiiorga on 22/06/2017.
 */

public class CategoriesService extends AsyncTask<Void, Void, String> {

    private CategoryFragment categoryFragment;

    @Override
    protected String doInBackground(Void... voids) {
        // Do some validation here

        try {
            String json_string;

            String a = StaticStrings.SERVER_URL + StaticStrings.GET_CATEGORIES_ROUTE;
            //String a = "http://192.168.1.101:1234/getCategories";
            URLConnection connection = new URL(a).openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setConnectTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


            StringBuilder stringBuilder = new StringBuilder();

            while ((json_string = bufferedReader.readLine()) != null) {
                stringBuilder.append(json_string + "\n");
            }

            bufferedReader.close();
            inputStream.close();

            json_string = stringBuilder.toString();
            System.out.print(json_string);
            if(json_string.equals("null\n")){
                return "";
            }
            return json_string;

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            Handler handler =  new Handler(ApplicationController.instance.getBaseContext().getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(ApplicationController.instance.getApplicationContext(), "Nu s-a putut conecta la server", Toast.LENGTH_SHORT).show();
                }
            });

            return "Nu s-a putut comunica cu server-ul!";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        ApplicationController.instance.getCategoryList().clear();
        try {
            if(result != null) {
                JSONArray resultsArray = new JSONArray(result);

                for(int i=0; i< resultsArray.length(); i++){
                    Category category = new Category(Integer.parseInt(resultsArray.getJSONObject(i).getString("id")), resultsArray.getJSONObject(i).getString("name"), resultsArray.getJSONObject(i).getString("imageUrl"));
                    ApplicationController.instance.addCategory(category);
                }
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        categoryFragment.setCategoryList(ApplicationController.instance.getCategoryList());
    }


    public void setFragment(CategoryFragment categoryFragment) {
        this.categoryFragment = categoryFragment;
    }
}
