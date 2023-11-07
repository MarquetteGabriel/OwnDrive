package fr.pamarg.owndriveapp.view.treestructure;

import java.util.ArrayList;
import java.util.List;

class SubItems {
    private final String name;


    private final int icon;

    public SubItems(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}

public class DrawerItem
{
    private final String name;
    private final int icon;
    private List<SubItems> subItems;
    private boolean hasSubItems;
    private boolean expanded;
    private List<DrawerItem> subFolders;

    public DrawerItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public DrawerItem(String name, int icon, boolean boolSubItems) {
        this.name = name;
        this.icon = icon;
        this.hasSubItems = boolSubItems;
        if(boolSubItems)
        {
            subItems = new ArrayList<>();
            subFolders = new ArrayList<>();
        }
    }

    public DrawerItem(SubItems subItems)
    {
        this.name = subItems.getName();
        this.icon = subItems.getIcon();
        this.hasSubItems = false;
    }

    public boolean hasSubItems() {
        return hasSubItems;
    }

    public String getName() {
        return name;
    }
    public int getIcon() {
        return icon;
    }

    public void addSubItem(SubItems subItem) {
        subItems.add(subItem);
    }

    public List<SubItems> getSubItems() {
        return subItems;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void addSubFolder(DrawerItem subFolder) {
        subFolders.add(subFolder);
    }

    public List<DrawerItem> getSubFolders() {
        return subFolders;
    }
}



