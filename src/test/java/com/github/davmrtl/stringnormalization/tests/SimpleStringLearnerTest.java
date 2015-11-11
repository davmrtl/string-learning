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
    public void testMatch() {
        StringLearner stringLearner = new SimpleStringLearner();

        // == Learning steps ==
        stringLearner.learn("ABC 123");
        stringLearner.learn("CCC 1234");

        // == Matching cases ==

        // Should match the first learn
        Assert.assertEquals(stringLearner.match("ADC 611"), true);

        // Should match the second learn
        Assert.assertEquals(stringLearner.match("QCA 1235"), true);

        // Should match the foreign characters
        Assert.assertEquals(stringLearner.match("ÉÂE 1239"), true);

        // == Non-matching cases ==

        // Should not match because of the empty string
        Assert.assertEquals(stringLearner.match(""), false);

        // Should not match because it's too long
        Assert.assertEquals(stringLearner.match("AAA 13241"), false);

        // Should not match because it's too short
        Assert.assertEquals(stringLearner.match("AEC"), false);

        // Should not match because it's too short and has wrong characters
        Assert.assertEquals(stringLearner.match("AEC#&"), false);

        // Should not match because of the wrong char
        Assert.assertEquals(stringLearner.match("A&W 123"), false);
    }

    @Test
    public void testMatchWithFixedTolerance() {
        SimpleStringLearner stringLearner = new SimpleStringLearner();

        // == Learning steps ==
        stringLearner.learn("ABC 123");
        stringLearner.learn("CCC 1234");

        // == Matching cases ==

        // Should match because only two chars are lowercase (instead of uppercase)
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("Abc 123", 2), true);

        // Should match because it's too long by three chars
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("ABC 516$$#", 3), true);

        // Should match because it's too short by one char
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("TED 12", 1), true);

        // == Non-matching cases ==

        // Should not match because of the empty string
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("", 2), false);

        // Should not match because it's way too long
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("ABC 516$$#3", 3), false);

        // Should not match because it's way too short
        Assert.assertEquals(stringLearner.matchWithFixedTolerance("TED 2", 1), false);
    }

    @Test
    public void testMatchWithVariableTolerance() {
        SimpleStringLearner stringLearner = new SimpleStringLearner();

        // == Learning steps ==
        stringLearner.learn("ABC 123");
        stringLearner.learn("CCC 1234");

        // == Matching cases ==

        // Should match because 40% of 8 learned characters is 3 errors
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("Abc#123", 0.4), true);

        // Should match because 30% of 8 learned characters is 2 errors
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("ABC 516$$", 0.3), true);

        // Should match because 20% of 8 learned characters is 1 error
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("TE/ 122", 0.2), true);

        // == Non-matching cases ==

        // Should not match because of the empty string
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("", 0.3), false);

        // Should not match because it's way too long
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("ABC 516$$#3", 0.4), false);

        // Should not match because it's way too short
        Assert.assertEquals(stringLearner.matchWithVariableTolerance("TED 2", 0.2), false);
    }
}
