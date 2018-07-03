package cl.gd.kt.acqt.dao;

import java.util.List;
import java.util.stream.Collectors;

import cl.gd.kt.acqt.model.invent.Inventory;
import cl.gd.kt.acqt.util.Constant;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class InventoryDAO extends JdbcRepositoryWrapper {

    public InventoryDAO (Vertx vertx, JsonObject config) {
        super(vertx, config);
    }
    
    private String selectAllInventorySql() {

    	StringBuilder query = new StringBuilder();
    	
        query.append("SELECT inv.seq, inv.serial_number, inv.description, inv.category, inv.assigned, ");
        query.append("inv.employee_id, inv.employee_first_name, inv.employee_last_name, inv.employee_dt_assignment, ");
        query.append("inv.dt_admission, inv.dt_egress, inv.enable ");
        query.append("FROM ").append(Constant.DB_SCHEMA).append(".acq_inventory inv ");
        query.append("WHERE inv.enable = true ");
        
        return query.toString();
    }
    
    private String selectInventorySql() {

    	StringBuilder query = new StringBuilder();
    	
        query.append("SELECT inv.seq, inv.serial_number, inv.description, inv.category, inv.assigned, ");
        query.append("inv.employee_id, inv.employee_first_name, inv.employee_last_name, inv.employee_dt_assignment, ");
        query.append("inv.dt_admission, inv.dt_egress, inv.enable ");
        query.append("FROM ").append(Constant.DB_SCHEMA).append(".acq_inventory inv ");
        query.append("WHERE inv.seq = ? ");
        
        return query.toString();
    }
    

    public void searchAllInventory(Handler<AsyncResult<List<Inventory>>> resultHandler) {

        this.retrieveAll(selectAllInventorySql()).map(rawList -> rawList.stream().map(Inventory::new).collect(Collectors.toList())).setHandler(resultHandler);
    }
    
    public void searchInventory(Long seq, Handler<AsyncResult<List<Inventory>>> resultHandler) {

        this.retrieveMany(new JsonArray().add(seq), selectInventorySql()).map(rawList -> rawList.stream().map(Inventory::new).collect(Collectors.toList())).setHandler(resultHandler);
    }
}
