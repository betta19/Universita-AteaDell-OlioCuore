package it.dstech.service;

import java.util.List;

import it.dstech.models.Docente;
import it.dstech.models.Esame;
import it.dstech.models.Studente;

public interface EsameI {

	Esame findByListaDocenti(List<Docente> listaDocenti);
	
	Esame findByEsame(List<Studente> listStudenti);

}
