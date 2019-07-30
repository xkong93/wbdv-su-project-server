package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.models.User;
import com.example.wbdvsu19projectserver.sevices.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


/**
 * @author Xuan Kong
 * @Date 2019-07-23.
 */
@RestController
public class ProductController {

  @Autowired
  ProductService productService;


  @GetMapping("/api/product/{pid}")
  public Product findProductById(@PathVariable("pid") Integer pid) {
    return productService.findProductById(pid);
  }

  /*
  https://stackoverflow.com/questions/34415144/how-to-parse-gzip-encoded-response-with-resttemplate-from-spring-web
  gzip encoding issue
  No need to use cors proxy server in back end. Use it only in front end.
   */
  @PostMapping("/apiv2/product/{urlKey}/user/{uid}")
  public List<User> createProduct(@PathVariable("urlKey") String urlKey, @PathVariable("uid") Integer uid) {
    String productUrl = "https://stockx.com/api/products/" + urlKey + "?includes=market,360&currency=USD";
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().build());
    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    HttpHeaders headers = new HttpHeaders();
    headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
    headers.set("accept-encoding", "gzip");
    headers.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
    headers.set("accept-language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7,zh-CN;q=0.6");
    headers.set("cache-control", "max-age=0");
    headers.set("dnt", "1");
    headers.set("upgrade-insecure-requests", "1");
    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<String> res = restTemplate.exchange(productUrl, HttpMethod.GET, entity, String.class);
    String rawJson = res.getBody();
    //convert raw json data into Product

    Product newProduct = rawJsonToProduct(rawJson);


    Product p = productService.createProduct(newProduct);
    Integer pid = p.getId();
      try {
        productService.addUserToProduct(uid,pid);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    return productService.getAllUsersFromProductById(pid);

  }


  //https://www.journaldev.com/2324/jackson-json-java-parser-api-example-tutorial#jackson-json-8211-read-specific-json-key
  private Product rawJsonToProduct(String rawJson) {
    ObjectMapper mapper = new ObjectMapper();
    Product newProduct = new Product();
    try {
      //map json to string
      JsonNode rootNode = mapper.readTree(rawJson);
      JsonNode uuidNode = rootNode.path("Product").path("uuid");
      JsonNode brandNode = rootNode.path("Product").path("brand");
      JsonNode urlKeyNode = rootNode.path("Product").path("urlKey");
      JsonNode titleNode = rootNode.path("Product").path("title");
      JsonNode descriptionNode = rootNode.path("Product").path("description");
      JsonNode marketPriceNode = rootNode.path("Product").path("market").path("averageDeadstockPrice");
      JsonNode retailPriceNode = rootNode.path("Product").path("retailPrice");
      JsonNode imageUrlNode = rootNode.path("Product").path("media").path("imageUrl");
      JsonNode styleCodeNode = rootNode.path("Product").path("styleId");
      JsonNode colorwayNode = rootNode.path("Product").path("colorway");
      JsonNode releaseDateNode = rootNode.path("Product").path("releaseDate");
      String uuid = uuidNode.asText();
      String brand = brandNode.asText();
      String title = titleNode.asText();
      String urlKey = urlKeyNode.asText();
      String description = descriptionNode.asText();
      Integer marketPrice = marketPriceNode.asInt();
      Integer retailPrice = retailPriceNode.asInt();
      String imageUrl = imageUrlNode.asText();
      String styleCode = styleCodeNode.asText();
      String colorway = colorwayNode.asText();
      String releaseDate = releaseDateNode.asText();

      //add traits to the new product
      newProduct.setUiud(uuid);
      newProduct.setBrand(brand);
      newProduct.setUrlKey(urlKey);
      newProduct.setTitle(title);
      newProduct.setDescription(description);
      newProduct.setMarketPrice(marketPrice);
      newProduct.setRetailPrice(retailPrice);
      newProduct.setImageUrl(imageUrl);
      newProduct.setStyleCode(styleCode);
      newProduct.setColorway(colorway);
      newProduct.setReleaseDate(releaseDate);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return newProduct;
  }


  @PostMapping("/api/product/{pid}/user/{uid}")
  public List<User> addUserToProduct(@PathVariable("pid") Integer pid, @PathVariable("uid")
          Integer uid) {
    productService.addUserToProduct(uid, pid);
    return productService.getAllUsersFromProductById(pid);
  }

  @GetMapping("/api/product/{pid}/user")
  public List<User> addUserToProduct(@PathVariable("pid") Integer pid) {
    return productService.getAllUsersFromProductById(pid);
  }

}
