package io.jenkins.plugins.imfeelinglucky;

import org.junit.Assert;
import org.junit.Test;

public class ImFeelingLuckyRootActionTest {

    @Test
    public void constructorDoesNotThrow() {
        new ImFeelingLuckyRootAction();
    }

    @Test
    public void getIconFileNameIsNotEmpty() {
        String iconFileName = new ImFeelingLuckyRootAction().getIconFileName();
        Assert.assertNotNull(iconFileName);
        Assert.assertFalse(iconFileName.isEmpty());
    }

    @Test
    public void getDisplayNameIsNotEmpty() {
        String displayName = new ImFeelingLuckyRootAction().getDisplayName();
        Assert.assertNotNull(displayName);
        Assert.assertFalse(displayName.isEmpty());
    }

    @Test
    public void getUrlNameIsNotEmpty() {
        String urlName = new ImFeelingLuckyRootAction().getUrlName();
        Assert.assertNotNull(urlName);
        Assert.assertFalse(urlName.isEmpty());
    }
}
