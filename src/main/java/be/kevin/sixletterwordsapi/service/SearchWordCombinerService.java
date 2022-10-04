package be.kevin.sixletterwordsapi.service;

import be.kevin.sixletterwordsapi.model.SixLetterWord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchWordCombinerService {

    public List<SixLetterWord> searchAllWordCombinationsFromByteArray(byte[] bytearray) throws IOException {
        List<String> allWords = this.getAllWordsFromByteArray(bytearray);
        return this.searchAllWordCombinations(allWords);
    }

    protected List<SixLetterWord> searchAllWordCombinations(List<String> allWords) {
        allWords = allWords.stream()
                .distinct()
                .collect(Collectors.toList());
        List<SixLetterWord> sixLetterWords = this.createSixLetterWords(allWords);
        List<String> wordParts = this.getAllWordParts(allWords);

        this.logSixLetterWords(sixLetterWords);

        this.addWordPartsUntilNoChangesLeft(sixLetterWords, wordParts);

        this.logSixLetterWords(sixLetterWords);

        return sixLetterWords;
    }

    private void addWordPartsUntilNoChangesLeft(List<SixLetterWord> sixLetterWords, List<String> wordParts) {
        boolean hasChange = true;
        while (hasChange) {
            hasChange = this.loopOverWordPartsToCombine(sixLetterWords, wordParts);
        }
    }

    private List<String> getAllWordParts(List<String> allWords) {
        return allWords.stream()
                .filter(word -> word.length() < 6)
                .collect(Collectors.toList());
    }

    private List<SixLetterWord> createSixLetterWords(List<String> allWords) {
        return allWords.stream()
                .filter(word -> word.length() == 6)
                .map(SixLetterWord::new)
                .collect(Collectors.toList());
    }

    private List<String> getAllWordsFromByteArray(byte[] bytearray) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytearray)))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }
        return words;
    }

    private boolean loopOverWordPartsToCombine(List<SixLetterWord> sixLetterWords, List<String> wordParts) {
        AtomicBoolean hasChange = new AtomicBoolean(false);
        sixLetterWords.forEach(sixLetterWord -> wordParts.forEach(wordPart -> sixLetterWord.addWordPart(wordPart, hasChange)));
        return hasChange.get();
    }

    private void logSixLetterWords(List<SixLetterWord> sixLetterWords) {
        if (log.isTraceEnabled()) {
            sixLetterWords.forEach(s -> log.trace(s.toString()));
        }
    }
}
