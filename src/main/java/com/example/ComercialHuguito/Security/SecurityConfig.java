package com.example.ComercialHuguito.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
@Configuration // Declara que esta clase contiene configuración de Spring (es un Bean)
@EnableWebSecurity // Habilita la seguridad web de Spring Security en la aplicación
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    // Inyecta el servicio que carga los detalles del usuario desde la base de datos

    @Autowired
    private AuthenticationSuccessHandler customSuccessHandler;
    // Inyecta un manejador personalizado que se ejecuta después del login exitoso,
    // por ejemplo, redirige a diferentes rutas según el rol

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura la autorización para diferentes rutas
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso sin autenticación a archivos estáticos como CSS, JS, imágenes, etc.
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**", "/webjars/**", "/uploads/**").permitAll()

                        // Permite acceso público a ciertas páginas como inicio, contacto, login, registro, logout
                        .requestMatchers("/", "/contacto", "/register", "/login", "/logout", "/servicios", "/nosotros").permitAll()

                        // Restringe acceso a rutas que requieren rol "cliente"
                        .requestMatchers("/cliente/**").hasRole("cliente")

                        // Restringe acceso a rutas que requieren rol "empleado"
                        .requestMatchers("/empleados/**").hasRole("empleado")

                        // Restringe acceso a rutas que requieren rol "administrador"
                        .requestMatchers("/administrador/**").hasRole("administrador")

                        // Cualquier otra ruta necesita que el usuario esté autenticado
                        .anyRequest().authenticated()
                )
                // Configura el formulario de login
                .formLogin(form -> form
                        .loginPage("/login") // Especifica la URL personalizada del formulario de login
                        .usernameParameter("email") // Campo del formulario que se usará como nombre de usuario
                        .passwordParameter("password") // Campo del formulario para la contraseña
                        .successHandler(customSuccessHandler) // Handler personalizado para redirigir tras login exitoso
                        .permitAll() // Permite a todos los usuarios acceder al login
                )
                // Configura el logout
                .logout(logout ->
                        logout.logoutSuccessUrl("/").permitAll() // Redirige al inicio después de cerrar sesión
                );

        return http.build(); // Construye y retorna el SecurityFilterChain
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean que proporciona un codificador de contraseñas seguro usando BCrypt
        // Se utiliza tanto para guardar como para verificar contraseñas codificadas
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Bean que expone el AuthenticationManager para poder usarlo en otras partes del proyecto (ej: login manual)
        return config.getAuthenticationManager();
    }
}
