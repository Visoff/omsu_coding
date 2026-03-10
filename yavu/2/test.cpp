#include <gtest/gtest.h>
#include <stdexcept>
#include "ring_buffer/RingBuffer.hpp"
#include "iterator/Iterator.hpp"

// unity build
#include "ring_buffer/RingBuffer.cpp"
#include "iterator/Iterator.cpp"

TEST(RingBufferTest, Constructor) {
    RingBuffer<int> buf(5);
    EXPECT_EQ(buf.size(), 0);
    EXPECT_TRUE(buf.is_empty());
}

TEST(RingBufferTest, PushBackAndFront) {
    RingBuffer<int> buf(3);
    buf.push_back(10);
    EXPECT_EQ(buf.front(), 10);
    EXPECT_EQ(buf.back(), 10);
    buf.push_back(20);
    EXPECT_EQ(buf.front(), 10);
    EXPECT_EQ(buf.back(), 20);
    buf.push_front(5);
    EXPECT_EQ(buf.front(), 5);
    EXPECT_EQ(buf.back(), 20);
    EXPECT_EQ(buf.size(), 3);
}

TEST(RingBufferTest, PopFrontBack) {
    RingBuffer<int> buf(3);
    buf.push_back(1);
    buf.push_back(2);
    buf.push_back(3);
    EXPECT_EQ(buf.pop_front(), 1);
    EXPECT_EQ(buf.pop_back(), 3);
    EXPECT_EQ(buf.size(), 1);
    EXPECT_EQ(buf.front(), 2);
}

TEST(RingBufferTest, PopEmptyThrows) {
    RingBuffer<int> buf(2);
    EXPECT_THROW(buf.pop_front(), std::out_of_range);
    EXPECT_THROW(buf.pop_back(), std::out_of_range);
}

TEST(RingBufferTest, Clear) {
    RingBuffer<int> buf(3);
    buf.push_back(1);
    buf.push_back(2);
    buf.clear();
    EXPECT_TRUE(buf.is_empty());
    EXPECT_EQ(buf.size(), 0);
}

TEST(RingBufferTest, IndexAccess) {
    RingBuffer<int> buf(3);
    buf.push_back(10);
    buf.push_back(20);
    buf.push_front(5);
    EXPECT_EQ(buf[0], 5);
    EXPECT_EQ(buf[1], 10);
    EXPECT_EQ(buf[2], 20);
    EXPECT_THROW(buf[3], std::out_of_range);
}

TEST(IteratorTest, IterateOverBuffer) {
    RingBuffer<int> buf(5);
    buf.push_back(1);
    buf.push_back(2);
    buf.push_back(3);

    Iterator<int> it(buf);
    std::vector<int> result;
    for (it.start(); !it.finish(); it.next()) {
        result.push_back(it.getValue());
    }
    std::vector<int> expected = {1, 2, 3};
    EXPECT_EQ(result, expected);
}

TEST(IteratorTest, IterateAfterPop) {
    RingBuffer<int> buf(3);
    buf.push_back(10);
    buf.push_back(20);
    buf.push_back(30);
    buf.pop_front();
    buf.pop_back(); 

    Iterator<int> it(buf);
    it.start();
    EXPECT_FALSE(it.finish());
    EXPECT_EQ(it.getValue(), 20);
    it.next();
    EXPECT_TRUE(it.finish());
}

TEST(IteratorTest, IteratorWithEmptyBuffer) {
    RingBuffer<int> buf(3);
    Iterator<int> it(buf);
    EXPECT_TRUE(it.finish());
    EXPECT_THROW(it.getValue(), std::out_of_range);
}

TEST(IteratorTest, ResetStart) {
    RingBuffer<int> buf(3);
    buf.push_back(5);
    buf.push_back(6);

    Iterator<int> it(buf);
    it.start();
    EXPECT_EQ(it.getValue(), 5);
    it.next();
    EXPECT_EQ(it.getValue(), 6);
    it.start();
    EXPECT_EQ(it.getValue(), 5);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
