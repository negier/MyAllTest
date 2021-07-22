package com.xuebinduan.lib_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.xuebinduan.lib_annotations.BindView;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class BindingProcessor extends AbstractProcessor {
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    //可以手动运行gradle的task#compileDebugJavaWithJavac，它即会调用这个方法
    //todo 我不知道为什么这个方法会被执行两次？
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        /*
         * JavaPoet大概用法：
         * JavaFile.builder().build().writeTo(Filer)
         */
        for (Element element : roundEnv.getRootElements()) {
            //element是所有的类文件，如MainActivity、XXXUtil、以及build文件目录下自动生成的那些类文件，如BuildConfig等。
            //当然我们这里只生成有BindView注解的类的相关的文件，通过hasBinding这个bool变量来判断。
            String packageStr = element.getEnclosingElement().toString();
            String classStr = element.getSimpleName().toString();
//            System.out.println(classStr);

            ClassName className = ClassName.get(packageStr, classStr + "$Binding");
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(ClassName.get(packageStr, classStr), "activity");
            boolean hasBinding = false;
            for (Element enclosedElement : element.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.FIELD) {
                    BindView bindView = enclosedElement.getAnnotation(BindView.class);
                    if (bindView != null) {
                        hasBinding = true;
                        constructorBuilder.addStatement("activity.$N = activity.findViewById($L)", enclosedElement.getSimpleName(), bindView.value());
                    }
                }
            }

            TypeSpec builtClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructorBuilder.build())
                    .build();

            if (hasBinding) {
                try {
                    JavaFile.builder(packageStr, builtClass)
                            .build()
                            .writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}