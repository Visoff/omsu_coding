#include <gtest/gtest.h>
#include <vector>
#include "hashmap/hashmap.hpp"
#include "linkedhashmap/linkedhashmap.hpp"

// unity build
#include "hashmap/hashmap.cpp"
#include "linkedhashmap/linkedhashmap.cpp"

TEST(HashMapGetOnEmptyMapCrash, GetOnEmptyCrashesDueToDivisionByZero) {
    HashMap<int, int> map;
    map.get(42);
}

TEST(HashMapRemoveOnEmptyBucketCrash, RemoveWhenBucketNullDereferences) {
    HashMap<int, int> map;
    map.put(1, 10);
    map.remove(1);
    map.remove(1);
}

TEST(HashMapRemoveNonExistentKeyDecrementsSizeIncorrectly, RemoveMissingKeyReducesSize) {
    HashMap<int, int> map;
    map.put(1, 10);
    map.remove(99);
    EXPECT_TRUE(map.get(1).has_value());
}

TEST(HashMapIteratorOnEmptyMapCrash, IteratorStartAccessesBucketsZeroWhenCapacityZero) {
    HashMap<int, int> map;
    auto it = map.iterator();
    it->start();
}

TEST(HashMapIteratorOutOfBoundsAccess, IteratorStartMayAccessBucketsBeyondCapacity) {
    HashMap<int, int> map;
    map.put(1, 10);
    auto it = map.iterator();
    it->start();
    while (!it->done()) it->next();
}

TEST(HashMapPutDoesNotInitializeBucketPointers, UninitializedBucketsCauseUndefinedBehavior) {
    HashMap<int, int> map;
    map.put(1, 10);
    map.put(2, 20);
    map.put(3, 30);
    EXPECT_TRUE(map.get(1).has_value());
    EXPECT_TRUE(map.get(2).has_value());
    EXPECT_TRUE(map.get(3).has_value());
}

TEST(HashMapDuplicateKeyUpdateFailsForTailNode, UpdateKeyThatIsLastInChain) {
    HashMap<int, int> map;
    for (int i = 0; i < 5; ++i) map.put(i, i);
    map.put(5, 50);
    map.put(0, 99);
    auto val = map.get(0);
    ASSERT_TRUE(val.has_value());
    EXPECT_EQ(val.value(), 99);
}

TEST(LinkedHashMapGetOnEmptyMapCrash, GetOnEmptyCrashesDueToDivisionByZero) {
    LinkedHashMap<int, int> map;
    map.get(42);
}

TEST(LinkedHashMapRemoveOnEmptyBucketCrash, RemoveWhenBucketNullDereferences) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.remove(1);
    map.remove(1);
}

TEST(LinkedHashMapRemoveNonExistentKeyDecrementsSizeIncorrectly, RemoveMissingKeyReducesSize) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.remove(99);
    EXPECT_TRUE(map.get(1).has_value());
}

TEST(LinkedHashMapPutDoesNotInitializeBucketPointers, UninitializedBucketsCauseUndefinedBehavior) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.put(2, 20);
    map.put(3, 30);
    EXPECT_TRUE(map.get(1).has_value());
    EXPECT_TRUE(map.get(2).has_value());
    EXPECT_TRUE(map.get(3).has_value());
}

TEST(LinkedHashMapRemoveDoesNotUpdateOrderLinks, RemoveNonHeadNodeBreaksOrderList) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.put(2, 20);
    map.put(3, 30);
    map.remove(2);
    auto it = map.iterator();
    it->start();
    std::vector<int> keys;
    while (!it->done()) {
        keys.push_back(it->current().first);
        it->next();
    }
    ASSERT_EQ(keys.size(), 2);
    EXPECT_EQ(keys[0], 1);
    EXPECT_EQ(keys[1], 3);
}

TEST(LinkedHashMapFirstInOrderNeverSet, IteratorAlwaysEmptyBecauseFirstInOrderIsNull) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.put(2, 20);
    auto it = map.iterator();
    it->start();
    EXPECT_FALSE(it->done());
    it->next();
    EXPECT_FALSE(it->done());
    it->next();
    EXPECT_TRUE(it->done());
}

TEST(LinkedHashMapBucketsTypeMismatch, UsingArrayIndexOnSinglePointerCorruptsMemory) {
    LinkedHashMap<int, int> map;
    map.put(1, 10);
    map.put(2, 20);
    map.put(3, 30);
    map.put(4, 40);
    map.put(5, 50);
    map.put(6, 60);
    EXPECT_TRUE(map.get(6).has_value());
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
