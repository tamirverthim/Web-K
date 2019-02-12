package com.earnix.webk.runtime.ui_events.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;

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
       handleMouseExit();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        handleMouseExit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        handleMouseExit();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
       handleMouseExit();
    }
    
    private void handleMouseExit () {
        val hoveredElement = mouseEventsAdapter.getHovered();
        val lastMouseEvent = mouseEventsAdapter.getLastAwtMouseEvent();

        if (hoveredElement != null && lastMouseEvent != null) {
            mouseEventsAdapter.mouseleave(hoveredElement, lastMouseEvent);
            mouseEventsAdapter.mouseout(hoveredElement, lastMouseEvent);
        }
    }
}
