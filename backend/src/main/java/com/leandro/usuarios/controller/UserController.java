package com.leandro.usuarios.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leandro.usuarios.model.User;
import com.leandro.usuarios.model.UserRepository;
import com.leandro.usuarios.storage.StorageService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	private final StorageService storageService;

	@Autowired
	public UserController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/uploads/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@GetMapping("/")
	public String hello() {
		return "Welocome to the api!";
	}
	
	@GetMapping("/list")
	public ResponseEntity list() {
		ListJson json = new ListJson();
		
		json.itens = repository.findAll();
		
		json.itens.forEach(user -> {
			user.formatDate();
		});
		
		return new ResponseEntity(json, HttpStatus.OK);
		
	}
	
	@GetMapping("/item")
	public ResponseEntity item(@RequestParam("id") String id) {
		
		ItemJson json = new ItemJson();
		
		Optional<User> user = repository.findById(Integer.parseInt(id));
		
		if(user.isPresent()) {
			json.user = user.get();
			json.user.formatDate();
		} else {
			json.status = false;
			json.message = "Usuário não encontrado!";
		}
		
		return new ResponseEntity(json, HttpStatus.OK);
		
	}
	
	@GetMapping("/delete")
	public ResponseEntity delete(@RequestParam("id") String id) {
		SaveJson json = new SaveJson();
		
		Optional<User> user = repository.findById(Integer.parseInt(id));
		
		if(user.isPresent()) {
			repository.delete(user.get());
			json.message = "Usuário excluído!";
		} else {
			json.status = false;
			json.message = "Usuário não encontrado!";
		}		
		
		return new ResponseEntity(json, HttpStatus.OK);
	}
	
	@PostMapping("/insert")
	public ResponseEntity insert(@RequestPart("name") String name,
								 @RequestPart("birthday") String birthday,
                                 @RequestPart("image") MultipartFile image
								) {
		
		SaveJson json = new SaveJson();
		
		if(name.isEmpty() || birthday.isEmpty() || image.isEmpty()) {
			json.status = false;
			json.message = "Preencha todos os campos!";
		} else {
			User user = new User();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			user.setName(name);
			
			try {
				user.setBirthday(dateFormat.parse(birthday));
			} catch (Exception e) {
				user.setBirthday(new Date());
			}
			
			storageService.store(image);
			user.setImage(image.getOriginalFilename());
			
			repository.save(user);
			
			json.message = "Usuário inserido!";
		}		
		
		return new ResponseEntity(json, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity update(@RequestPart("id") String id,
								 @RequestPart("name") String name,
								 @RequestPart("birthday") String birthday,
								 @RequestPart("old_image") String oldImage,
                                 @RequestPart(value="image", required = false) MultipartFile image
								) {
		
		SaveJson json = new SaveJson();
		
		if(id.isEmpty() || name.isEmpty() || birthday.isEmpty() || oldImage.isEmpty()) {
			json.status = false;
			json.message = "Preencha todos os campos!";
		} else {
			User user = new User();
			
			user.setId(Integer.parseInt(id));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			user.setName(name);
			
			try {
				user.setBirthday(dateFormat.parse(birthday));
			} catch (Exception e) {
				user.setBirthday(new Date());
			}
			
			if(!image.isEmpty()) {
				storageService.store(image);
				user.setImage(image.getOriginalFilename());
			} else {
				user.setImage(oldImage);
			}		
			
			
			repository.save(user);
			
			json.message = "Usuário editado!";
		}		
		
		return new ResponseEntity(json, HttpStatus.OK);
	}

}

class ListJson{
	public boolean status = true;
	public List<User> itens;
}

class ItemJson{
	public boolean status = true;
	public String message = "";
	public User user;
}

class SaveJson{
	public boolean status = true;
	public String message = "";
}