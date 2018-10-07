package org.xhtmlrenderer.browser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.Timer;
import java.util.TimerTask;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrowserStatus extends JPanel {
    
    @Getter JLabel text;
    JLabel memory;

    void init() {
        createComponents();
        createLayout();
        createEvents();
    }

    private void createComponents() {
        text = new JLabel("Status");
        memory = new JLabel("? MB / ? MB");
    }

    private void createLayout() {
        setLayout(new BorderLayout(5, 5));
        add("Center", text);
        add("East", memory);
    }

    public Insets getInsets() {
        return new Insets(3, 4, 3, 4);
    }

    private void createEvents() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Runtime rt = Runtime.getRuntime();
                long used = rt.totalMemory() - rt.freeMemory();
                long total = rt.totalMemory();

                used = used / (1024 * 1024);
                total = total / (1024 * 1024);

                final String text = used + "M / " + total + "M";
                SwingUtilities.invokeLater(() -> memory.setText(text));
            }
        }, 0, 5000);
    }

}
