package com.klm.hackathon.demo;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.util.StringUtils;

/**
 * @author x085441 (Sathiesh Suyambudhasan)
 * @since Nov 02, 2019
 */
@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.klm.hackathon"})
public class Configuration {

    private String esPassword;

    @Bean RestClient esRestClient() {
        Set<String> distinctUrls = Arrays.stream(StringUtils.split("http://localhost:9200,http://127.0.0.1:9200", ",")).collect(Collectors.toSet());
        List<HttpHost> httpHosts = new ArrayList<>();
        distinctUrls.forEach(url -> {
            try {
                URI uri = new URI(url);
                httpHosts.add(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()));
            }
            catch (URISyntaxException e) {
                throw new BeanCreationException("esRestClient", "Failed to create a esRestClient", e);
            }
        });
        int timeout = 10;
        RestClientBuilder esRestClientBuilder = RestClient.builder(httpHosts.stream().toArray(HttpHost[]::new));//
        esRestClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectionRequestTimeout(0).setSocketTimeout(60000).setConnectTimeout(timeout * 1000))
                        .setMaxRetryTimeoutMillis(120000);// Default is 30000

        if (!StringUtils.isEmpty(esPassword)) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));

            esRestClientBuilder.setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setSSLContext(getSslContext()));
        }
        return esRestClientBuilder.build();
    }

    private SSLContext getSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

            sslContext.init(null, new TrustManager[] {new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new SecureRandom());
        }
        catch (Exception e) {
            //log.error("Exception occurred while creating the esRestClient", e);
        }
        return sslContext;
    }

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        final Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setIndentOutput(false);
        bean.afterPropertiesSet();
        final ObjectMapper objectMapper = bean.getObject();
        if (objectMapper != null) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);
            //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;
    }
}
