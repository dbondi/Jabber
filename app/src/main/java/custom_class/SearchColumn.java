package custom_class;

public class SearchColumn {
    private SearchTab leftTab;
    private SearchTab rightTab;

    public SearchColumn(){

    }
    public SearchColumn(SearchTab leftTab, SearchTab rightTab){
        this.leftTab = leftTab;
        this.rightTab = rightTab;
    }

    public SearchTab getLeftTab() {
        return leftTab;
    }

    public void setLeftTab(SearchTab leftTab) {
        this.leftTab = leftTab;
    }

    public SearchTab getRightTab() {
        return rightTab;
    }

    public void setRightTab(SearchTab rightTab) {
        this.rightTab = rightTab;
    }
}
