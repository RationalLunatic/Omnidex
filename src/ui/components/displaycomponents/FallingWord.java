package ui.components.displaycomponents;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.datatypes.lexicographicdata.FlashCard;

public class FallingWord extends StackPane {
    private FlashCard flashCard;
    private BasicWord basicWord;
    private Label visibleWord;
    private Label hiddenTranslation;
    private String portionCompleted;
    private boolean typingCompleted;
    private boolean translationCompleted;

    public FallingWord(BasicWord basicWord) {
        this.basicWord = basicWord;
        flashCard = new FlashCard(basicWord.getWord(), basicWord.getTranslation());
        translationCompleted = false;
        typingCompleted = false;
        portionCompleted = "";
        init();
    }

    private void init() {
        initLabels();
        addLabelsToContainers();
    }

    private void initLabels() {
        visibleWord = new Label(basicWord.getWord());
        hiddenTranslation = new Label(generateHiddenLabel("", basicWord.getTranslation().length()));
        visibleWord.setFont(Font.font(18));
        hiddenTranslation.setFont(Font.font(24));
    }

    private String generateHiddenLabel(String toReturn, int numDashes) {
        for(int i = 0; i < numDashes; i++) {
            toReturn += " _";
        }
        return toReturn;
    }

    private void addLabelsToContainers() {
        this.getChildren().addAll(visibleWord, hiddenTranslation);
    }

    public void updateCompletionStatus(String letter) {
        portionCompleted += letter;
        hiddenTranslation.setText(generateHiddenLabel(portionCompleted, basicWord.getWord().length() - portionCompleted.length()));
        if(portionCompleted.equals(basicWord.getTranslation().toUpperCase())) typingCompleted = true;
    }

    public boolean isNextLetter(String letter) {
        String toCheck = portionCompleted + letter;
        toCheck = toCheck.toUpperCase();
        System.out.println(basicWord.getTranslation().toUpperCase() + " " + toCheck);
        return basicWord.getTranslation().toUpperCase().startsWith(toCheck) || basicWord.getTranslation().toUpperCase().equals(toCheck);
    }

    public FlashCard getFlashCard() { return flashCard; }
    public void translationComplete() { translationCompleted = true; }
    public boolean isTranslationComplete() { return translationCompleted; }
    public boolean isTypingComplete() { return typingCompleted; }
    public void reset() {
        portionCompleted = "";
        typingCompleted = false;
        translationCompleted = false;
        hiddenTranslation.setText(generateHiddenLabel("", basicWord.getTranslation().length()));
    }
}
