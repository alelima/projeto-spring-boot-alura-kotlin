package br.com.alura.forum.controller

import br.com.alura.forum.config.security.TokenService
import br.com.alura.forum.controller.dto.TokenDto
import br.com.alura.forum.controller.form.LoginForm
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AutenticacaoController(val authenticationManager: AuthenticationManager,
                             val tokenService: TokenService) {

    @PostMapping
    fun autenticar(@RequestBody @Valid loginForm: LoginForm): ResponseEntity<TokenDto> {
        val dadosLogin: UsernamePasswordAuthenticationToken =  loginForm.converter()
        try {
            val authentication = authenticationManager.authenticate(dadosLogin)
            val token = tokenService.gerarToken(authentication)
            return ResponseEntity.ok(TokenDto(token, "Bearer"))
        } catch (e: AuthenticationException) {
            return ResponseEntity.badRequest().build<TokenDto>()
        }
    }
}