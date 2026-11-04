package com.malbert.tempo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FilterTest {

	@Test
	void testFilter() {
		Hierarchy unfiltered = new ArrayBasedHierarchy(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 },
				new int[] { 0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2 });
		Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
		Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[] { 1, 2, 5, 8, 10, 11 },
				new int[] { 0, 1, 1, 0, 1, 2 });
		assertEquals(filteredExpected.formatString(), filteredActual.formatString());
	}
	
	/**
	 * Tested with empty nodes
	 */
	@Test
	void testFilterEmpty() {
		Hierarchy unfiltered = new ArrayBasedHierarchy(new int[] { },
				new int[] { });
		Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
		Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[] {  },
				new int[] {  });
		assertEquals(filteredExpected.formatString(), filteredActual.formatString());
	}
	
	/**
	 * Tested with missing depth values 
	 */
	@Test
	void testFilterNoDepth() {
		Hierarchy unfiltered = new ArrayBasedHierarchy(new int[] { 1, 2 },
				new int[] { 0 });
		Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 != 0);
		Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[] { 1 },
				new int[] { 0 });
		assertEquals(filteredExpected.formatString(), filteredActual.formatString());
	}
	
	/**
	 *        test (node % 2 != 0)
	 * 1		true
	 * 2		false
	 * - 3		true
	 * - - 4  	false
	 * 5		true
	 * - 6		false
	 * - 7		true
	 * 
	 * Should return node 1, 5, 7
	 */
	@Test
	void testFilter2() {
		Hierarchy unfiltered = new ArrayBasedHierarchy(new int[] { 1, 2, 3, 4, 5, 6, 7 },
				new int[] {0, 0, 1, 2, 0, 1, 1 });
		Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 2 != 0);
		Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[] { 1, 5, 7 },
				new int[] { 0, 0, 1 });
		assertEquals(filteredExpected.formatString(), filteredActual.formatString());
	}
	
	/**
	 *        test (node % % 3 == 0)
	 * 1		false
	 * - 2		false
	 * - - 3	true
	 * - 5		false
	 * 6		true
	 * - 9		true
	 * 
	 * Should return node 6, 9
	 */
	@Test
	void testFilter3() {
		Hierarchy unfiltered = new ArrayBasedHierarchy(new int[] { 1, 2, 3, 5, 6, 9 },
				new int[] {0, 1, 2, 1, 0, 1 });
		Hierarchy filteredActual = HierarchyFilter.filter(unfiltered, nodeId -> nodeId % 3 == 0);
		Hierarchy filteredExpected = new ArrayBasedHierarchy(new int[] { 6, 9 },
				new int[] { 0, 1 });
		assertEquals(filteredExpected.formatString(), filteredActual.formatString());
	}
	
	
}
