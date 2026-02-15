#include "box/Box.hpp"
#include "functional/functional.hpp"
#include "container/container.hpp"
#include <gtest/gtest.h>
#include <sstream>

// unity build
#include "box/Box.cpp"
#include "functional/functional.cpp"
#include "container/container.cpp"

class BoxTest : public ::testing::Test {
protected:
  void SetUp() override {
    box1 = Box(10, 20, 30, 5.5, 100);
    box2 = Box(10, 20, 30, 5.5, 100);
    box3 = Box(15, 25, 35, 7.5, 150);
  }

  Box box1;
  Box box2;
  Box box3;
};

TEST_F(BoxTest, ConstructorAndEquality) {
  EXPECT_EQ(box1, box2);
  EXPECT_FALSE(box1 == box3);

  Box defaultBox;
  EXPECT_EQ(defaultBox.get_length(), 0);
  EXPECT_EQ(defaultBox.get_width(), 0);
  EXPECT_EQ(defaultBox.get_height(), 0);
  EXPECT_DOUBLE_EQ(defaultBox.get_weight(), 0.0);
  EXPECT_EQ(defaultBox.get_value(), 0);
}

TEST_F(BoxTest, StreamOperators) {
  std::stringstream ss;
  ss << box1;

  Box readBox;
  ss >> readBox;

  EXPECT_EQ(box1, readBox);

  std::stringstream bad_ss("not_a_number 20 30 5.5 100");
  EXPECT_NO_THROW(bad_ss >> readBox);
}

TEST(TotalValueTest, EmptyArray) {
  BoxArray empty;
  EXPECT_DOUBLE_EQ(total_value(empty), 0.0);
}

TEST(TotalValueTest, SingleBox) {
  BoxArray boxes = {Box(1, 1, 1, 1.0, 50)};
  EXPECT_DOUBLE_EQ(total_value(boxes), 50.0);
}

TEST(TotalValueTest, MultipleBoxes) {
  BoxArray boxes = {Box(1, 1, 1, 1.0, 100), Box(2, 2, 2, 2.0, 200),
                    Box(3, 3, 3, 3.0, 300)};
  EXPECT_DOUBLE_EQ(total_value(boxes), 600.0);
}

TEST(FitsInDimensionsTest, VariousCases) {
  BoxArray boxes = {Box(1, 2, 3, 1.0, 100), Box(2, 3, 4, 2.0, 200)};

  EXPECT_TRUE(fits_in_dimensions(boxes, 30.0));
  EXPECT_TRUE(fits_in_dimensions(boxes, 15.0));
  EXPECT_FALSE(fits_in_dimensions(boxes, 14.9));

  BoxArray empty;
  EXPECT_TRUE(fits_in_dimensions(empty, 0.0));
}

TEST(MaxWeightWithVolumeLessThanTest, EdgeCases) {
  BoxArray empty;
  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(empty, 100), 0.0);

  BoxArray boxes = {Box(2, 2, 2, 5.0, 100), Box(3, 3, 3, 8.0, 200),
                    Box(1, 1, 1, 3.0, 50), Box(4, 4, 4, 10.0, 300)};

  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 0), 0.0);
  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 1), 3.0);
  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 8), 5.0);
  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 27), 8.0);
  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 100), 10.0);
}

TEST(CanBoxesBeNestedTest, VariousCases) {
  BoxArray nestable = {Box(10, 10, 10, 5.0, 100), Box(9, 9, 9, 4.0, 90),
                       Box(8, 8, 8, 3.0, 80)};
  EXPECT_TRUE(can_boxes_be_nested(nestable));

  BoxArray sameSize = {Box(10, 10, 10, 5.0, 100), Box(10, 10, 10, 5.0, 100)};
  EXPECT_FALSE(can_boxes_be_nested(sameSize));

  BoxArray notNestable = {Box(10, 10, 10, 5.0, 100), Box(9, 9, 10, 4.0, 90)};
  EXPECT_FALSE(can_boxes_be_nested(notNestable));

  BoxArray empty;
  EXPECT_TRUE(can_boxes_be_nested(empty));

  BoxArray single = {Box(5, 5, 5, 2.0, 50)};
  EXPECT_TRUE(can_boxes_be_nested(single));

  BoxArray rotated = {Box(10, 20, 30, 5.0, 100), Box(15, 25, 5, 4.0, 90)};
  EXPECT_TRUE(can_boxes_be_nested(rotated));
}

TEST(EdgeCasesTest, NegativeValues) {
  BoxArray boxes = {Box(-1, 2, 3, -1.0, -100)};

  EXPECT_DOUBLE_EQ(total_value(boxes), -100.0);
  EXPECT_TRUE(fits_in_dimensions(boxes, 10.0));

  EXPECT_DOUBLE_EQ(max_weight_with_volume_less_than(boxes, 0), 0.0);
}

class ContainerTest : public ::testing::Test {
protected:
    ContainerTest() : c10(100, 100, 100, 50.0) {}

