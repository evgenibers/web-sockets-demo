package net.evgenibers.wsd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Echo output message DTO.
 *
 * @author Evgeni Bokhanov
 * @see EchoInDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EchoOutDto {
	private String text;
}
