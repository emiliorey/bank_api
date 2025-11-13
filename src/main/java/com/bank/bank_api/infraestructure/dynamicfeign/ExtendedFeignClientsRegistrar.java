package com.bank.bank_api.infraestructure.dynamicfeign;


import io.github.classgraph.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExtendedFeignClientsRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        log.info("Escaneando interfaces con @DynamicFeignClient ...");

        String[] basePackages = getBasePackages(metadata);

        for (String basePackage : basePackages) {
            log.info("Escaneando paquete: {}", basePackage);
            scanAndRegisterDynamicClients(basePackage, registry);
        }
    }

    private String[] getBasePackages(AnnotationMetadata metadata) {
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableExtendedFeignClients.class.getName());
        if (attrs != null && attrs.containsKey("basePackages")) {
            return (String[]) attrs.get("basePackages");
        }
        // fallback: paquete del @SpringBootApplication
        String className = metadata.getClassName();
        try {
            return new String[]{Class.forName(className).getPackage().getName()};
        } catch (ClassNotFoundException e) {
            log.warn("No se pudo determinar el paquete base, usando '.'");
            return new String[]{"."};
        }
    }

    private void scanAndRegisterDynamicClients(String basePackage, BeanDefinitionRegistry registry) {
        try (ScanResult scanResult = new ClassGraph()
                .enableAnnotationInfo()
                .acceptPackages(basePackage) // Escanea el paquete base
                .scan()) {

            ClassInfoList interfaces = scanResult.getClassesWithAnnotation(DynamicFeignClient.class.getName());

            log.info("Se encontraron {} interfaces con @DynamicFeignClient", interfaces.size());

            for (ClassInfo classInfo : interfaces) {
                try {
                    String className = classInfo.getName();
                    log.info("Procesando interfaz: {}", className);

                    Class<?> clazz = Class.forName(className);
                    DynamicFeignClient ann = clazz.getAnnotation(DynamicFeignClient.class);

                    if (StringUtils.isEmpty(ann.defaultUrl())) {
                        throw new IllegalArgumentException("It is missing the defaultUrl");
                    }
                    if (StringUtils.isEmpty(ann.name())) {
                        throw new IllegalArgumentException("It is missing the name");
                    }
                    String defaultUrl = ann.defaultUrl().trim();
                    String name = ann.name().trim();

                    // Crear cliente Feign dinámico
                    Object feignClient = FeignDynamicFactory.createClient(clazz, defaultUrl);

                    // Registrar el bean
                    GenericBeanDefinition def = new GenericBeanDefinition();
                    def.setBeanClass(clazz);
                    def.setInstanceSupplier(() -> feignClient);

                    registry.registerBeanDefinition(name, def);
                    log.info("DynamicFeignClient '{}' registrado con URL: {}", name, defaultUrl);
                } catch (Exception e) {
                    log.error("Error registrando DynamicFeignClient: {}", e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("Error durante el escaneo de @DynamicFeignClient: {}", e.getMessage(), e);
        }
    }

    public <T> T registerOrUpdateClient(ConfigurableListableBeanFactory beanFactory,
                                        ApplicationContext applicationContext,
                                        Class<T> clazz,
                                        String baseUrl) {

        DynamicFeignClient ann = clazz.getAnnotation(DynamicFeignClient.class);
        if (ann == null) {
            throw new IllegalArgumentException("Missing @DynamicFeignClient annotation");
        }
        String beanName = ann.name();
        if (baseUrl == null || baseUrl.isBlank()) baseUrl = ann.defaultUrl();

        // Crear la nueva instancia del cliente Feign
        T newClient = FeignDynamicFactory.createClient(clazz, baseUrl);

        // Intentar usar DefaultListableBeanFactory para eliminar registro anterior
        if (beanFactory instanceof DefaultListableBeanFactory dlbf) {
            // 1) Destruir singleton en el registro de singletons (si existe)
            if (dlbf.containsSingleton(beanName)) {
                dlbf.destroySingleton(beanName);
            }
            // 2) Remover la definición de bean si existiera
            if (dlbf.containsBeanDefinition(beanName)) {
                try {
                    dlbf.removeBeanDefinition(beanName);
                } catch (Exception ex) {
                    // loguear si no se puede remover (ej. si fue registrado sólo como singleton)
                }
            }
            // 3) Registrar la nueva instancia como singleton
            dlbf.registerSingleton(beanName, newClient);

            // Si querés exponer también una BeanDefinition (opcional):
//             GenericBeanDefinition def = new GenericBeanDefinition();
//             def.setBeanClass(clazz);
//             def.setInstanceSupplier(() -> newClient);
//             dlbf.registerBeanDefinition(beanName, def);

        } else {
            // Fallback: intentar con el ApplicationContext si es configurable
            var ctx = (applicationContext instanceof org.springframework.context.ConfigurableApplicationContext cac)
                    ? cac
                    : null;
            if (ctx != null) {
                DefaultListableBeanFactory dlbf2 = (DefaultListableBeanFactory) ctx.getBeanFactory();
                if (dlbf2.containsSingleton(beanName)) dlbf2.destroySingleton(beanName);
                if (dlbf2.containsBeanDefinition(beanName)) dlbf2.removeBeanDefinition(beanName);
                dlbf2.registerSingleton(beanName, newClient);
            } else {
                throw new IllegalStateException("No se puede acceder a DefaultListableBeanFactory para reemplazar el bean");
            }
        }

        // Opcional: log e retorno
        log.info("Feign dinámico '{}' actualizado con URL: {}", beanName, baseUrl);
        return newClient;
    }

}