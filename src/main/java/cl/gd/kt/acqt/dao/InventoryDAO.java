package cl.gd.kt.acqt.dao;

import java.util.List;
import java.util.stream.Collectors;

import cl.gd.kt.acqt.model.employee.Employee;
import cl.gd.kt.acqt.model.invent.Inventory;
import cl.gd.kt.acqt.util.Constant;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class InventoryDAO extends JdbcRepositoryWrapper {
	
	private static final String TABLE_INVENTORY = ".acq_inventory inv ";
	private static final String FROM = "FROM ";

    public InventoryDAO (Vertx vertx, JsonObject config) {
        super(vertx, config);
    }
    
    private String selectAllInventorySql() {

    	StringBuilder query = new StringBuilder();
    	
        query.append("SELECT inv.seq, inv.serial_number, inv.description, inv.category, inv.assigned, ");
        query.append("inv.employee_id, inv.employee_first_name, inv.employee_last_name, inv.employee_dt_assignment, ");
        query.append("inv.dt_admission, inv.dt_egress, inv.enable ");
        query.append(FROM).append(Constant.DB_SCHEMA).append(TABLE_INVENTORY);
        query.append("WHERE inv.enable = true ");
        
        return query.toString();
    }
    
    private String selectInventorySql() {

    	StringBuilder query = new StringBuilder();
    	
        query.append("SELECT inv.seq, inv.serial_number, inv.description, inv.category, inv.assigned, ");
        query.append("inv.employee_id, inv.employee_first_name, inv.employee_last_name, inv.employee_dt_assignment, ");
        query.append("inv.dt_admission, inv.dt_egress, inv.enable ");
        query.append(FROM).append(Constant.DB_SCHEMA).append(TABLE_INVENTORY);
        query.append("WHERE inv.seq = ? ");
        
        return query.toString();
    }
    
    private String selectInventoryVacantSql() {
    	StringBuilder query = new StringBuilder();
    	
        query.append("SELECT inv.seq ");
        query.append(FROM).append(Constant.DB_SCHEMA).append(TABLE_INVENTORY);
        query.append("WHERE inv.assigned = false ");
        
        return query.toString();	
    }
    
    private String inventoryRegistryForEmployeeSql() {
    	StringBuilder query = new StringBuilder();
    	
        query.append("UPDATE ").append(Constant.DB_SCHEMA).append(TABLE_INVENTORY);
        query.append("SET employee_id = ?, ");
        query.append("employee_first_name = ?, ");
        query.append("employee_last_name = ?, ");
        query.append("employee_dt_assignment = now(), ");
        query.append("assigned = true ");
        query.append("WHERE seq = ? ");
        
        return query.toString();
    	
    }
    
    /**
     * 
     * @param resultHandler
     */
    public void searchAllInventory(Handler<AsyncResult<List<Inventory>>> resultHandler) {
        this.retrieveAll(selectAllInventorySql()).map(rawList -> rawList.stream().map(Inventory::new).collect(Collectors.toList())).setHandler(resultHandler);
    }
    
    /**
     * 
     * @param seq
     * @param resultHandler
     */
    public void searchInventory(Long seq, Handler<AsyncResult<List<Inventory>>> resultHandler) {
        this.retrieveMany(new JsonArray().add(seq), selectInventorySql()).map(rawList -> rawList.stream().map(Inventory::new).collect(Collectors.toList())).setHandler(resultHandler);
    }
    
    /**
     * 
     * @param resultHandler
     */
    public void searchAllInventoryVacant(Handler<AsyncResult<List<Inventory>>> resultHandler) {
        this.retrieveAll(selectInventoryVacantSql()).map(rawList -> rawList.stream().map(Inventory::new).collect(Collectors.toList())).setHandler(resultHandler);
    }
    
    /**
     * 
     * @param seqInventory
     * @param employee
     * @param resultHandler
     */
    public void updateRegistryForEmployee(Long seqInventory, Employee employee, Handler<AsyncResult<Void>> resultHandler) {
    	JsonArray params = new JsonArray();
    	params.add(employee.getId());
    	params.add(employee.getFirstName());
    	params.add(employee.getLastName());
    	params.add(seqInventory);
    	
        this.executeNoResult(params, inventoryRegistryForEmployeeSql(), resultHandler);
    }
}
