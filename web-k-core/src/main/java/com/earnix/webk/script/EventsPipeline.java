package com.earnix.webk.script;

import com.earnix.webk.render.Box;
import com.earnix.webk.swing.BasicPanel;
import com.earnix.webk.swing.FSMouseListener;
import lombok.experimental.FieldDefaults;

import java.awt.event.MouseEvent;

/**
 * @author Taras Maslov
 * 10/26/2018
 */
public class EventsPipeline  implements FSMouseListener  {

    ScriptContext scriptContext;
    
    @Override
    public void onMouseOver(BasicPanel panel, Box box) {
        
    }

    @Override
    public void onMouseOut(BasicPanel panel, Box box) {

    }

    @Override
    public void onMouseUp(BasicPanel panel, Box box) {

    }

    @Override
    public void onMousePressed(BasicPanel panel, MouseEvent e) {

    }

    @Override
    public void onMouseDragged(BasicPanel panel, MouseEvent e) {

    }

    @Override
    public void reset() {

    }
}
