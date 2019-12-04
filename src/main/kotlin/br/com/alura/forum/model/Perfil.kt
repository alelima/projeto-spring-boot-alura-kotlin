package br.com.alura.forum.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Perfil : GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val nome: String? = null

    override fun getAuthority(): String {
        return nome!!
    }
}