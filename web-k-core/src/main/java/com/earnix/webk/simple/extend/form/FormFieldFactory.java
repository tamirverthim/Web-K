package com.earnix.webk.simple.extend.form;

import com.earnix.webk.layout.LayoutContext;
import com.earnix.webk.render.BlockBox;
import com.earnix.webk.simple.extend.XhtmlForm;

/**
 * @author Taras Maslov
 * 9/24/2018
 */
public interface FormFieldFactory {

    FormField create(XhtmlForm form, LayoutContext context, BlockBox box);
}
