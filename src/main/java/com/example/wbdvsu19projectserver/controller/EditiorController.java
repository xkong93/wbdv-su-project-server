package com.example.wbdvsu19projectserver.controller;

import com.example.wbdvsu19projectserver.models.Editor;
import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.sevices.EditorService;
import com.example.wbdvsu19projectserver.sevices.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;


/**
 * @author Xuan Kong
 * @Date 2019-08-11.
 */
@RestController
public class EditiorController {

  @Autowired
  EditorService editorService;

  @Autowired
  ProductService productService;
  static String code = "AIRJORDAN";

  @PostMapping("api/editor/{code}")
  public Editor createEditor(
          @PathVariable("code") String code,
          @RequestBody Editor editor) {

    if (code.equals(this.code)) {
      return editorService.createEditor(editor);
    } else if (!code.equals(this.code)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WRONG ACCESS CODE");
    }

    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DUP ENTRY");
  }


  @GetMapping("/api/editor/{eid}/product/{urlKey}")
  public void addProductToEditor(
          @PathVariable("eid") Integer eid, @PathVariable("urlKey") String urlKey) {

    Editor editor = editorService.findEditorById(eid);
    Product p = productService.findProductByUrlkey(urlKey);
    if (editor.getFeaturedProducts().contains(p)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "product already exists");
    }
    editorService.addProductToEditor(eid, urlKey);
  }

  @GetMapping("/api/editor/{eid}/product")
  public Set<Product> getFeaturedProductsforEditorById(@PathVariable("eid") Integer eid) {
    Editor e = editorService.findEditorById(eid);
    return e.getFeaturedProducts();
  }

  @GetMapping("/api/editor/product")
  public List<Editor> getAllFeaturedProductsforEditors() {
    List<Editor> editors = editorService.findAllEditors();
//    ObjectMapper mapper = new ObjectMapper();
//    ObjectNode root = mapper.createObjectNode();
//    ArrayNode myEditors = root.putArray("");

    return editors;
  }

  @DeleteMapping("/api/editor/{eid}/product/{urlKey}")
  public void removeProductFromEditorCollection(@PathVariable("urlKey") String urlKey){

      editorService.removeProductFromEditorCollection(urlKey);
  }



}
