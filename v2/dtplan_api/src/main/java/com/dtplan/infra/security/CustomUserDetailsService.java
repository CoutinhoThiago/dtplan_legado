package com.dtplan.infra.security;

import com.dtplan.domain.usuario.Usuario;
import com.dtplan.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return usuario; // Retorna a entidade Usuario, que já implementa UserDetails
    }
}
