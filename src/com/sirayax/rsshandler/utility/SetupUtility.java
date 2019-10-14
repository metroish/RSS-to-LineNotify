package com.sirayax.rsshandler.utility;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SetupUtility {

    private Properties properties;
    private String propertiesPath;
    private SimpleDateFormat formatter;

    public SetupUtility() {
        propertiesPath = Objects.requireNonNull(getClass().getClassLoader().getResource("")).getPath() + "app.properties";
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            properties = new Properties();
            properties.load(new FileInputStream(propertiesPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadLineNotifyToken() {
        return properties.getProperty("RSS.Line.Notify.Token");
    }

    public String loadLineNotifyURL() {
        return properties.getProperty("RSS.Line.Notify.URL");
    }

    public ArrayList<String> loadURL() {
        return new ArrayList<>(Arrays.asList(properties.getProperty("RSS.URL.List").split(";")));
    }

    public Date loadLastExecutionTime() {
        try {
            return formatter.parse(properties.getProperty("RSS.Last.Execution"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 12);
    }

    public void saveLastExecutionTime() {
        try {
            properties.setProperty("RSS.Last.Execution", formatter.format(new Date()));
            properties.store(new FileWriter(propertiesPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
