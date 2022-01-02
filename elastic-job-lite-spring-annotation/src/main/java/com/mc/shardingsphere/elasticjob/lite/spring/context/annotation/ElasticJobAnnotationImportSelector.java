package com.mc.shardingsphere.elasticjob.lite.spring.context.annotation;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author mengcheng
 * @date 2021/02/28  下午9:31
 **/
public class ElasticJobAnnotationImportSelector implements ImportSelector, BeanClassLoaderAware {



    private ClassLoader classLoader;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> strings = Lists.newArrayList(ElasticJobConstant.ELASTIC_JOB_ANNOTATION_BEAN_PROCESS_NAME);
        if (!exitClass(ElasticJobConstant.ELASTIC_JOB_SPRING_BOOT_START_BEAN_NAME)) {
            strings.add(ElasticJobConstant.ELASTIC_JOB_START_BEAN_NAME);
        }
        return strings.toArray(new String[0]);
    }

    private boolean exitClass(String className) {
        try {
            classLoader = Objects.isNull(classLoader) ? ClassUtils.getDefaultClassLoader() : classLoader;
            classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
