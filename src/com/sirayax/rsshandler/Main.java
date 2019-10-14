package com.sirayax.rsshandler;

import com.sirayax.rsshandler.processer.ContentExtractor;
import com.sirayax.rsshandler.processer.PublishService;
import com.sirayax.rsshandler.utility.SetupUtility;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        SetupUtility setupUtility = new SetupUtility();
        Date lastExecutionTime = setupUtility.loadLastExecutionTime();
        String lineNotifyURL = setupUtility.loadLineNotifyURL();
        String lineNotifyToken = setupUtility.loadLineNotifyToken();
        ArrayList<String> urlList = setupUtility.loadURL();
        ContentExtractor extractor = new ContentExtractor(lastExecutionTime);
        PublishService publishService = new PublishService();
        urlList.forEach(url -> extractor.extract(url).forEach(article -> {
            String bodyString = "message=" + article.toPublishString();
            System.out.println(bodyString);
            publishService.setRequest("POST", lineNotifyURL, HttpRequest.BodyPublishers.ofString(bodyString), "application/x-www-form-urlencoded", lineNotifyToken);
            publishService.sendRequest();
        }));
        setupUtility.saveLastExecutionTime();
    }


}
