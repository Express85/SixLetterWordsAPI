package be.kevin.sixletterwordsapi.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@ToString
public class WordCombination {

    private final String wordPart;
    private final List<WordCombination> afterParts;

    private static final String COMBINE_CHARACTER = "+";
    private static final String EQUALS_CHARACTER = "=";

    public WordCombination(String firstWordPart) {
        this.wordPart = firstWordPart;
        this.afterParts = new ArrayList<>();
    }

    public void addWordPart(String sixLetterWord, String previousCombination, String extraWordPart, AtomicBoolean change) {
        String combineWords = previousCombination + wordPart;
        for (WordCombination wordCombination : this.afterParts) {
            wordCombination.addWordPart(sixLetterWord, combineWords, extraWordPart, change);
        }

        if (!this.isComplete(combineWords) && sixLetterWord.startsWith(combineWords + extraWordPart) && this.afterParts.stream().noneMatch(wordCombination -> wordCombination.getWordPart().equals(extraWordPart))) {
            this.afterParts.add(new WordCombination(extraWordPart));
            change.set(true);
        }
    }

    public List<String> getJoinedWordsForPrint(String sixLetterWord, String previousCombination) {
        List<String> cominations = new ArrayList<>();
        String combineWords = previousCombination != null ? (previousCombination + COMBINE_CHARACTER + this.wordPart) : this.wordPart;
        if (!this.afterParts.isEmpty()) {
            for (WordCombination wordCombination : this.afterParts) {
                cominations.addAll(wordCombination.getJoinedWordsForPrint(sixLetterWord, combineWords));
            }
        } else if (this.isComplete(combineWords.replace(COMBINE_CHARACTER, ""))) {
            cominations.add(combineWords + EQUALS_CHARACTER + sixLetterWord);
        }

        return cominations;
    }

    public boolean isComplete(String previousCombination) {
        return previousCombination.length() == 6;
    }
}
