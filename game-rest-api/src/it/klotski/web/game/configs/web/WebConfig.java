package it.klotski.web.game.configs.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static it.klotski.web.game.constants.ApplicationConstants.ADAPTER_FACTORY;

/**
 * Questa classe rappresenta la configurazione del Web per l'applicazione.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    /**
     * Estende la lista dei convertitori di messaggi HTTP per includere il convertitore Gson.
     *
     * @param converters La lista dei convertitori di messaggi HTTP.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.registerTypeAdapterFactory(ADAPTER_FACTORY).create();
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson);
        gsonHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
    }

    /**
     * Restituisce l'istanza di Gson utilizzata per la conversione dei messaggi HTTP.
     *
     * @return L'istanza di Gson.
     */
    @Bean
    public Gson gson() {
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(DateTypeAdapter.FACTORY).registerTypeAdapterFactory(ADAPTER_FACTORY);
        return b.create();
    }

    /**
     * Configura i convertitori di messaggi HTTP per supportare vari tipi di dati.
     *
     * @param converters La lista dei convertitori di messaggi HTTP.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        stringConverter.setSupportedMediaTypes(List.of(MediaType.TEXT_PLAIN));
        converters.add(stringConverter);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        gsonHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));
        converters.add(gsonHttpMessageConverter);
    }

    /**
     * Crea e registra un filtro CORS per consentire le richieste cross-origin.
     *Il filtro CORS determina quali domini sono consentiti ad accedere alle risorse del server attraverso le
     * richieste HTTP
     * @return Il bean di registrazione del filtro CORS.
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:8000","http://localhost:3100","http://172.200.0.8:3100"));
        config.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                "X-XSRF-TOKEN",
                HttpHeaders.ACCEPT
        ));
        config.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.DELETE.name()
        ));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        config.setMaxAge(MAX_AGE);
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }
}
