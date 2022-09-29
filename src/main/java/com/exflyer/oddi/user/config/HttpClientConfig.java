package com.exflyer.oddi.user.config;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class HttpClientConfig {

    @Configuration
    @Profile({"main","op","stg","dev","nextnow","local"})
    static class OptHttpClientConfig {
        @Bean
        public HttpClient httpClient() {
            try {
                SSLContextBuilder builder = SSLContexts.custom();
                builder.loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                        return true;
                    }
                });
                SSLContext sslContext = builder.build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, new X509HostnameVerifier() {
                    @Override
                    public void verify(String host, SSLSocket ssl)
                        throws IOException {
                    }

                    @Override
                    public void verify(String host, X509Certificate cert)
                        throws SSLException {
                    }

                    @Override
                    public void verify(String host, String[] cns,
                        String[] subjectAlts) throws SSLException {
                    }

                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });

                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create().register("https", sslsf)
                    .build();

                PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                //.build());
                connManager.setMaxTotal(200);

                RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(10 * 60 * 1000)
                    .setConnectionRequestTimeout(60 * 1000)
                    .setSocketTimeout(10 * 60 * 1000).build();

                CloseableHttpClient httpClient =
                    HttpClients.custom()
                        .setRetryHandler(
                            (exception, executionCount, context) -> {
                                if (executionCount > 1) {
                                    log.warn("Maximum tries reached for client http pool ");
                                    return false;
                                }
                                if (exception instanceof org.apache.http.NoHttpResponseException) {
                                    log.warn("No response from server on " + executionCount + " call");
                                    return true;
                                }
                                return false;
                            })
                        .setConnectionManager(connManager)
                        .setDefaultRequestConfig(
                            RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                        .setSSLSocketFactory(sslsf)
                        .setDefaultRequestConfig(config)
                        .build();

                return httpClient;
            } catch (Exception e) {
                log.error("ssl httpclient exception", e);
                return HttpClientBuilder.create().build();
            }
        }
    }
}
