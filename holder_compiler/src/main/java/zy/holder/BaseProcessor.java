package zy.holder;

import java.util.Map;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class BaseProcessor extends AbstractProcessor {

    private static final String KEY_MODULE_NAME = "MODULE_NAME";

    Elements elementUtils;

    Filer filer;

    Types typeUtils;

    String moduleName;

    Logger logger;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        elementUtils = env.getElementUtils();
        filer = env.getFiler();
        typeUtils = env.getTypeUtils();
        logger = new Logger(env.getMessager());
        Map<String, String> options = env.getOptions();
        if (options != null && !options.isEmpty()) {
            moduleName = options.get(KEY_MODULE_NAME);
        }
        if (moduleName != null && !moduleName.isEmpty()) {
            moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
            logger.i("config MODULE_NAME is ---> " + moduleName);
        } else {
            throw new IllegalArgumentException("Please config annotationProcessorOptions");
        }
    }
}
