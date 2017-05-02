package com.learning5.security;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.learning5.model.User;
 
@RestController
public class HelloWorldRestController {
 
   // @Autowired
    //UserService userService;  //Service which will do all data retrieval/manipulation work
 
     
	@Autowired
	private TokenStore tokenStore;

	
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(value = "/test/testall", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
    	
    	System.out.println("In listAllUsers");
    	
    	System.out.println("tokenStore: " + tokenStore);
    	
    	List<User> users = new ArrayList<User>();
    	users.add(new User());
    	
    	/*
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        */
    	
    	return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
 
     
}