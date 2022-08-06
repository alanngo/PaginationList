package paginate;

import java.util.*;

public class PaginatedList<E> implements Iterable<List<E>>{
    //helpers
    private static final String ONE_BASED_INDEX = "We use 1-based indexing here sir";
    private static final String NEGATIVE_PAGE_SIZE = "Use positive page size";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private static <E> List<E> buildPage(int start, int end, List<E> allPages) {
        List<E> ret = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            try {
                ret.add(allPages.get(i));
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        return ret;
    }

    private static <E> void buildPaginated(List<List<E>> data, List<E> allPages, int n, int fullPageSize) {
        int start = 0;
        int end = n - 1;

        while (start <= end) {

            List<E> page = buildPage(start, end, allPages);
            data.add(page);

            start = end + 1;
            end += n;
            if (end > fullPageSize)
                end = fullPageSize - 1;
        }
    }

    private void fill(Collection<E> elements, int n, Comparator<E> criteria) {
        if (n < 1)
            throw new IndexOutOfBoundsException(NEGATIVE_PAGE_SIZE);

        List<E> allPages = new ArrayList<>(elements);
        if (criteria != null)
            allPages.sort(criteria);
        int fullPageSize = allPages.size();

        if (fullPageSize <= n) // within capacity
            data.add(allPages);

        else // outside capacity
            buildPaginated(data, allPages, n, fullPageSize);
    }


    // fields
    private final List<List<E>> data = new ArrayList<>();

    /**
     * Builds a paginated list with a user-defined page size and sorting criteria from input elements
     *
     * @param elements collection of elements to paginate
     * @param pageSize number of pages to set
     * @param criteria Comparator sorting criteria
     */
    public PaginatedList(Collection<E> elements, int pageSize, Comparator<E> criteria) {
        fill(elements, pageSize, criteria);
    }

    /**
     * Builds a paginated list with a user-defined page size from input elements
     *
     * @param elements collection of elements to paginate
     * @param pageSize number of pages to set
     */
    public PaginatedList(Collection<E> elements, int pageSize) {this(elements, pageSize, null);}


    /**
     * Builds a paginated list with a user-defined sorting criteria and default page size of 10 from input elements
     *
     * @param elements collection of elements to paginate
     * @param criteria Comparator sorting criteria
     */
    public PaginatedList(Collection<E> elements, Comparator<E> criteria) {this(elements, DEFAULT_PAGE_SIZE, criteria);}

    // constructors

    /**
     * Builds a paginated list with a default page size of 10 from input elements
     *
     * @param elements collection of elements to paginate
     */
    public PaginatedList(Collection<E> elements) {this(elements, DEFAULT_PAGE_SIZE, null);}

    // accessors

    /**
     * Gets the contents of a page
     *
     * @param pageNum page number to index into
     * @return list of contents at pageNum
     * @throws IndexOutOfBoundsException uses one-based indexing
     */
    public List<E> get(int pageNum) {
        if (pageNum < 1 || pageNum > data.size())
            throw new IndexOutOfBoundsException(ONE_BASED_INDEX);
        return data.get(pageNum - 1);
    }

    /**
     * first page
     *
     * @return data.get(0)
     */
    public List<E> first() {return get(1);}

    /**
     * last page
     *
     * @return data.get(data.size () -1)
     */
    public List<E> last() {return get(data.size());}

    /**
     * Gets the full list of pages
     *
     * @return list of pages
     */
    public List<List<E>> getList() {return data;}

    /**
     * converts the paginated list to single list
     *
     * @return list of all elements
     */
    public List<E> flatten() {
        List<E> ret = new ArrayList<>();
        data.forEach(ret::addAll);
        return ret;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<List<E>> iterator() {return data.iterator();}


    // overridden methods from java.lang.Object
    @Override
    public String toString() {return getList().toString();}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PaginatedList<?> obj)) return false;
        if (o == this) return true;
        return data.equals(obj.data);
    }

    @Override
    public int hashCode() {return data.hashCode();}


}
