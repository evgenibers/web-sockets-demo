package net.evgenibers.wsd.domain.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error message output DTO.
 *
 * @author Evgeni Bokhanov.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageOutDto {
	private String message;
}
