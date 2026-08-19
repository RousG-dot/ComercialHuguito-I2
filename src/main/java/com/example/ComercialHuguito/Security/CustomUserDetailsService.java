package com.example.ComercialHuguito.Security;

import com.example.ComercialHuguito.Models.AppUser;
import com.example.ComercialHuguito.Models.Empleados;
import com.example.ComercialHuguito.Repository.AppUserRepository;
import com.example.ComercialHuguito.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser user = userRepository.findByEmail(email);
        if (user != null) {
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole()) // administrador
                    .build();
        }

        Empleados empleado = empleadoRepository.findByCorreo(email);
        if (empleado != null) {
            // Listado de cargos válidos para ingresar al sistema
            List<String> cargosValidos = List.of("almacenero", "analista", "repartidor");

            String cargo = empleado.getCargo().toLowerCase();

            if (cargosValidos.contains(cargo)) {
                return User.builder()
                        .username(empleado.getCorreo())
                        .password(empleado.getContrasena()) // debe estar encriptada
                        .roles("empleado") // se mapea como un solo rol para Spring Security
                        .build();
            } else {
                throw new UsernameNotFoundException("El cargo del empleado no tiene permisos de acceso.");
            }
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}

