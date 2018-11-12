package com.earnix.webk.script.html;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.web_idl.ReadonlyAttribute;
import com.earnix.webk.script.web_idl.Unsigned;
import com.earnix.webk.script.whatwg_dom.EventHandler;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
public interface ApplicationCache {
    // update status
    @Unsigned short UNCACHED = 0;
    @Unsigned short IDLE = 1;
    @Unsigned short CHECKING = 2;
    @Unsigned short DOWNLOADING = 3;
    @Unsigned short UPDATEREADY = 4;
    @Unsigned short OBSOLETE = 5;

    @ReadonlyAttribute
    @Unsigned
    short status();

    // updates
    void update();

    void abort();

    void swapCache();

    // events
    Attribute<EventHandler> onchecking();

    Attribute<EventHandler> onerror();

    Attribute<EventHandler> onnoupdate();

    Attribute<EventHandler> ondownloading();

    Attribute<EventHandler> onprogress();

    Attribute<EventHandler> onupdateready();

    Attribute<EventHandler> oncached();

    Attribute<EventHandler> onobsolete();
}
