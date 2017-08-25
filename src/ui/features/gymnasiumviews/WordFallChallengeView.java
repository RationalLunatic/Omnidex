package ui.features.gymnasiumviews;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.sqlite.SQLiteJDBC;
import skeletonkey.GameDifficulty;
import ui.components.PaneKeys;
import ui.components.dataclumps.WordFallVocabulary;
import ui.components.displaycomponents.FallingWord;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.*;

public class WordFallChallengeView extends ScalingStackPane {
    private WordFallVocabulary vocabulary;
    private List<TranslateTransition> animations;
    private FallingWord mostRecentWord;
    private ScalingVBox HUDisplay;
    private ScalingVBox vocabDisplay;
    private ChoiceBox<GameDifficulty> difficultyChoice;
    private ChoiceBox<String> vocabListChoice;
    private Map<GameDifficulty, Integer> animationSpeed;
    private Timer timer;
    private Label killCount;
    private int level;
    private boolean lastWordDropped;
    private boolean lostGame;

    public WordFallChallengeView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initGameOptions();
        initGameOptionsDisplay();
        initMainGameDisplay();
        initHUDisplay();
    }

    private void initGameOptionsDisplay() {
        initGameOptionsVocabDisplay();
        initGameOptionsChoiceBoxes();
        setGameOptionsChoiceBoxBehavior();
        addGameOptionsToContainer();
    }

    private void startGame() {
        initVocab();
        initGame();
        displayLevelSplashScreen();
    }

    private void stopGame() {
        stopAnimations();
        vocabulary.resetVocabulary(level);
        this.getChildren().clear();
    }

    private void processPlayerInput(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER && vocabListChoice.getValue() != null && difficultyChoice.getValue() != null) {
            startGame();
            return;
        } else {
            if(mostRecentWord != null) {
                if(mostRecentWord.isNextLetter(keyEvent.getCode().getName())) {
                    updateWord(mostRecentWord, keyEvent);
                    return;
                }
            }
            for(FallingWord activeWord : vocabulary.getVisibleWords()) {
                if(activeWord.isNextLetter(keyEvent.getCode().getName())) {
                    updateWord(activeWord, keyEvent);
                    return;
                }
            }
        }

    }

    private void updateWord(FallingWord fallingWord, KeyEvent keyEvent) {
        fallingWord.updateCompletionStatus(keyEvent.getCode().getName());
        if(fallingWord.isTypingComplete()) {
            this.getChildren().remove(fallingWord);
            vocabulary.getVisibleWords().remove(mostRecentWord);
            mostRecentWord = null;
            int numKills = Integer.parseInt(killCount.getText()) + 1;
            killCount.setText(numKills + "");
            if(vocabulary.getVisibleWords().isEmpty() && lastWordDropped) winGame();
        } else {
            mostRecentWord = fallingWord;
        }
    }

    private void displayLevelSplashScreen() {
        ScalingVBox preGameDisplay = new ScalingVBox(getViewBindings());
        for(FallingWord word : vocabulary.getWordsOfLevel(level)) {
            Label toAdd = new Label(word.getFlashCard().getFront() + ": " + word.getFlashCard().getBack());
            toAdd.setFont(Font.font(18));
            preGameDisplay.getChildren().add(toAdd);
        }
        preGameDisplay.addEventFilter(KeyEvent.KEY_RELEASED, e -> playLevel());
        preGameDisplay.setAlignment(Pos.CENTER);
        this.getChildren().add(preGameDisplay);
        preGameDisplay.requestFocus();
    }

    private void playLevel() {
        this.getChildren().clear();
        this.getChildren().add(HUDisplay);
        this.requestFocus();
        startWordDropTimer();
    }

    private void startWordDropTimer() {
        animations = new ArrayList<>();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int index = 0;
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(index == vocabulary.getWordsAccumulated(level).size()) {
                            timer.cancel();
                            lastWordDropped = true;
                            index = 0;
                            return;
                        }
                        dropWord(index);
                        index++;
                    }
                });
            }
        }, 0, animationSpeed.get(difficultyChoice.getValue()) / 10);
    }

    private void dropWord(int index) {
        Point2D point = generateRandomPoint();
        FallingWord toDrop = vocabulary.generateFallingWord(level, index, point);
        addFallingWordToGame(toDrop);
        startWordFall(toDrop, point);
    }

    private void addFallingWordToGame(FallingWord toDrop) {
        if(mostRecentWord == null) mostRecentWord = toDrop;
        vocabulary.addToVisibleWords(toDrop);
        this.getChildren().add(toDrop);
    }

    private void startWordFall(FallingWord fallingWord, Point2D point) {
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(animationSpeed.get(difficultyChoice.getValue())), fallingWord);
        translateTransition.setFromY(point.getY());
        translateTransition.setToY(point.getY() * -1);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.onFinishedProperty().setValue(e ->
        {
            fallingWord.translationComplete();
            if(!fallingWord.isTypingComplete()) {
                loseGame(fallingWord);
            }
        });
        translateTransition.play();
        animations.add(translateTransition);
    }

    private void loseGame(FallingWord fallingWord) {
        if(!lostGame) {
            lostGame = true;
            stopGame();
            displayPostGame(fallingWord);
        }
    }

    private void winGame() {
        stopGame();
        if(level != vocabulary.numberOfLevels()-1)
        {
            level++;
            displayLevelSplashScreen();
        } else {
            this.getChildren().clear();
            init();
        }
    }

    private void displayPostGame(FallingWord fallingWord) {
        ScalingLabel info = new ScalingLabel(getViewBindings().widthProperty(),fallingWord.getFlashCard().getFront() + ": " + fallingWord.getFlashCard().getBack(), 1.0);
        ScalingLabel defeat = new ScalingLabel(getViewBindings().widthProperty(), "You were killed by: ", 0.8);
        ScalingVBox postGameDisplay = new ScalingVBox(getViewBindings());
        postGameDisplay.getChildren().addAll(defeat, info);
        postGameDisplay.setAlignment(Pos.CENTER);
        postGameDisplay.setOnMouseClicked(e -> postGameDisplay.requestFocus());
        this.getChildren().clear();
        this.getChildren().addAll(postGameDisplay);
    }

    private Point2D generateRandomPoint() {
        Random rand = new Random();
        double yCoord = this.getPrefHeight()/2 - 20;
        double xCoord = rand.nextInt((int)this.getPrefWidth()/3);
        int multiplier = (rand.nextInt(100) > 50) ? 1 : -1;
        return new Point2D(multiplier * xCoord, -1 * yCoord);
    }

    private void stopAnimations() {
        for(TranslateTransition transition : animations) {
            transition.stop();
        }
        timer.cancel();
        animations.clear();
    }

    private void addGameOptionsToContainer() {
        HBox choiceContainer = new HBox();
        choiceContainer.getChildren().addAll(vocabListChoice, difficultyChoice);
        this.getChildren().addAll(choiceContainer);
    }

    private void initGameOptionsVocabDisplay() {
        vocabDisplay = new ScalingVBox(getViewBindings());
        vocabDisplay.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(vocabDisplay);
    }

    private void initGameOptionsChoiceBoxes() {
        difficultyChoice = new ChoiceBox<>();
        vocabListChoice = new ChoiceBox<>();
        difficultyChoice.getItems().addAll(GameDifficulty.values());
        vocabListChoice.getItems().addAll(SQLiteJDBC.getInstance().getLibraryIO().getSubjects());
    }

    private void setGameOptionsChoiceBoxBehavior() {
        vocabListChoice.setOnAction(e -> {
            vocabDisplay.getChildren().clear();
            for(BasicWord word : SQLiteJDBC.getInstance().getLibraryIO().getVocabList(vocabListChoice.getValue())) {
                vocabDisplay.getChildren().add(new Label(word.getWord() + ": " + word.getTranslation()));
            }
        });
    }

    private void initMainGameDisplay() {
        this.setOnMouseClicked(e -> this.requestFocus());
        this.addEventFilter(KeyEvent.KEY_RELEASED, e -> processPlayerInput(e));
    }

    private void initGameOptions() {
        animationSpeed = new HashMap<>();
        animationSpeed.put(GameDifficulty.EASY, 20000);
        animationSpeed.put(GameDifficulty.MEDIUM, 15000);
        animationSpeed.put(GameDifficulty.HARD, 12000);
    }

    private void initHUDisplay() {
        HUDisplay = new ScalingVBox(getViewBindings());
        killCount = new Label("0");
        killCount.setFont(Font.font(20));
        HUDisplay.getChildren().add(killCount);
        HUDisplay.setAlignment(Pos.BOTTOM_RIGHT);
    }

    private void initGame() {
        level = 0;
        lostGame = false;
        lastWordDropped = false;
        this.getChildren().clear();
    }

    private void initVocab() {
        vocabulary = new WordFallVocabulary(vocabListChoice.getValue(), difficultyChoice.getValue());
    }
}
