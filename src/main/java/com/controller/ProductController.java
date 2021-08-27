package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ProductBean;
import com.bean.ResponseBean;
import com.dao.ProductDao;

@RestController
public class ProductController {
	
	@Autowired
	ProductDao productDao;
	
	@PostMapping("/product")
	public ResponseBean<ProductBean> insert(@RequestBody ProductBean productBean){
		ResponseBean<ProductBean> responseBean = new ResponseBean<>();
		
		boolean flag =  productDao.insert(productBean);
		if(flag == true) {
			responseBean.setStatus(200);
			responseBean.setMsg("product saved succesfully!");
			responseBean.setData(productBean);
		}else {
			responseBean.setStatus(-1);
			responseBean.setMsg("product not saved!");
			responseBean.setData(null);
		}
		
		return responseBean;
	}
	
	@GetMapping("product")
	public ResponseBean<List<ProductBean>> getAllData(){
		ResponseBean<List<ProductBean>> responseBean = new ResponseBean<>();
		List<ProductBean> list =  productDao.getAllData();
		if(list.size()==0) {
			responseBean.setStatus(-1);
			responseBean.setMsg("product not available!");
			responseBean.setData(null);
		}else {
			responseBean.setStatus(200);
			responseBean.setMsg("product get succesfully!");
			responseBean.setData(list);
		}
		return responseBean;
		
	}
	
	@DeleteMapping("product/{id}")
	public ResponseBean<ProductBean> deleteData(@PathVariable("id") int id){
		ResponseBean<ProductBean> responseBean = new ResponseBean<>();
		ProductBean bean = productDao.getDataById(id);
		int row = productDao.deleteData(id);
		
		if(row == 0) {
			responseBean.setStatus(-1);
			responseBean.setMsg("product not available!");
			responseBean.setData(null);
		}else {
			responseBean.setStatus(200);
			responseBean.setMsg("product delete succesfully!");
			responseBean.setData(bean);
		}
		return responseBean;
		}
	
	@PutMapping("product/{id}")
	public ResponseBean<ProductBean> updateData(@RequestBody ProductBean productBean,@PathVariable("id") int id){
		ResponseBean<ProductBean> responseBean = new ResponseBean<ProductBean>();
		ProductBean bean2 = productDao.getDataById(id);
		System.out.println(bean2);
		int i = productDao.updateProduct(productBean,id);
		
		ProductBean bean3 = productDao.getDataById(id);
		System.out.println(bean3);
		if(i == 0) {
			responseBean.setStatus(-1);
			responseBean.setMsg("product not available!");
			responseBean.setData(null);
		}else {
			responseBean.setStatus(200);
			responseBean.setMsg("product updated succesfully!");
			responseBean.setData(bean3);
		}
		return responseBean;
		
		
	}
}
