package heavynimbus.backend.controller.pub;

import heavynimbus.backend.dto.product.ProductResponse;
import heavynimbus.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/product")
public class ProductController {
  private final ProductService productService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse getProductAttributes() {
    return productService.getProduct();
  }
}
