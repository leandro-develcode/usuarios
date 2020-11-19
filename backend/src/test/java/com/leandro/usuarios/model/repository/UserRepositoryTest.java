package com.leandro.usuarios.model.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.leandro.usuarios.model.User;
import com.leandro.usuarios.model.UserRepository;

@SpringBootTest	
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
	
	@Autowired
	UserRepository repository;
	
	@Test
	public void inserir() {
		User user = new User();
		//user.setId(Long.valueOf(1));
		//user.setId(1);
		user.setName("Leandro");
		user.setImage("image1.png");
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		try {
			user.setBirthday(date.parse("04/03/1996"));
		} catch (Exception e) {
			user.setBirthday(new Date());
		}
		
		repository.save(user);
		Optional<User> usera = repository.findById(1);
				
	}
}
