package br.com.alura.forum.config.security

import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService(val usuarioRepository: UsuarioRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val usuario = usuarioRepository.findByEmail(it)
            if (usuario != null) {
                return usuario!!
            }
        }
        throw UsernameNotFoundException("Dados Invalidos")
    }
}