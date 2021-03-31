package ar.com.pagatutti.clientapi.beans;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSent {
	private String sid;
	private String code;
	private String cel;
	private LocalDateTime expiresIn;
}
