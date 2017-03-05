package com.edwin.redditclient;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FUCK on 20/02/2017.
 */
public class MyUnitTests {
    @Test
    public void setHeaderTitle() throws Exception {

        Subreddit dummySubreddit = new Subreddit();
        assertEquals("", dummySubreddit.getHeaderTitle());

        String validTitle = "This title is not \"null\"";
        dummySubreddit.setHeaderTitle(validTitle);
        dummySubreddit.setHeaderTitle("null");
        assertEquals(validTitle, dummySubreddit.getHeaderTitle());

        String rateTitle = "%&%/&%/=ñññüu(--->|⇢|⇠)";
        dummySubreddit.setHeaderTitle(rateTitle);
        assertEquals(rateTitle, dummySubreddit.getHeaderTitle());
    }

    @Test
    public void setBannerUrl() throws Exception {
        Subreddit dummySubreddit = new Subreddit();
        assertEquals("", dummySubreddit.getBannerUrl());

        String validUrl = "http://valid.com/good.png";
        dummySubreddit.setBannerUrl(validUrl);
        dummySubreddit.setBannerUrl("null");
        assertEquals(validUrl, dummySubreddit.getBannerUrl());
    }

}