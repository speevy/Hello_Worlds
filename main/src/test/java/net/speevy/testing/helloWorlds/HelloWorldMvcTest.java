package net.speevy.testing.helloWorlds;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.util.*;

import org.json.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import net.speevy.testing.helloWorlds.greetings.*;

@WebMvcTest(includeFilters = {@Filter(type = ASSIGNABLE_TYPE, classes = {GreetingsMapperImpl.class})})
public class HelloWorldMvcTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

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


	@Test
	public void greetingsUpdate() throws Exception {
		mockServiceUpdate(1L);
		mockServiceUpdate(2L);
		mockServiceUpdateNotFound(3L);
		
		when(service.getGreeting(2L)).thenReturn(Optional.of(new Greetings(2L, "Hi, Mock")));
		when(service.getGreeting(3L)).thenReturn(Optional.empty());

		mockMvc.perform(putJson("/api/test/1", "Hello Mars!"))
			.andExpect(status().isOk());

		mockMvc.perform(putJson("/api/test/2", "Hi Mars!"))
			.andExpect(status().isOk());

		mockMvc.perform(putJson("/api/test/3", "Hello Unknown!"))
			.andExpect(status().isNotFound());
		
		ArgumentCaptor<Greetings> argumentCaptor = ArgumentCaptor.forClass(Greetings.class);
		verify(service, times (3)).save(argumentCaptor.capture());
		List<Greetings> captured = argumentCaptor.getAllValues();

		assertEquals(3, captured.size());
		assertEquals(new Greetings(1L, "Hello Mars!"), captured.get(0));
		assertEquals(new Greetings(2L, "Hi Mars!"), captured.get(1));
		assertEquals(new Greetings(3L, "Hello Unknown!"), captured.get(2));
	}

	private MockHttpServletRequestBuilder putJson(String uri, String message) throws JSONException {
		JSONObject content = new JSONObject();
		content.put("hello", message);
		return put(uri)
				.contentType(APPLICATION_JSON_UTF8)
		        .content(content.toString());
	}

	private void mockServiceUpdate(final long id) {
		when(service.save(argThat(generateGreetingsMatcherById(id))))
		.thenAnswer( i -> Optional.of ((Greetings)(i.getArguments()[0])));
	}

	private ArgumentMatcher<Greetings> generateGreetingsMatcherById(final long id) {
		final ArgumentMatcher<Greetings> matcher = g -> g != null && Long.valueOf(id).equals(g.getId());
		return matcher;
	}

	private void mockServiceUpdateNotFound(final long id) {
		when(service.save(argThat(generateGreetingsMatcherById(id))))
		.thenReturn( Optional.empty());
	}

}
