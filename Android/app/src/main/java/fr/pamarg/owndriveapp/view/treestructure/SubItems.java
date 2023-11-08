package fr.pamarg.owndriveapp.view.treestructure;

public class SubItems {
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
