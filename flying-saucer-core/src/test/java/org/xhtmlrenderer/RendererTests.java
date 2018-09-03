package org.xhtmlrenderer;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.xhtmlrenderer.tools.ReferenceComparison;

import java.io.File;
import java.io.IOException;

/**
 * @author Taras Maslov
 * 9/3/2018
 */
public class RendererTests {
    
    @Test
    public void regression() throws IOException {
        ReferenceComparison rc = new ReferenceComparison(1024);
        File source = new File("tests/regress/xhtml");
        File reference = new File("tests/regress/snapshots/1.0");
        File failed = new File("target/regression-failed");
        val numFailed = rc.compareDirectory(source, reference, failed);

        Assert.assertEquals(0, numFailed);
    }
}
