package net.speevy.testing.helloWorlds.greetings;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingsEntity {

	private Long id;
	private String message;
}
