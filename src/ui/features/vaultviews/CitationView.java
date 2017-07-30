package ui.features.vaultviews;

import javafx.geometry.Pos;
import resources.datatypes.Quote;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class CitationView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingVBox inputContainer;
    private ScalingScrollPane quoteViewContainer;
    private SimpleListTextDisplay quoteView;
    private LabeledInputBox authorInput;
    private LabeledInputBox sourceInput;
    private LabeledInputBox quoteInput;
    private LabeledInputBox tagInput;
    private ScalingButton submit;
    private ScalingButton delete;

    public CitationView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initUIElements();
        addUIElementsToContainers();
        initButtonBehavior();
        loadQuotes();
    }

    private void initButtonBehavior() {
        submit.setOnMouseClicked(e -> submitQuote());
        delete.setOnMouseClicked(e -> deleteQuote());
    }

    private void addUIElementsToContainers() {
        inputContainer.getChildren().addAll(authorInput, sourceInput, quoteInput, tagInput, submit, delete);
        quoteViewContainer.setContent(quoteView);
        mainContainer.getChildren().addAll(quoteViewContainer, inputContainer);
        this.getChildren().add(mainContainer);
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        inputContainer = new ScalingVBox(getViewBindings());
        quoteViewContainer = new ScalingScrollPane(getRequestSender(), getViewBindings(), PaneKeys.CITATION);
        quoteView = new SimpleListTextDisplay("Quotes", getViewBindings());
        ScaledDoubleBinding buttonHeightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack scalingButtonBindingsPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeightBinding);
        submit = new ScalingButton("Submit Quote", scalingButtonBindingsPack);
        delete = new ScalingButton("Delete Quote", scalingButtonBindingsPack);
        quoteView.setAlignment(Pos.CENTER);
    }

    private void initUIElements() {
        authorInput = new LabeledInputBox("Enter Author Name: ", getViewBindings());
        sourceInput = new LabeledInputBox("Enter Title of Source: ", getViewBindings());
        quoteInput = new LabeledInputBox("Enter Quote: ", getViewBindings());
        tagInput = new LabeledInputBox("Enter Tag Descriptors:", getViewBindings());

    }

    private boolean validInput() {
        return !authorInput.isEmpty() && !sourceInput.isEmpty()
                && !quoteInput.isEmpty() && !tagInput.isEmpty();
    }

    private Quote addTags(Quote quoteToModify) {
        String[] spaceBreakdown = tagInput.getInput().split("\\s+");
        String[] spaceWithCommasBreakdown = tagInput.getInput().split("[,]\\s+");
        String[] breakdownToUse;
        if(spaceBreakdown[0].contains(",")) {
            breakdownToUse = spaceWithCommasBreakdown;
        } else {
            breakdownToUse = spaceBreakdown;
        }
        for(String tag : breakdownToUse) {
            quoteToModify.addTag(tag);
        }
        return quoteToModify;
    }

    private void loadQuotes() {
        for(Quote quote : SQLiteJDBC.getInstance().getAllQuotes()) {
            quoteView.addLine("'" + quote.getQuote() + "' - " + quote.getAuthor() + ", " + quote.getSource());
        }
    }

    private void submitQuote() {
        if(validInput() && SQLiteJDBC.getInstance().isQuoteUnique(quoteInput.getInput())) {
            Quote toAdd = new Quote(authorInput.getInput(), sourceInput.getInput(), quoteInput.getInput());
            toAdd = addTags(toAdd);
            SQLiteJDBC.getInstance().addQuoteToLibrary(toAdd.getAuthor(), toAdd.getSource(), toAdd.getQuote(), toAdd.getTags());
            quoteView.addLine("'" + toAdd.getQuote() + "' - " + toAdd.getAuthor() + ", " + toAdd.getSource());
        }
    }

    private void deleteQuote() {
        if(validInput()) {
            SQLiteJDBC.getInstance().deleteQuote(quoteInput.getInput());
            quoteView.getChildren().clear();
            loadQuotes();
        }
    }
}
