package fr.pamarg.owndriveapp.model.treeManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.pamarg.owndriveapp.Network.CallAPIManager;
import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Files;
import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Folders;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class JsonManager
{

    public static void getTreeFiles(MainActivityViewModel mainActivityViewModel, String ip) {
        try {
            JSONObject jsonObject = CallAPIManager.readDatas(ip);
            jsonAnalyser(mainActivityViewModel, jsonObject);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void jsonAnalyser(MainActivityViewModel mainActivityViewModel, JSONObject file) throws JSONException
    {
        JSONObject fileManager = file.getJSONObject("file_manager");
        Folders folders = new Folders("home", true, R.drawable.button_folder);
        JSONArray filesArray = fileManager.getJSONArray("files");

        for (int i = 0; i < filesArray.length(); i++) {
            JSONObject subFile = filesArray.getJSONObject(i);

            Object object = getValues(subFile);
            if (object.getClass() == Files.class) {
                folders.addSubItem((Files) object);
            } else if (object.getClass() == Folders.class) {
                Folders folder = (Folders) object;
                if (folder.hasSubItems()) {
                    analyseJsonFolder(subFile, folder);
                }
                folders.addSubFolder(folder);
            }
        }

        mainActivityViewModel.setTreeFolders(folders);
    }

    private static void analyseJsonFolder(JSONObject file, Folders folders) throws JSONException {
        JSONArray contentsArray = file.getJSONArray("contents");
        for (int j = 0; j < contentsArray.length(); j++) {
            JSONObject subFile = contentsArray.getJSONObject(j);

            Object object = getValues(subFile);
            if (object.getClass() == Files.class) {
                folders.addSubItem((Files) object);
            } else if (object.getClass() == Folders.class) {
                Folders folder = (Folders) object;
                if (folder.hasSubItems()) {
                    analyseJsonFolder(subFile, folder);
                }
                folders.addSubFolder(folder);
            }
        }
    }

    private static Object getValues(JSONObject file) throws JSONException
    {
        String name = file.getString("name");
        String type = file.getString("type");
        String size = file.getString("size");
        String modifiedAt = file.getString("modified_at");

        if (type.equals("directory")) {
            JSONArray jsonArray = file.getJSONArray("contents");
            if (jsonArray.length() > 0) {
                return new Folders(name, size, modifiedAt, true, R.drawable.button_folder);
            } else {
                return new Folders(name, size, modifiedAt, false, R.drawable.button_folder);
            }

        } else {
            return new Files(name, size, modifiedAt, R.drawable.file);
        }
    }
}
