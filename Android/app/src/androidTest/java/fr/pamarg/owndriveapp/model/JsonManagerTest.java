package fr.pamarg.owndriveapp.model;

import static org.junit.Assert.assertEquals;

import android.os.Environment;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class JsonManagerTest {

    String content = "{\n" +
            "  \"file_manager\": {\n" +
            "    \"root_directory\": \"/home/user/documents\",\n" +
            "    \"current_directory\": \"/home/user/documents/work\",\n" +
            "    \"files\": [\n" +
            "      {\n" +
            "        \"name\": \"file1.txt\",\n" +
            "        \"type\": \"text\",\n" +
            "        \"size\": \"1024 KB\",\n" +
            "        \"created_at\": \"2023-01-01T12:00:00Z\",\n" +
            "        \"modified_at\": \"2023-01-05T14:30:00Z\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"file2.jpg\",\n" +
            "        \"type\": \"image\",\n" +
            "        \"size\": \"2048 KB\",\n" +
            "        \"created_at\": \"2023-02-10T09:45:00Z\",\n" +
            "        \"modified_at\": \"2023-02-12T11:20:00Z\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"name\": \"folder1\",\n" +
            "        \"type\": \"directory\",\n" +
            "        \"size\": \"N/A\",\n" +
            "        \"created_at\": \"2023-03-20T15:10:00Z\",\n" +
            "        \"modified_at\": \"2023-03-20T15:10:00Z\",\n" +
            "        \"contents\": [\n" +
            "          {\n" +
            "            \"name\": \"subfile1.txt\",\n" +
            "            \"type\": \"text\",\n" +
            "            \"size\": \"512 KB\",\n" +
            "            \"created_at\": \"2023-03-20T15:15:00Z\",\n" +
            "            \"modified_at\": \"2023-03-20T15:45:00Z\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"name\": \"subfile2.txt\",\n" +
            "            \"type\": \"text\",\n" +
            "            \"size\": \"768 KB\",\n" +
            "            \"created_at\": \"2023-03-20T15:20:00Z\",\n" +
            "            \"modified_at\": \"2023-03-20T15:50:00Z\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    File jsonFile;
    String fileName = "tree.json";
    MainActivityViewModel mainActivityViewModel;


    @Before
    public void setUp() throws Exception
    {
        jsonFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
        jsonFile.setWritable(true);
        jsonFile.setReadable(true);

        FileWriter fileWriter = new FileWriter(jsonFile, true);
        fileWriter.write(content);
        fileWriter.close();
        mainActivityViewModel = new MainActivityViewModel();
    }

    @Test
    public void readDatasTest() throws IOException {
        JsonManager.readDatas(InstrumentationRegistry.getInstrumentation().getContext(), mainActivityViewModel);
        assertEquals(content, mainActivityViewModel.getTreeFolders());
    }


}