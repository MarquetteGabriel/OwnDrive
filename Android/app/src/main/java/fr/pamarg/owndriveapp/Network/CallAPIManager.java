package fr.pamarg.owndriveapp.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallAPIManager
{
    private static final String urlLink_AllFiles = ":8080/api/all";

    // Get the list of all files and folders
    public static JSONObject readDatas(String ip)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;


        try {
            URL url = new URL("http://" + ip + urlLink_AllFiles);
            connection = (HttpURLConnection) url.openConnection();
            //HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            inputStreamReader.close();
            connection.disconnect();
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection != null)
            {
                connection.disconnect();
            }
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public static String[] getFilesOfCurrentPage(String ip)
    {
        JSONObject jsonObject = readDatas(ip);
        return new String[]{"Documents vraiment très personnel", "Images.jpg", "Videos.mp4", "Music.mp3", "Others.png", "Documents.docx", "Images.txt", "Videos.pdf", "Music", "Others.api", "Documents.java", "Images", "Videos.c", "Music", "Others"};
    }
}
