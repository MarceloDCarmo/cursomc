package com.mdcarmo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdcarmo.cursomc.domain.Cidade;
import com.mdcarmo.cursomc.domain.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
}
