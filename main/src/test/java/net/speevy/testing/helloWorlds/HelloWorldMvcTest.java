package net.speevy.testing.helloWorlds;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.web.servlet.MockMvc;

import net.speevy.testing.helloWorlds.greetings.*;

@WebMvcTest(includeFilters = {@Filter(type = ASSIGNABLE_TYPE, classes = {GreetingsMapperImpl.class})})
public class HelloWorldMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GreetingsService service;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		final List<Greetings> result = List.of(
				new Greetings(1L, "Hello World"),
				new Greetings(2L, "Hi World")
				);
		
		when(service.findAll()).thenReturn(result);
		
		this.mockMvc.perform(get("/api/test"))
				.andExpect(status().isOk())
				.andExpect(content().json("[{'hello':'Hello World'}, {'hello':'Hi World'}]"));
	}

	@Test
	public void greetingShouldReturnMessageFromServiceWithParam() throws Exception {
		when(service.getGreeting(1L)).thenReturn(Optional.of(new Greetings(1L, "Hello, Mock")));
		when(service.getGreeting(2L)).thenReturn(Optional.of(new Greetings(2L, "Hi, Mock")));
		when(service.getGreeting(3L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/test/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, Mock")));

		mockMvc.perform(get("/api/test/2"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hi, Mock")));

		mockMvc.perform(get("/api/test/3"))
		.andExpect(status().isNotFound());

	}

}
