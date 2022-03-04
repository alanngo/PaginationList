package paginate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class PaginatedList<E> {

    // fields
    private List<List<E>> data;

    //helpers
    private static final String ONE_BASED_INDEX="We use 1-based indexing here sir";
    private static final String NEGATIVE_PAGE_SIZE="Use positive page size";
    private static final int DEFAULT_PAGE_SIZE=10;
    private static <E> List<E> buildPage(int start, int end, List<E> allPages) {
        List<E> ret = new ArrayList<>();
        for (int i = start; i <=end; i++)
        {
            try
            {
                ret.add(allPages.get(i));
            }
            catch(IndexOutOfBoundsException e)
            {
                break;
            }
        }

        return ret;
    }
    private static<E> void buildPaginated (List<List<E>> data, List<E>allPages, int n, int fullPageSize) {
        int start = 0;
        int end = n -1;

        while (start <= end) {

            List<E> page = buildPage(start, end, allPages);
            data.add(page);

            start = end + 1;
            end += n;
            if (end > fullPageSize)
                end = fullPageSize - 1;
        }
    }

    private void populate(Collection<E> elements, int n, Comparator<E> criteria) {
        if (n<1)
            throw new IndexOutOfBoundsException(NEGATIVE_PAGE_SIZE);

        List<E> allPages = new ArrayList<>(elements);
        if (criteria!=null)
            allPages.sort(criteria);
        int fullPageSize = allPages.size();
        data = new ArrayList<>();

        if (fullPageSize <= n) // within capacity
            data.add(allPages);

        else // outside capacity
            buildPaginated(data, allPages, n, fullPageSize);
    }

    /**
     * Builds a paginated list with a user-defined page size and sorting criteria from input elements
     * @param elements collection of elements to paginate
     * @param n number of pages to set
     * @param criteria Comparator sorting criteria
     */
    public PaginatedList(Collection<E> elements, int n, Comparator<E> criteria){populate(elements, n, criteria);}
    /**
     * Builds a paginated list with a user-defined page size from input elements
     * @param elements collection of elements to paginate
     * @param n number of pages to set
     */
    public PaginatedList(Collection<E> elements, int n) {this(elements, n, null);}

    /**
     * Builds a paginated list with a user-defined sorting criteria and default page size of 10 from input elements
     * @param elements collection of elements to paginate
     * @param criteria Comparator sorting criteria
     */
    public PaginatedList(Collection<E> elements, Comparator<E> criteria) {this(elements, DEFAULT_PAGE_SIZE, criteria);}

    /**
     * Builds a paginated list with a default page size of 10 from input elements
     * @param elements collection of elements to paginate
     */
    public PaginatedList(Collection<E> elements) {this(elements, DEFAULT_PAGE_SIZE, null);}

    /**
     * Gets the contents of a page
     * @param pageNum page number to index into
     * @throws IndexOutOfBoundsException uses one-based indexing
     * @return list of contents at pageNum
     */
    public List<E> get(int pageNum) {
        if (pageNum <1 || pageNum> data.size())
            throw new IndexOutOfBoundsException(ONE_BASED_INDEX);
        return data.get(pageNum -1);
    }

    /**
     * Gets the full list of pages
     * @return list of pages
     */
    public List<List<E>> getList(){return data;}

    // overridden methods from java.lang.Object
    @Override
    public String toString() {return getList().toString();}
}
