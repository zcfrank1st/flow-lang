package com.chaos.graphviz.flow;

import com.chaos.graphviz.flow.jj.FlowDot;
import com.chaos.graphviz.flow.jj.ParseException;
import freemarker.template.TemplateException;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author chaos
 */
public class FlowCraft {
    public static void main(String[] args) throws IOException, TemplateException, ParseException {
        FlowDot flowDot = new FlowDot(new ByteArrayInputStream(Files.readAllBytes(Paths.get(System.getProperty("input")))));
        flowDot.build();

        StringWriter writer = new StringWriter();

        FreemarkerPart freemarkerPart = new FreemarkerPart();
        freemarkerPart.process(FlowDot.nodes, writer);

        MutableGraph g = new Parser().read(writer.toString());

        String output = System.getProperty("output");
        Renderer renderer = Graphviz.fromGraph(g).render(Format.SVG);
        if (StringUtils.isEmpty(output)) {
            renderer.toString();
        } else {
            renderer.toFile(new File(output));
        }
    }
}
