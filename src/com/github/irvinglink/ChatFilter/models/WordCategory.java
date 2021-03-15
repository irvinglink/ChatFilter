package com.github.irvinglink.ChatFilter.models;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class WordCategory {

    private final ChatFilterPlugin chatFilter = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    private final int weight;
    private final int wordsCount;
    private final List<String> words;

    private final Pattern pattern;

    public WordCategory(int weight, int wordsCount, List<String> words) {
        this.weight = weight;
        this.wordsCount = wordsCount;
        this.words = words;

        StringBuilder regex = new StringBuilder();

        for (int i = 0; i < words.size(); i++)
            regex.append("(?=.*").append(words.get(i)).append(")");

        this.pattern = Pattern.compile(regex.toString());
    }

    public int getWeight() {
        return weight;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public List<String> getWords() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WordCategory that = (WordCategory) o;

        return weight == that.weight &&
                wordsCount == that.wordsCount &&
                Objects.equals(words, that.words);

    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, wordsCount, words);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("WordCategory{weight=").append(weight).append(", wordsCount=").append(wordsCount).append(", words=").append(words).append("}").toString();
    }

    public Pattern getPattern() {
        return pattern;
    }

}
