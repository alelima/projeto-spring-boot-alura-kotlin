package br.com.alura.forum.config.security

import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AutenticacaoViaTokenFilter(val tokenService: TokenService,
                                 val usuarioRepository: UsuarioRepository): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = recuperarToken(request)
        if(tokenService.isTokenValido(token)) {
            autenticarCliente(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun autenticarCliente(token: String?) {
        val idUsuario = tokenService.getIdUsuario(token)
        val usuario = usuarioRepository.findByIdOrNull(idUsuario)
        val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        return if(token.isNullOrEmpty() || !token.startsWith("Bearer ")) {
            null
        } else {
            token.substring(7, token.length)
        }
    }
}