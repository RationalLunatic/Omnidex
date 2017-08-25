package ui.components.dataclumps;

import javafx.geometry.Point2D;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.sqlite.SQLiteJDBC;
import skeletonkey.GameDifficulty;
import ui.components.displaycomponents.FallingWord;

import java.util.*;

public class WordFallVocabulary {
    private List<BasicWord> vocabularyList;
    private List<FallingWord> allWords;
    private List<FallingWord> visibleWords;
    private Map<Integer, List<FallingWord>> wordsPerLevel;
    private GameDifficulty difficulty;
    private String subject;

    public WordFallVocabulary(String subject, GameDifficulty difficulty) {
        this.difficulty = difficulty;
        this.subject = subject;
        initLists();
        initVocab();
        splitVocabIntoLevels();
    }

    public void resetVocabulary(int level) {
        for(FallingWord word : getWordsAccumulated(level)) {
            word.reset();
        }
    }

    private void initLists() {
        vocabularyList = SQLiteJDBC.getInstance().getLibraryIO().getVocabList(subject);
        allWords = new ArrayList<>();
        visibleWords = new ArrayList<>();
        wordsPerLevel = new HashMap<>();
    }

    private void initVocab() {
        switch(difficulty) {
            case EASY: addFlashCardsForward(); break;
            case MEDIUM: addFlashCardsReverse(); break;
            case HARD: addFlashCardsBothDirections(); break;
        }
        Collections.shuffle(allWords);
    }

    private void addFlashCardsForward() {
        for(BasicWord word : vocabularyList) {
            allWords.add(new FallingWord(word));
        }
    }

    private void addFlashCardsReverse() {
        for(BasicWord word : vocabularyList) {
            allWords.add(new FallingWord(new BasicWord(word.getTranslation(), word.getWord(), word.getPartOfSpeech())));
        }
    }

    private void addFlashCardsBothDirections() {
        addFlashCardsForward();
        addFlashCardsReverse();
    }

    private void splitVocabIntoLevels() {
        wordsPerLevel = new HashMap<>();
        int lvlCount = 0;
        List<FallingWord> wordsInLevel = new ArrayList<>();
        for(FallingWord word : allWords) {
            wordsInLevel.add(word);
            if(wordsInLevel.size() >= 10) {
                wordsPerLevel.put(lvlCount, wordsInLevel);
                lvlCount++;
                wordsInLevel = new ArrayList<>();
            }
        }
        if(!wordsPerLevel.keySet().contains(lvlCount)) wordsPerLevel.put(lvlCount, wordsInLevel);
    }

    public List<FallingWord> getWordsAccumulated(int level) {
        List<FallingWord> wordsAccumulated = new ArrayList<>();
        for(int i = 0; i <= level; i++) {
            wordsAccumulated.addAll(wordsPerLevel.get(i));
        }
        return wordsAccumulated;
    }

    public FallingWord generateFallingWord(int level, int index, Point2D point) {
        FallingWord toDrop = getWordsAccumulated(level).get(index);
        toDrop.setTranslateX(point.getX());
        toDrop.setTranslateY(point.getY());
        return toDrop;
    }
    public int numberOfLevels() { return wordsPerLevel.keySet().size(); }
    public List<FallingWord> getWordsOfLevel(int level) { return wordsPerLevel.get(level); }
    public List<FallingWord> getVisibleWords() { return visibleWords; }
    public void addToVisibleWords(FallingWord word) { visibleWords.add(word); }
}
