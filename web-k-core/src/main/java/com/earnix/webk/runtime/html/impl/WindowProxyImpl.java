package com.earnix.webk.runtime.html.impl;

import com.earnix.webk.runtime.html.Window;
import com.earnix.webk.runtime.html.WindowProxy;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Taras Maslov
 * 10/31/2018
 */
@AllArgsConstructor
public class WindowProxyImpl implements WindowProxy {

    @Delegate(types = Window.class)
    private Window target;
}
