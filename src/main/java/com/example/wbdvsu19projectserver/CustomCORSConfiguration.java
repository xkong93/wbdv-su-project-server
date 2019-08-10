package com.example.wbdvsu19projectserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS
 *
 * //https://www.wencst.com/archives/1635
 */
@Configuration
public class CustomCORSConfiguration {
  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("https://calm-taiga-99221.herokuapp.com");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addExposedHeader("Content-Type");
    corsConfiguration.addExposedHeader("accept");
    corsConfiguration.addExposedHeader("Origin");
    corsConfiguration.addExposedHeader("Access-Control-Request-Method");
    corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
    corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
//    corsConfiguration.addExposedHeader("Set-Cookie");

    corsConfiguration.setAllowCredentials(true); //kep point!! required
    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return corsConfiguration;
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildConfig());
    return new CorsFilter(source);
  }
}