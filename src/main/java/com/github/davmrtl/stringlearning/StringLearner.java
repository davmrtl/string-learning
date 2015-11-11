package com.github.davmrtl.stringlearning;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Davmrtl on 2015-11-09.
 */
public interface StringLearner {
    void learn(String str);
    boolean match(String testStr);
    int getLearnCount();
    LinkedList<List<Integer>> getLearnedCharacters();
}
