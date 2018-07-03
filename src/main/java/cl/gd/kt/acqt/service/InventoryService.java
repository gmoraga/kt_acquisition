package cl.gd.kt.acqt.service;

import java.util.List;

import cl.gd.kt.acqt.dao.InventoryDAO;
import cl.gd.kt.acqt.model.invent.Inventory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class InventoryService {

	private final InventoryDAO inventoryDAO;
	
	public InventoryService(Vertx vertx, JsonObject config) {
	    this.inventoryDAO = new InventoryDAO(vertx, config);
	}
	
	
	public void searchAllInventory (Handler<AsyncResult<List<Inventory>>> resultHandler) {
		this.inventoryDAO.searchAllInventory(resultHandler);
	}
	
	public void searchInventory (Long seq, Handler<AsyncResult<List<Inventory>>> resultHandler) {
		this.inventoryDAO.searchInventory(seq, resultHandler);
	}
}