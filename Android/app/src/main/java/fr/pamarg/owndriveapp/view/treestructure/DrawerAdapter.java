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

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private final Context context;
    private final List<DrawerItem> drawerItemList;
    private final DrawerItemClickedListener listener;
    private View HeaderView;

    public interface DrawerItemClickedListener
    {
        void onItemClicked(DrawerItem drawerItem);
    }

    public DrawerAdapter(Context context, List<DrawerItem> drawerItemList, DrawerItemClickedListener listener)
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
            DrawerItem drawerItem = drawerItemList.get(position - 1);
            ((ItemViewHolder) holder).bind(drawerItem);
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

        public void bind(DrawerItem drawerItem)
        {
            textView.setText(drawerItem.getName());
            imageView.setImageResource(drawerItem.getIcon());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition() - 1;
            DrawerItem drawerItem = drawerItemList.get(position);
            if(drawerItem.hasSubItems()) {
                if (!drawerItem.isExpanded()) {
                    openFolder(drawerItem, position);
                } else {
                    closeFolder(drawerItem, position);
                }
            }
            else
            {
                listener.onItemClicked(drawerItem);
            }
        }
    }


    private void openFolder(DrawerItem drawerItem, int position)
    {
        drawerItem.setExpanded(true);
        List<SubItems> subItems = drawerItem.getSubItems();
        List<DrawerItem> subFolders = drawerItem.getSubFolders();
        int insertPosition = position + 1;
        for (DrawerItem subFolder : subFolders) {
            subFolder.setExpanded(false);
            drawerItemList.add(insertPosition++, subFolder);
        }
        for (SubItems subItem : subItems) {
            drawerItemList.add(insertPosition++, new DrawerItem(subItem));
        }
        notifyItemRangeInserted(position + 2, subItems.size() + subFolders.size());
    }

    private void closeFolder(DrawerItem drawerItem, int position)
    {
        List<SubItems> subItems = drawerItem.getSubItems();
        List<DrawerItem> subFolders = drawerItem.getSubFolders();
        int insertPosition = position + 1;
        for (DrawerItem subFolder : subFolders) {
            if(subFolder.isExpanded())
            {
                closeFolder(subFolder, insertPosition);
            }
            drawerItemList.remove(insertPosition);

        }
        for (SubItems ignored : subItems) {
            drawerItemList.remove(insertPosition);
        }
        drawerItem.setExpanded(false);
        notifyItemRangeRemoved(position + 2, subItems.size() + subFolders.size());
    }

}



