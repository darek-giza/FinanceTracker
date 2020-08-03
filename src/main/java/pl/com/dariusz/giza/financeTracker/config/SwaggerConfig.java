package pl.com.dariusz.giza.financeTracker.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket get() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo())
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .useDefaultResponseMessages(false);

        return docket.select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Finance tracker API",
                "Finance tracker",
                "1.00",
                null,
                new Contact("Dariusz Giza", null, "darek_giza@op.pl"),
                "my own licence", null,
                Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        final SecurityContext build = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
        return build;
    }

    ArrayList<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

}
