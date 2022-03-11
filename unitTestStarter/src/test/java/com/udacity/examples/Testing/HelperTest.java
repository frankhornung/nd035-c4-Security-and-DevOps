package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

// https://docs.spring.io/spring-boot/docs/1.5.22.RELEASE/reference/html/boot-features-testing.html
// @SpringBootTest
// we did not include the spring-boot-starter-test annotation and this project is not a spring-boot project at all.
// thus, we cannot use the annotation @Test as we expected it to work, instead we require the tests to start with "test"
public class HelperTest{
    //public class HelperTest extends TestCase{

    @Before
    public void init() {
        System.out.println("init executed"); }
    @After
    public void teardown() {
        System.out.println("teardown executed"); }
    @Test
    public void test() {
        assertEquals("test", "test1");    }
    @Test
    public void validate_getCount() {
        List<String> empNames = Arrays.asList("sareeta", "", "john","");
        assertEquals(2, Helper.getCount(empNames));
    }
    @Test
    public void validate_3lengthString() {
        List<String> empNames = Arrays.asList("sareeta", "", "Jeff","sam");
        assertEquals(2, Helper.getStringsOfLength3(empNames));
    }
    @Test
    public void verify_list_is_squared(){
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expected = Arrays.asList(169, 16, 225, 36, 289, 64, 361, 1, 4, 9);
        assertEquals(expected, Helper.getSquareList(yrsOfExperience));
    }
    @Test
    public void verify_merged_list(){
        List<String> empNames = Arrays.asList("sareeta", "", "john","");
        assertEquals("sareeta, john", Helper.getMergedList(empNames));
    }
    @Test
    public void verify_getMax(){
        List<Integer> empLevel = Arrays.asList(3,3,3,5,7,2,2,5,7,5);
        assertEquals(7, Helper.getStats(empLevel).getMax());
    }
    // This method must be public and static
    @BeforeClass
    public static void initClass() {
        System.out.println("init Class executed");
    }
    @AfterClass
    public static void teardownclass() {
        System.out.println("teardown Class executed");
    }
    @Test
    public void verify_ArrayListTest(){
        int[] yrsOfExperience = {13,4,15,6,17,8,19,1,2,3};
        int[] expected = {13,4,15,6,17,8,19,1,2,3};
        assertArrayEquals(expected, yrsOfExperience);
    }

	@Test
    public void testAssert(){
        assertEquals(1,1);
    }

    @Test
    public void testVerifyGetCount(){
        List<String> empNames= Arrays.asList("sareeta", "udacity");
        final long actual = Helper.getCount(empNames);
        assertEquals(2,actual);
    }

    @Test
    public void testVerifyStats(){
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expectedList = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics stats =  Helper.getStats(yrsOfExperience);
        assertEquals(19, stats.getMax());
        assertEquals(expectedList,yrsOfExperience);
    }

    @Test
    public void testCompareArrays(){
        int[] yrs = {10,14,2};
        int[] expectedYrs = {10,14,2};
        assertArrayEquals(expectedYrs,yrs);
    }

    @Ignore
    @Test
    public void testGetMergedList(){
        List<String> stringList = Arrays.asList("abc", "def", "ghi");
        String actual = Helper.getMergedList(stringList);
        assertEquals("abc, def, ghi",actual);
    }


}
