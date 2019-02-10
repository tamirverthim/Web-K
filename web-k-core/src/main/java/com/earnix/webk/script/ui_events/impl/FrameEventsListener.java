package com.earnix.webk.script.ui_events.impl;

import com.earnix.webk.script.ScriptContext;
import com.earnix.webk.script.whatwg_dom.Element;
import lombok.RequiredArgsConstructor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Taras Maslov
 * 12/18/2018
 */
@RequiredArgsConstructor
public class FrameEventsListener implements WindowListener, MouseListener {

    private final ScriptContext scriptContext;
    private final MouseEventsAdapter mouseEventsAdapter;


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (mouseEventsAdapter.getHoveredBox() != null) {
            Element element = mouseEventsAdapter.getHoveredBox().getElement();
            mouseEventsAdapter.mouseleave(element, mouseEventsAdapter.getLastAwtMouseEvent());
            mouseEventsAdapter.mouseout(element, mouseEventsAdapter.getLastAwtMouseEvent());
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        if (mouseEventsAdapter.getHoveredBox() != null && mouseEventsAdapter.getLastAwtMouseEvent() != null) {
            Element element =  mouseEventsAdapter.getHoveredBox().getElement();
            mouseEventsAdapter.mouseleave(element, mouseEventsAdapter.getLastAwtMouseEvent());
            mouseEventsAdapter.mouseout(element, mouseEventsAdapter.getLastAwtMouseEvent());
        }
    }
}