    Container c10;
    Box boxLight{10, 10, 10, 5.0, 100};
    Box boxHeavy{20, 20, 20, 30.0, 200};
    Box boxMedium{15, 15, 15, 15.0, 150};
};

TEST_F(ContainerTest, ConstructorAndEmpty) {
    EXPECT_EQ(c10.get_length(), 100);
    EXPECT_EQ(c10.get_width(), 100);
    EXPECT_EQ(c10.get_height(), 100);
    EXPECT_DOUBLE_EQ(c10.get_max_weight(), 50.0);
    EXPECT_EQ(c10.count(), 0);
    EXPECT_DOUBLE_EQ(c10.total_weight(), 0.0);
    EXPECT_DOUBLE_EQ(c10.total_value(), 0.0);
}

TEST_F(ContainerTest, AddBoxWithoutIndex) {
    int idx = c10.add_box(boxLight);
    EXPECT_EQ(idx, 0);
    EXPECT_EQ(c10.count(), 1);
    EXPECT_DOUBLE_EQ(c10.total_weight(), 5.0);
    EXPECT_DOUBLE_EQ(c10.total_value(), 100.0);

    Box retrieved = c10.get_box(0);
    EXPECT_EQ(retrieved, boxLight);

    idx = c10.add_box(boxMedium);
    EXPECT_EQ(idx, 1);
    EXPECT_EQ(c10.count(), 2);
    EXPECT_DOUBLE_EQ(c10.total_weight(), 20.0);
    EXPECT_DOUBLE_EQ(c10.total_value(), 250.0);
}

TEST_F(ContainerTest, AddBoxWeightException) {
    c10.add_box(boxHeavy);
    EXPECT_THROW(c10.add_box(boxHeavy), DoesNotFitException);
    EXPECT_EQ(c10.count(), 1);
    EXPECT_DOUBLE_EQ(c10.total_weight(), 30.0);
}

TEST_F(ContainerTest, AddBoxAtIndex) {
    c10.add_box(boxLight);
    c10.add_box(boxHeavy);
    c10.add_box(boxMedium, 0);
    EXPECT_EQ(c10.count(), 3);
    EXPECT_EQ(c10.get_box(0), boxMedium);
    EXPECT_EQ(c10.get_box(1), boxLight);
    EXPECT_EQ(c10.get_box(2), boxHeavy);
}

TEST_F(ContainerTest, RemoveBox) {
    c10.add_box(boxLight);
    c10.add_box(boxMedium);
    c10.add_box(boxHeavy);

    Box removed = c10.remove_box(1);
    EXPECT_EQ(removed, boxMedium);
    EXPECT_EQ(c10.count(), 2);
    EXPECT_EQ(c10.get_box(0), boxLight);
    EXPECT_EQ(c10.get_box(1), boxHeavy);
}

TEST_F(ContainerTest, TotalWeightAndValue) {
    c10.add_box(boxLight);
    c10.add_box(boxMedium);
    c10.add_box(boxHeavy);
    EXPECT_DOUBLE_EQ(c10.total_weight(), 5.0 + 15.0 + 30.0);
    EXPECT_DOUBLE_EQ(c10.total_value(), 100.0 + 150.0 + 200.0);
}

TEST_F(ContainerTest, SubscriptOperator) {
    c10.add_box(boxLight);
    c10.add_box(boxMedium);

    const Container& constRef = c10;
    EXPECT_EQ(constRef[0], boxLight);
    EXPECT_EQ(constRef[1], boxMedium);

    c10[0] = boxHeavy;
    EXPECT_EQ(c10.get_box(0), boxHeavy);
    EXPECT_EQ(c10[0].get_value(), 200);

    c10[1].set_value(999);
    EXPECT_EQ(c10.get_box(1).get_value(), 999);
}

TEST_F(ContainerTest, StreamOperators) {
    c10.add_box(boxLight);
    c10.add_box(boxMedium);

    std::stringstream ss;
    ss << c10;

    Container c2(0,0,0,0);
    ss >> c2;

    EXPECT_EQ(c2.get_length(), 100);
    EXPECT_EQ(c2.get_width(), 100);
    EXPECT_EQ(c2.get_height(), 100);
    EXPECT_DOUBLE_EQ(c2.get_max_weight(), 50.0);
    EXPECT_EQ(c2.count(), 2);
    EXPECT_EQ(c2.get_box(0), boxLight);
    EXPECT_EQ(c2.get_box(1), boxMedium);
}

TEST_F(ContainerTest, StreamEmpty) {
    std::stringstream ss;
    ss << c10;

    Container c2(1,2,3,4.5);
    ss >> c2;
    EXPECT_EQ(c2.get_length(), 100);
    EXPECT_EQ(c2.get_width(), 100);
    EXPECT_EQ(c2.get_height(), 100);
    EXPECT_DOUBLE_EQ(c2.get_max_weight(), 50.0);
    EXPECT_EQ(c2.count(), 0);
}

int main() {
  ::testing::InitGoogleTest();
  return RUN_ALL_TESTS();
}
