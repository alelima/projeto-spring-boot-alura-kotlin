package br.com.alura.forum.config.security

import br.com.alura.forum.model.Usuario
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${forum.jwt.expiration}")
    lateinit var expiration: String

    @Value("\${forum.jwt.secret}")
    lateinit var secret: String

    fun gerarToken(authentication: Authentication): String {
        val logado = authentication.principal as Usuario
        val dataExpiracao = Date(Date().time + expiration.toLong())
        return Jwts.builder().setIssuer("API do Forum da Alura")
                .setSubject(logado.id.toString()).setIssuedAt(Date())
                .setExpiration(dataExpiracao).signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    fun isTokenValido(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getIdUsuario(token: String?): Long {
        val body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).body
        return body.subject.toLong()
    }
}