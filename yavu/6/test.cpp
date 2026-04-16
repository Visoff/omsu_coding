#include <gtest/gtest.h>
#include <sstream>
#include "dictionary/dictionary.hpp"

#include "dictionary/dictionary.cpp"

TEST(DictionaryTest, EmptyDictionary) {
    Dictionary dict;
    EXPECT_EQ(dict.size(), 0);
    EXPECT_EQ(dict.count("hello"), 0);
}

TEST(DictionaryTest, AddSingleWord) {
    Dictionary dict;
    dict.add("hello");
    EXPECT_EQ(dict.size(), 1);
    EXPECT_EQ(dict.count("hello"), 1);
    EXPECT_EQ(dict.count("world"), 0);
}

TEST(DictionaryTest, AddDuplicateWord) {
    Dictionary dict;
    dict.add("hello");
    dict.add("hello");
    EXPECT_EQ(dict.size(), 2);          // total occurrences
    EXPECT_EQ(dict.count("hello"), 2);
}

TEST(DictionaryTest, AddMultipleWords) {
    Dictionary dict;
    dict.add("banana");
    dict.add("apple");
    dict.add("cherry");
    dict.add("apple");
    EXPECT_EQ(dict.size(), 4);
    EXPECT_EQ(dict.count("apple"), 2);
    EXPECT_EQ(dict.count("banana"), 1);
    EXPECT_EQ(dict.count("cherry"), 1);
    EXPECT_EQ(dict.count("date"), 0);
}

TEST(DictionaryTest, RemoveWordWithCountOne) {
    Dictionary dict;
    dict.add("hello");
    dict.add("world");
    dict.remove("hello");
    EXPECT_EQ(dict.size(), 1);
    EXPECT_EQ(dict.count("hello"), 0);
    EXPECT_EQ(dict.count("world"), 1);
}

TEST(DictionaryTest, RemoveWordWithHigherCount) {
    Dictionary dict;
    dict.add("hello");
    dict.add("hello");
    dict.add("hello");
    dict.remove("hello");
    EXPECT_EQ(dict.size(), 2);
    EXPECT_EQ(dict.count("hello"), 2);
    dict.remove("hello");
    EXPECT_EQ(dict.size(), 1);
    EXPECT_EQ(dict.count("hello"), 1);
    dict.remove("hello");
    EXPECT_EQ(dict.size(), 0);
    EXPECT_EQ(dict.count("hello"), 0);
}

TEST(DictionaryTest, RemoveNonExistentWord) {
    Dictionary dict;
    dict.add("test");
    dict.remove("missing");
    EXPECT_EQ(dict.size(), 1);
    EXPECT_EQ(dict.count("test"), 1);
}

TEST(DictionaryTest, RemoveFromEmpty) {
    Dictionary dict;
    dict.remove("anything");
    EXPECT_EQ(dict.size(), 0);
}

TEST(DictionaryTest, RemoveNodeWithOneChild) {
    Dictionary dict;
    dict.add("b");
    dict.add("a");
    dict.remove("b");
    EXPECT_EQ(dict.size(), 1);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("b"), 0);
}

TEST(DictionaryTest, RemoveNodeWithTwoChildren) {
    Dictionary dict;
    dict.add("b");
    dict.add("a");
    dict.add("c");
    dict.remove("b");
    EXPECT_EQ(dict.size(), 2);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("c"), 1);
    EXPECT_EQ(dict.count("b"), 0);
}

TEST(DictionaryTest, OutputOperator) {
    Dictionary dict;
    dict.add("banana");
    dict.add("apple");
    dict.add("apple");
    dict.add("cherry");
    std::ostringstream oss;
    oss << dict;
    // Format: "<total> word1 count1 word2 count2 ...\n"
    // Words in alphabetical order
    std::string expected = "4 apple 2 banana 1 cherry 1 \n";
    EXPECT_EQ(oss.str(), expected);
}

TEST(DictionaryTest, CopyConstructor) {
    Dictionary dict1;
    dict1.add("hello");
    dict1.add("world");
    dict1.add("hello");
    Dictionary dict2(dict1);
    EXPECT_EQ(dict2.size(), 3);
    EXPECT_EQ(dict2.count("hello"), 2);
    EXPECT_EQ(dict2.count("world"), 1);
    // Modify original, copy should be unaffected
    dict1.add("extra");
    EXPECT_EQ(dict1.size(), 4);
    EXPECT_EQ(dict2.size(), 3);
}

