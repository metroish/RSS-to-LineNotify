package com.sirayax.rsshandler.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class SetupUtility {

    public ArrayList<String> loadURL() {
        ArrayList<String> urlList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("url.txt").toURI()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                urlList.add(line);
            }
            bufferedReader.close();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return urlList;
    }

    public String recordHandleTime(String type) {
        try {
            if ("read".equals(type)) {
                BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("lastOrder.txt").toURI()));
                String line = bufferedReader.readLine();
                if (line != null) {
                    bufferedReader.close();
                    return line;
                } else {
                    return String.valueOf(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
                }
            }
            if ("write".equals(type)) {
                Files.write(Paths.get(ClassLoader.getSystemResource("lastOrder.txt").toURI()), String.valueOf(System.currentTimeMillis()).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "OK";
    }

}
