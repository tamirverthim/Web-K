package org.xhtmlrenderer.net;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
public class Network {
    public static String load(String uri){
        try {
            return IOUtils.toString(new URI(uri), "UTF-8");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
