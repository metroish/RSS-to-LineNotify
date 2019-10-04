package com.sirayax.rsshandler.processer;

import com.sirayax.rsshandler.vo.ArticleObject;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ContentExtractor {
    private XMLInputFactory factory;
    private XMLEventReader eventReader;
    private XMLEvent xmlEvent;
    private ArrayList<ArticleObject> articleList;
    private ArticleObject articleObject;
    private String filterTime;

    public ContentExtractor() {
        this("NA");
    }

    public ContentExtractor(String cuttingPoint) {
        factory = XMLInputFactory.newInstance();
        filterTime = cuttingPoint;
    }

    public ArrayList<ArticleObject> extract(String url) {
        articleList = new ArrayList<>();
        Date filteredOldArticleDate = null;
        if (!"NA".equals(filterTime)) {
            filteredOldArticleDate = new Date(Long.parseLong(filterTime));
        }
        try {
            eventReader = factory.createXMLEventReader(new URL(url).openStream());
            while (eventReader.hasNext()) {
                xmlEvent = eventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if ("item".equalsIgnoreCase(startElement.getName().getLocalPart())) {
                        articleObject = new ArticleObject();
                    }
                    if (articleObject != null) {
                        switch (startElement.getName().getLocalPart().toLowerCase()) {
                            case "title":
                                articleObject.setTitle(eventReader.nextEvent().asCharacters().getData().trim());
                                break;
                            case "link":
                                articleObject.setLink(eventReader.nextEvent().asCharacters().getData().trim());
                                break;
                            case "description":
                                articleObject.setDescription(eventReader.nextEvent().asCharacters().getData().trim());
                                break;
                            case "pubdate":
                                articleObject.setPubDate(eventReader.nextEvent().asCharacters().getData().trim());
                                break;
                        }
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if ("item".equalsIgnoreCase(endElement.getName().getLocalPart())) {
                        if (filteredOldArticleDate != null) {
                            if (convertStringToDate(articleObject.getPubDate()).before(filteredOldArticleDate)) {
                                articleObject = null;
                                continue;
                            }
                        }
                        articleList.add(articleObject);
                    }
                }
            }
            eventReader.close();
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        return articleList;
    }

    private Date convertStringToDate(String timeString) {
        List<SimpleDateFormat> formatter = new ArrayList<>();
        // convert to RFC822 date time format with English locale
        formatter.add(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US));
        formatter.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        Date convertDate = null;
        Date parse;
        for (SimpleDateFormat tempFormat : formatter) {
            try {
                parse = tempFormat.parse(timeString);
            } catch (ParseException e) {
                e.printStackTrace();
                continue;
            }
            if (parse != null) {
                convertDate = parse;
                break;
            }
        }
        return convertDate != null ? convertDate : new Date();
    }
}
