package com.chaos.graphviz.flow;

import com.chaos.graphviz.flow.jj.FlowDot;
import com.chaos.graphviz.flow.jj.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chaos
 */
public class FlowCraft {
    public static void main(String[] args) throws IOException, TemplateException, ParseException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(FlowCraft.class.getResource("flow.ftlh").getPath().replace("flow.ftlh", "")));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        FlowDot flowDot = new FlowDot(new ByteArrayInputStream("".getBytes()));
        flowDot.build();


        Map root = new HashMap();
        root.put("nodes", FlowDot.nodes);

        Template temp = cfg.getTemplate("flow.ftlh");
        StringWriter writer = new StringWriter();
        temp.process(root, writer);

        MutableGraph g = new Parser().read(writer.toString());
        Graphviz.fromGraph(g).render(Format.SVG).toFile(new File(""));
    }
}
