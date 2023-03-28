package com.nttdata.product;


import com.nttdata.product.entity.Product;
import com.nttdata.product.service.ProductService;
import com.nttdata.product.service.imp.ProductServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductApplicationTests {


	ProductService productService=new ProductServiceImp();

	@Test
	void contextLoads() {
	}

	@Test
	void getAll(){

	}
}
