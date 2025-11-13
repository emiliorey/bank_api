package com.bank.bank_api.infraestructure.dynamicfeign;

import feign.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DynamicClientService {

    private final ApplicationContext applicationContext;
    private final ConfigurableListableBeanFactory beanFactory;
    private final ExtendedFeignClientsRegistrar extendedFeignClientsRegistrar;

    public void changeBaseUrl(String newUrl) {
        extendedFeignClientsRegistrar.registerOrUpdateClient(beanFactory, applicationContext, ExternalApiClient.class, newUrl);
    }

    public List<DynamicFeignInfo> getBeansDynamicFeignClientDefaultConfiguration() {
        List<DynamicFeignInfo> dynamicFeignInfos = new ArrayList<>();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DynamicFeignClient.class);
        beans.forEach((key,value) -> {

            DynamicFeignClient dynamicFeignClient = (DynamicFeignClient) value.getClass().getInterfaces()[0].getAnnotations()[0];
            dynamicFeignInfos.add(new DynamicFeignInfo(value.getClass().getInterfaces()[0].getName(),key, dynamicFeignClient.defaultUrl()));

        });
        return dynamicFeignInfos;
    }

    public List<DynamicFeignInfo> getBeansDynamicFeignClientCurrentConfiguration() {
        List<DynamicFeignInfo> dynamicFeignInfos = new ArrayList<>();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DynamicFeignClient.class);
        beans.forEach((key,value) -> {

            Target<?> target =  getFeignTarget(value);
            dynamicFeignInfos.add(new DynamicFeignInfo(value.getClass().getInterfaces()[0].getName(),key, Objects.requireNonNull(target).url()));

        });
        return dynamicFeignInfos;
    }

    public static Target<?> getFeignTarget(Object feignProxy) {
        try {
            if (!Proxy.isProxyClass(feignProxy.getClass())) {
                return null;
            }
            var invocationHandler = Proxy.getInvocationHandler(feignProxy);
            Field targetField = invocationHandler.getClass().getDeclaredField("target");
            targetField.setAccessible(true);
            return (Target<?>) targetField.get(invocationHandler);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el Target del Feign proxy", e);
        }
    }

}
