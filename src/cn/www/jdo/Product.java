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
	private Integer status = 1; // 商品：1为正常，2为下架
	
	
	@Temporal(TemporalType.DATE)
	private Date createDate;//上架时间
	
	@Column
	private Integer count;//总数
	
	@Column
	private  Integer sellCount;//销售数
	

	@Column
	private String htmlPath;//html连接
	
}
