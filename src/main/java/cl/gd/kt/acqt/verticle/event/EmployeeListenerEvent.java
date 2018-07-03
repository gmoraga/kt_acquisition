package cl.gd.kt.acqt.verticle.event;

import cl.gd.kt.acqt.verticle.RestApiVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServerOptions;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.BridgeEvent;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeListenerEvent extends RestApiVerticle {
	

	private volatile Handler<BridgeEvent> eventHandler = event -> event.complete(true);
	
	@Override
	public void start() {
		log.info("Beginning kt-acquisitions...");
		
	    vertx.eventBus().consumer("test", (Message<JsonObject> msg) -> {
	        	log.info("LLEGO");
	        	log.info("msg: "+msg);
	      });
	    
	    TcpEventBusBridge bridge = TcpEventBusBridge.create(vertx, 
	    		new BridgeOptions()
	    		/*.addInboundPermitted(new PermittedOptions().setAddress("hello"))
	            .addInboundPermitted(new PermittedOptions().setAddress("echo"))*/
	            .addInboundPermitted(new PermittedOptions().setAddress("test"))
	            /*.addOutboundPermitted(new PermittedOptions().setAddress("echo"))
	            .addOutboundPermitted(new PermittedOptions().setAddress("ping"))*/, 
	            new NetServerOptions(), event -> eventHandler.handle(event));

	    bridge.listen(7002, res -> {
	    	log.info("kt-acquisitions: 7002: "+res.succeeded());
	    	log.info("result: "+res.result());
	    });
	}
}