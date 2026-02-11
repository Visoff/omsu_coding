#include "box/Box.hpp"
#include "functional/functional.hpp"
#include <gtest/gtest.h>
#include <sstream>

// unity build
#include "box/Box.cpp"
#include "functional/functional.cpp"

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
  EXPECT_EQ(defaultBox.length, 0);
  EXPECT_EQ(defaultBox.width, 0);
  EXPECT_EQ(defaultBox.height, 0);
  EXPECT_DOUBLE_EQ(defaultBox.weight, 0.0);
  EXPECT_EQ(defaultBox.value, 0);
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

int main() {
  ::testing::InitGoogleTest();
  int _ = RUN_ALL_TESTS();
  return 0;
}
