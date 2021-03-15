package com.github.irvinglink.ChatFilter.monitors;

import com.github.irvinglink.ChatFilter.loaders.ILoader;
import com.github.irvinglink.ChatFilter.loaders.ThresholdLoader;
import com.github.irvinglink.ChatFilter.loaders.WhitelistIPLoader;
import com.github.irvinglink.ChatFilter.loaders.WordCategoryLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoaderMonitor {

    private final List<ILoader> loaderList = Collections.synchronizedList(new ArrayList<>());

    public LoaderMonitor() {
        register();
        load();
    }

    private void register() {
        loaderList.add(new WordCategoryLoader());
        loaderList.add(new ThresholdLoader());
        loaderList.add(new WhitelistIPLoader());
    }

    private void load() {
        loaderList.forEach(ILoader::load);
    }

    public void update(){
        loaderList.forEach(ILoader::update);
    }
}
