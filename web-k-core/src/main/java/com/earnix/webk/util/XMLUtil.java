/*
 * {{{ header & license
 * XMLUtil.java
 * Copyright (c) 2004, 2005 Patrick Wright
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
 * }}}
 */
package com.earnix.webk.util;

import com.earnix.webk.dom.Jsoup;
import com.earnix.webk.dom.nodes.DocumentModel;

import java.io.File;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;


/**
 * Booch utility class for XML processing using DOM
 */
public class XMLUtil {

    public static DocumentModel documentFromString(final String documentContents) {
        return Jsoup.parse(documentContents);
    }

    public static DocumentModel documentFromFile(final String filename)
            throws Exception {

        return Jsoup.parse(new File(filename), "UTF-8");
    }

}
