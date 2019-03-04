/*
 * {{{ header & license
 * Copyright (c) 2007 Sean Bright
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.earnix.webk.swing;

import com.earnix.webk.render.Box;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * A MouseTracker is used to delegate mouse events to the {@link FSMouseListener} instances
 * associated with a {@link BasicPanel}. The tracker will start receiving events as soon
 * as the first listener is added (via {@link #addListener(FSMouseListener)} and will stop receiving events as soon
 * as the last listener is removed via {@link #removeListener(FSMouseListener)}. This binding is handled automatically
 * via the add and remove methods and the tracker will remain active as long as the tracker has at least one listener.
 * The MouseTracker is also responsible for using MouseEvent coordinates to located the Box on which the mouse is
 * acting.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MouseTracker implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    BasicPanel panel;
    List<FSMouseListener> handlers;
    Box lastHoveredBox;
    boolean enabled;

    /**
     * Instantiates a MouseTracker to listen to mouse events for the given panel.
     *
     * @param panel the panel for which mouse events should be delegated.
     */
    public MouseTracker(BasicPanel panel) {
        this.panel = panel;

        /**
         * {@link CopyOnWriteArrayList} is used to allow iterate and modify handlers at the same time. It may happen
         * if the handler tries to add/remove itself directly or indirectly during the iteration over the handlers
         * (e.g. on {@link #fireMouseUp(Box) event.}.
         */
        handlers = new CopyOnWriteArrayList<>();
    }

    /**
     * Adds a listener to receive callbacks on mouse events.
     *
     * @param l the listener
     */
    public void addListener(FSMouseListener l) {
        if (l == null) {
            return;
        }

        handlers.add(l);

        if (!enabled && handlers.size() > 0) {
            panel.addMouseListener(this);
            panel.addMouseMotionListener(this);

            enabled = true;
        }
    }

    /**
     * Removes the given listener, after which it will no longer receive callbacks on mouse events.
     *
     * @param l the listener to remove
     */
    public void removeListener(FSMouseListener l) {
        if (l == null) {
            return;
        }

        handlers.remove(l);

        if (enabled && handlers.size() == 0) {
            panel.removeMouseListener(this);
            panel.removeMouseMotionListener(this);

            enabled = false;
        }
    }

    /**
     * Returns a (new) list of all listeners currently tracked for receiving events.
     *
     * @return a (new) list of all listeners currently tracked for receiving events.
     */
    public List<FSMouseListener> getListeners() {
        return new ArrayList<>(handlers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        handleMouseMotion(panel.find(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        handleMouseMotion(panel.find(e));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClick(panel.find(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseMotion(panel.find(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        handleMouseUp(panel.find(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        fireMousePressed(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        fireMouseDragged(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // todo
    }

    /**
     * Utility method; calls {@link FSMouseListener#reset()} for all listeners currently being tracked.
     */
    public void reset() {
        lastHoveredBox = null;
        handlers.forEach(FSMouseListener::reset);
    }

    private void handleMouseClick(Box box) {
        if(box == null) {
            return;
        }
        
        fireMouseClick(box);
    }

    // handles delegation of mouse events to listeners
    private void handleMouseMotion(Box box) {
        if (box == null || box.equals(lastHoveredBox)) {
            return;
        }

        if (lastHoveredBox != null) {
            fireMouseOut(lastHoveredBox);
        }

        fireMouseOver(box);

        lastHoveredBox = box;
    }


    private void handleMouseUp(Box box) {
        if (box == null) {
            return;
        }

        fireMouseUp(box);
    }

    private void fireMouseClick(Box box){
        handlers.forEach(h -> h.onMouseClick(panel, box));
    }
    
    // delegates onMouseOver() to all listeners
    private void fireMouseOver(Box box) {
        handlers.forEach(_handler -> _handler.onMouseOver(panel, box));
    }

    // delegates onMouseOut() to all listeners
    private void fireMouseOut(Box box) {
        handlers.forEach(_handler -> _handler.onMouseOut(panel, box));
    }

    // delegates onMouseUp() to all listeners
    private void fireMouseUp(Box box) {
        handlers.forEach(_handler -> (_handler).onMouseUp(panel, box));
    }

    // delegates onMousePressed() to all listeners
    private void fireMousePressed(MouseEvent e) {
        handlers.forEach(_handler -> _handler.onMousePressed(panel, e));
    }

    // delegates onMouseDragged() to all listeners
    private void fireMouseDragged(MouseEvent e) {
        handlers.forEach(_handler -> _handler.onMouseDragged(panel, e));
    }
}
