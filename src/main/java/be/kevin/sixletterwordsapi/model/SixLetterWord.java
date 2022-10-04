package be.kevin.sixletterwordsapi.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Getter
@ToString
public class SixLetterWord {

    private final String word;
    private final List<WordCombination> wordCombinations;

    public SixLetterWord(String word) {
        this.word = word;
        this.wordCombinations = new ArrayList<>();
    }

    public void addWordPart(String extraWordPart, AtomicBoolean hasChange) {
        if (!this.wordCombinations.isEmpty()) {
            this.wordCombinations.forEach(afterWordPart -> afterWordPart.addWordPart(this.word, "", extraWordPart, hasChange));
        }
        if(this.word.startsWith(extraWordPart) && this.wordCombinations.stream().noneMatch(wordCombination -> wordCombination.getWordPart().equals(extraWordPart))) {
            this.wordCombinations.add(new WordCombination(extraWordPart));
            hasChange.set(true);
        }
    }

    public String getSixLetterWordForPrint(CharSequence delimiter) {
        return this.wordCombinations.stream()
                .map(wordCombination -> wordCombination.getJoinedWordsForPrint(word, null))
                .flatMap(Collection::stream)
                .collect(Collectors.joining(delimiter));
    }
}