TEST(DictionaryTest, AssignmentOperator) {
    Dictionary dict1;
    dict1.add("foo");
    dict1.add("bar");
    dict1.add("foo");
    Dictionary dict2;
    dict2 = dict1;
    EXPECT_EQ(dict2.size(), 3);
    EXPECT_EQ(dict2.count("foo"), 2);
    EXPECT_EQ(dict2.count("bar"), 1);
    dict1.add("baz");
    EXPECT_EQ(dict1.size(), 4);
    EXPECT_EQ(dict2.size(), 3);
}

TEST(DictionaryTest, MoveConstructor) {
    Dictionary dict1;
    dict1.add("move");
    dict1.add("test");
    size_t oldSize = dict1.size();
    Dictionary dict2(std::move(dict1));
    EXPECT_EQ(dict2.size(), oldSize);
    EXPECT_EQ(dict2.count("move"), 1);
    EXPECT_EQ(dict2.count("test"), 1);
    EXPECT_EQ(dict1.size(), 0);
    EXPECT_EQ(dict1.count("move"), 0);
}

TEST(DictionaryTest, MoveAssignment) {
    Dictionary dict1;
    dict1.add("hello");
    dict1.add("world");
    Dictionary dict2;
    dict2 = std::move(dict1);
    EXPECT_EQ(dict2.size(), 2);
    EXPECT_EQ(dict2.count("hello"), 1);
    EXPECT_EQ(dict2.count("world"), 1);
    EXPECT_EQ(dict1.size(), 0);
}

TEST(DictionaryTest, AVLRotationsLeft) {
    // Insert ascending order to trigger left rotations
    Dictionary dict;
    dict.add("a");
    dict.add("b");
    dict.add("c");
    dict.add("d");
    dict.add("e");
    // After rotations tree should be balanced, but we just check correctness
    EXPECT_EQ(dict.size(), 5);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("b"), 1);
    EXPECT_EQ(dict.count("c"), 1);
    EXPECT_EQ(dict.count("d"), 1);
    EXPECT_EQ(dict.count("e"), 1);
}

TEST(DictionaryTest, AVLRotationsRight) {
    // Insert descending order to trigger right rotations
    Dictionary dict;
    dict.add("e");
    dict.add("d");
    dict.add("c");
    dict.add("b");
    dict.add("a");
    EXPECT_EQ(dict.size(), 5);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("b"), 1);
    EXPECT_EQ(dict.count("c"), 1);
    EXPECT_EQ(dict.count("d"), 1);
    EXPECT_EQ(dict.count("e"), 1);
}

TEST(DictionaryTest, AVLRotationsLeftRight) {
    Dictionary dict;
    dict.add("c");
    dict.add("a");
    dict.add("b");  // triggers left-right rotation
    EXPECT_EQ(dict.size(), 3);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("b"), 1);
    EXPECT_EQ(dict.count("c"), 1);
}

TEST(DictionaryTest, AVLRotationsRightLeft) {
    Dictionary dict;
    dict.add("a");
    dict.add("c");
    dict.add("b");  // triggers right-left rotation
    EXPECT_EQ(dict.size(), 3);
    EXPECT_EQ(dict.count("a"), 1);
    EXPECT_EQ(dict.count("b"), 1);
    EXPECT_EQ(dict.count("c"), 1);
}

TEST(DictionaryTest, ComplexAddRemoveSequence) {
    Dictionary dict;
    for (char c = 'a'; c <= 'z'; ++c) {
        dict.add(std::string(1, c));
    }
    EXPECT_EQ(dict.size(), 26);
    for (char c = 'a'; c <= 'z'; ++c) {
        EXPECT_EQ(dict.count(std::string(1, c)), 1);
    }
    // Remove every other letter
    for (char c = 'a'; c <= 'z'; c += 2) {
        dict.remove(std::string(1, c));
    }
    EXPECT_EQ(dict.size(), 13);
    for (char c = 'a'; c <= 'z'; ++c) {
        if ((c - 'a') % 2 == 0)
            EXPECT_EQ(dict.count(std::string(1, c)), 0);
        else
            EXPECT_EQ(dict.count(std::string(1, c)), 1);
    }
}

TEST(DictionaryTest, LargeNumberOfDuplicates) {
    Dictionary dict;
    const std::string word = "duplicate";
    const size_t N = 1000;
    for (size_t i = 0; i < N; ++i) {
        dict.add(word);
    }
    EXPECT_EQ(dict.size(), N);
    EXPECT_EQ(dict.count(word), N);
    // Remove half
    for (size_t i = 0; i < N / 2; ++i) {
        dict.remove(word);
    }
    EXPECT_EQ(dict.size(), N - N / 2);
    EXPECT_EQ(dict.count(word), N - N / 2);
}

int main() {
    testing::InitGoogleTest();
    return RUN_ALL_TESTS();
}
