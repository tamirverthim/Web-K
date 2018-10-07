/*
 * StyleReference.java
 * Copyright (c) 2004, 2005 Torbjoern Gannholm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 */
package com.earnix.kbrowser.context;

import com.earnix.kbrowser.css.constants.CSSName;
import com.earnix.kbrowser.css.extend.AttributeResolver;
import com.earnix.kbrowser.css.extend.lib.DOMTreeResolver;
import com.earnix.kbrowser.css.newmatch.CascadedStyle;
import com.earnix.kbrowser.css.newmatch.Matcher;
import com.earnix.kbrowser.css.newmatch.PageInfo;
import com.earnix.kbrowser.css.sheet.PropertyDeclaration;
import com.earnix.kbrowser.css.sheet.Stylesheet;
import com.earnix.kbrowser.css.sheet.StylesheetInfo;
import com.earnix.kbrowser.dom.nodes.Document;
import com.earnix.kbrowser.dom.nodes.Element;
import com.earnix.kbrowser.dom.nodes.Node;
import com.earnix.kbrowser.extend.NamespaceHandler;
import com.earnix.kbrowser.extend.UserAgentCallback;
import com.earnix.kbrowser.extend.UserInterface;
import com.earnix.kbrowser.layout.SharedContext;
import com.earnix.kbrowser.swing.BasicPanel;
import com.earnix.kbrowser.util.XRLog;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Torbjoern Gannholm
 */
public class StyleReference {
    /**
     * The Context this StyleReference operates in; used for property
     * resolution.
     */
    private SharedContext _context;

    private NamespaceHandler _nsh;

    private Document _doc;

    private StylesheetFactoryImpl _stylesheetFactory;

    /**
     * Instance of our element-styles matching class. Will be null if new rules
     * have been added since last match.
     */
    private Matcher _matcher;

    private UserAgentCallback _uac;

    /**
     * Default constructor for initializing members.
     */
    public StyleReference(UserAgentCallback userAgent) {
        _uac = userAgent;
        _stylesheetFactory = new StylesheetFactoryImpl(userAgent);
    }

    public void setDocumentContext(SharedContext context, NamespaceHandler nsh, Document doc, UserInterface ui) {
        _context = context;
        _nsh = nsh;
        _doc = doc;
        AttributeResolver attRes = new StandardAttributeResolver(_nsh, _uac, ui);

        List infos = getStylesheets();
        XRLog.match("media = " + _context.getMedia());
        _matcher = new Matcher(
                new DOMTreeResolver(),
                attRes,
                _stylesheetFactory,
                readAndParseAll(infos, _context.getMedia()),
                _context.getMedia());
    }

    private List readAndParseAll(List infos, String medium) {
        List result = new ArrayList(infos.size() + 15);
        for (Iterator i = infos.iterator(); i.hasNext(); ) {
            StylesheetInfo info = (StylesheetInfo) i.next();
            if (info.appliesToMedia(medium)) {
                Stylesheet sheet = info.getStylesheet();

                if (sheet == null) {
                    sheet = _stylesheetFactory.getStylesheet(info);
                }

                if (sheet != null) {
                    if (sheet.getImportRules().size() > 0) {
                        result.addAll(readAndParseAll(sheet.getImportRules(), medium));
                    }

                    result.add(sheet);
                } else {
                    XRLog.load(Level.WARNING, "Unable to load CSS from " + info.getUri());
                }
            }
        }

        return result;
    }

    public boolean isHoverStyled(Element e) {
        return _matcher.isHoverStyled(e);
    }

    /**
     * Returns a Map keyed by CSS property names (e.g. 'border-width'), and the
     * assigned value as a SAC CSSValue instance. The properties should have
     * been matched to the element when the Context was established for this
     * StyleReference on the Document to which the Element belongs. See {@link
     * BasicPanel#setDocument(Document, java.net.URL)}
     * for an example of how to establish a StyleReference and associate to a
     * Document.
     *
     * @param e The DOM Element for which to find properties
     * @return Map of CSS property names to CSSValue instance assigned to it.
     */
    public java.util.Map getCascadedPropertiesMap(Element e) {
        CascadedStyle cs = _matcher.getCascadedStyle(e, false);//this is only for debug, I think
        java.util.LinkedHashMap props = new java.util.LinkedHashMap();
        for (java.util.Iterator i = cs.getCascadedPropertyDeclarations(); i.hasNext(); ) {
            PropertyDeclaration pd = (PropertyDeclaration) i.next();

            String propName = pd.getPropertyName();
            CSSName cssName = CSSName.getByPropertyName(propName);
            props.put(propName, cs.propertyByName(cssName).getValue());
        }
        return props;
    }

