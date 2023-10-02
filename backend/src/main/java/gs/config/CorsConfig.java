package gs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Define the API paths you want to allow CORS for
            .allowedOrigins("http://localhost:3000") // Specify the allowed origins (your client's URL)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the allowed HTTP methods
            .allowCredentials(true); // Allow sending credentials (e.g., cookies, authorization headers)
    }
}

