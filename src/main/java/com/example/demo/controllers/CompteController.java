package com.example.demo.controllers;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.entities.Compte;
import com.example.demo.repositories.CompteRepository;

@Controller
@RequestMapping("comptes")
public class CompteController {
	//@Autowired
	private final CompteRepository compteRepository;

    public CompteController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
 
    @GetMapping("list")
    public String afficher(Model model) {
        List<Compte> comptes = compteRepository.findAll();
        model.addAttribute("comptes", comptes);
        return "afficher"; // correspond à afficher.html
    }

	
	@GetMapping("add")
	public String ajouter(Model model)
	{
		model.addAttribute("comptes",new Compte());
		return "ajouter";
	}

	
	@PostMapping("add")
	public String add(@ModelAttribute Compte comptes)
	{
		compteRepository.save(comptes);
		  return "redirect:/comptes/list";
		
		
	}

	@GetMapping("supprimer/{id}")
	public String supprimer(@PathVariable("id") Long id) {
 		List<Compte>comptes=(List<Compte>) compteRepository.findAll();
		for(Compte c : comptes) {
			if(c.getId()==id) {
				compteRepository.delete(c);
				break;
			}
		}
		
		return "redirect:../list";
		
	}
	@GetMapping("modifier/{id}")
	public String afficherFormulaireModification(@PathVariable("id") Long id, Model model) {
	    Compte compte = compteRepository.findById(id).orElseThrow();
	    model.addAttribute("compte", compte);	
	    return "details"; // correspond à modifier.html
	}

	@PostMapping("modifier")
	public String modifierCompte(@ModelAttribute("compte") Compte compteModifie) {
	    Compte compteExistant = compteRepository.findById(compteModifie.getId())
	        .orElseThrow(() -> new IllegalArgumentException("Compte introuvable : " + compteModifie.getId()));

	    // Mise à jour des champs que tu veux permettre
	    compteExistant.setTitulaire(compteModifie.getTitulaire());
	    compteExistant.setSolde(compteModifie.getSolde());

	    compteRepository.save(compteExistant); // là on sauvegarde un compte qu’on contrôle
	    return "redirect:/comptes/list";
	}



	
   }