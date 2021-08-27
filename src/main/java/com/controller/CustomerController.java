package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.CustomerBean;
import com.bean.ResponseBean;
import com.dao.CustomerDao;
import com.util.TokenGenerator;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerDao dao;
	
	@Autowired
	TokenGenerator tokenGenerator;
	
	@PostMapping("customer")
	public ResponseBean<CustomerBean> addCustomer(@RequestBody CustomerBean customerBean){
		ResponseBean<CustomerBean> responseBean = new ResponseBean<>();
		boolean flag =  dao.insert(customerBean);
		if(flag == true) {
			responseBean.setStatus(200);
			responseBean.setMsg("customer added succesfully!");
			responseBean.setData(customerBean);
		}else {
			responseBean.setStatus(-1);
			responseBean.setMsg("customer not saved!");
			responseBean.setData(null);
		}
		return responseBean;
	}
	
	@PostMapping("authenticate")
	public ResponseBean<CustomerBean> authenticate(@RequestBody CustomerBean customerBean){
		ResponseBean<CustomerBean> responseBean = new ResponseBean<>();
		customerBean = dao.authenticate(customerBean.getEmail(),customerBean.getPassword());
		if(customerBean == null) {
			responseBean.setStatus(-1);
			responseBean.setMsg("customer not exist!");
			responseBean.setData(null);
		}else {
			String token = TokenGenerator.generateToken();
			customerBean.setToken(token);
			dao.updateToken(customerBean.getCid(),token);
			responseBean.setStatus(200);
			responseBean.setMsg("customer authenticated succesfully!");
			responseBean.setData(customerBean);
		}
		return responseBean;
	}
	
	@GetMapping("getcustomerbytoken/{token}")
	public ResponseBean<CustomerBean> getCustomerByToken(@PathVariable("token") String token){
		ResponseBean<CustomerBean> responseBean = new ResponseBean<>();
		CustomerBean customerBean = dao.getCustomerByToken(token);
		if(customerBean == null) {
			responseBean.setStatus(-1);
			responseBean.setMsg("customer not exist!");
			responseBean.setData(null);
		}else {
			responseBean.setStatus(200);
			responseBean.setMsg("customer retrived succesfully!");
			responseBean.setData(customerBean);
		}
		return responseBean;
	}
}
