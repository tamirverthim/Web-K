package com.earnix.webk.script.xhr.impl;

import com.earnix.webk.script.web_idl.Attribute;
import com.earnix.webk.script.whatwg_dom.EventHandler;
import com.earnix.webk.script.whatwg_dom.EventTarget;
import com.earnix.webk.script.whatwg_dom.impl.EventTargetImpl;
import com.earnix.webk.script.whatwg_dom.impl.Level1EventTarget;
import com.earnix.webk.script.xhr.XMLHttpRequestUpload;
import lombok.AccessLevel;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

/**
 * @author Taras Maslov
 * 12/2p0/2018
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XMLHttpRequestUploadImpl implements XMLHttpRequestUpload {

    @Delegate(types = {EventTarget.class})
    EventTargetImpl eventTargetImpl = new EventTargetImpl();
    Level1EventTarget level1EventTarget = new Level1EventTarget(eventTargetImpl);

    @Override
    public Attribute<EventHandler> onloadstart() {
        return level1EventTarget.getHandlerAttribute("onloadstart");
    }

    @Override
    public Attribute<EventHandler> onprogress() {
        return level1EventTarget.getHandlerAttribute("onprogress");
    }

    @Override
    public Attribute<EventHandler> onabort() {
        return level1EventTarget.getHandlerAttribute("onabort");
    }

    @Override
    public Attribute<EventHandler> onerror() {
        return level1EventTarget.getHandlerAttribute("onerror");
    }

    @Override
    public Attribute<EventHandler> onload() {
        return level1EventTarget.getHandlerAttribute("onload");
    }

    @Override
    public Attribute<EventHandler> ontimeout() {
        return level1EventTarget.getHandlerAttribute("ontimeout");
    }

    @Override
    public Attribute<EventHandler> onloadend() {
        return level1EventTarget.getHandlerAttribute("onloadend");
    }
}
