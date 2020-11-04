package br.com.alura.forum.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
@Configuration
@Profile("dev")
class DevSecurityConfigurations() : WebSecurityConfigurerAdapter() {


    //configura a autorização
    override fun configure(http: HttpSecurity?) {
        http?.let {
            it.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll()
                    .and().csrf().disable()
        }
    }
}
