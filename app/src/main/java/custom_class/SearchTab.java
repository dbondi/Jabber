package custom_class;

public class SearchTab {
    private String name;
    private String image;
    private String ID;
    private String type;
    private Place place;

    public SearchTab(){

    }

    public SearchTab(String name, String image, String ID, String type, Place place){
        this.name = name;
        this.image = image;
        this.ID = ID;
        this.type = type;
        this.place = place;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
