package com.github.davmrtl.stringnormalization.tests;

import com.github.davmrtl.stringnormalization.SimpleStringLearner;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Davmrtl on 2015-11-09.
 */
public class TestSimpleStringLearner {

    @Test
    public void testSimpleLearningSequence() {
        SimpleStringLearner stringLearner = new SimpleStringLearner();

        // Learning steps
        stringLearner.learn("ABC 123");

        Assert.assertEquals(stringLearner.getLearnedCharacters().size(), 7);
        Assert.assertEquals(stringLearner.getLearnCount(), 1);

        stringLearner.learn("CCC 1234");

        Assert.assertEquals(stringLearner.getLearnedCharacters().size(), 8);
        Assert.assertEquals(stringLearner.getLearnCount(), 2);

        // Matching cases
        Assert.assertEquals(stringLearner.match("ADC 611"), true);
        Assert.assertEquals(stringLearner.match("QCA 1235"), true);

        // Non-matching cases
        Assert.assertEquals(stringLearner.match(""), false);
        Assert.assertEquals(stringLearner.match("ACS#516"), false);
        Assert.assertEquals(stringLearner.match("ACS #16"), false);
        Assert.assertEquals(stringLearner.match("ACs 516"), false);
        Assert.assertEquals(stringLearner.match("ACS 51"), false);
        Assert.assertEquals(stringLearner.match("ASKLM"), false);
    }
}
