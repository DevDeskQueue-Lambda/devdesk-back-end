package com.digitalsolutionsbydon.devdesk.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;

import static com.digitalsolutionsbydon.devdesk.config.AuthorizationServerConfig.CLIENT_ID;
import static com.digitalsolutionsbydon.devdesk.config.AuthorizationServerConfig.CLIENT_SECRET;

// http://localhost:5000/swagger-ui.html
@Configuration
public class Swagger2Config
{
    private static final String AUTH_SERVER = "https://lambda-devdesk.herokuapp.com";
    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).select()
                //                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.digitalsolutionsbydon.devdesk")).paths(PathSelectors.any()).build().useDefaultResponseMessages(false) // Allows only my exception responses
                .ignoredParameterTypes(Pageable.class) // allows only my paging parameter list
                .apiInfo(apiEndPointsInfo()).securitySchemes(Arrays.asList(securityScheme())).securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("DevDesk Queue Build Week Project").description("DevDesk Backend developed in Java Spring").contact(new Contact("Donald Whitely", "https://www.donwhitely.com", "dswhitely1@gmail.com")).license("MIT").licenseUrl("https://github.com/DevDeskQueue-Lambda/devdesk-back-end/blob/master/LICENSE").version("1.0.0").build();
    }

    @Bean
    public SecurityConfiguration security()
    {
        return SecurityConfigurationBuilder.builder().clientId(CLIENT_ID).clientSecret(CLIENT_SECRET).scopeSeparator(" ").useBasicAuthenticationWithAccessCodeGrant(true).build();
    }

    private SecurityScheme securityScheme()
    {
        GrantType grantType = new AuthorizationCodeGrantBuilder().tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/login", "oauthtoken")).tokenRequestEndpoint(new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET)).build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth").grantTypes(Arrays.asList(grantType)).scopes(Arrays.asList(scopes())).build();
        return oauth;
    }

    private AuthorizationScope[] scopes()
    {
        AuthorizationScope[] scopes = {new AuthorizationScope("read", "for read operations"), new AuthorizationScope("write", "for write operations"), new AuthorizationScope("trust", "Access foo API")};
        return scopes;
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/foos.*"))
                .build();
    }
}
