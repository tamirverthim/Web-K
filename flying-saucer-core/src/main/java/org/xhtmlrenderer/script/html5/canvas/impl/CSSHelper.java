//package org.xhtmlrenderer.js.html5.canvas.impl;
//
//import java.awt.*;
//
///**
// * @author Taras Maslov
// * 8/15/2018
// */
//public class CSSHelper {
//
//    protected static final String COLOR_RGB_NAME = "rgb";
//    protected static final String COLOR_RGBA_NAME = "rgba";
//    protected static final String COLOR_HSL_NAME = "hsl";
//    protected static final String COLOR_HSLA_NAME = "hsla";
//    protected static final int COLOR_PARAMS_COUNT = 3;
//    protected static final int MAX_VALUE = 255;
//    protected static final int MIN_VALUE = 0;
//    protected static final int PERCENT_CONVERSION = 100;
//    protected static final int MAX_HUE = 360;
//    
//    public Color parseCSSColor(String cssColor){
//        if ((COLOR_RGB_NAME.equals(func.getFunctionName()) && args.size() == COLOR_PARAMS_COUNT)
//                || COLOR_RGBA_NAME.equals(func.getFunctionName()) && args.size() == COLOR_PARAMS_COUNT + 1) {
//
//            boolean percVals = false;
//            boolean intVals = false;
//            int[] rgb = new int[COLOR_PARAMS_COUNT];
//            for(int i = 0; i < COLOR_PARAMS_COUNT; i++) {
//                Term<?> term = args.get(i);
//                // term is number and numeric
//                if(term instanceof TermInteger ) {
//                    rgb[i] = ((TermInteger)term).getIntValue();
//                    intVals = true;
//                }
//                // term is percent
//                else if(term instanceof TermPercent) {
//                    final int value = ((TermPercent) term).getValue().intValue();
//                    rgb[i] = (value * MAX_VALUE) / PERCENT_CONVERSION;
//                    percVals = true;
//                }
//                // not valid term
//                else {
//                    return null;
//                }
//            }
//
//            if (percVals && intVals) //do not allow both percentages and int values combined
//                return null;
//
//            // limits
//            for(int i = 0; i < rgb.length; i++) {
//                if(rgb[i] < MIN_VALUE) rgb[i] = MIN_VALUE;
//                if(rgb[i] > MAX_VALUE) rgb[i] = MAX_VALUE;
//            }
//
//            //alpha
//            int a = MAX_VALUE;
//            if (args.size() > COLOR_PARAMS_COUNT)
//            {
//                Term<?> term = args.get(COLOR_PARAMS_COUNT);
//                if (term instanceof TermNumber || term instanceof TermInteger) {
//                    float alpha = getFloatValue(term);
//                    a = Math.round(alpha * MAX_VALUE);
//                    if (a < MIN_VALUE) a = MIN_VALUE;
//                    if (a > MAX_VALUE) a = MAX_VALUE;
//                }
//                else
//                    return null; //unacceptable alpha value
//            }
//
//            return new TermColorImpl(rgb[0], rgb[1], rgb[2], a);
//        }
//        else if ((COLOR_HSL_NAME.equals(func.getFunctionName()) && args.size() == COLOR_PARAMS_COUNT)
//                || COLOR_HSLA_NAME.equals(func.getFunctionName()) && args.size() == COLOR_PARAMS_COUNT + 1) {
//
//            float h, s, l;
//            Term<?> hterm = args.get(0);
//            if (hterm instanceof TermNumber || hterm instanceof TermInteger) {
//                h = getFloatValue(hterm);
//                while (h >= MAX_HUE) h -= MAX_HUE;
//                while (h < 0) h += MAX_HUE;
//                h = h / MAX_HUE; //normalize to 0..1
//            }
//            else
//                return null;
//
//            Term<?> sterm = args.get(1);
//            if (sterm instanceof TermPercent) {
//                int is = ((TermPercent) sterm).getValue().intValue();
//                if (is > 100) is = 100;
//                else if (is < 0) is = 0;
//                s = is / 100.0f;
//            }
//            else
//                return null;
//
//            Term<?> lterm = args.get(2);
//            if (lterm instanceof TermPercent) {
//                int il = ((TermPercent) lterm).getValue().intValue();
//                if (il > 100) il = 100;
//                else if (il < 0) il = 0;
//                l = il / 100.0f;
//            }
//            else
//                return null;
//
//            int[] rgb = hslToRgb(h, s, l);
//
//            // alpha
//            int a = MAX_VALUE;
//            if (args.size() > COLOR_PARAMS_COUNT)
//            {
//                Term<?> term = args.get(3);
//                if (term instanceof TermNumber || term instanceof TermInteger) {
//                    float alpha = getFloatValue(term);
//                    a = Math.round(alpha * MAX_VALUE);
//                    if (a < MIN_VALUE) a = MIN_VALUE;
//                    if (a > MAX_VALUE) a = MAX_VALUE;
//                }
//                else
//                    return null; // unacceptable alpha value
//            }
//
//            return new TermColorImpl(rgb[0], rgb[1], rgb[2], a);
//        }
//        // invalid function
//        else
//            return null;
//    }
//        else
//                return null; //couldn't parse arguments
//    }
//    
//}
