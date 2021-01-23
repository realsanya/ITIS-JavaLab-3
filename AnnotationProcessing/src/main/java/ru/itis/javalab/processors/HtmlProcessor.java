package ru.itis.javalab.processors;

import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.javalab.annotations.HtmlForm;
import ru.itis.javalab.annotations.HtmlInput;
import ru.itis.javalab.models.Form;
import ru.itis.javalab.models.Input;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"annotations.HtmlForm", "annotations.HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(HtmlForm.class);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");

        try {
            configuration.setTemplateLoader(new FileTemplateLoader(new File("src/main/resources")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Element element : annotatedElements) {
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path file = Paths.get(path);
            Form form;
            Template template;
            BufferedWriter outWriter;
            try {
                outWriter = new BufferedWriter(new FileWriter(file.toFile()));
                template = configuration.getTemplate("form.ftl");
                HtmlForm elementAnnotation = element.getAnnotation(HtmlForm.class);
                form = new Form(elementAnnotation.action(), elementAnnotation.method());
                List<Input> inputList = new ArrayList<>();
                List<? extends Element> elements = element.getEnclosedElements();
                for (Element el : elements) {
                    HtmlInput inputAnnotation = el.getAnnotation(HtmlInput.class);
                    if (inputAnnotation != null) {
                       inputList.add(new Input(inputAnnotation.type(), inputAnnotation.name(), inputAnnotation.placeholder()));
                    }
                }

                Map<String, Object> map = new HashMap<>();
                map.put("form", form);
                map.put("inputs", inputList);
                FileWriter fileWriter = new FileWriter(out.toFile());
                template.process(map, outWriter);

            } catch (IOException e) {
                throw new IllegalStateException(e);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
