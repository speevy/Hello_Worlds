package net.speevy.testing.helloWorlds.greetings;

import org.springframework.data.annotation.Id;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greetings {

	@Id
	private Long id;
	private String hello;
}
