package be.kevin.sixletterwordsapi.controller;

import be.kevin.sixletterwordsapi.model.SixLetterWord;
import be.kevin.sixletterwordsapi.service.SearchWordCombinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApplicationController {
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private SearchWordCombinerService searchWordCombinerService;

    @GetMapping("/application")
    public String getApplicationName() {
        return this.appName;
    }

    @PostMapping("/api/file")
    public String search(@RequestBody byte[] body) throws IOException {
        List<SixLetterWord> sixLetterWords = this.searchWordCombinerService.searchAllWordCombinationsFromByteArray(body);
        CharSequence delimiter = "\n";
        return sixLetterWords.stream()
                .map(sixLetterWord -> sixLetterWord.getSixLetterWordForPrint(delimiter))
                .collect(Collectors.joining(delimiter));
    }
}
