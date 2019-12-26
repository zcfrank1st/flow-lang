package com.chaos.graphviz.flow;

import com.chaos.graphviz.flow.jj.FlowDot;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerPart {
    private static final String TEMPLATE_NAME = "flow.ftlh";
    private Template temp;

    public FreemarkerPart() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        temp = cfg.getTemplate(TEMPLATE_NAME);
    }

    public void process(Object value, Writer writer) throws IOException, TemplateException {
        Map root = new HashMap();
        root.put("nodes", value);
        temp.process(root, writer);
    }
}
