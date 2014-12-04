package opennote.navigation;

/**
 * Created by Bwandy on 27/11/14.
 */
public class Model{

    private int icon;
    private int id;
    private String title;
    private String counter;

    private boolean isGroupHeader = false;

    public Model() {
        this(-1, "Catégories",null, 0);
        isGroupHeader = true;
    }

    public Model(int icon, String title, int id) {
        super();
        this.icon = icon;
        this.title = title;
        this.id = id;
    }

    public Model(int icon, String title, String counter, int id) {
        super();
        this.icon = icon;
        this.title = title;
        this.counter = null;
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    //gettters & setters...
}