    /**
     * Gets the pseudoElementStyle attribute of the StyleReference object
     *
     * @param node          PARAM
     * @param pseudoElement PARAM
     * @return The pseudoElementStyle value
     */
    public CascadedStyle getPseudoElementStyle(Node node, String pseudoElement) {
        Element e = null;
        if (node instanceof Element) {
            e = (Element) node;
        } else {
            e = (Element) node.parent();
        }
        return _matcher.getPECascadedStyle(e, pseudoElement);
    }

    /**
     * Gets the CascadedStyle for an element. This must then be converted in the
     * current context to a CalculatedStyle (use getDerivedStyle)
     *
     * @param e       The element
     * @param restyle
     * @return The style value
     */
    public CascadedStyle getCascadedStyle(Element e, boolean restyle) {
        if (e == null) return CascadedStyle.emptyCascadedStyle;
        return _matcher.getCascadedStyle(e, restyle);
    }

    public PageInfo getPageStyle(String pageName, String pseudoPage) {
        return _matcher.getPageCascadedStyle(pageName, pseudoPage);
    }

    /**
     * Flushes any stylesheet associated with this stylereference (based on the user agent callback) that are in cache.
     */
    public void flushStyleSheets() {
        String uri = _uac.getBaseURL();
        StylesheetInfo info = new StylesheetInfo();
        info.setUri(uri);
        info.setOrigin(StylesheetInfo.AUTHOR);
        if (_stylesheetFactory.containsStylesheet(uri)) {
            _stylesheetFactory.removeCachedStylesheet(uri);
            XRLog.cssParse("Removing stylesheet '" + uri + "' from cache by request.");
        } else {
            XRLog.cssParse("Requested removing stylesheet '" + uri + "', but it's not in cache.");

        }
    }

    public void flushAllStyleSheets() {
        _stylesheetFactory.flushCachedStylesheets();
    }

    /**
     * Gets StylesheetInfos for all stylesheets and inline styles associated
     * with the current document. Default (user agent) stylesheet and the inline
     * style for the current media are loaded and cached in the
     * StyleSheetFactory by URI.
     *
     * @return The stylesheets value
     */
    private List getStylesheets() {
        List infos = new LinkedList();
        long st = System.currentTimeMillis();

        StylesheetInfo defaultStylesheet = _nsh.getDefaultStylesheet(_stylesheetFactory);
        if (defaultStylesheet != null) {
            infos.add(defaultStylesheet);
        }

        StylesheetInfo[] refs = _nsh.getStylesheets(_doc);
        int inlineStyleCount = 0;
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                String uri;

                if (!refs[i].isInline()) {
                    uri = _uac.resolveURI(refs[i].getUri());
                    refs[i].setUri(uri);
                } else {
                    refs[i].setUri(_uac.getBaseURL() + "#inline_style_" + (++inlineStyleCount));
                    Stylesheet sheet = _stylesheetFactory.parse(
                            new StringReader(refs[i].getContent()), refs[i]);
                    refs[i].setStylesheet(sheet);
                    refs[i].setUri(null);
                }
            }
        }
        infos.addAll(Arrays.asList(refs));

        // TODO: here we should also get user stylesheet from userAgent

        long el = System.currentTimeMillis() - st;
        XRLog.load("TIME: parse stylesheets  " + el + "ms");

        return infos;
    }

    public void removeStyle(Element e) {
        if (_matcher != null) {
            _matcher.removeStyle(e);
        }
    }

    public List getFontFaceRules() {
        return _matcher.getFontFaceRules();
    }

    public void setUserAgentCallback(UserAgentCallback userAgentCallback) {
        _uac = userAgentCallback;
        _stylesheetFactory.setUserAgentCallback(userAgentCallback);
    }

    public void setSupportCMYKColors(boolean b) {
        _stylesheetFactory.setSupportCMYKColors(b);
    }
}