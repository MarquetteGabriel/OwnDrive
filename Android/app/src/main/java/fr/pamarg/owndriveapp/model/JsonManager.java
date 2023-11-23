package fr.pamarg.owndriveapp.model;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.model.directoryfiles.Files;
import fr.pamarg.owndriveapp.model.directoryfiles.Folders;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class JsonManager
{

    public static void readDatas(Context context, MainActivityViewModel mainActivityViewModel)
    {
        File jsonFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "tree.json");
        //jsonAnalyser(context, mainActivityViewModel, jsonFile);
    }


    public static void jsonAnalyser(Context context, MainActivityViewModel mainActivityViewModel)//, File file)
    {
        try {
            //InputStream inputStream = java.nio.file.Files.newInputStream(file.toPath());//context.getAssets().open("tree.json");
            InputStream inputStream = context.getAssets().open("tree.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String test = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(test);

            JSONObject fileManager = jsonObject.getJSONObject("file_manager");

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
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Folders analyseJsonFolder(JSONObject file, Folders folders) throws JSONException {
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
        return folders;
    }

    private static Object getValues(JSONObject file) throws JSONException{
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
