package com.zys.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * Created by zhangyansong on 2020/10/19
 * @Configuration(proxyBeanMethods = false),默认true，proxyBeanMethods 代理bean的方法，true为full模式，false为lite模式
 * true时：springboot启动时会把bean注册到容器中，外部调用的是容器中bean，如和其他bean依赖时需要为true
 * false时：springboot不会代理bean不把bean注册到容器中，外部调用bean每次都会创建一个新的对象
 * false优势：如果bean不和其他bean依赖，可以设置为false，springboot启动时就不会代理和检查bean提告启动速度
 */
@Configuration
public class RestTemplateConfig {
    @Bean("myRestTemplate") //注入到容器中的bean默认为名为方法名，也可在bean注解中指定名字
    public RestTemplate myRestTemplate() throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(30000);
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(), null, null,
                //绕过域名验证
                new X509HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }

                    @Override
                    public void verify(String s, SSLSocket sslSocket) throws IOException {

                    }

                    @Override
                    public void verify(String s, X509Certificate x509Certificate) throws SSLException {

                    }

                    @Override
                    public void verify(String s, String[] strings, String[] strings1) throws SSLException {

                    }
                });
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        // 解决中文乱码问题
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;

    }

    @Bean
    private static SSLContext createIgnoreVerifySSL() throws Exception {
        SSLContext sc = SSLContext.getInstance("TLS");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

}
