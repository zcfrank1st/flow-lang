import com.chaos.graphviz.flow.jj.FlowDot;
import com.chaos.graphviz.flow.jj.ParseException;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;

@RunWith(JUnit4.class)
public class ParserTest {
    private Gson gson = new Gson();

    @Test
    public void testParser() throws ParseException {
        String lang = "start a to b comment 起始; step b called 定义 to c; end c;";

        FlowDot dot = new FlowDot(new ByteArrayInputStream(lang.getBytes()), "utf-8");
        dot.build();

        System.out.println(gson.toJson(dot.nodes));
    }
}
