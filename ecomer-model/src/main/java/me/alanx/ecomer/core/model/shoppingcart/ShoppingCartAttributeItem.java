package me.alanx.ecomer.core.model.shoppingcart;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import me.alanx.ecomer.core.constants.SchemaConstant;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.common.audit.AuditListener;
import me.alanx.ecomer.core.model.common.audit.AuditSection;
import me.alanx.ecomer.core.model.common.audit.Auditable;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SHOPPING_CART_ATTR_ITEM", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class ShoppingCartAttributeItem extends ApplicationEntity<Long, ShoppingCartAttributeItem> implements Auditable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SHP_CART_ATTR_ITEM_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SHP_CRT_ATTR_ITM_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Embedded
	private AuditSection auditSection = new AuditSection();
	

	
	@Column(name="PRODUCT_ATTR_ID", nullable=false)
	private Long productOptionPriceId;
	
	@Transient
	private ProductOptionPrice productOptionPrice;
	

	
	@ManyToOne(targetEntity = ShoppingCartItem.class)
	@JoinColumn(name = "SHP_CART_ITEM_ID", nullable = false)
	private ShoppingCartItem shoppingCartItem;
	
	public ShoppingCartAttributeItem(ShoppingCartItem shoppingCartItem, ProductOptionPrice productAttribute) {
		this.shoppingCartItem = shoppingCartItem;
		this.productOptionPrice = productAttribute;
		this.productOptionPriceId = productAttribute.getId();
	}
	
	public ShoppingCartAttributeItem() {

	}
	
	

	public ShoppingCartItem getShoppingCartItem() {
		return shoppingCartItem;
	}

	public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
		this.shoppingCartItem = shoppingCartItem;
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;
		
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}


	public void setProductAttributeId(Long productAttributeId) {
		this.productOptionPriceId = productAttributeId;
	}

	public Long getProductAttributeId() {
		return productOptionPriceId;
	}

	public void setProductAttribute(ProductOptionPrice productAttribute) {
		this.productOptionPrice = productAttribute;
	}

	public ProductOptionPrice getProductAttribute() {
		return productOptionPrice;
	}


}
