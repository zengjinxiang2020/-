/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co
 * 注意：
 * 本软件为www.yixiang.co开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package co.yixiang.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void testToFile() {
        long retval = FileUtil.toFile(new MockMultipartFile("foo", (byte[]) null)).getTotalSpace();
        assertEquals(500695072768L, retval);
    }

    @Test
    public void testGetExtensionName() {
        Assert.assertEquals("foo", FileUtil.getExtensionName("foo"));
        Assert.assertEquals("exe", FileUtil.getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        Assert.assertEquals("foo", FileUtil.getFileNameNoEx("foo"));
        Assert.assertEquals("bar", FileUtil.getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        Assert.assertEquals("1000B   ", FileUtil.getSize(1000));
        Assert.assertEquals("1.00KB   ", FileUtil.getSize(1024));
        Assert.assertEquals("1.00MB   ", FileUtil.getSize(1048576));
        Assert.assertEquals("1.00GB   ", FileUtil.getSize(1073741824));
    }
}
