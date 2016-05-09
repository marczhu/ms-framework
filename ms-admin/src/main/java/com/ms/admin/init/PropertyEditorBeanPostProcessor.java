package com.ms.admin.init;

import com.ms.admin.controller.user.CustomPrimitiveNumberEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;


/**
 * 注册全局PropertyEditor
 * Created by mark.zhu on 2016/2/26.
 */
public class PropertyEditorBeanPostProcessor implements BeanPostProcessor {
    public static final Logger LOGGER = LoggerFactory.getLogger(PropertyEditorBeanPostProcessor.class);
    private static final String BLANK = " ";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ConfigurableWebBindingInitializer) {
            LOGGER.info("config PropertyEditorRegistrar for ConfigurableWebBindingInitializer");
            ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) bean;
            initializer.setPropertyEditorRegistrar(new PropertyEditorRegistrar() {
                @Override
                public void registerCustomEditors(PropertyEditorRegistry registry) {
                    registry.registerCustomEditor(String.class, new StringTrimmerEditor(BLANK, true));
                    registry.registerCustomEditor(byte.class, new CustomPrimitiveNumberEditor(byte.class));
                    registry.registerCustomEditor(short.class, new CustomPrimitiveNumberEditor(short.class));
                    registry.registerCustomEditor(int.class, new CustomPrimitiveNumberEditor(int.class));
                    registry.registerCustomEditor(long.class, new CustomPrimitiveNumberEditor(long.class));
                    registry.registerCustomEditor(float.class, new CustomPrimitiveNumberEditor(float.class));
                    registry.registerCustomEditor(double.class, new CustomPrimitiveNumberEditor(double.class));
                }
            });
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("post process after initialization bean: " + bean.getClass().getName());
        }
        return bean;
    }
}
