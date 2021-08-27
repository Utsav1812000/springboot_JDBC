package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.ProductBean;

@Repository
public class ProductDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insert(ProductBean productBean) {
		int row = jdbcTemplate.update("insert into product(name,price,quantity) values(?,?,?)", productBean.getName(),productBean.getPrice(),productBean.getQuantity());
		if(row == 0) {
			return false;
		}else {
			return true;
		}
	}

	public List<ProductBean> getAllData() {
		return jdbcTemplate.query("select * from product", new BeanPropertyRowMapper<ProductBean>(ProductBean.class));
	}

	public int deleteData(int id) {
		int row =  jdbcTemplate.update("delete from product where pid like ?",id);
		return row;
	}
	public ProductBean getDataById(int id) {
		return  jdbcTemplate.queryForObject("select * from product where pid=?", new BeanPropertyRowMapper<ProductBean>(ProductBean.class),id);
	}

	public int updateProduct(ProductBean bean2,int id) {
		int row = jdbcTemplate.update("update product set name=?,price=?,quantity=? where pid=?",bean2.getName(),bean2.getPrice(),bean2.getQuantity(),id);
		
		return row;
	}
	
	
}
