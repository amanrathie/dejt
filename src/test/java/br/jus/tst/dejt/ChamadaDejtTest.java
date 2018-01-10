package br.jus.tst.dejt;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChamadaDejtTest {

	@Test
	public void testRestCall() throws Exception {
		String url = "https://homologacao.jt.jus.br/dejt/services/data/consultarProximoDiaUtilDisponibilizacao/12";
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build());

		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

		TestRestTemplate template = new TestRestTemplate();
		((HttpComponentsClientHttpRequestFactory) template.getRestTemplate().getRequestFactory()).setHttpClient(httpClient);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YW1hbkBwb2xpc3lzLmNvbS5icjpyb3JpejAw");

		ResponseEntity<String> exchange = template.exchange(url, HttpMethod.POST, new HttpEntity<>(headers),
				String.class);

		System.out.println(exchange.getBody());
		assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}

}
