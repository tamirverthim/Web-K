package com.earnix.webk.dom.examples;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.select.Elements;
import com.earnix.webk.script.impl.ElementImpl;
import com.earnix.webk.script.whatwg_dom.impl.DocumentImpl;

import java.io.IOException;

/**
 * A simple example, used on the jsoup website.
 */
public class Wikipedia {
    public static void main(String[] args) throws IOException {
        DocumentImpl doc = Jsoup.connect("http://en.wikipedia.org/").get();
        log(doc.title());

        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (ElementImpl headline : newsHeadlines) {
            log("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
        }
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}
