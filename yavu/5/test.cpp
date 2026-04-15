#include "bintree/bintree.cpp"
#include "bintree/bintree.hpp"
#include <gtest/gtest.h>

TEST(IntBinTreeTest, InsertAndFind) {
  IntBinTree tree;
  tree.insert(10, "");
  EXPECT_EQ(tree.find(10), "");

  tree.insert(20, "0");
  EXPECT_EQ(tree.find(20), "0");

  tree.insert(30, "1");
  EXPECT_EQ(tree.find(30), "1");

  tree.insert(99, "0");
  EXPECT_EQ(tree.find(99), "0");
  EXPECT_EQ(tree.find(20), "");
}

TEST(IntBinTreeTest, InsertInvalidPath) {
  IntBinTree tree;

  EXPECT_THROW(tree.insert(10, "0"), std::runtime_error);

  tree.insert(10, "");

  EXPECT_THROW(tree.insert(20, "00"), std::runtime_error);
}

TEST(IntBinTreeTest, FindMultipleOccurrences) {
  IntBinTree tree;
  tree.insert(42, "");
  tree.insert(42, "0");
  tree.insert(42, "1");

  EXPECT_EQ(tree.find(42), "");
}

TEST(IntBinTreeTest, RemoveLeafs) {
  IntBinTree tree;

  tree.insert(1, "");
  tree.insert(2, "0");
  tree.insert(3, "1");
  tree.insert(4, "00");
  tree.insert(5, "01");

  tree.remove_leafs();
  EXPECT_EQ(tree.find(4), "");
  EXPECT_EQ(tree.find(5), "");
  EXPECT_EQ(tree.find(3), "");
  EXPECT_EQ(tree.find(2), "0");
  EXPECT_EQ(tree.find(1), "");

  tree.remove_leafs();
  EXPECT_EQ(tree.find(2), "");
  EXPECT_EQ(tree.find(1), "");
}

TEST(IntBinTreeTest, Average) {
  IntBinTree tree;
  tree.insert(10, "");
  tree.insert(20, "0");
  tree.insert(30, "1");

  EXPECT_DOUBLE_EQ(tree.get_average(), 20.0);

  tree.insert(40, "00");

  EXPECT_DOUBLE_EQ(tree.get_average(), 25.0);
}

TEST(IntBinTreeTest, AllPositive) {
  IntBinTree tree;
  EXPECT_TRUE(tree.are_all_numbers_positive());

  tree.insert(5, "");
  EXPECT_TRUE(tree.are_all_numbers_positive());

  tree.insert(-3, "0");
  EXPECT_FALSE(tree.are_all_numbers_positive());
}

TEST(IntBinTreeTest, AllEven) {
  IntBinTree tree;
  EXPECT_TRUE(tree.are_all_numbers_even());

  tree.insert(2, "");
  EXPECT_TRUE(tree.are_all_numbers_even());

  tree.insert(4, "0");
  EXPECT_TRUE(tree.are_all_numbers_even());

  tree.insert(3, "1");
  EXPECT_FALSE(tree.are_all_numbers_even());
}

TEST(IntBinTreeTest, IsBST) {
  IntBinTree tree;
  EXPECT_TRUE(tree.isBST());

  tree.insert(50, "");
  tree.insert(30, "0");
  tree.insert(80, "1");
  tree.insert(20, "00");
  tree.insert(40, "01");
  tree.insert(70, "10");
  tree.insert(90, "11");
  EXPECT_TRUE(tree.isBST());

  IntBinTree tree2;
  tree2.insert(50, "");
  tree2.insert(60, "0");
  EXPECT_FALSE(tree2.isBST());
}

TEST(IntBinTreeTest, CopyConstructor) {
  IntBinTree tree;
  tree.insert(10, "");
  tree.insert(20, "0");

  IntBinTree tree2(tree);
  EXPECT_EQ(tree2.find(10), "");
  EXPECT_EQ(tree2.find(20), "0");

  tree.insert(30, "1");
  EXPECT_EQ(tree2.find(30), "");
}

TEST(IntBinTreeTest, AssignmentOperator) {
  IntBinTree tree;
  tree.insert(10, "");
  tree.insert(20, "0");

  IntBinTree tree2;
  tree2 = tree;
  EXPECT_EQ(tree2.find(10), "");
  EXPECT_EQ(tree2.find(20), "0");

  tree.insert(30, "1");
  EXPECT_EQ(tree2.find(30), "");
}

TEST(IntBinTreeTest, RemoveLeafsStaleStatistics) {
  IntBinTree tree;
  tree.insert(10, "");
  tree.insert(20, "0");
  tree.insert(30, "1");

  EXPECT_DOUBLE_EQ(tree.get_average(), 20.0);

  tree.remove_leafs();

  EXPECT_DOUBLE_EQ(tree.get_average(), 10.0);

  EXPECT_EQ(tree.find(20), "");
  EXPECT_EQ(tree.find(30), "");
  EXPECT_EQ(tree.find(10), "");
}

int main(int argc, char **argv) {
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}
