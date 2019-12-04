package br.com.alura.forum.model

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class Usuario : UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nome: String? = null
    var email: String? = null
    var senha: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var perfis: List<Perfil> = mutableListOf<Perfil>()

    override fun getAuthorities(): List<Perfil> {
        return this.perfis
    }

    override fun isEnabled(): Boolean {
        return true;
    }

    override fun getUsername(): String {
        return this.email!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true;
    }

    override fun getPassword(): String {
        return this.senha!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
        return true;
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (id == null) 0 else id!!.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as Usuario?
        if (id == null) {
            if (other!!.id != null)
                return false
        } else if (id != other!!.id)
            return false
        return true
    }

}
