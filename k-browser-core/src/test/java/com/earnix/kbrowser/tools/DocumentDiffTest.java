/*
 * {{{ header & license
 * Copyright (c) 2004, 2005 Joshua Marinacci
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
package com.earnix.kbrowser.tools;

import com.earnix.kbrowser.render.Box;
import com.earnix.kbrowser.simple.Graphics2DRenderer;
import com.earnix.kbrowser.util.AssertHelper;
import com.earnix.kbrowser.util.Util;
import com.earnix.kbrowser.util.XMLUtil;
import com.earnix.kbrowser.util.XRLog;
import lombok.extern.slf4j.Slf4j;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;

@Slf4j
public class DocumentDiffTest {
    public static final int width = 500;
    public static final int height = 500;

    public void runTests(File dir, int width, int height)
            throws Exception {
        AssertHelper.assertState(dir.exists());
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                runTests(files[i], width, height);
                continue;
            }
            if (files[i].getName().endsWith(".xhtml")) {
                String testfile = files[i].getAbsolutePath();
                String difffile = testfile.substring(0, testfile.length() - 6) + ".diff";
                XRLog.log("unittests", Level.WARNING, "test file = " + testfile);
                //Uu.p( "diff file = " + difffile );
                try {
                    boolean is_correct = compareTestFile(testfile, difffile, width, height);
                    XRLog.log("unittests", Level.WARNING, "is correct = " + is_correct);
                } catch (Throwable thr) {
                    XRLog.log("unittests", Level.WARNING, thr.toString());
                    thr.printStackTrace();
                }
            }
        }

    }

    public void generateDiffs(File dir, int width, int height)
            throws Exception {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                generateDiffs(files[i], width, height);
                continue;
            }
            if (files[i].getName().endsWith(".xhtml")) {
                String testfile = files[i].getAbsolutePath();
                String difffile = testfile.substring(0, testfile.length() - 6) + ".diff";
                //Uu.p("test file = " + testfile);
                generateTestFile(testfile, difffile, width, height);
                log.debug("generated = " + difffile);
            }
        }

    }

    public static void generateTestFile(String test, String diff, int width, int height)
            throws Exception {
        log.debug("test = " + test);
        String out = xhtmlToDiff(test, width, height);
        //Uu.p("diff = \n" + out);
        Util.string_to_file(out, new File(diff));

    }

    public static String xhtmlToDiff(String xhtml, int width, int height)
            throws Exception {
        com.earnix.kbrowser.dom.nodes.Document doc = XMLUtil.documentFromFile(xhtml);
        Graphics2DRenderer renderer = new Graphics2DRenderer();
        renderer.setDocument(doc, new File(xhtml).toURL().toString());

        BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) buff.getGraphics();

        Dimension dim = new Dimension(width, height);
        renderer.layout(g, dim);
        renderer.render(g);

        StringBuffer sb = new StringBuffer();
        getDiff(sb, renderer.getPanel().getRootBox(), "");
        return sb.toString();
    }

    public boolean compareTestFile(String test, String diff, int width, int height)
            throws Exception {
        String tin = xhtmlToDiff(test, width, height);
        String din = null;
        try {
            din = Util.file_to_string(diff);
        } catch (FileNotFoundException ex) {
            XRLog.log("unittests", Level.WARNING, "diff file missing");
            return false;
        }
        //XRLog.log("unittests",Level.WARNING,"tin = " + tin);
        //XRLog.log("unittests",Level.WARNING,"din = " + din);
        if (tin.equals(din)) {
            return true;
        }
        XRLog.log("unittests", Level.WARNING, "warning not equals");
        File dfile = new File("correct.diff");
        File tfile = new File("test.diff");
        XRLog.log("unittests", Level.WARNING, "writing to " + dfile + " and " + tfile);
        Util.string_to_file(tin, tfile);
        Util.string_to_file(din, dfile);
        //System.exit(-1);
        return false;
    }

    /**
     * Gets the diff attribute of the DocumentDiffTest object
     *
     * @param sb  PARAM
     * @param box PARAM
     * @param tab PARAM
     */
    public static void getDiff(StringBuffer sb, Box box, String tab) {
        /* sb.append(tab + box.getTestString() + "\n"); */
        for (int i = 0; i < box.getChildCount(); i++) {
            getDiff(sb, (Box) box.getChild(i), tab + " ");
        }

    }

    /**
     * The main program for the DocumentDiffTest class
     *
     * @param args The command line arguments
     * @throws Exception Throws
     */
    public static void main(String[] args)
            throws Exception {

        XRLog.setLevel("plumbing.general", Level.OFF);
        //String testfile = "tests/diff/background/01.xhtml";
        //String difffile = "tests/diff/background/01.diff";
        String file = null;
        if (args.length == 0) {
            file = "tests/diff";
        } else {
            file = args[0];
        }
        DocumentDiffTest ddt = new DocumentDiffTest();
        if (new File(file).isDirectory()) {
            ddt.runTests(new File(file), width, height);
        } else {
            System.out.println(xhtmlToDiff(file, 1280, 768));
        }
    }
}

