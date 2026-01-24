package studio.startapps.pandemona.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .useRequestHeader("X-API-Version")
                .useMediaTypeParameter(MediaType.APPLICATION_JSON, "version")  // Media type
                .addSupportedVersions("1.0","2.0")
                .setDefaultVersion("1.0");
    }
}
