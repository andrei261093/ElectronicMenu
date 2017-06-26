package com.example.andreiiorga.electronicmenu.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.andreiiorga.electronicmenu.StaticElements.StaticStrings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by andreiiorga on 19/06/2017.
 */

public class AuthService extends AsyncTask<Void, Void, String> {

    private String password;
    private String tableNo;
    private Activity loginActivity;

    public AuthService(Activity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void setCredentials(String tableNo, String password){
        this.tableNo = tableNo;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Do some validation here

        try {
            String json_string;

            String a = StaticStrings.SERVER_URL + StaticStrings.GET_TABLE_BY_NAME_ROUTE + tableNo;
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
            return "Nu s-a putut comunica cu server-ul!";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(s);
            if(loginSuccessful(jsonObject.getString("tableNo"), jsonObject.getString("tablePassword"))){
                Toast.makeText(loginActivity, "Autentificare reusita!", Toast.LENGTH_LONG).show();
                StaticStrings.TABLE_NO = jsonObject.getString("tableNo");
                showMenu();
            }else {
                Toast.makeText(loginActivity, "Autentificare esuata!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(s.equals("")){
                Toast.makeText(loginActivity, "Masa nu exista!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(loginActivity, s, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
    }

    private boolean loginSuccessful(String tableNo, String tablePassword) {
        if(this.tableNo.equals(tableNo) && this.password.equals(tablePassword)){
            return true;
        }
        return false;
    }

    private void showMenu() {
   /*     Intent intent = new Intent(loginActivity, ElectronicMenu.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        loginActivity.startActivity(intent);*/
        loginActivity.finish();
    }


}
