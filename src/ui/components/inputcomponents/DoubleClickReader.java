package ui.components.inputcomponents;

public class DoubleClickReader {
    private long lastClickTimeSig;
    private long currentClickTimeSig;
    private boolean doubleClicked;

    public DoubleClickReader() {
        lastClickTimeSig = 0;
        currentClickTimeSig = 0;
        doubleClicked = false;
    }

    public void updateClicks() {
        long difference = 0;
        currentClickTimeSig = System.currentTimeMillis();
        if(lastClickTimeSig != 0 && currentClickTimeSig != 0) {
            difference = currentClickTimeSig - lastClickTimeSig;
            if(difference < 500) {
                doubleClicked = true;
            } else {
                doubleClicked = false;
            }
        }
        lastClickTimeSig = currentClickTimeSig;
    }

    public boolean isDoubleClick() {
        return doubleClicked;
    }
}
