package zy.holder;


import com.google.auto.service.AutoService;
import zy.holder.annotation.Holder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

@AutoService(Processor.class)
public class HolderProcessor extends BaseProcessor {

    private static final String PACKAGE_NAME = "zy.holder";

    private static final String ONE_ADAPTER = "OneAdapter";

    private static final String INTERFACE_SERVICE_REGISTER = "HolderRegister";

    private static final String GENERATE_PACKAGE_NAME = PACKAGE_NAME + ".generate";

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Holder.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Set<TypeElement> classSet = new LinkedHashSet<>();
        for (Element element : env.getElementsAnnotatedWith(Holder.class)) {
            parseClassSet(element, classSet);
        }
        try {
            if (!classSet.isEmpty()) {
                generate(classSet).writeTo(filer);
            }
        } catch (IOException e) {
            logger.e("generate HolderRegister file error --- " + e.getMessage());
        }
        return false;
    }

    private JavaFile generate(Set<TypeElement> classSet) {
        return JavaFile.builder(GENERATE_PACKAGE_NAME + "." + moduleName, createType(classSet))
                .addFileComment("Generated code. Do not modify!")
                .build();
    }

    private TypeSpec createType(Set<TypeElement> classSet) {
        return TypeSpec.classBuilder(Character.toUpperCase(moduleName.charAt(0))
                + moduleName.substring(1) + "$$HolderRegister")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(PACKAGE_NAME, INTERFACE_SERVICE_REGISTER))
                .addMethod(createRegisterMethod(classSet))
                .build();
    }

    private MethodSpec createRegisterMethod(Set<TypeElement> classSet) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("register")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);
        for (TypeElement element : classSet) {
            String annotateClassName = getAnnotationClassValue(element);
            builder.addStatement("$T.globalRegister($T.class, $T.class, $L)", ClassName.get(PACKAGE_NAME, ONE_ADAPTER),
                    ClassName.bestGuess(annotateClassName),
                    ClassName.bestGuess(element.getQualifiedName().toString()), 0);
        }
        return builder.build();
    }

    private void parseClassSet(Element element, Set<TypeElement> classSet) {
        classSet.add((TypeElement) element);
    }

    private String getAnnotationClassValue(TypeElement element) {
        String annotateValue;
        try {
            annotateValue = element.getAnnotation(Holder.class).value().getName();
        } catch (MirroredTypeException e) {
            annotateValue = e.getTypeMirror().toString();
        }
        return annotateValue;
    }
}
