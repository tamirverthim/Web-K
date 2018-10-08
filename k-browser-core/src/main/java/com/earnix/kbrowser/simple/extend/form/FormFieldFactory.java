package com.earnix.kbrowser.simple.extend.form;

import com.earnix.kbrowser.layout.LayoutContext;
import com.earnix.kbrowser.render.BlockBox;
import com.earnix.kbrowser.simple.extend.XhtmlForm;

/**
 * @author Taras Maslov
 * 9/24/2018
 */
public interface FormFieldFactory {

    FormField create(XhtmlForm form, LayoutContext context, BlockBox box);
}
