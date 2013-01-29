package cn.www.jdo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


// 商品
@Entity
@Table(name = "p_product")
public class Product {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(unique = true, nullable = false, length = 50)
	private String name; //名
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;// 录入人
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brandId")
	private Brand brand;// 关联品牌（可以为空）
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modelId")
	private Model model;// 关联型号（可以为空）

	@Column
	private Double unitsPrice; // 单价
	
	@Column
	private Double discount; // 折扣
	
	@Column
	private Integer status = 2; // 商品：1为上架，2为下架
	
	
	@Temporal(TemporalType.DATE)
	private Date createDate= new java.util.Date();//录入时间
	
	@Column
	private Integer count;//总数
	
	@Column
	private  Integer sellCount;//销售数
	

	@Column
	private String htmlPath;//html连接


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Brand getBrand() {
		return brand;
	}


	public void setBrand(Brand brand) {
		this.brand = brand;
	}


	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}


	public Double getUnitsPrice() {
		return unitsPrice;
	}


	public void setUnitsPrice(Double unitsPrice) {
		this.unitsPrice = unitsPrice;
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public Integer getSellCount() {
		return sellCount;
	}


	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}


	public String getHtmlPath() {
		return htmlPath;
	}


	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	
	
	
}
