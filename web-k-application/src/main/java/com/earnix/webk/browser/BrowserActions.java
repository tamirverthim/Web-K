/*
 * {{{ header & license
 * Copyright (c) 2004 Joshua Marinacci
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
package com.earnix.webk.browser;

import com.earnix.webk.browser.actions.EmptyAction;
import com.earnix.webk.browser.actions.FontSizeAction;
import com.earnix.webk.layout.SharedContext;
import com.earnix.webk.simple.XHTMLPanel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class BrowserActions {

    WebKApplication root;
    Action openFile;
    Action quit;
    Action forward;
    Action backward;
    Action refresh;
    Action reload;
    Action load;
    Action stop;
    Action printPreview;
    Action goHome;
    Action usersManual;
    Action aboutPage;
    Action increaseFont;
    Action decreaseFont;
    Action resetFont;
    Action goToPage;

    /**
     * The system logger for app.browser
     */
    public static final Logger logger = Logger.getLogger("app.browser");

    BrowserActions(WebKApplication root) {
        this.root = root;
    }

    void init() {
        URL url = null;
        url = getImageUrl("images/process-stop.png");
        stop = new AbstractAction("Stop", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                // TODO: stop not coded
                System.out.println("stop called");
                // root.panel.view.stop();
            }
        };
        // TODO: need right API call for ESC
        //stop.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE));
        stop.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        openFile = new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                openAndShowFile();
            }
        };
        openFile.putValue(Action.NAME, "Open File...");
        setAccel(openFile, KeyEvent.VK_O);
        setMnemonic(openFile, KeyEvent.VK_O);

        quit = new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        };

        setName(quit, "Quit");
        setAccel(quit, KeyEvent.VK_Q);
        setMnemonic(quit, KeyEvent.VK_Q);

        url = getImageUrl("images/go-previous.png");
        backward = new EmptyAction("Back", "Go back one page", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().goBack();
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        backward.setEnabled(false);
        backward.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
                        KeyEvent.ALT_MASK));


        url = getImageUrl("images/go-next.png");
        forward = new EmptyAction("Forward", "Go forward one page", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().goForward();
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        forward.setEnabled(false);
        forward.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
                        KeyEvent.ALT_MASK));

        url = getImageUrl("images/view-refresh.png");
        refresh = new EmptyAction("Refresh", "Refresh page", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().getView().invalidate();
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        refresh.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke("F5"));

        url = getImageUrl("images/view-refresh.png");
        reload = new EmptyAction("Reload", "Reload page", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().reloadPage();
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        reload.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_F5,
                        InputEvent.SHIFT_MASK));
        reload.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);

        printPreview = new EmptyAction("Print Preview", "Print preview mode", null) {
            public void actionPerformed(ActionEvent evt) {
                togglePrintPreview();
            }
        };
        printPreview.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

        load = new AbstractAction("Load") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String url_text = root.getPanel().getUrl().getText();
                    root.getPanel().loadPage(url_text);
//                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        url = getImageUrl("images/media-playback-start_16x16.png");
        goToPage = new EmptyAction("Go", "Go to URL in address bar", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String url_text = root.getPanel().getUrl().getText();
                    root.getPanel().loadPage(url_text);
//                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        url = getImageUrl("images/go-home.png");
        goHome = new EmptyAction("Go Home", "Browser homepage", new ImageIcon(url)) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().loadPage(root.getStartPage());
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        usersManual = new EmptyAction("FS User's Guide", "Flying Saucer User's Guide", null) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    root.getPanel().loadPage("/users-guide-r8.html");
                    root.getPanel().getView().repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        aboutPage = new EmptyAction("About", "About the Browser Demo", null) {
            public void actionPerformed(ActionEvent evt) {
                try {
                    showAboutDialog();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        increaseFont = new FontSizeAction(root, FontSizeAction.INCREMENT);
        increaseFont.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        increaseFont.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

        resetFont = new FontSizeAction(root, FontSizeAction.RESET);
        resetFont.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_0,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        resetFont.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

        decreaseFont = new FontSizeAction(root, FontSizeAction.DECREMENT);
        decreaseFont.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        decreaseFont.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

        setName(increaseFont, "Increase");
        setName(resetFont, "Normal");
        setName(decreaseFont, "Decrease");
    }

    private void showAboutDialog() {
        final JDialog aboutDlg = new JDialog(root.getFrame());
        aboutDlg.setSize(new Dimension(500, 450));

        BrowserUserAgent uac = new BrowserUserAgent(getRoot());
        XHTMLPanel panel = new XHTMLPanel(uac);
        uac.setRepaintListener(panel);
        panel.setOpaque(false);

        panel.setDocument("demo:/demos/about.xhtml");

        JPanel outer = new JPanel(new BorderLayout());
        outer.add(panel, BorderLayout.CENTER);
        final JButton btn = new JButton(new AbstractAction("OK") {
            public void actionPerformed(ActionEvent e) {
                aboutDlg.dispose();
            }
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                btn.requestFocusInWindow();
            }
        });
        JPanel control = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        control.add(btn);
        outer.add(control, BorderLayout.SOUTH);

        aboutDlg.getContentPane().setLayout(new BorderLayout());
        aboutDlg.getContentPane().add(outer, BorderLayout.CENTER);

        aboutDlg.setTitle("About the Browser Demo");

        int xx = (root.getFrame().getWidth() - aboutDlg.getWidth()) / 2;
        int yy = (root.getFrame().getHeight() - aboutDlg.getHeight()) / 2;
        aboutDlg.setLocation(xx, yy);
        aboutDlg.setModal(true);
        aboutDlg.setVisible(true);
    }

    private void togglePrintPreview() {
        try {
            SharedContext sharedContext = root.getPanel().getView().getSharedContext();

            // flip status--either we are in "print" mode (print media) or non-print (screen media)
            if (sharedContext.isPrint()) {
                sharedContext.setPrint(false);
                sharedContext.setInteractive(true);
            } else {
                sharedContext.setPrint(true);
                sharedContext.setInteractive(false);
            }
            printPreview.putValue(Action.SHORT_DESCRIPTION,
                    !sharedContext.isPrint() ? "Print preview" : "Normal view");
            root.getPanel().reloadPage();
            root.getPanel().getView().repaint();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void openAndShowFile() {
        try {
            FileDialog fd = new FileDialog(root.getFrame(), "Open a local file", FileDialog.LOAD);
            fd.show();
            if (fd.getFile() != null) {
                final String url = new File(fd.getDirectory(), fd.getFile()).toURI().toURL().toString();
                root.getPanel().loadPage(url);
            }
        } catch (Exception ex) {
            logger.info("error:" + ex);
        }
    }

    /**
     * Sets the name attribute of the BrowserActions object
     *
     * @param act  The new name value
     * @param name The new name value
     */
    private static void setName(Action act, String name) {
        act.putValue(Action.NAME, name);
    }

    /**
     * Sets the accel attribute of the BrowserActions object
     *
     * @param act The new accel value
     * @param key The new accel value
     */
    private static void setAccel(Action act, int key) {
        act.putValue(Action.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(key,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    /**
     * Sets the mnemonic attribute of the BrowserActions object
     *
     * @param act  The new mnemonic value
     * @param mnem The new mnemonic value
     */
    private static void setMnemonic(Action act, Integer mnem) {
        act.putValue(Action.MNEMONIC_KEY, mnem);
    }

    private static URL getImageUrl(String url) {
        return BrowserActions.class.getClassLoader().getResource(url);
    }
}
