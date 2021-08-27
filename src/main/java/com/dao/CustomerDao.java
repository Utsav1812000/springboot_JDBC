package com.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.CustomerBean;

@Repository
public class CustomerDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insert(CustomerBean customerBean) {	
		int row = jdbcTemplate.update("insert into customer(name,email,password) values(?,?,?)",customerBean.getName(),customerBean.getEmail(),customerBean.getPassword());
		if(row == 0) {
			return false;
		}else{
			return true;
		}
	}

	public CustomerBean authenticate(String email, String password) {
		CustomerBean customerBean = null;
		try {
			customerBean = jdbcTemplate.queryForObject("select * from customer where email like ? and password like ?", new BeanPropertyRowMapper<>(CustomerBean.class),email,password);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customerBean;
	}

	public void updateToken(int cid, String token) {
		jdbcTemplate.update("update customer set token = ? where cid=?",token,cid);
	}

	public CustomerBean getCustomerByToken(String token) {
		CustomerBean customerBean = null;
		try {
			customerBean =  jdbcTemplate.queryForObject("select * from customer where token = ?", new BeanPropertyRowMapper<CustomerBean>(CustomerBean.class),token);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customerBean;
	}
	
	
}
