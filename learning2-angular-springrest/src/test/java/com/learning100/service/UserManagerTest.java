package com.learning100.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
 
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
(
  {
   "classpath:dispatcherangular-servlet.xml"
  }
)
@WebAppConfiguration
public class UserManagerTest {

	@Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //mockMvc = MockMvcBuilders.standaloneSetup(new MediaListController()).build();
    }
	
    @Test
    public void validate_get_address() throws Exception {

        //mockMvc.perform(get("/users/rest/getallusers"));
        
        mockMvc.perform(get("/users/rest/getallusers")
        	       .header("host", "localhost"))
        	       .andExpect(status().isOk());
        
        /*
                .andExpect(status().isOk());
        
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.street").value("12345 Horton Ave"));
         */
    }
}