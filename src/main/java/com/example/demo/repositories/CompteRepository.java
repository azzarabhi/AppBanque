package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {


	

}