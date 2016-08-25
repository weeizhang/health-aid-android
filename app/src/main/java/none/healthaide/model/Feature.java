package none.healthaide.model;

public class Feature {
    private int iconRes;
    private String name;

    public Feature(int iconRes, String name) {
        this.iconRes = iconRes;
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
