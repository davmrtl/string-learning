# string-learning

**string-learning** is a lightweight library for Java that helps learning from a set of strings.

It provides a simple way to know if a string matches the others:

    StringLearner stringLearner = new SimpleStringLearner();
    
    stringLearner.learn("ABC 123");
    stringLearner.learn("DEF 456");
    
    stringLearner.match("GHI 789"); // Matching!
    stringLearner.match("Hello world!"); // Not matching :(