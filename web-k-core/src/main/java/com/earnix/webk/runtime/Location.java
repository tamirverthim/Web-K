package com.earnix.webk.runtime;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Taras Maslov
 * 5/29/2018
 */
@Slf4j
public class Location {
    String href;
    String hostname;
    String pathname;
    String protocol;

    void assign(Location next) {
        log.error("location.assign");
    }

    ;
}
