package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

//==> 상품관리에서 서비스할내용 추상화 / 캐슐화한 Service Interface Definition
public interface ProductService {
	
	// 상품등록
	public void addProduct(Product product) throws Exception;
	
	// 상품상세조회
	public Product getProduct(int prodNo) throws Exception;
	
	// 상품리스트 
	public Map<String, Object> getProductList(Search search) throws Exception;

	// 상품정보수정
	public void updateProduct(Product product) throws Exception; 
}
