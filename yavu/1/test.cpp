#include <gtest/gtest.h>
#include <sstream>
#include "dynarr/dynarr.hpp"

// unity build
#include "dynarr/dynarr.cpp"

TEST(DynArrayTest, DefaultConstructor) {
    DynArray a;
    EXPECT_EQ(a.length(), 0);
    EXPECT_EQ(a.capacity(), 1);
}

TEST(DynArrayTest, SizeConstructor) {
    DynArray a(DynArray::Sized{}, 5);
    EXPECT_EQ(a.length(), 5);
    EXPECT_EQ(a.capacity(), 5);
    for (size_t i = 0; i < 5; ++i)
        EXPECT_EQ(a[i], 0);
}

TEST(DynArrayTest, SizeAndValueConstructor) {
    DynArray a(DynArray::Sized{}, 5, 42);
    EXPECT_EQ(a.length(), 5);
    EXPECT_EQ(a.capacity(), 5);
    for (size_t i = 0; i < 5; ++i)
        EXPECT_EQ(a[i], 42);
}

TEST(DynArrayTest, ReserveConstructor) {
    DynArray a(DynArray::Reserve{}, 10);
    EXPECT_EQ(a.length(), 0);
    EXPECT_EQ(a.capacity(), 10);
}

TEST(DynArrayTest, CopyConstructor) {
    DynArray a(DynArray::Sized{}, 3, 7);
    DynArray b(a);
    EXPECT_EQ(b.length(), 3);
    EXPECT_EQ(b.capacity(), 3);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(b[i], 7);
    a[0] = 99;
    EXPECT_EQ(b[0], 7);
}

TEST(DynArrayTest, MoveConstructor) {
    DynArray a(DynArray::Sized{}, 3, 7);
    DynArray b(std::move(a));
    EXPECT_EQ(b.length(), 3);
    EXPECT_EQ(b.capacity(), 3);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(b[i], 7);
    EXPECT_EQ(a.length(), 0);
    EXPECT_EQ(a.capacity(), 0);
}

TEST(DynArrayTest, WithCapacity) {
    DynArray a = DynArray::with_capacity(20);
    EXPECT_EQ(a.length(), 0);
    EXPECT_EQ(a.capacity(), 20);
}

TEST(DynArrayTest, LengthAndCapacity) {
    DynArray a(DynArray::Sized{}, 5, 1);
    EXPECT_EQ(a.length(), 5);
    EXPECT_EQ(a.capacity(), 5);
    a.reserve(10);
    EXPECT_EQ(a.length(), 5);
    EXPECT_EQ(a.capacity(), 10);
}

TEST(DynArrayTest, Subscript) {
    DynArray a(DynArray::Sized{}, 3);
    a[0] = 10;
    a[1] = 20;
    a[2] = 30;
    EXPECT_EQ(a[0], 10);
    EXPECT_EQ(a[1], 20);
    EXPECT_EQ(a[2], 30);
    const DynArray& ca = a;
    EXPECT_EQ(ca[1], 20);
}

TEST(DynArrayTest, ResizeLarger) {
    DynArray a(DynArray::Sized{}, 3, 1);
    a.resize(5);
    EXPECT_EQ(a.length(), 5);
    EXPECT_GE(a.capacity(), 5);
    EXPECT_EQ(a[0], 1);
    EXPECT_EQ(a[1], 1);
    EXPECT_EQ(a[2], 1);
    EXPECT_EQ(a[3], 0);
    EXPECT_EQ(a[4], 0);
}

TEST(DynArrayTest, ResizeSmaller) {
    DynArray a(DynArray::Sized{}, 5, 2);
    a.resize(3);
    EXPECT_EQ(a.length(), 3);
    EXPECT_GE(a.capacity(), 3);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(a[i], 2);
}

TEST(DynArrayTest, ResizeWithinCapacity) {
    DynArray a(DynArray::Sized{}, 3, 5);
    a.reserve(10);
    a.resize(6);
    EXPECT_EQ(a.length(), 6);
    EXPECT_EQ(a.capacity(), 10);
    EXPECT_EQ(a[3], 0);
    EXPECT_EQ(a[4], 0);
    EXPECT_EQ(a[5], 0);
}

TEST(DynArrayTest, ReserveLarger) {
    DynArray a(DynArray::Sized{}, 3, 7);
    a.reserve(10);
    EXPECT_EQ(a.length(), 3);
    EXPECT_EQ(a.capacity(), 10);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(a[i], 7);
}

TEST(DynArrayTest, ReserveSmaller) {
    DynArray a(DynArray::Sized{}, 3, 7);
    a.reserve(2);
    EXPECT_EQ(a.length(), 3);
    EXPECT_EQ(a.capacity(), 3);
}

TEST(DynArrayTest, PushBack) {
    DynArray a;
    a.pushBack(10);
    EXPECT_EQ(a.length(), 1);
    EXPECT_GE(a.capacity(), 1);
    EXPECT_EQ(a[0], 10);
    a.pushBack(20);
    EXPECT_EQ(a.length(), 2);
    EXPECT_EQ(a[0], 10);
    EXPECT_EQ(a[1], 20);
    size_t old_cap = a.capacity();
    for (int i = 0; i < 10; ++i)
        a.pushBack(i);
    EXPECT_GT(a.capacity(), old_cap);
}

