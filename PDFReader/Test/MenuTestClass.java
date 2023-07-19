import org.example.service.Menu;
import org.example.service.SearchService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Stack;

public class MenuTestClass {

    private final Menu menu = new Menu();
    private final SearchService service = new SearchService();

    @Test
    Stack<String> recursiveFileStackTest(){

        var directory = "D:\\WCCHomework\\Summer2023\\CSS200\\Chapter9";

        var stack = menu.populateFileList(directory);
        var size = stack.size();

        return stack;
    }

    @Test
    void runSearchOnFileStack() {
        var stack = recursiveFileStackTest();
        var size = stack.size();
        for (int i = 0; i < size; i++) {


        }

        System.out.println("final stack size: " + stack);

    }
}
