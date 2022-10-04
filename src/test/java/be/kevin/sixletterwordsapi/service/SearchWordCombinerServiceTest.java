package be.kevin.sixletterwordsapi.service;

import be.kevin.sixletterwordsapi.model.SixLetterWord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SearchWordCombinerServiceTest {

    @InjectMocks
    private SearchWordCombinerService searchWordCombinerService = new SearchWordCombinerService();

    @Test
    public void searchAllWordCombinations() {
        List<String> words = new ArrayList<>();
        words.add("foobar");
        words.add("fo");
        words.add("bar");
        words.add("oobar");
        words.add("obar");
        words.add("f");
        words.add("b");
        words.add("o");

        List<SixLetterWord> sixLetterWords = searchWordCombinerService.searchAllWordCombinations(words);

        Assertions.assertEquals(1, sixLetterWords.size(), "Number of six letter words");
        Assertions.assertEquals(2, sixLetterWords.get(0).getWordCombinations().size(), "Number of starting words");
        Assertions.assertEquals(5, sixLetterWords.get(0).getSixLetterWordForPrint("\n").split("\n").length, "Number of completed word combinations");
    }
}
