package ru.itis.javalab.processors;

import com.google.auto.service.AutoService;
import ru.itis.javalab.annotations.HtmlForm;
import ru.itis.javalab.annotations.HtmlInput;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"annotations.HtmlForm", "annotations.HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(HtmlForm.class);

        for (Element element : annotatedElements) {
            BufferedWriter out;

            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path outPath = Paths.get(path);

            try {
                out = new BufferedWriter(new FileWriter(outPath.toFile()));
                HtmlForm elementAnnotation = element.getAnnotation(HtmlForm.class);
                out.write("<form action='" + elementAnnotation.action() + "' method='" + elementAnnotation.method() + "'>\n");
                Set<? extends Element> inputAnnotation = roundEnvironment.getElementsAnnotatedWith(HtmlInput.class);
                for (Element elementIn : annotatedElements) {
                    if (inputAnnotation != null) {
                        HtmlInput input = elementIn.getAnnotation(HtmlInput.class);
                        out.write("<input type='" + input.type() + "' name='" + input.name() + "' placeholder='" + input.placeholder() + "'>\n");
                    }
                }
                out.write("</form");
                out.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return true;
    }
}
