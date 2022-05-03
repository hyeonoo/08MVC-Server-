package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> 상품관리 Controller
@RequestMapping("/product/*")
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties , classpath:config/commonservice.xml 참조
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception{
	@RequestMapping(value="addProductView", method=RequestMethod.GET)
	public String addProduct() throws Exception{
		
		System.out.println("/product/addProduct GET");
		
		return "forward:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
	
		System.out.println("/product/addProduct POST");
		//Business Logic
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search")  Search search, @RequestParam(value = "menu", required=false) String menu,  Model model, HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct");
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		//Business logic 수행
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
	
		
		
		return "forward:/product/listProduct.jsp";
	}
	
	//@RequestMapping("/updateProductView.do")
	//public String updateProductView(@RequestParam("prodNo") int prodNo,  Model model) throws Exception{
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/product/updateProductView GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		//Model과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, Model model, HttpSession session) throws Exception{
		
		System.out.println("/prduct/updateProduct POST");
		//Business logic
		productService.updateProduct(product);
		
	
		return "forward:/product/getProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct")
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam(value="menu", required=false) String menu, Model model) throws Exception{
		
		System.out.println("/product/getProduct GET");
		
		Product product = productService.getProduct(prodNo);
		
		model.addAttribute("product", product);
		model.addAttribute("menu", menu);
		
		
		return "forward:/product/getProduct.jsp";
	}
	
	

}
