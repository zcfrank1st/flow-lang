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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author chaos
 */
public class FlowCraft {
    public static void main(String[] args) throws IOException, TemplateException, ParseException {
        if (StringUtils.isEmpty(System.getProperty("input"))) {
            if (StringUtils.isEmpty(getInputText())) {
                System.out.println("Error Input Text Or File !");
            }
            FlowDot flowDot = new FlowDot(new ByteArrayInputStream(getInputText().getBytes()), "utf-8");
            flowDot.build();
        } else {
            FlowDot flowDot = new FlowDot(new ByteArrayInputStream(Files.readAllBytes(Paths.get(input))));
            flowDot.build();
        }

        StringWriter writer = new StringWriter();

        FreemarkerPart freemarkerPart = new FreemarkerPart();
        freemarkerPart.process(FlowDot.nodes, writer);

        MutableGraph g = new Parser().read(writer.toString());

        String output = System.getProperty("output");
        Renderer renderer = Graphviz.fromGraph(g).render(Format.SVG);
        if (StringUtils.isEmpty(output)) {
            System.out.println(renderer.toString());
        } else {
            renderer.toFile(new File(output));
        }
    }

    private String getInputText() {
        return System.getProperty("input");
    }
}
