package com.github.davmrtl.stringnormalization.tests;

import com.github.davmrtl.stringnormalization.SimpleStringLearner;
import com.github.davmrtl.stringnormalization.StringLearner;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Davmrtl on 2015-11-09.
 */
public class SimpleStringLearnerTest {

    @Test
    public void testGetLearnedCharacters() {
        StringLearner stringLearner = new SimpleStringLearner();

        Assert.assertEquals(stringLearner.getLearnedCharacters().size(), 0);

        stringLearner.learn("abc");
        Assert.assertEquals(stringLearner.getLearnedCharacters().size(), 3);

        stringLearner.learn("defg");
        Assert.assertEquals(stringLearner.getLearnedCharacters().size(), 4);
    }

    @Test
    public void testGetLearnCount() {
        StringLearner stringLearner = new SimpleStringLearner();

        Assert.assertEquals(stringLearner.getLearnCount(), 0);

        stringLearner.learn("abc");
        Assert.assertEquals(stringLearner.getLearnCount(), 1);

        stringLearner.learn("defg");
        Assert.assertEquals(stringLearner.getLearnCount(), 2);
    }

    @Test
    public void sequenceOfSuccessfulMatch() {
        StringLearner stringLearner = new SimpleStringLearner();

        // == Learning steps ==
        stringLearner.learn("ABC 123");
        stringLearner.learn("CCC 1234");

        // == Matching cases ==

        // Matching because of first learn
        Assert.assertEquals(stringLearner.match("ADC 611"), true);

        // Matching because of second learn
        Assert.assertEquals(stringLearner.match("QCA 1235"), true);
    }

    @Test
    public void sequenceOfFailingMatch() {
        StringLearner stringLearner = new SimpleStringLearner();

        // == Learning steps ==
        stringLearner.learn("ABC 123");
        stringLearner.learn("CCC 1234");

        // == Non-matching cases ==

        // Non-matching because of empty
        Assert.assertEquals(stringLearner.match(""), false);

        // Non-matching because of too long
        Assert.assertEquals(stringLearner.match("AAA 13241"), false);

        // Non-matching because of too short
        Assert.assertEquals(stringLearner.match("AEC"), false);

        // Non-matching because of the wrong char
        Assert.assertEquals(stringLearner.match("A&W 123"), false);
    }
}
