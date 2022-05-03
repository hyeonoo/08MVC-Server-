package com.model2.mvc.web.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

//==> 상품관리 RestController
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping( value="json/addProduct", method=RequestMethod.POST)
	public Product addProduct (@RequestBody Product product) throws Exception{
		
		System.out.println("/product/json/addProduct : POST");
		
		productService.addProduct(product);
		
		return productService.getProduct(product.getProdNo());
		
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}/{menu}", method=RequestMethod.GET)
	public Map getProduct ( @PathVariable int prodNo, @PathVariable String menu   ) throws Exception{
		
		System.out.println("product/json/getProduct : GET");
		System.out.println(prodNo);
		System.out.println(menu);
		
		
		Product product = productService.getProduct(prodNo);
		
		Map map = new HashMap();
		map.put("product", product);
		map.put("menu", menu);
		
		System.out.println(map);
		
		return map;
	}
	
	@RequestMapping(value="json/listProduct")
	public Map listProduct(@RequestBody Search search, @PathVariable String menu ) throws Exception{
		
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		Map map1 = new HashMap();
		map1.put("list", map.get("list"));
		map1.put("resultPage", resultPage);
		map1.put("search", search);
		map1.put("menu", menu);
		
		return map1;
	}
	
	@RequestMapping( value="json/updateProduct", method=RequestMethod.POST )
	public Product updateProduct( @RequestBody Product product,  HttpSession session ) throws Exception{
		
		System.out.println("/product/json/updateProduct : POST");
		
		productService.updateProduct(product);
		System.out.println(product);
		
		return productService.getProduct(product.getProdNo());
		
	}

}
