package fr.pamarg.owndriveapp.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class CallAPIManager
{
    private static final String urlLink_AllFiles = "/api/all";

    // Get the list of all files and folders
    public static JSONObject readDatas(String ip) throws JSONException, IOException
    {
        URL url = new URL("https://" + ip + urlLink_AllFiles);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();
        return new JSONObject(inline.toString());
    }


    public static String[] getFilesOfCurrentPage(String ip)
    {
        try {
            JSONObject jsonObject = readDatas(ip);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}