package org.xhtmlrenderer.browser;

/**
 * Entry point for launching Browser application in a sandboxed
 * environment; use instead of BrowserStartup in that case.
 * Disables or removes menu items and controls that are not
 * useful in a sandboxed environment.
 *
 * @author Patrick Wright
 */
public class BrowserSandboxLauncher {
    public static void main(String[] args) {
        KBrowserApplication bs = new KBrowserApplication();
        bs.initUI();

        bs.getPanel().getUrl().setVisible(false);
        bs.getPanel().getGoToPage().setVisible(false);
        bs.getActions().getOpenFile().setEnabled(false);

        bs.launch();
    }
}
