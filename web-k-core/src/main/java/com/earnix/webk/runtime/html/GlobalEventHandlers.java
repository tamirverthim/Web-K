package com.earnix.webk.runtime.html;

import com.earnix.webk.runtime.web_idl.Attribute;
import com.earnix.webk.runtime.web_idl.LenientThis;
import com.earnix.webk.runtime.web_idl.Mixin;
import com.earnix.webk.runtime.whatwg_dom.EventHandler;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@Mixin
public interface GlobalEventHandlers {
    Attribute<EventHandler> onabort();

    Attribute<EventHandler> onauxclick();

    Attribute<EventHandler> onblur();

    Attribute<EventHandler> oncancel();

    Attribute<EventHandler> oncanplay();

    Attribute<EventHandler> oncanplaythrough();

    Attribute<EventHandler> onchange();

    Attribute<EventHandler> onclick();

    Attribute<EventHandler> onclose();

    Attribute<EventHandler> oncontextmenu();

    Attribute<EventHandler> oncuechange();

    Attribute<EventHandler> ondblclick();

    Attribute<EventHandler> ondrag();

    Attribute<EventHandler> ondragend();

    Attribute<EventHandler> ondragenter();

    Attribute<EventHandler> ondragexit();

    Attribute<EventHandler> ondragleave();

    Attribute<EventHandler> ondragover();

    Attribute<EventHandler> ondragstart();

    Attribute<EventHandler> ondrop();

    Attribute<EventHandler> ondurationchange();

    Attribute<EventHandler> onemptied();

    Attribute<EventHandler> onended();

//    Attribute<OnErrorEventHandler> onerror(); todo

    Attribute<EventHandler> onfocus();

    Attribute<EventHandler> oninput();

    Attribute<EventHandler> oninvalid();

    Attribute<EventHandler> onkeydown();

    Attribute<EventHandler> onkeypress();

    Attribute<EventHandler> onkeyup();

    Attribute<EventHandler> onload();

    Attribute<EventHandler> onloadeddata();

    Attribute<EventHandler> onloadedmetadata();

    Attribute<EventHandler> onloadend();

    Attribute<EventHandler> onloadstart();

    Attribute<EventHandler> onmousedown();

    @LenientThis
    Attribute<EventHandler> onmouseenter();

    @LenientThis
    Attribute<EventHandler> onmouseleave();

    Attribute<EventHandler> onmousemove();

    Attribute<EventHandler> onmouseout();

    Attribute<EventHandler> onmouseover();

    Attribute<EventHandler> onmouseup();

    Attribute<EventHandler> onwheel();

    Attribute<EventHandler> onpause();

    Attribute<EventHandler> onplay();

    Attribute<EventHandler> onplaying();

    Attribute<EventHandler> onprogress();

    Attribute<EventHandler> onratechange();

    Attribute<EventHandler> onreset();

    Attribute<EventHandler> onresize();

    Attribute<EventHandler> onscroll();

    Attribute<EventHandler> onsecuritypolicyviolation();

    Attribute<EventHandler> onseeked();

    Attribute<EventHandler> onseeking();

    Attribute<EventHandler> onselect();

    Attribute<EventHandler> onstalled();

    Attribute<EventHandler> onsubmit();

    Attribute<EventHandler> onsuspend();

    Attribute<EventHandler> ontimeupdate();

    Attribute<EventHandler> ontoggle();

    Attribute<EventHandler> onvolumechange();

    Attribute<EventHandler> onwaiting();
}
