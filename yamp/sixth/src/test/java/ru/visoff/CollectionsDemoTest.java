package ru.visoff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionsDemoTest {
    private Human ivanov;
    private Human petrov;
    private Human sidorov;
    private Human ivanovSameAge;

    @BeforeEach
    void setUp() {
        ivanov = new Human("Ivanov", "Ivan", "Ivanovich", 30);
        petrov = new Human("Petrov", "Petr", "Petrovich", 25);
        sidorov = new Human("Sidorov", "Sidr", "Sidorovich", 30);
        ivanovSameAge = new Human("Ivanov", "Ivan", "Ivanovich", 30);
    }

    @Test
    void countStringsStartingWith_shouldCountCorrectly() {
        List<String> strings = Arrays.asList("apple", "apricot", "banana", "cherry");
        assertEquals(2, CollectionsDemo.countStringsStartingWith(strings, 'a'));
        assertEquals(1, CollectionsDemo.countStringsStartingWith(strings, 'b'));
        assertEquals(0, CollectionsDemo.countStringsStartingWith(strings, 'z'));
    }

    @Test
    void countStringsStartingWith_emptyList_returnsZero() {
        assertEquals(0, CollectionsDemo.countStringsStartingWith(Collections.emptyList(), 'a'));
    }

    @Test
    void selectNamesakes_shouldReturnPeopleWithSameSurname() {
        List<Human> humans = Arrays.asList(ivanov, petrov, sidorov, ivanovSameAge);
        Collection<Human> namesakes = CollectionsDemo.selectNamesakes(humans, ivanov);
        assertEquals(Collections.singleton(ivanovSameAge), new HashSet<>(namesakes));
    }

    @Test
    void selectNamesakes_noNamesakes_returnsEmptyList() {
        List<Human> humans = Arrays.asList(petrov, sidorov);
        Collection<Human> namesakes = CollectionsDemo.selectNamesakes(humans, ivanov);
        assertEquals(Collections.emptySet(), new HashSet<>(namesakes));
    }

    @Test
    void copyWithout_shouldReturnNewListWithoutSpecifiedPerson() {
        List<Human> humans = new ArrayList<>(Arrays.asList(ivanov, petrov, sidorov));
        Collection<Human> copy = CollectionsDemo.copyWithout(humans, ivanov);
        assertIterableEquals(Arrays.asList(petrov, sidorov), copy);
    }

    @Test
    void copyWithout_personNotPresent_returnsFullCopy() {
        List<Human> humans = Arrays.asList(ivanov, petrov);
        Collection<Human> copy = CollectionsDemo.copyWithout(humans, sidorov);
        assertIterableEquals(humans, copy);
    }

    @Test
    void selectNotIntersected_shouldReturnSetsWithoutIntersection() {
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6));
        Set<Integer> set3 = new HashSet<>(Arrays.asList(5, 7, 8));
        Set<Integer> target = new HashSet<>(Arrays.asList(3, 4));

        Collection<Set<Integer>> sets = Arrays.asList(set1, set2, set3);
        Collection<Set<Integer>> result = CollectionsDemo.selectNotIntersected(sets, target);

        assertIterableEquals(Collections.singletonList(set3), result);
    }

    @Test
    void selectNotIntersected_emptyCollection_returnsEmpty() {
        Collection<Set<Integer>> empty = Collections.emptyList();
        Set<Integer> target = new HashSet<>(Arrays.asList(1, 2));
        assertTrue(CollectionsDemo.selectNotIntersected(empty, target).isEmpty());
    }

    @Test
    void selectEldest_shouldReturnSetWithMaxAgePeople() {
        List<Human> humans = Arrays.asList(ivanov, petrov, sidorov);
        Set<Human> eldest = CollectionsDemo.selectEldest(humans);
        assertEquals(new HashSet<>(Arrays.asList(ivanov, sidorov)), eldest);
    }

    @Test
    void selectEldest_worksWithSubtypes() {
        Student youngest = new Student("A", "A", "A", 20, "CS");
        Student oldest1 = new Student("B", "B", "B", 25, "Math");
        Student oldest2 = new Student("C", "C", "C", 25, "Phys");
        List<Student> students = Arrays.asList(youngest, oldest1, oldest2);
        Set<Student> eldest = CollectionsDemo.selectEldest(students);
        assertEquals(new HashSet<>(Arrays.asList(oldest1, oldest2)), eldest);
    }

    @Test
    void selectEldest_emptyCollection_returnsEmptySet() {
        assertTrue(CollectionsDemo.selectEldest(Collections.emptyList()).isEmpty());
    }

    @Test
    void selectOrderedByFIO_shouldReturnListSortedByFullName() {
        List<Human> humans = Arrays.asList(petrov, ivanov, sidorov);
        List<Human> sorted = CollectionsDemo.selectOrderedByFIO(humans);
        assertIterableEquals(Arrays.asList(ivanov, petrov, sidorov), sorted);
    }

    @Test
    void selectOrderedByFIO_worksWithStudents() {
        Student s1 = new Student("C", "C", "C", 20, "CS");
        Student s2 = new Student("A", "A", "A", 25, "Math");
        Student s3 = new Student("B", "B", "B", 22, "Phys");
        List<Student> students = Arrays.asList(s1, s2, s3);
        List<Student> sorted = CollectionsDemo.selectOrderedByFIO(students);
        assertIterableEquals(Arrays.asList(s2, s3, s1), sorted);
    }

    @Test
    void selectOrderedByFIO_emptyList_returnsEmptyList() {
        assertTrue(CollectionsDemo.selectOrderedByFIO(Collections.emptyList()).isEmpty());
    }

    @Test
    void mapById_shouldReturnHumansWithGivenIds() {
        Map<Integer, Human> map = new HashMap<>();
        map.put(1, ivanov);
        map.put(2, petrov);
        map.put(3, sidorov);
        Set<Integer> ids = new HashSet<>(Arrays.asList(1, 3, 99));
        Set<Human> result = CollectionsDemo.mapById(map, ids);
        assertEquals(new HashSet<>(Arrays.asList(ivanov, sidorov)), result);
    }

    @Test
    void mapById_emptySet_returnsEmptySet() {
        Map<Integer, Human> map = Collections.singletonMap(1, ivanov);
        assertTrue(CollectionsDemo.mapById(map, Collections.emptySet()).isEmpty());
    }

    @Test
    void selectAdults_shouldReturnIdsOfPeopleAge18OrAbove() {
        Map<Integer, Human> map = new HashMap<>();
        map.put(1, new Human("A", "A", "A", 17));
        map.put(2, new Human("B", "B", "B", 18));
        map.put(3, new Human("C", "C", "C", 25));
        map.put(4, null);
        Collection<Integer> adultIds = CollectionsDemo.selectAdults(map);
        assertEquals(new HashSet<>(Arrays.asList(2, 3)), new HashSet<>(adultIds));
    }

    @Test
    void selectAdults_emptyMap_returnsEmptyList() {
        assertTrue(CollectionsDemo.selectAdults(Collections.emptyMap()).isEmpty());
    }

    @Test
    void mapAges_shouldMapIdToAge() {
        Map<Integer, Human> map = new HashMap<>();
        map.put(1, ivanov);
        map.put(2, petrov);
        map.put(3, null);
        Map<Integer, Integer> ages = CollectionsDemo.mapAges(map);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 30);
        expected.put(2, 25);
        assertEquals(expected, ages);
    }

    @Test
    void mapByAge_shouldGroupHumansByAge() {
        Set<Human> humans = new HashSet<>(Arrays.asList(ivanov, petrov, sidorov, ivanovSameAge));
        Map<Integer, List<Human>> byAge = CollectionsDemo.mapByAge(humans);

        // Expected groups: age 30 -> {ivanov, sidorov} (order not guaranteed)
        //                 age 25 -> {petrov}
        Map<Integer, Set<Human>> expected = new HashMap<>();
        expected.put(30, new HashSet<>(Arrays.asList(ivanov, sidorov)));
        expected.put(25, new HashSet<>(Collections.singletonList(petrov)));

        Map<Integer, Set<Human>> actual = new HashMap<>();
        byAge.forEach((age, list) -> actual.put(age, new HashSet<>(list)));

        assertEquals(expected, actual);
    }

    @Test
    void mapByAge_emptySet_returnsEmptyMap() {
        assertTrue(CollectionsDemo.mapByAge(Collections.emptySet()).isEmpty());
    }

    @Test
    void mapByAgeAndInitialSurnameLetter_shouldCreateNestedMapWithSortedDescending() {
        Human a1 = new Human("Antonov", "Ivan", "Ivanovich", 30);
        Human a2 = new Human("Akimov", "Petr", "Petrovich", 30);
        Human b1 = new Human("Bobrov", "Sidor", "Sidorovich", 30);
        Human c1 = new Human("Chernov", "Ivan", "Ivanovich", 25);
        Set<Human> humans = new HashSet<>(Arrays.asList(a1, a2, b1, c1));

        Map<Integer, Map<Character, List<Human>>> result =
                CollectionsDemo.mapByAgeAndInitialSurnameLetter(humans);

        Map<Integer, Map<Character, List<Human>>> expected = new HashMap<>();

        Map<Character, List<Human>> age30Map = new HashMap<>();
        age30Map.put('A', Arrays.asList(a1, a2));
        age30Map.put('B', Collections.singletonList(b1));
        expected.put(30, age30Map);

        Map<Character, List<Human>> age25Map = new HashMap<>();
        age25Map.put('C', Collections.singletonList(c1));
        expected.put(25, age25Map);

        assertEquals(expected, result);
    }

    @Test
    void mapByAgeAndInitialSurnameLetter_emptySet_returnsEmptyMap() {
        assertTrue(CollectionsDemo.mapByAgeAndInitialSurnameLetter(Collections.emptySet()).isEmpty());
    }
}
