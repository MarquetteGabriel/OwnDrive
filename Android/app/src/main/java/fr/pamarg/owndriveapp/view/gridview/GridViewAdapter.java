package fr.pamarg.owndriveapp.view.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import fr.pamarg.owndriveapp.R;

public class GridViewAdapter extends BaseAdapter {

    private final Context context;
    private final String[] fileName;
    private final int[] fileIcon;

    private LayoutInflater inflater;

    public GridViewAdapter(Context context, String[] fileName, int[] fileIcon) {
        this.context = context;
        this.fileName = fileName;
        this.fileIcon = fileIcon;
    }

    @Override
    public int getCount() {
        return fileName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null){
            view = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView fileIcon_ImageView = view.findViewById(R.id.imageFiles);
        TextView fileName_TextView = view.findViewById(R.id.textFiles);
        CheckBox checkBox = view.findViewById(R.id.checkbox);

        fileIcon_ImageView.setImageResource(fileIcon[position]);
        fileName_TextView.setText(fileName[position]);

        return view;
    }
}
