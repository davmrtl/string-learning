package com.github.davmrtl.stringlearning;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Davmrtl on 2015-11-09.
 */
public class SimpleStringLearner implements StringLearner {
    LinkedList<List<Integer>> learnedCharacters = new LinkedList<List<Integer>>();
    int learnCount = 0;

    public void learn(String str) {
        int strLen = str.length();
        int learnedCharactersLen = learnedCharacters.size() - 1;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int currentType = Character.getType(c);
            List<Integer> currentTypes;

            if ((i == 0 && learnedCharactersLen == 0) || i > learnedCharactersLen) {
                currentTypes = new ArrayList<Integer>();

                currentTypes.add(currentType);

                if (learnCount > 0) {
                    currentTypes.add(null);
                }

                learnedCharacters.add(currentTypes);
            } else {
                currentTypes = learnedCharacters.get(i);

                if (!currentTypes.contains(currentType)) {
                    currentTypes.add(currentType);
                }
            }
        }

        for (int i = strLen; i < learnedCharactersLen && learnCount > 0; i++) {
            List<Integer> currentTypes = learnedCharacters.get(i);

            if (!currentTypes.contains(null)) {
                currentTypes.add(null);
            }
        }

        learnCount++;
    }

    public boolean match(String testStr) {
        return matchWithFixedTolerance(testStr, 0);
    }

    public boolean matchWithFixedTolerance(String testStr, int tolerance) {
        int errorCount = 0;

        if (testStr.length() > learnedCharacters.size()) {
            errorCount += testStr.length() - learnedCharacters.size();
        }

        for (int i = 0; i < testStr.length() && i < learnedCharacters.size() && errorCount <= tolerance; i++) {
            char c = testStr.charAt(i);

            errorCount += learnedCharacters.get(i).contains(Character.getType(c)) ? 0 : 1;
        }

        for (int i = testStr.length(); i < learnedCharacters.size() && errorCount <= tolerance; i++) {
            errorCount += learnedCharacters.get(i).contains(null) ? 0 : 1;
        }

        return errorCount <= tolerance;
    }

    public boolean matchWithVariableTolerance(String testStr, double toleranceInPercent) {
        int tolerance = (int) Math.floor((double) learnedCharacters.size() * toleranceInPercent);

        return matchWithFixedTolerance(testStr, tolerance);
    }

    public int getLearnCount() {
        return learnCount;
    }

    public LinkedList<List<Integer>> getLearnedCharacters() {
        return learnedCharacters;
    }
}
