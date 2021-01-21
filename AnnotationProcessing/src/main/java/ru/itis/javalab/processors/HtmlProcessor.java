package ru.itis.javalab.processors;

import com.google.auto.service.AutoService;
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
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(HtmlProcessor.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        for (Element element : annotatedElements) {
            Map<String, Object> root = new HashMap<>();
            Form form = new Form();
            root.put("form", form);
            Template template;
            try {
                template = configuration.getTemplate("form.ftl");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            Path filePath = Paths.get(path);
            BufferedWriter out;
            try {
                out = new BufferedWriter(new FileWriter(filePath.toFile()));
                HtmlForm formAnnotation = element.getAnnotation(HtmlForm.class);
                form.setAction(formAnnotation.action());
                form.setMethod(formAnnotation.method());
                List<Input> inputs = new ArrayList<>();
                form.setInputs(inputs);
                for (Element elementIn : element.getEnclosedElements()) {
                    HtmlInput inputAnnotation = elementIn.getAnnotation(HtmlInput.class);
                    if (inputAnnotation != null) {
                        inputs.add(new Input(inputAnnotation.type(), inputAnnotation.name(), inputAnnotation.placeholder()));
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            try {
                template.process(root, out);
                out.close();
            } catch (TemplateException | IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}
