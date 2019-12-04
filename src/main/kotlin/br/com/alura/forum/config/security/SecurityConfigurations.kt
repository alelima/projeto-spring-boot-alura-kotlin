package br.com.alura.forum.config.security

import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfigurations(val autenticacaoService: AutenticacaoService,
                             val tokenService: TokenService,
                             val usuarioRepository: UsuarioRepository) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    //configura autenticação
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.let {
            it.userDetailsService(autenticacaoService).passwordEncoder(BCryptPasswordEncoder())
        }
    }

    //configura a autorização
    override fun configure(http: HttpSecurity?) {
        http?.let {
            it.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                    .antMatchers(HttpMethod.POST, "/auth").permitAll()
                    .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                    .anyRequest().authenticated()
                    .and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().addFilterBefore(AutenticacaoViaTokenFilter(tokenService, usuarioRepository),
                            UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    override fun configure(web: WebSecurity?) {
        web?.let {
            it.ignoring()
                    .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
        }
    }
}
