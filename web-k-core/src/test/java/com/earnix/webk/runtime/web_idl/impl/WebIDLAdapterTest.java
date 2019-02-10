package com.earnix.webk.runtime.web_idl.impl;

import com.earnix.webk.runtime.whatwg_dom.impl.Jsoup;
import com.earnix.webk.runtime.ScriptContext;
import com.earnix.webk.runtime.web_idl.Iterable;
import com.earnix.webk.simple.XHTMLPanel;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class WebIDLAdapterTest {
    
    public class TestIterableImpl implements Iterable<String> {
        
        @Override
        public String item(int index) {
            return "A";
        }

        @Override
        public int length() {
            return 1;
        }
    }
    
    
    @Test
    public void testIterable(){
        val doc = Jsoup.parse("<html></html>");
        val panel = new XHTMLPanel();
        panel.setDocument(doc);
        ScriptContext sc = panel.getScriptContext();
        
        val impl = new TestIterableImpl();
        WebIDLAdapter adapter = WebIDLAdapter.obtain(sc, impl);
        sc.getEngine().put("testObject", adapter);
        
        Object returned = sc.eval("testObject[0]");
        Object returnedLength = sc.eval("testObject.length");

        Assert.assertEquals("A", returned);
        Assert.assertEquals(1, returnedLength);
    }
}