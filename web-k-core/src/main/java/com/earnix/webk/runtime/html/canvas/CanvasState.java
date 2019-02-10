package com.earnix.webk.runtime.html.canvas;

import com.earnix.webk.runtime.web_idl.Mixin;

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
