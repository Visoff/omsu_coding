#include "./RingDoublyLinkedList/RingDoublyLinkedList.hpp"
#include <gtest/gtest.h>

#include "./RingDoublyLinkedList/RingDoublyLinkedList.cpp"

class RingDoublyLinkedListTest : public ::testing::Test {
protected:
  List<int> *list;

  void SetUp() override { list = new RingDoublyLinkedList<int>(); }
  void TearDown() override { delete list; }
};

TEST_F(RingDoublyLinkedListTest, NewListIsEmpty) {
  EXPECT_TRUE(list->is_empty());
  EXPECT_EQ(list->size(), 0);
}

TEST_F(RingDoublyLinkedListTest, ClearEmptiesList) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  list->insert_in_place(it, 20);
  list->insert_in_place(it, 30);
  EXPECT_FALSE(list->is_empty());
  EXPECT_EQ(list->size(), 3);

  list->clear();
  EXPECT_TRUE(list->is_empty());
  EXPECT_EQ(list->size(), 0);
}

TEST_F(RingDoublyLinkedListTest, InsertAtBeginning) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  list->insert_in_place(it, 20);
  list->insert_in_place(it, 30);

  auto iter = list->iterate();
  EXPECT_FALSE(iter->done());
  EXPECT_EQ(iter->current(), 30);
  iter->next();
  EXPECT_EQ(iter->current(), 20);
  iter->next();
  EXPECT_EQ(iter->current(), 10);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, InsertAfterIterator) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);

  it = list->iterate();
  EXPECT_EQ(it->current(), 10);

  list->insert_in_place(it, 20);

  it->next();
  list->insert_in_place(it, 30);

  auto iter = list->iterate();
  EXPECT_EQ(iter->current(), 10);
  iter->next();
  EXPECT_EQ(iter->current(), 20);
  iter->next();
  EXPECT_EQ(iter->current(), 30);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, InsertAtEnd) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  it = list->iterate();
  list->insert_in_place(it, 20);
  it->next();
  list->insert_in_place(it, 30);

  auto iter = list->iterate();
  iter->next();
  iter->next();
  list->insert_in_place(iter, 40);

  iter = list->iterate();
  EXPECT_EQ(iter->current(), 10);
  iter->next();
  EXPECT_EQ(iter->current(), 20);
  iter->next();
  EXPECT_EQ(iter->current(), 30);
  iter->next();
  EXPECT_EQ(iter->current(), 40);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, FindExistingElement) {
  auto it = list->iterate();
  list->insert_in_place(it, 5);
  it = list->iterate();
  list->insert_in_place(it, 10);
  it->next();
  list->insert_in_place(it, 15);

  auto found = list->find(10);
  EXPECT_FALSE(found->done());
  EXPECT_EQ(found->current(), 10);

  found->next();
  EXPECT_EQ(found->current(), 15);
}

TEST_F(RingDoublyLinkedListTest, FindNonExistingElement) {
  auto it = list->iterate();
  list->insert_in_place(it, 1);
  list->insert_in_place(it, 2);
  auto found = list->find(42);
  EXPECT_TRUE(found->done());
}

TEST_F(RingDoublyLinkedListTest, DeleteFirstElement) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  it = list->iterate();
  list->insert_in_place(it, 20);
  it->next();
  list->insert_in_place(it, 30);

  auto first = list->iterate();
  int deleted = list->delete_in_place(first);
  EXPECT_EQ(deleted, 10);
  EXPECT_EQ(list->size(), 2);

  auto iter = list->iterate();
  EXPECT_EQ(iter->current(), 20);
  iter->next();
  EXPECT_EQ(iter->current(), 30);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, DeleteMiddleElement) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  it = list->iterate();
  list->insert_in_place(it, 20);
  it->next();
  list->insert_in_place(it, 30);

  auto middle = list->iterate();
  middle->next();
  int deleted = list->delete_in_place(middle);
  EXPECT_EQ(deleted, 20);
  EXPECT_EQ(list->size(), 2);

  auto iter = list->iterate();
  EXPECT_EQ(iter->current(), 10);
  iter->next();
  EXPECT_EQ(iter->current(), 30);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, DeleteLastElement) {
  auto it = list->iterate();
  list->insert_in_place(it, 10);
  it = list->iterate();
  list->insert_in_place(it, 20);
  it->next();
  list->insert_in_place(it, 30);

  auto last = list->iterate();
  last->next();
  last->next();
  int deleted = list->delete_in_place(last);
  EXPECT_EQ(deleted, 30);
  EXPECT_EQ(list->size(), 2);

  auto iter = list->iterate();
  EXPECT_EQ(iter->current(), 10);
  iter->next();
  EXPECT_EQ(iter->current(), 20);
  iter->next();
  EXPECT_TRUE(iter->done());
}

TEST_F(RingDoublyLinkedListTest, IteratorStartAndDone) {
  auto it = list->iterate();
  list->insert_in_place(it, 100);
  it = list->iterate();
  list->insert_in_place(it, 200);

  auto iter = list->iterate();
  EXPECT_FALSE(iter->done());
  EXPECT_EQ(iter->current(), 100);

  iter->next();
  EXPECT_FALSE(iter->done());
  EXPECT_EQ(iter->current(), 200);

  iter->next();
  EXPECT_TRUE(iter->done());

  EXPECT_THROW(iter->current(), const char *);

  iter->start();
  EXPECT_FALSE(iter->done());
  EXPECT_EQ(iter->current(), 100);
}

int main(int argc, char **argv) {
  ::testing::InitGoogleTest(&argc, argv);
  return RUN_ALL_TESTS();
}
