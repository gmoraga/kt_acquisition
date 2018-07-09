package cl.gd.kt.acqt.client;

import cl.gd.kt.acqt.model.employee.Employee;
import cl.gd.kt.acqt.util.AppEnum;
import cl.gd.kt.acqt.util.SystemUtil;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestClient {
	
	private static final String CONTENT_TYPE = "content-type";
	private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
	private static final long TIMEOUT = 20000L;
	private static final String EXCEPTION = "Exception: ";
	private static final String ID_EMPLOYEE =  "{idEmployee}";
	private WebClient webClient;

	/**
	 * 
	 * @param vertx
	 */
	public RestClient(Vertx vertx) {
		WebClientOptions options = new WebClientOptions();
		options.setTryUseCompression(true);
		options.setKeepAlive(false);

		this.webClient = WebClient.create(vertx, options);
	}
	
	/**
	 * 
	 * @param idEmployee
	 * @return
	 */
	public Future<Employee> getDataEmployee(Long idEmployee) {
		Future<Employee> future = Future.future();
		if (null != idEmployee) {
			try {
				String uri = getUrl(AppEnum.API_URL_GET_EMPLOYEE_ID.name(), idEmployee);
				HttpRequest<Buffer> request = this.webClient.getAbs(uri).timeout(TIMEOUT);
				request.putHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
				request.sendJson(idEmployee, ar -> {
					if (ar.succeeded() && 200 == ar.result().statusCode()) {
						future.complete(Json.decodeValue(ar.result().bodyAsString(), Employee.class));
					} else {
						log.error("Error getDataEmployee : " + ar.cause());
						future.complete(new Employee());
					}
				});
			} catch (Exception e) {
				log.error(EXCEPTION+e);
				future.complete(new Employee());
			}
		} else {
			future.complete(new Employee());
		}
		return future;
	}
	
	/**
	 * 
	 * @param keyUrl
	 * @param idEmployee
	 * @return
	 */
	private String getUrl(String keyUrl, Long idEmployee) {
		return SystemUtil.getEnvironmentStrValue(keyUrl).replace(ID_EMPLOYEE, String.valueOf(idEmployee));
	}

}
