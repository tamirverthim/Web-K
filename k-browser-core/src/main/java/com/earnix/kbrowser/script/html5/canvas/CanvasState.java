package com.earnix.kbrowser.script.html5.canvas;

import com.earnix.kbrowser.script.web_idl.Mixin;

/**
 * @author Taras Maslov
 * 6/20/2018
 */
@Mixin
public interface CanvasState {
    // state
    void save(); // push state on state stack

    void restore(); // pop state stack and restore state
}
