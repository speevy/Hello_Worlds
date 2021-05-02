package net.speevy.testing.helloWorlds.greetings;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "GREETINGS")
@Entity
public class GreetingsJpa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String message;
}
