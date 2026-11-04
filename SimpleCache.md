## Task 2: Code Review

1. Expired values are never removed from the cache.

	**Explanation**: Expired values must be removed from the cache because the cache will keep growing. There should be a scheduler that runs periodically to check the values and remove the expired ones.

	**Impact**: The cache size will keep increasing in production and will eventually cause an OutOfMemoryError, halting the application.

2. No remove method on the cache.

	**Explanation**: There is no remove method on the cache. One should be added. And in the `get()`, if the value is expired, we should call the `remove()` method there to remove the value.

	**Impact**: This will cause some expired data to stay in the cache until it gets removed by the scheduler.

3. No maximum size of the cache.

	**Explanation**: There is normally a size limit in caching mechanisms to prevent the cache from growing indefinitely. A size limit should be added to the SimpleCache class to remove the least recent values in case the cache reaches its limit.

	**Impact**: Same as the first point, we might get an OutOfMemoryError if there is too much data in the cache.

4. Do not use `System.currentTimeMillis()` for comparing time.

	**Explanation**: When comparing times it is better to use `System.nanoTime()` because `System.currentTimeMillis()` uses the wall-clock.

	**Impact**: During changes to daylight saving time, we might get a negative value if using `System.currentTimeMillis()`, causing inconsistent values.

5. To get the size of the cache, do not return the `size()` of the hash map.

	**Explanation**: According to the Javadoc of the ConcurrentHashMap, in a concurrent application, the size() is only an estimation and returns an int which maxes out at Integer.MAX_VALUE. It is better to return the mappingCount() which is still an estimation, but it returns a long. We could change the method name to estimatedSize() so that there is no confusion.

	**Impact**: It would return an unreliable estimation of the cache size.

6. There is no constructor in the SimpleCache class.

	**Explanation**: This is not an issue, but we could add one and make the ttlMs variable a parameter of the Cache. Also, the maximum size of the cache should be added in the constructor. In addition to those parameters, we can call the following constructor of the ConcurrentHashMap object: ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel)

	- Initial capacity: We could set it to 60000, given we will insert approximately 100 items per second or 60000 per minute and a 60000 ttl.
	- Load factor: We could set it at 0.5 to reduce collisions.
	- Concurrency level: We could set it to 10 or maybe a little bit more if we know the number of threads.

	**Impact**: This fix will make the SimpleCache more versatile and increase its performance.

7. No unit tests.

	**Explanation**: Some unit tests should be added to run some tests based on the number of reads, writes, and number of threads used in production to ensure that there are no performance or concurrency issues.

8. No Javadoc.

	**Explanation**: Add Javadoc on all methods, classes, and variables so that all the developers understand quickly what the class does.

9. No logging.

	**Explanation**: Some logging could be added to know the size of the cache, the number of cache hits and misses to understand the performance of the cache in production. Be sure that logging does not affect performance.