TEST(DynArrayTest, PopBack) {
    DynArray a(DynArray::Sized{}, 3, 5);
    int val = a.popBack();
    EXPECT_EQ(val, 5);
    EXPECT_EQ(a.length(), 2);
    EXPECT_EQ(a[0], 5);
    EXPECT_EQ(a[1], 5);
    val = a.popBack();
    EXPECT_EQ(val, 5);
    EXPECT_EQ(a.length(), 1);
    val = a.popBack();
    EXPECT_EQ(val, 5);
    EXPECT_EQ(a.length(), 0);
}

TEST(DynArrayTest, CopyAssignment) {
    DynArray a(DynArray::Sized{}, 3, 7);
    DynArray b;
    b = a;
    EXPECT_EQ(b.length(), 3);
    EXPECT_EQ(b.capacity(), 3);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(b[i], 7);
    a = a;
    EXPECT_EQ(a.length(), 3);
    EXPECT_EQ(a[0], 7);
}

TEST(DynArrayTest, MoveAssignment) {
    DynArray a(DynArray::Sized{}, 3, 7);
    DynArray b;
    b = std::move(a);
    EXPECT_EQ(b.length(), 3);
    EXPECT_EQ(b.capacity(), 3);
    for (size_t i = 0; i < 3; ++i)
        EXPECT_EQ(b[i], 7);
    EXPECT_EQ(a.length(), 0);
    EXPECT_EQ(a.capacity(), 0);
}

TEST(DynArrayTest, Equality) {
    DynArray a(DynArray::Sized{}, 3, 5);
    DynArray b(DynArray::Sized{}, 3, 5);
    DynArray c(DynArray::Sized{}, 3, 6);
    DynArray d(DynArray::Sized{}, 2, 5);
    EXPECT_TRUE(a == b);
    EXPECT_FALSE(a == c);
    EXPECT_FALSE(a == d);
}

TEST(DynArrayTest, Inequality) {
    DynArray a(DynArray::Sized{}, 3, 5);
    DynArray b(DynArray::Sized{}, 3, 5);
    DynArray c(DynArray::Sized{}, 3, 6);
    EXPECT_FALSE(a != b);
    EXPECT_TRUE(a != c);
}

TEST(DynArrayTest, LexicographicalLess) {
    DynArray a{1, 2, 3};
    DynArray b{1, 2, 4};
    DynArray c{1, 2, 3, 0};
    DynArray d{1, 2};
    EXPECT_TRUE(a < b);
    EXPECT_FALSE(b < a);
    EXPECT_TRUE(a < c);
    EXPECT_FALSE(c < a);
    EXPECT_TRUE(d < a);
    EXPECT_FALSE(a < d);
}

TEST(DynArrayTest, Greater) {
    DynArray a{1, 2, 3};
    DynArray b{1, 2, 4};
    EXPECT_TRUE(b > a);
    EXPECT_FALSE(a > b);
}

TEST(DynArrayTest, LessOrEqual) {
    DynArray a{1, 2, 3};
    DynArray b{1, 2, 3};
    DynArray c{1, 2, 4};
    EXPECT_TRUE(a <= b);
    EXPECT_TRUE(a <= c);
    EXPECT_FALSE(c <= a);
}

TEST(DynArrayTest, GreaterOrEqual) {
    DynArray a{1, 2, 3};
    DynArray b{1, 2, 3};
    DynArray c{1, 2};
    EXPECT_TRUE(a >= b);
    EXPECT_TRUE(a >= c);
    EXPECT_FALSE(c >= a);
}

TEST(DynArrayTest, Concatenation) {
    DynArray a{1, 2, 3};
    DynArray b{4, 5};
    DynArray c = a + b;
    EXPECT_EQ(c.length(), 5);
    EXPECT_EQ(c[0], 1);
    EXPECT_EQ(c[1], 2);
    EXPECT_EQ(c[2], 3);
    EXPECT_EQ(c[3], 4);
    EXPECT_EQ(c[4], 5);
}

TEST(DynArrayTest, OutputOperator) {
    DynArray a{1, 2, 3};
    std::ostringstream oss;
    oss << a;
    EXPECT_EQ(oss.str(), "[1, 2, 3]");
}

TEST(DynArrayTest, InputOperator) {
    DynArray a(DynArray::Sized{}, 3);
    std::istringstream iss("10 20 30");
    iss >> a;
    EXPECT_EQ(a[0], 10);
    EXPECT_EQ(a[1], 20);
    EXPECT_EQ(a[2], 30);
}

TEST(DynArrayTest, EmptyArray) {
    DynArray a;
    EXPECT_EQ(a.length(), 0);
    a.pushBack(42);
    EXPECT_EQ(a.length(), 1);
    a.popBack();
    EXPECT_EQ(a.length(), 0);
    DynArray b;
    EXPECT_TRUE(a == b);
    DynArray c = a + b;
    EXPECT_EQ(c.length(), 0);
}

TEST(DynArrayTest, LargeCapacity) {
    DynArray a(DynArray::Reserve{}, 1000);
    EXPECT_EQ(a.capacity(), 1000);
    for (int i = 0; i < 1000; ++i)
        a.pushBack(i);
    EXPECT_EQ(a.length(), 1000);
    EXPECT_EQ(a.capacity(), 1000);
    for (int i = 0; i < 1000; ++i)
        EXPECT_EQ(a[i], i);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
