package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.dstech.models.Esame;
import it.dstech.models.Libretto;
import it.dstech.models.Role;
import it.dstech.models.User;
import it.dstech.repository.EsameRepository;
import it.dstech.repository.LibrettoRepository;
import it.dstech.repository.RoleRepository;
import it.dstech.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private LibrettoRepository librettoRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private EsameRepository esameRepo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Libretto libretto = new Libretto();
        libretto.setStudente(user.getUsername());
        libretto.setUser(null);
        libretto.setVoto(0);
        libretto.setEsame(null);
        librettoRepository.save(libretto);
        Role userRole = roleRepository.findByRole("STUDENTE");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        return userRepository.save(user);
    }

	public boolean aggiungiEsame(User userD, Esame esame) {
		esame.setId(esame.getId());
		userD.setId(userD.getId());
		esame.setDocente(userD.getUsername());
		userD.getListaEsame().add(esame);
		Esame save = esameRepo.save(esame);
		userRepository.save(userD);
		return save != null;

	}
	
	public Optional<User> findById (int id) {
		return userRepository.findById(id);
	}
	
		
}
