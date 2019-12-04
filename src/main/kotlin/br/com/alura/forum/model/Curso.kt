package br.com.alura.forum.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Curso(var nome: String?, var categoria: String?) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

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
        val other = obj as Curso?
        if (id == null) {
            if (other!!.id != null)
                return false
        } else if (id != other!!.id)
            return false
        return true
    }

}
