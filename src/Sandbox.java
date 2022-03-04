
import paginate.PaginatedList;

import java.util.*;
import static java.lang.System.*;
public class Sandbox
{
    public static void main(String[] args) {
        List<String> elems = Arrays.asList("z", "y", "x", "a", "b", "c", "d", "e", "f");
        PaginatedList<String> pages = new PaginatedList<>(elems, String::compareToIgnoreCase);
        out.println(pages.getList());
        out.println(pages.get(1));
    }
}
