package org.debugroom.wedding.external.api.impl.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.external.ExternalProperties;
import org.debugroom.wedding.external.api.AddressSearch;
import org.debugroom.wedding.external.model.Address;

@Component("addressSearch")
public class AddressSearchImpl implements AddressSearch{

	@Inject
	ExternalProperties externalProperties;

	@Override
	public Address getAddress(String zipCode) throws BusinessException{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri = null;
		try {
			uri = new URIBuilder()
							.setScheme("http")
							.setHost(externalProperties.getAddressSearchAPIHost())
							.setParameter("zipcode", zipCode)
							.build();
		} catch (URISyntaxException e) {
			throw new SystemException("addressSearch.error.0001", 
					externalProperties.getAddressSearchAPIHost(), e);
		}
		HttpGet httpGet = new HttpGet(uri);

		StringBuilder stringBuilder = new StringBuilder();
		AddressJsonResponse addressJsonResponse = null;

		HttpEntity httpEntity = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				httpEntity = response.getEntity();
				if(httpEntity != null){
					InputStream inputStream = httpEntity.getContent();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(inputStream));
					String line = null;
					while((line = bufferedReader.readLine()) != null){
						stringBuilder.append(line);
					}
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			addressJsonResponse =  mapper.readValue(stringBuilder.toString(),
					AddressJsonResponse.class);

		} catch (ClientProtocolException e) {
			new BusinessException("addressSearch.error.0002", e);
		} catch (IOException e) {
			new SystemException("addressSearch.error.0003", httpEntity.toString(), e);
		}
		
		if(addressJsonResponse.getData() == null){
			throw new BusinessException("addressSearch.error.0004", null, zipCode);
		}

		return addressJsonResponse.getData();
	}

}
