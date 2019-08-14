package com.example.wbdvsu19projectserver.sevices;

import com.example.wbdvsu19projectserver.models.Editor;
import com.example.wbdvsu19projectserver.models.Product;
import com.example.wbdvsu19projectserver.repositories.EditorRepository;
import com.example.wbdvsu19projectserver.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
@Service
public class EditorService {

  @Autowired
  EditorRepository editorRepository;

  @Autowired
  ProductRepository productRepository;


  public Editor createEditor(Editor editor) {
    return editorRepository.save(editor);
  }

  public void addProductToEditor(Integer eid, String urlKey) {
    Editor editor = editorRepository.findById(eid).get();
    Product product = productRepository.findProductByUrlKey(urlKey);
    product.setEditor(editor);
    productRepository.save(product);
  }


  public Editor findEditorById(Integer eid) {
    Editor e = editorRepository.findById(eid).get();
    return e;
  }

  public Set<Product> findAllProductsForEditorById(Integer eid) {
    Editor e = editorRepository.findById(eid).get();
    return e.getFeaturedProducts();

  }

  public List<Editor> findAllEditors() {
    List<Editor> editors = (List<Editor>) editorRepository.findAll();

    return editors;
  }

  public void removeProductFromEditorCollection(String urlKey) {
    Product product = productRepository.findProductByUrlKey(urlKey);
    product.setEditor(null);
    productRepository.save(product);
  }

}
