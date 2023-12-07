package fr.pamarg.owndriveapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.model.treeManager.JsonManager;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class FilesFragment extends Fragment
{

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
    public FilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        LinearLayout button = view.findViewById(R.id.add_folder);
        button.setOnClickListener(view1 -> {
            JsonManager.readDatas(requireActivity().getApplicationContext(), mainActivityViewModel);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}