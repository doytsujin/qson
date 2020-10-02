package io.quarkus.funqy.deployment.bindings.http;

import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.bootstrap.BootstrapDebug;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.builditem.GeneratedClassBuildItem;
import io.quarkus.gizmo.ClassOutput;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratedClassGizmoAdaptor implements ClassOutput {

    private final BuildProducer<GeneratedClassBuildItem> classOutput;
    private final Map<String, StringWriter> sources;

    public GeneratedClassGizmoAdaptor(BuildProducer<GeneratedClassBuildItem> classOutput) {
        this.classOutput = classOutput;
        this.sources = BootstrapDebug.DEBUG_SOURCES_DIR != null ? new ConcurrentHashMap<>() : null;
    }

    @Override
    public void write(String className, byte[] bytes) {
        String source = null;
        if (sources != null) {
            StringWriter sw = sources.get(className);
            if (sw != null) {
                source = sw.toString();
            }
        }
        classOutput.produce(new GeneratedClassBuildItem(true, className, bytes, source));
    }

    @Override
    public Writer getSourceWriter(String className) {
        if (sources != null) {
            StringWriter writer = new StringWriter();
            sources.put(className, writer);
            return writer;
        }
        return ClassOutput.super.getSourceWriter(className);
    }

}
