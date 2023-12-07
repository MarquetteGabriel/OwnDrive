package fr.pamarg.owndriveapp.model.treeManager.directoryfiles;

import java.util.ArrayList;
import java.util.List;

public class Folders
{
    private final String name;
    private String size;
    private String modifiedat;
    private int icon;
    private List<Files> subItems;
    private boolean hasSubItems;
    private boolean expanded;
    private List<Folders> subFolders;
    private int padding;

    public Folders(String name, String size, String modifiedat, boolean hasSubItems, int icon) {
        this.name = name;
        this.size = size;
        this.icon = icon;
        this.modifiedat = modifiedat;
        this.hasSubItems = hasSubItems;
        subItems = new ArrayList<>();
        subFolders = new ArrayList<>();
    }

    public Folders(String name, boolean hasSubItems, int icon) {
        this.name = name;
        this.hasSubItems = hasSubItems;
        this.icon = icon;
        subItems = new ArrayList<>();
        subFolders = new ArrayList<>();
    }

    public Folders(String name, int icon) {
        this.name = name;
        this.icon = icon;
        this.hasSubItems = false;
    }

    public Folders(String name, int icon, boolean boolSubItems) {
        this.name = name;
        this.icon = icon;
        this.hasSubItems = boolSubItems;
        if(boolSubItems)
        {
            subItems = new ArrayList<>();
            subFolders = new ArrayList<>();
        }
    }

    public Folders(String name, String size, String modifiedat) {
        this.name = name;
        this.size = size;
        this.modifiedat = modifiedat;
    }

    public Folders(Files files)
    {
        this.name = files.getName();
        this.hasSubItems = false;
    }

    public void setSubItems(boolean hasSubItems)
    {
        this.hasSubItems = hasSubItems;
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

    public void addSubItem(Files subItem) {
        subItems.add(subItem);
    }

    public List<Files> getSubItems() {
        return subItems;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void addSubFolder(Folders subFolder) {
        subFolders.add(subFolder);
    }

    public List<Folders> getSubFolders() {
        return subFolders;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}



