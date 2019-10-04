package com.sirayax.rsshandler;

import com.sirayax.rsshandler.processer.ContentExtractor;
import com.sirayax.rsshandler.processer.PublishService;
import com.sirayax.rsshandler.utility.SetupUtility;

import java.net.http.HttpRequest;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SetupUtility setupUtility = new SetupUtility();
        String lastExecutionTime = setupUtility.recordHandleTime("read");
        ArrayList<String> urlList = setupUtility.loadURL();
        ContentExtractor extractor = new ContentExtractor(lastExecutionTime);
        PublishService publishService = new PublishService();
        urlList.forEach(url -> extractor.extract(url).forEach(article -> {
            String bodyString = "message=" + article.toPublishString();
            System.out.println(bodyString);
            publishService.setRequest("POST", "https://notify-api.line.me/api/notify", HttpRequest.BodyPublishers.ofString(bodyString), "application/x-www-form-urlencoded", "");
            publishService.sendRequest();
        }));
        setupUtility.recordHandleTime("write");
    }


}
