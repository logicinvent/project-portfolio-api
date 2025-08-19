package br.com.project.portfolio.rest.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String APP_TITLE = "Project Portfolio API";
    public static final String APP_VERSION = "1.0";
    public static final String APP_DESCRIPTION = "Project Portfolio API";

    public static final String CONTACT_NAME = "Jean Fernandes";
    public static final String CONTACT_URL  = "http://www.ppa.com.br";
    public static final String CONTACT_MAIL = "contact@ppa.com.br";

    public static final String LICENSE_NAME = "Apache 2.0";
    public static final String LICENSE_URL  = "http://springdoc.org";

    public static final String SECURITY_SCHEME_NAME = "basicAuth";
    public static final String SECURITY_SCHEME_TYPE = "basic"; // HTTP Basic

    public static final String GROUP_PUBLIC = "public";
    public static final String BASE_PACKAGE = "br.com.project.portfolio.rest.api";
    public static final String API_PATHS    = "/api/**";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        SECURITY_SCHEME_NAME,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(SECURITY_SCHEME_TYPE)
                ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(new Info()
                        .title(APP_TITLE)
                        .version(APP_VERSION)
                        .description(APP_DESCRIPTION)
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .url(CONTACT_URL)
                                .email(CONTACT_MAIL))
                        .license(new License()
                                .name(LICENSE_NAME)
                                .url(LICENSE_URL))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(GROUP_PUBLIC)
                .packagesToScan(BASE_PACKAGE)
                .pathsToMatch(API_PATHS)
                .build();
    }
}
