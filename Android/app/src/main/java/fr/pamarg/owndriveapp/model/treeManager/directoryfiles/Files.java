package fr.pamarg.owndriveapp.model.treeManager.directoryfiles;

public class Files {
    private final String name;
    private final String size;
    private final int icon;
    private final String modifiedat;
    private int padding;

    public Files(String name, String size, String modifiedat, int icon) {
        this.name = name;
        this.size = size;
        this.icon = icon;
        this.modifiedat = modifiedat;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getModifiedat() {
        return modifiedat;
    }

    public int getIcon() {
        return icon;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}
