package cl.gd.kt.acqt.verticle.api;

import cl.gd.kt.acqt.service.InventoryService;
import cl.gd.kt.acqt.verticle.RestApiVerticle;
import cl.gd.kt.acqt.verticle.util.ValidateInventoryApiUtil;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryApiVerticle extends RestApiVerticle {
	private static final String ID = "id";

	//routes
	private static final String API_GET_ACQUISITION_INVENTORIES_ID = "/api/acquisition/v1/inventories/:id";
	private static final String API_GET_ACQUISITION_INVENTORIES_ALL = "/api/acquisition/v1/inventories/";
	//services
	private InventoryService inventoryService;

	/**
	 * 
	 * @param router
	 * @param notifyImpl
	 */
	public InventoryApiVerticle(Router router, InventoryService inventoryService) {
		//routes
		this.loadRoute(router);

		//services
		this.inventoryService = inventoryService;
	}

	/**
	 * 
	 * @param router
	 */
	public void loadRoute(Router router) {
		//events
		router.get(API_GET_ACQUISITION_INVENTORIES_ID).handler(this::getInventoryBySeq);
		router.get(API_GET_ACQUISITION_INVENTORIES_ALL).handler(this::getAllInventory);
	}

	/**
	 *
	 * @param context
	 */
	private void getInventoryBySeq(RoutingContext context) {
		context.vertx().executeBlocking(future -> {
			try {
				final String seqStr = context.request().getParam(ID);
				ValidateInventoryApiUtil.getId(seqStr);
				final Long seq = Long.valueOf(seqStr);
				//List<AverageDefectiveMaintenanceSlots> listAverages = Arrays.asList(Json.decodeValue(context.getBodyAsString(), AverageDefectiveMaintenanceSlots[].class));
				this.inventoryService.searchInventory(seq, resultHandler(context, Json::encodePrettily, 200));
			} catch (Exception e) {
				badRequest(context, e);
			}
		}, false, resultHandler(context));
	}
	
	private void getAllInventory(RoutingContext context) {
		context.vertx().executeBlocking(future -> {
			this.inventoryService.searchAllInventory(resultHandler(context, Json::encodePrettily, 200));
		}, false, resultHandler(context));
}
	

}