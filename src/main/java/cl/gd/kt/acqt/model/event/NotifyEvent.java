package cl.gd.kt.acqt.model.event;

import lombok.Data;

@Data
public class NotifyEvent {
	
	private Long idBusiness;
	private String businessDomain;
	private String typeEvent;
	private String messageId;
	
}
