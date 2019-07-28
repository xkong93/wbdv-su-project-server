package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.sevices.ProductService;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */
@RestController
public class ProductController {

  private static HttpURLConnection connection;
  @Autowired
  ProductService productService;


  @GetMapping("/api/product/{pid}")
  public Product findProductById(@PathVariable("pid") Long pid) {
    return productService.findProductById(pid);
  }

  /*
  https://stackoverflow.com/questions/34415144/how-to-parse-gzip-encoded-response-with-resttemplate-from-spring-web
  乱码 gzip 原因
  no need to use cors proxy server.only necessary to use in front end.
   */
  @GetMapping("/search/{words}")
  public String searchProduct(@PathVariable("words") String words) {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().build());
    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    String searchAPI = "https://stockx.com/api/browse?_search=" + words;

    HttpHeaders headers = new HttpHeaders();
    headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
    headers.set("accept-encoding", "gzip");
    headers.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
    headers.set("accept-language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7,zh-CN;q=0.6");
    headers.set("cache-control", "max-age=0");
    headers.set("dnt", "1");
    headers.set("upgrade-insecure-requests", "1");
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> res = restTemplate.exchange(searchAPI, HttpMethod.GET, entity, String.class);
    return res.getBody();
  }



  //legacy code
  @GetMapping("/search2/{words}")
  public void searchProduct2(@PathVariable("words") String words) {
    BufferedReader reader;
    String line;
    StringBuilder responseContent = new StringBuilder();
    try {
      String searchAPI = "https://stockx.com/api/browse?_search=" + words;
      URL url = new URL(searchAPI);
      connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);

      int status = connection.getResponseCode();
      System.out.println(status);
      if (status > 299) {
        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        while ((line = reader.readLine()) != null) {
          responseContent.append(line);
        }
        reader.close();
      } else {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
          responseContent.append(line);
        }
        reader.close();
      }
      System.out.println(responseContent.toString());

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      connection.disconnect();
    }


  }
}
