package com.github.irvinglink.ChatFilter.models;

import com.github.irvinglink.ChatFilter.ChatFilterPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class WordCategory {

    private final ChatFilterPlugin chatFilter = ChatFilterPlugin.getPlugin(ChatFilterPlugin.class);

    private final int weight;
    private final int wordsCount;
    private final boolean accumulateWeight;
    private final List<String> words;

    private final List<String> regexList = Collections.synchronizedList(new ArrayList<>());

    public WordCategory(int weight, int wordsCount, boolean accumulateWeight, List<String> words) {
        this.weight = weight;
        this.wordsCount = wordsCount;
        this.accumulateWeight = accumulateWeight;
        this.words = words;


        for (int i = 0; i < words.size(); i++) {

            StringBuilder regex = new StringBuilder();
            regex.append("\\b").append(words.get(i)).append("\\b");

            regexList.add(regex.toString());
        }
    }

    public int getWeight() {
        return weight;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public boolean isAccumulateWeight() {
        return accumulateWeight;
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> getRegexList() {
        return regexList;
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

}
