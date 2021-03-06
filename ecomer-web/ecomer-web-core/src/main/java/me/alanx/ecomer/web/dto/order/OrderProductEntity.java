package me.alanx.ecomer.web.dto.order;

import java.io.Serializable;

import me.alanx.ecomer.web.dto.catalog.product.ReadableProduct;

public class OrderProductEntity extends OrderProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int orderedQuantity;
	private ReadableProduct product;


	
	
	public void setOrderedQuantity(int orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public int getOrderedQuantity() {
		return orderedQuantity;
	}
	public ReadableProduct getProduct() {
		return product;
	}
	public void setProduct(ReadableProduct product) {
		this.product = product;
	}




}
