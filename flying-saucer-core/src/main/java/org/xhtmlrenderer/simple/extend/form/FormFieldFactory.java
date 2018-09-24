package org.xhtmlrenderer.simple.extend.form;

import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.XhtmlForm;

/**
 * @author Taras Maslov
 * 9/24/2018
 */
public interface FormFieldFactory {
    
    FormField create(XhtmlForm form, LayoutContext context, BlockBox box);
}
