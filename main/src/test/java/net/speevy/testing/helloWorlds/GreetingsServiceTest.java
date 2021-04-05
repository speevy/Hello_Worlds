package net.speevy.testing.helloWorlds;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import net.speevy.testing.helloWorlds.greetings.*;

@ExtendWith(MockitoExtension.class)
public class GreetingsServiceTest {

	@Mock
	private GreetingsRepository repository;

	@Test
	public void testGreetingsServiceGet() {
		when(repository.findById(1L))
		.thenReturn(Optional.of(new GreetingsEntity(1L, "Hello World")));

		when(repository.findById(2L))
		.thenReturn(Optional.of(new GreetingsEntity(2L, "Hi World")));
		
		when(repository.findById(3L))
		.thenReturn(Optional.empty());

		GreetingsService service = instanceService();
		
		final Optional<GreetingsEntity> greeting1 = service.getGreeting(1L);
		final Optional<GreetingsEntity> greeting2 = service.getGreeting(2L);
		final Optional<GreetingsEntity> greeting3 = service.getGreeting(3L);
		
		assertTrue(greeting1.isPresent());
		assertTrue(greeting2.isPresent());
		assertTrue(greeting3.isEmpty());

		assertEquals("Hello World", greeting1.get().getMessage());
		assertEquals("Hi World", greeting2.get().getMessage());
	}

	private GreetingsService instanceService() {
		GreetingsService service = new GreetingsServiceImpl(
				repository
				);
		return service;
	}
	
	@Test
	public void testGreetingsServiceUpdate() {
		GreetingsService service = instanceService();
		final GreetingsEntity greetings = new GreetingsEntity(1L, "Greetings!");
		service.update(greetings);
		
		ArgumentCaptor<GreetingsEntity> argumentCaptor = ArgumentCaptor.forClass(GreetingsEntity.class);
		verify(repository).update(argumentCaptor.capture());
		GreetingsEntity result = argumentCaptor.getValue();
		
		assertEquals(greetings, result);

	}

	@Test
	public void testGreetingsServiceAll() {
		final GreetingsEntity greetings1 = new GreetingsEntity(1L, "Hello World");
		final GreetingsEntity greetings2 = new GreetingsEntity(2L, "Hi World");
		
		when(repository.findAll())
		.thenReturn(List.of(
				greetings1,
				greetings2
			));

		GreetingsService service = instanceService();

		Collection<GreetingsEntity> result = service.findAll();
		
		assertTrue(result.contains(greetings1));
		assertTrue(result.contains(greetings2));
		assertEquals(2, result.size());
	}

	@Test
	public void testGreetingsServiceSave() {
		GreetingsService service = instanceService();
		final GreetingsEntity greetings = new GreetingsEntity(null, "Greetings!");
		service.save(greetings);
		
		ArgumentCaptor<GreetingsEntity> argumentCaptor = ArgumentCaptor.forClass(GreetingsEntity.class);
		verify(repository).save(argumentCaptor.capture());
		GreetingsEntity result = argumentCaptor.getValue();
		
		assertEquals(greetings, result);

	}

}
