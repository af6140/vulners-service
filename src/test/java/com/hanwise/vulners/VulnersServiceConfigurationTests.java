/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hanwise.vulners;

import java.util.*;

import com.hanwise.vulners.entity.CVEOSPackageId;
import com.hanwise.vulners.service.JSONService;
import com.hanwise.vulners.util.CVESourceSummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Basic integration tests for service demo application.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VulnersServiceConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@TestConfiguration
public class VulnersServiceConfigurationTests {

	private static Logger logger = LoggerFactory.getLogger(VulnersServiceConfigurationTests.class);
	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JSONService jsonService;

	@Before
    public void setHeader(){
        testRestTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Accept", "application/json");
                    return execution.execute(request, body);
                }));
    }

	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/hello-world", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.mgt + "/info", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToCVEListEndpoint() throws Exception {
		Map<String,String> urlVariables = new HashMap<String,String>();
		urlVariables.put("name", "kexec-tools");
		urlVariables.put("version", "2.0.5");
		String uri= "http://localhost:" + this.port + "/cve-list?name={name}&version={version}";
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = this.testRestTemplate.getForEntity(
				uri, List.class, urlVariables);
		logger.debug(this.jsonService.writeObjectAsString(entity.getBody()));
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

    @Test
    public void shouldReturn204WhenSendingRequestToCVEListEndpointFoundNothing() throws Exception {
        Map<String,String> urlVariables = new HashMap<String,String>();
        urlVariables.put("name", "dummy");
        urlVariables.put("version", "2.0.5");
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/cve-list?name={name}&version={version}", List.class, urlVariables);
		logger.debug(this.jsonService.writeObjectAsString(entity));
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

	@Test
	public void shouldReturn200WhenSendingPostRequestToCVEListEndpoint() throws Exception {
		String uri= "http://localhost:" + this.port + "/cve-list";

		List<CVEOSPackageId> pkgIds = new LinkedList<>();

		CVEOSPackageId id1= new CVEOSPackageId();
		id1.setOsType("CentOS"); id1.setOsVersion("7"); id1.setArch("x86_64"); id1.setPackageName("kexec-tools"); id1.setPackageVersion("1.0.0");
		CVEOSPackageId id2= new CVEOSPackageId();
		id2.setOsType("CentOS"); id2.setOsVersion("7"); id2.setArch("x86_64"); id2.setPackageName("openssh"); id2.setPackageVersion("6.6.1p1-23.el7_2");

		pkgIds.add(id1); pkgIds.add(id2);
		String requestJSON= this.jsonService.writeObjectAsString(pkgIds);
		logger.debug("Request data: {}", requestJSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.APPLICATION_JSON );
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				requestJSON, headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = this.testRestTemplate.postForEntity(uri,requestEntity, List.class);
		logger.debug(this.jsonService.writeObjectAsString(entity.getBody()));
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
    //[{"osType":"CentOS","osVersion":"7","packageVersion":"1.0.0","packageName":"kexec-tools","arch":"x86_64"}]
	@Test
	public void shouldReturn200WhenSendingInvalidPostRequestToCVEListEndpoint() throws Exception {
		String uri= "http://localhost:" + this.port + "/cve-list";

		String requestJSON= "[{\"osType\":\"CentOS\",\"osVersion\":\"7\",\"packageVersion\":\"1.0.0\",\"packageName1\":\"kexec-tools\",\"arch\":\"x86_64\"}]";
		logger.debug("Request data: {}", requestJSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.APPLICATION_JSON );
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				requestJSON, headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = this.testRestTemplate.postForEntity(uri,requestEntity, List.class);

		logger.debug(this.jsonService.writeObjectAsString(entity.getBody()));
		then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void shouldReturn200WhenPostRequestToCVEListVersionCompareEndpoint() throws Exception {
		String uri= "http://localhost:" + this.port + "/cve-list";

		String requestJSON= "[{\"osType\":\"CentOS\",\"osVersion\":\"7\",\"packageVersion\":\"3.10.0-693.11.6\",\"packageName\":\"kernel\",\"arch\":\"x86_64\"}]";
		logger.debug("Request data: {}", requestJSON);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.APPLICATION_JSON );
		HttpEntity<String> requestEntity = new HttpEntity<String>(
				requestJSON, headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = this.testRestTemplate.postForEntity(uri,requestEntity, List.class);

		logger.debug(this.jsonService.writeObjectAsString(entity.getBody()));
		then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void shouldReturn200WhenRequestDataInfoEndPoint() throws  Exception{
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
				"http://localhost:"+this.port+"/cve-data", String.class
		);
		logger.debug("Entity: "+entity.toString());
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

}
