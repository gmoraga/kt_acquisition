package cl.gd.kt.acqt.service;

import java.util.List;

import cl.gd.kt.acqt.client.RestClient;
import cl.gd.kt.acqt.dao.InventoryDAO;
import cl.gd.kt.acqt.model.employee.Employee;
import cl.gd.kt.acqt.model.invent.Inventory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryService {

	private static final String TYPE_EVENT_CREATE = "CREATE";
	private static final String TYPE_EVENT_UPDATE = "UPDATE";
	private static final String TYPE_EVENT_DELETE = "DELETE";
	
	private static final String MESSAGE_ID_BUSINESS = "idBusiness";
	private static final String MESSAGE_BUSINESS_DOMAIN = "businessDomain";
	private static final String MESSAGE_TYPE_EVENT = "typeEvent";
	private static final String MESSAGE_ID = "messageId";
	
	private final InventoryDAO inventoryDAO;
	private final RestClient restClient;
	
	public InventoryService(Vertx vertx, JsonObject config) {
	    this.inventoryDAO = new InventoryDAO(vertx, config);
	    this.restClient = new RestClient(vertx);
	}
	
	/**
	 * 
	 * @param resultHandler
	 */
	public void searchAllInventory (Handler<AsyncResult<List<Inventory>>> resultHandler) {
		this.inventoryDAO.searchAllInventory(resultHandler);
	}
	
	/**
	 * 
	 * @param seq
	 * @param resultHandler
	 */
	public void searchInventory (Long seq, Handler<AsyncResult<List<Inventory>>> resultHandler) {
		this.inventoryDAO.searchInventory(seq, resultHandler);
	}
	
	/**
	 * 
	 * @param jsObj
	 */
	public void logicEventEmployee(JsonObject jsObj) {
		if (TYPE_EVENT_CREATE.equals(jsObj.getString(MESSAGE_TYPE_EVENT))) {
			this.restClient.getDataEmployee(jsObj.getLong(MESSAGE_ID_BUSINESS)).setHandler(handlerEmployee -> {
				if (handlerEmployee.succeeded()) {
					this.registerComputerForEmployee(handlerEmployee.result());
				} else {
					log.error("Employee not registry.");
				}
			});
		} else {
			log.info("Other event: "+jsObj.getString(MESSAGE_TYPE_EVENT));
		}
		
	}
	
	/**
	 * 
	 * @param employee
	 */
	private void registerComputerForEmployee(Employee employee) {
		
		this.inventoryDAO.searchAllInventoryVacant(resultVacantHandler -> {
			if (resultVacantHandler.succeeded() && null != resultVacantHandler.result() && 
					!resultVacantHandler.result().isEmpty()) {
				this.inventoryDAO.updateRegistryForEmployee(
						resultVacantHandler.result().get(0).getSeq(), employee, resultUpdateHandler -> {
					if (resultUpdateHandler.succeeded()) {
						log.info("Inventory registry OK.");
					} else {
						log.info("Problem inventory registry");
					}
				});
			} else {
				log.error("No Inventory available.");
			}
		});
		
	}
}