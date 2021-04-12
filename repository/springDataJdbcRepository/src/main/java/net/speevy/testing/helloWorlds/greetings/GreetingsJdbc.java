package net.speevy.testing.helloWorlds.greetings;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("GREETINGS")
public class GreetingsJdbc {

	@Id
	private Long id;
	private String message;
}
