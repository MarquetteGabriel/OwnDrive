package fr.pamarg.owndriveapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import fr.pamarg.owndriveapp.Network.CallAPIManager;
import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.model.treeManager.JsonManager;
import fr.pamarg.owndriveapp.view.gridview.GridViewAdapter;
import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class FilesFragment extends Fragment
{

    GridViewAdapter gridViewAdapter;
    ViewDataBinding binding;
    GridView gridView;

    String[] textAnswer = {"Documents vraiment très personnel", "Images.jpg", "Videos.mp4", "Music.mp3", "Others.png", "Documents.docx", "Images.txt", "Videos.pdf", "Music", "Others.api", "Documents.java", "Images", "Videos.c", "Music", "Others"};

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_files, container, false);
        //View view = inflater.inflate(R.layout.fragment_files, container, false);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        mainActivityViewModel.setIsLongClicked(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.grid_view);

        List<String> filesNameList = new ArrayList<>();
        String[] filesOfCurrentPage = CallAPIManager.getFilesOfCurrentPage(mainActivityViewModel.getIpAddress().getValue());

        for (String fileName : filesOfCurrentPage)
        {
            filesNameList.add(cropTextLength(fileName));
        }
        String[] filesName = new String[filesNameList.size()];
        filesName = filesNameList.toArray(filesName);

        int[] filesImages = setFilesImages(textAnswer);
        gridViewAdapter = new GridViewAdapter(requireContext().getApplicationContext(), filesName, filesImages);
        gridView.setAdapter(gridViewAdapter);

        LinearLayout layoutAddFolder = view.findViewById(R.id.ns_add_folder);
        LinearLayout layoutAddFile = view.findViewById(R.id.ns_add_file);
        LinearLayout layoutAddPeople = view.findViewById(R.id.ns_add_people);
        LinearLayout layoutSearch = view.findViewById(R.id.ns_search);
        LinearLayout layoutMore = view.findViewById(R.id.ns_more);

        LinearLayout layoutRename = view.findViewById(R.id.s_rename);
        LinearLayout layoutMove = view.findViewById(R.id.s_move);
        LinearLayout layoutShare = view.findViewById(R.id.s_share);
        LinearLayout layoutDelete = view.findViewById(R.id.s_delete);
        LinearLayout layoutClose = view.findViewById(R.id.s_close);


        layoutAddFolder.setOnClickListener(view1 -> {
            // TODO: add folder
            JsonManager.getTreeFiles(mainActivityViewModel, mainActivityViewModel.getIpAddress().getValue());
        });

        layoutAddFile.setOnClickListener(view1 -> {
            // TODO: add file
        });

        layoutAddPeople.setOnClickListener(view1 -> {
            // TODO: add people
        });

        layoutSearch.setOnClickListener(view1 -> {
            // TODO: search
        });

        layoutMore.setOnClickListener(view1 -> {
            // TODO: more
        });

        layoutRename.setOnClickListener(view1 -> {

                });

        layoutMove.setOnClickListener(view1 -> {

                });

        layoutShare.setOnClickListener(view1 -> {

                });

        layoutDelete.setOnClickListener(view1 -> {

                });

        layoutClose.setOnClickListener(view1 -> {
            uncheckedBoxes();
            switchToLongClickState(false);
                });

        gridView.setOnItemClickListener(this::gridViewOnClick);
        gridView.setOnItemLongClickListener(this::gridViewOnLongClick);

        gridView.setOnItemClickListener((adapterView, view12, i, l) -> {

        });

        binding.executePendingBindings();
    }

    private boolean gridViewOnLongClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Toast.makeText(requireContext().getApplicationContext(), textAnswer[position], Toast.LENGTH_SHORT).show();
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        mainActivityViewModel.setIsLongClicked(true);
        switchToLongClickState(true);
        checkBox.setChecked(true);
        return true;
    }

    private void gridViewOnClick(AdapterView<?> adapterView, View view12, int position, long l) {
        Toast.makeText(requireContext().getApplicationContext(), textAnswer[position], Toast.LENGTH_SHORT).show();
        if(!textAnswer[position].contains(".")) {
            //TODO : request to api new folder and substitute
        }
        else
        {
            //TODO : open file

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://tmps + "), "text/plain");
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            try{
                startActivity(intent);
            }
              catch (Exception e)
              {

              }
        }
    }

    private String cropTextLength(String text)
    {
        if (text.length() > 17)
        {
            return text.substring(0, 17) + "...";
        }
        return text;
    }

    private int[] setFilesImages(String[] filesType)
    {
        List<Integer> filesImagesList = new ArrayList<>();
        for (String file : filesType) {
            if (!file.contains(".")) {
                filesImagesList.add(R.drawable.button_folder);
            } else if (file.contains(".pdf")) {
                filesImagesList.add(R.drawable.logo_pdf);
            } else if (file.contains(".jpg") || file.contains(".png") || file.contains(".jpeg") ||
                    file.contains(".gif")) {
                filesImagesList.add(R.drawable.logo_image);
            } else if (file.contains(".mp3")) {
                filesImagesList.add(R.drawable.logo_mp3);
            } else if (file.contains(".mp4")) {
                filesImagesList.add(R.drawable.logo_mp4);
            } else if (file.contains(".txt") || file.contains(".docx") || file.contains(".c") ||
                    file.contains(".py") || file.contains(".java") || file.contains(".cpp") ||
                    file.contains(".h") || file.contains(".html") || file.contains(".css") ||
                    file.contains(".js") || file.contains(".php") || file.contains(".json") ||
                    file.contains(".xml")) {
                filesImagesList.add(R.drawable.logo_txt);
            } else {
                filesImagesList.add(R.drawable.logo_file);
            }
        }

        int[] filesImages = new int[filesImagesList.size()];
        for (int i = 0; i < filesImagesList.size(); i++)
        {
            filesImages[i] = filesImagesList.get(i);
        }
        return filesImages;
    }

    private void switchToLongClickState(boolean state)
    {
        ConstraintLayout layoutNotSelected = requireView().findViewById(R.id.fileConstraintLayoutNotSelected);
        ConstraintLayout layoutSelected = requireView().findViewById(R.id.fileConstraintLayoutSelected);

        layoutNotSelected.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
        layoutSelected.setVisibility(state ? View.VISIBLE : View.INVISIBLE);

        configureBoxesVisibility(state);
    }

    private void configureBoxesVisibility(boolean state)
    {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            View checkBoxView = gridView.getChildAt(i);
            CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
            checkBox.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void uncheckedBoxes()
    {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            View checkBoxView = gridView.getChildAt(i);
            CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
            checkBox.setChecked(false);
        }
    }
}