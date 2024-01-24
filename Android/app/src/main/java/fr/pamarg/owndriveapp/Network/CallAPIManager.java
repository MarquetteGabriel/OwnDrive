package fr.pamarg.owndriveapp.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;


public class CallAPIManager
{
    // get the list of all files and folders
    private static final String urlLink_AllFiles = "/api/all";
    // get the list of files and folders name for a screen
    private static final String urlLink_Screen= "/api/screen";
    private static final String urlLink_CreateFolder = "/api/createFolder";

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


    public static String[] getFilesOfCurrentPage(MainActivityViewModel mainActivityViewModel)
    {
        return new String[]{"Documents vraiment très personnel", "Images.jpg", "Videos.mp4", "Music.mp3", "Others.png", "Documents.docx", "Images.txt", "Videos.pdf", "Music", "Others.api", "Documents.java", "Images", "Videos.c", "Music", "Others"};
    }

    public static void createFolder(String ip, String fileName)
    {
        try {
            URL url = new URL("http://" + ip + ":11900" + urlLink_CreateFolder);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = fileName.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
        }
        catch (IOException e)
        {

        }
    }
}
