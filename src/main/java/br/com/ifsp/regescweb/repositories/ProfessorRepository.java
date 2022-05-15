package br.com.ifsp.regescweb.repositories;

import br.com.ifsp.regescweb.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> { //entidade para criação do repositório, tipo da chave primária do repositório
}
