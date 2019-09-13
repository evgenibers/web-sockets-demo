package net.evgenibers.wsd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Echo input message DTO.
 *
 * @author Evgeni Bokhanov
 * @see EchoOutDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EchoInDto {
	@NotNull
	@Size(min = 1, max = 50)
	private String text;
}
