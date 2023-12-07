package fr.pamarg.owndriveapp.view.treestructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.pamarg.owndriveapp.R;
import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Files;
import fr.pamarg.owndriveapp.model.treeManager.directoryfiles.Folders;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private final Context context;
    private final List<Object> drawerItemList;
    private final DrawerItemClickedListener listener;
    private View HeaderView;
    private int paddingFolders;

    public interface DrawerItemClickedListener
    {
        void onItemClicked(Folders folders);
    }

    public DrawerAdapter(Context context, List<Object> drawerItemList, DrawerItemClickedListener listener)
    {
        this.context = context;
        this.drawerItemList = drawerItemList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER)
        {
            return new HeaderViewHolder(HeaderView);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.drawer_item, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder)
        {}
        else
        {
            Object object = drawerItemList.get(position - 1);
            int padding;
            if(object.getClass() == Folders.class)
            {
                padding = ((Folders) object).getPadding();
            }
            else
            {
                padding = ((Files) object).getPadding();
            }
            ((ItemViewHolder) holder).bind(drawerItemList.get(position - 1));
            ((ItemViewHolder) holder).imageView.setPadding(padding,0,0,0);
            ((ItemViewHolder) holder).textView.setPadding(10, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return drawerItemList.size() + 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
        {
            return VIEW_TYPE_HEADER;
        }
        else
        {
            return VIEW_TYPE_ITEM;
        }
    }

    public void addHeaderView(View headerView)
    {
        HeaderView = headerView;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        public HeaderViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final TextView textView;
        private final ImageView imageView;

        public ItemViewHolder(View view)
        {
            super(view);
            view.setOnClickListener(this);
            textView = view.findViewById(R.id.item_text);
            imageView = view.findViewById(R.id.item_icon);
        }

        public void bind(Object object)
        {
            if(object.getClass() == Folders.class)
            {
                textView.setText(((Folders) object).getName());
                imageView.setImageResource(((Folders) object).getIcon());
            }
            else
            {
                textView.setText(((Files) object).getName());
                imageView.setImageResource(((Files) object).getIcon());
            }

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition() - 1;
            if(drawerItemList.get(position).getClass() == Folders.class)
            {
                Folders folders = (Folders) drawerItemList.get(position);
                if(folders.hasSubItems()) {
                    if (!folders.isExpanded()) {
                        openFolder(folders, position);
                    } else {
                        closeFolder(folders, position);
                    }
                }
                else
                {
                    listener.onItemClicked(folders);
                }
            }
        }
    }


    private void openFolder(Folders folders, int position)
    {
        folders.setExpanded(true);
        List<Files> subItems = folders.getSubItems();
        List<Folders> subFolders = folders.getSubFolders();
        int insertPosition = position + 1;
        paddingFolders = folders.getPadding() + 50;
        for (Folders subFolder : subFolders) {
            subFolder.setPadding(paddingFolders);
            subFolder.setExpanded(false);
            drawerItemList.add(insertPosition++, subFolder);
        }
        for (Files subItem : subItems) {
            subItem.setPadding(paddingFolders);
            drawerItemList.add(insertPosition++, subItem);
        }
        notifyItemRangeInserted(position + 2, subItems.size() + subFolders.size());
    }

    private void closeFolder(Folders folders, int position) {
        List<Files> subItems = folders.getSubItems();
        List<Folders> subFolders = folders.getSubFolders();
        int insertPosition = position + 1;
        paddingFolders = folders.getPadding() - 50;
        for (Folders subFolder : subFolders) {
            if (subFolder.isExpanded()) {
                closeFolder(subFolder, insertPosition);
            }
            drawerItemList.remove(insertPosition);

        }
        for (Files ignored : subItems) {
            drawerItemList.remove(insertPosition);
        }
        folders.setExpanded(false);
        notifyItemRangeRemoved(position + 2, subItems.size() + subFolders.size());
    }

}



