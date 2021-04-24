package net.speevy.testing.helloWorlds;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
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
		when(repository.findById(1L)).thenReturn(Optional.of(new Greetings(1L, "Hello World")));

		when(repository.findById(2L)).thenReturn(Optional.of(new Greetings(2L, "Hi World")));

		when(repository.findById(3L)).thenReturn(Optional.empty());

		GreetingsService service = instanceService();

		final Optional<Greetings> greeting1 = service.getGreeting(1L);
		final Optional<Greetings> greeting2 = service.getGreeting(2L);
		final Optional<Greetings> greeting3 = service.getGreeting(3L);

		assertTrue(greeting1.isPresent());
		assertTrue(greeting2.isPresent());
		assertTrue(greeting3.isEmpty());

		assertEquals("Hello World", greeting1.get().getMessage());
		assertEquals("Hi World", greeting2.get().getMessage());
	}

	private GreetingsService instanceService() {
		GreetingsService service = new GreetingsServiceImpl(repository);
		return service;
	}

	@Test
	public void testGreetingsServiceUpdate() {
		GreetingsService service = instanceService();
		final Greetings greetings = new Greetings(1L, "Greetings!");
		service.update(greetings);

		ArgumentCaptor<Greetings> argumentCaptor = ArgumentCaptor.forClass(Greetings.class);
		verify(repository).update(argumentCaptor.capture());
		Greetings result = argumentCaptor.getValue();

		assertEquals(greetings, result);

	}

	@Test
	public void testGreetingsServiceAll() {
		final Greetings greetings1 = new Greetings(1L, "Hello World");
		final Greetings greetings2 = new Greetings(2L, "Hi World");

		when(repository.findAll()).thenReturn(List.of(greetings1, greetings2));

		GreetingsService service = instanceService();

		Collection<Greetings> result = service.findAll();

		assertTrue(result.contains(greetings1));
		assertTrue(result.contains(greetings2));
		assertEquals(2, result.size());
	}

	@Test
	public void testGreetingsServiceSave() {
		GreetingsService service = instanceService();
		final Greetings greetings = new Greetings(null, "Greetings!");
		service.save(greetings);

		ArgumentCaptor<Greetings> argumentCaptor = ArgumentCaptor.forClass(Greetings.class);
		verify(repository).save(argumentCaptor.capture());
		Greetings result = argumentCaptor.getValue();

		assertEquals(greetings, result);

	}

	@Test
	public void testGreetingsServiceDeleteExisting() {
		long id = 1L;
		when(repository.existsById(id)).thenReturn(true);

		GreetingsService service = instanceService();

		assertTrue(service.delete(id));

		verify(repository).deleteById(id);
	}

	@Test
	public void testGreetingsServiceDeleteNotExisting() {
		long id = 2L;
		when(repository.existsById(id)).thenReturn(false);

		GreetingsService service = instanceService();

		assertFalse(service.delete(id));

		verify(repository, times(0)).deleteById(any());
	}
}
