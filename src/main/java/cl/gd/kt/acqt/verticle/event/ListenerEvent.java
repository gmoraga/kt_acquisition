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
public class ListenerEvent extends RestApiVerticle {
	
//	@Override
//	public void start() {
//		
//		log.info("Beginning kt-acquisitions...");
//		//vertx.eventBus().publish("",  new JsonObject().put("STATUS", "Beginning"));
//
//		 NetClient client = vertx.createNetClient();
//		
//	    vertx.eventBus().consumer("test", (Message<JsonObject> msg) -> {
//	    	log.info("msg:"+msg);
//	        client.close();
//	      });
//		
//		
//	    TcpEventBusBridge bridge = TcpEventBusBridge.create(
//	            vertx,
//	            new BridgeOptions()
//	                .addInboundPermitted(new PermittedOptions().setAddress("in"))
//	                .addOutboundPermitted(new PermittedOptions().setAddress("out")));
//
//	        bridge.listen(7002, res -> {
//	          if (res.succeeded()) {
//	            // succeed...
//	        	  log.info("succeed:"+res.result());
//	          } else {
//	            // fail...
//	        	  log.info("fail");
//	          }
//	        });
//		
//	}
	private volatile Handler<BridgeEvent> eventHandler = event -> event.complete(true);
	
	@Override
	public void start() {
		log.info("Beginning kt-acquisitions...");
		
	    vertx.eventBus().consumer("hello", (Message<JsonObject> msg) -> msg.reply(new JsonObject().put("value", "Hello " + msg.body().getString("value"))));

	    vertx.eventBus().consumer("echo", (Message<JsonObject> msg) -> msg.reply(msg.body()));

	    vertx.setPeriodic(1000, __ -> vertx.eventBus().send("ping", new JsonObject().put("value", "hi")));
	    
	    vertx.eventBus().consumer("test", (Message<JsonObject> msg) -> {
	        	log.info("LLEGO");
	      });

		
	    TcpEventBusBridge bridge = TcpEventBusBridge.create(
	            vertx,
	            new BridgeOptions()
	                    .addInboundPermitted(new PermittedOptions().setAddress("hello"))
	                    .addInboundPermitted(new PermittedOptions().setAddress("echo"))
	                    .addInboundPermitted(new PermittedOptions().setAddress("test"))
	                    .addOutboundPermitted(new PermittedOptions().setAddress("echo"))
	                    .addOutboundPermitted(new PermittedOptions().setAddress("ping")), 
	                    new NetServerOptions(), event -> eventHandler.handle(event));

	    bridge.listen(7002, res -> {
	    	log.info("kt-acquisitions: 7002: "+res.succeeded());
	    	log.info("result: "+res.result());
	    });
	}
}