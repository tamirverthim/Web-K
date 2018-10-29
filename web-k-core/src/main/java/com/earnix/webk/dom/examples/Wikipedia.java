package com.earnix.webk.dom.examples;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.nodes.DocumentModel;
import com.earnix.webk.dom.nodes.ElementModel;
import com.earnix.webk.dom.select.Elements;

import java.io.IOException;

/**
 * A simple example, used on the jsoup website.
 */
public class Wikipedia {
    public static void main(String[] args) throws IOException {
        DocumentModel doc = Jsoup.connect("http://en.wikipedia.org/").get();
        log(doc.title());

        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (ElementModel headline : newsHeadlines) {
            log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
        }
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}
