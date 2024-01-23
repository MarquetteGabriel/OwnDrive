package fr.pamarg.owndriveapp.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class CallAPIManager
{
    private static final String urlLink_AllFiles = "/api/all";

    // Get the list of all files and folders
    public static JSONObject readDatas(String ip) throws JSONException, IOException
    {
        URL url = new URL("http://" + ip + ":11900" + urlLink_AllFiles);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();
        String modifiedString = inline.substring(1, inline.toString().length() - 1);
        return new JSONObject(modifiedString);
    }
}
