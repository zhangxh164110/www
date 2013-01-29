package cn.www.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import cn.www.jdo.Brand;
import cn.www.jdo.Category;
import cn.www.jdo.Model;
import cn.www.jdo.Product;
import cn.www.jdo.SellRecord;
import cn.www.jdo.User;
import cn.www.utils.MD5;
import cn.www.utils.TimeUtil;
import cn.www.utils.query.OP;
import cn.www.utils.query.Page;
import cn.www.utils.query.QueryParam;

/**
 * 网站后台管理
 * @author Administrator
 *
 */
@ResultPath("/pages/backend")
public class BackEndAction  extends BaseAction{

	private static final long serialVersionUID = 1L;
	private long id;
	private int pageSize = 15;
	private int pageNumber = 1;
	@SuppressWarnings({ "unused", "rawtypes" })
	private Page pager;
	/************************用户登入和退出***********************************************/
	@Action("login")
	public void logIn(){
		HttpServletRequest request = this.getRequest();
		try{
			String username = request.getParameter("userName");
			String userpass = request.getParameter("pwd");
			
			int code = 1;
			
			List<QueryParam> parmaList = new ArrayList<QueryParam>();
			QueryParam param = new QueryParam();
			param.setField("userName");
			param.setOp(OP.equal);
			param.setValue(new Object[]{username});
			parmaList.add(param);
			
			QueryParam param2 = new QueryParam();
			param2.setField("userPass");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{MD5.toMD5(userpass.trim())});
			parmaList.add(param2);
			List<User> list = this.getCommonManager().findByCustomized( User.class, parmaList, null);
			user = list.size()> 0 ? list.get(0) : null;
			if(user == null){
				// 用户名或密码错
				this.outputData(-1);
				return;
			}else if( user.getRole()>2 ){
				// 不是后台管理用户
				this.outputData(-2);
				return;
			}else{
					
				String loginFlag = request.getParameter("loginFlag");
				int times = 0;
				if(loginFlag != null && loginFlag.equals("1")){
					times = 14 * 24 * 60 * 60;
				}else{
					times = -1;
				}
				
				this.clearUserByCookie();
				this.addUserToCookie(user, times);
				
			}
			this.outputData(code);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * 用户注销sss
	 */
	@Action(value="logout",results = { @Result(name = "success", location = "login",type="redirect") })
	public String logout(){
		HttpServletResponse response = this.getResponse();
		Cookie[] cookies = this.getRequest().getCookies();
		if(cookies != null){
			for(int i = 0; i < cookies.length; i++){
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				response.addCookie(cookies[i]);
			}
		}
		try {
			response.sendRedirect("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	
	}
	/**
	 * 进入后台主界面
	 */
	@Action("backEndMain")
	public String backEndMain(){
		return "index";
	}
	/*********************用户管理模块*****************************************/
	private User user;
	/**
	 * 用户列表
	 */
	@Action("listUser")
	public String listUser(){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		
		if(  user!=null && user.getUserName()!=null && user.getUserName().trim().length()>0 ){
			QueryParam param = new QueryParam();
			param.setField("userName");
			param.setOp(OP.like);
			param.setValue(new Object[]{ "%"+user.getUserName()+"%"});
			paramList.add(param);
		}
	    pager = this.getCommonManager().findPageByCustomized(pageNumber, pageSize, User.class, paramList, null);
	    return "/user/listuser";
	}
	
	/**
	 * 编辑用户
	 */
	@Action("editUser")
	public String editUser(){
		if( id == 0 ){
			user = new User();
		}else{
			user = this.getCommonManager().findEntityByPK( User.class,id);
		}
		return "";
	}
	
	/**
	 * 保存用户
	 */
	@Action("saveUser")
	public void saveUser(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String oldName = request.getParameter("oldName");
			String md5 = request.getParameter("md5");
			
			if( user.getUserPass().trim().length()>0) user.setUserPass( MD5.toMD5( user.getUserPass()));
			else user.setUserPass( md5 );
			if( user.getId() == 0 ){
				if( this.isExistUser( user.getUserName())){
					this.outputData(1);
					return ;
				}
				this.getCommonManager().saveEntity( user );
			}else{
				if( !user.getUserName().equals(oldName) && this.isExistUser(user.getUserName())){
					this.outputData(1);
					return;
				}
				User oldUser = this.getCommonManager().findEntityByPK( User.class, user.getId());
				oldUser.setUserName( user.getUserName() );
				oldUser.setStatus( user.getStatus() );
				this.getCommonManager().modifyEntity( oldUser );
			}
			this.clearUserByCookie();
			this.addUserToCookie(user, -1);
			this.outputData( 0 );
		} catch (Exception e) {
			this.outputData( 2 );
			e.printStackTrace();
		}
			
	}
	
	//判断用户是否存在
	private boolean isExistUser( String name ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("userName");
		param.setOp(OP.equal);
		param.setValue(new Object[]{name});
		paramList.add(param);
		return this.getCommonManager().findByCustomized( User.class, paramList, null).size()>0;
	}
	
	/**
	 * 删除用户
	 */
	@Action("delUser")
	public void delUser(){
		try {
			try {
				if( !isExsitUserData(id) ){
					this.getCommonManager().deleteEntityByPK( User.class, id );
					this.outputData(1);
					return;
				}
				this.outputData(3);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				this.outputData(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	//判断用房是否能删除
	private boolean isExsitUserData( long id ){
		return this.getCommonManager().findByCustomizedSQL( Product.class, "userId="+id).size()>0;
	}
	/*******************************菜单******************************************/
	private Category category;
	private List<Category> listCategory;
	/**
	 * 菜单列表
	 */
	@Action("lisetCategory")
	public String lisetCategory(){
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Category.class,null, null);
		return "";
	}
	
	/**
	 * 编辑菜单
	 * @return
	 */
	@Action("editCategory")
	public String editCategory(){
		listCategory = this.getCommonManager().findByCustomized( Category.class, null, null);
		this.getRequest().setAttribute( "listCategory", listCategory );
		if( id==0 ){
			category = new Category();
		}else{
			category = this.getCommonManager().findEntityByPK( Category.class, id );
		}
		return "";
	}
	
	/**
	 * 保存菜单
	 */
	@Action("saveCategory")
	public void saveCategory(){
		try {
			if( category.getId() == 0 ){
				if( isExistCategory(category.getName()) ){
					this.outputData(1);
					return;
				}
				this.getCommonManager().saveEntity( category );
			}else{
				String oldName = this.getRequest().getParameter("oldName");
				if( !category.getName().equals(oldName) && isExistCategory(category.getName())){
					this.outputData(1);
					return;
				}
				Category obj = this.getCommonManager().findEntityByPK( Category.class, category.getId());
				obj.setParent( category.getParent() );
				obj.setName( category.getName() );
				this.getCommonManager().modifyEntity( obj );
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}

	//判断菜单是否存在
	private boolean isExistCategory( String name ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("name");
		param.setOp(OP.equal);
		param.setValue(new Object[]{name});
		paramList.add(param);
		return this.getCommonManager().findByCustomized( Category.class, paramList, null).size()>0;
	}
	
	/**
	 * 删除菜单
	 */
	@Action("delCategory")
	public void delCategory(){
		try {
			this.getCommonManager().deleteEntityByPK( Category.class, id );
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	
	/**********************品牌**********************************/
	private Brand brand;
	private List<Brand> listBrand;
	/**
	 * 品牌列表
	 */
	@Action("lisetBrand")
	public String lisetBrand(){
		listCategory = this.getCommonManager().findByCustomized( Category.class, null, null);
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		if( brand.getName()!=null && brand.getName().trim().length()>0 ){
			QueryParam param = new QueryParam();
			param.setField("name");
			param.setOp(OP.like);
			param.setValue(new Object[]{ "%"+brand.getName()+"%"});
			paramList.add(param);
		}
		if( brand.getCategory()!=null && brand.getCategory().getId()!=0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("category.id");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{ brand.getCategory().getId()});
			paramList.add(param2);
		}
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Brand.class,null, null);
		return "";
	}
	
	/**
	 * 编辑品牌
	 * @return
	 */
	@Action("editBrand")
	public String editBrand(){
		listCategory = this.getCommonManager().findByCustomized( Category.class, null, null);
		if( id==0 ){
			brand = new Brand();
		}else{
			brand = this.getCommonManager().findEntityByPK( Brand.class, id );
		}
		return "";
	}
	
	/**
	 * 保存品牌
	 */
	@Action("saveBrand")
	public void saveBrand(){
		try {
			if( brand.getId() == 0 ){
				if( isExistBrand(brand.getName()) ){
					this.outputData(1);
					return;
				}
				this.getCommonManager().saveEntity( brand );
			}else{
				String oldName = this.getRequest().getParameter("oldName");
				if( !brand.getName().equals(oldName) && isExistBrand(brand.getName())){
					this.outputData(1);
					return;
				}
				Brand obj = this.getCommonManager().findEntityByPK( Brand.class, brand.getId());
				obj.setCategory( brand.getCategory() );
				obj.setName( brand.getName() );
				this.getCommonManager().modifyEntity( obj );
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}

	//判断品牌是否存在
	private boolean isExistBrand( String name ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("name");
		param.setOp(OP.equal);
		param.setValue(new Object[]{name});
		paramList.add(param);
		return this.getCommonManager().findByCustomized( Brand.class, paramList, null).size()>0;
	}
	
	/**
	 * 删除品牌
	 */
	@Action("delBrand")
	public void delBrand(){
		try {
			if( !isExsitBrandData(id) ){
				this.getCommonManager().deleteEntityByPK( Brand.class, id );
				this.outputData(1);
				return;
			}
			this.outputData(3);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	//判断品牌能否删除
	private boolean isExsitBrandData( long id ){
		return this.getCommonManager().findByCustomizedSQL( Product.class, "brandId="+id).size()>0;
	}
	
	/********************型号************************/
	private Model model;
	private List<Model> listModel;
	/**
	 * 型号列表
	 */
	@Action("lisetModel")
	public String lisetModel(){
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null);
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		
		if( model.getName()!=null && model.getName().trim().length()>0 ){
			QueryParam param = new QueryParam();
			param.setField("name");
			param.setOp(OP.like);
			param.setValue(new Object[]{ "%"+model.getName()+"%"});
			paramList.add(param);
		}
		if( model.getBrand()!=null && model.getBrand().getId()!=0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("brand.id");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{ model.getBrand().getId()});
			paramList.add(param2);
		}
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Model.class,paramList, null);
		return "";
	}
	
	/**
	 * 编辑型号
	 * @return
	 */
	@Action("editModel")
	public String editModel(){
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null);
		if( id==0 ){
			model = new Model();
		}else{
			model = this.getCommonManager().findEntityByPK( Model.class, id );
		}
		return "";
	}
	
	/**
	 * 保存型号
	 */
	@Action("saveModel")
	public void saveModel(){
		try {
			if( model.getId() == 0 ){
				if( isExistModel(model.getName()) ){
					this.outputData(1);
					return;
				}
				this.getCommonManager().saveEntity( model );
			}else{
				String oldName = this.getRequest().getParameter("oldName");
				if( !model.getName().equals(oldName) && isExistModel(model.getName())){
					this.outputData(1);
					return;
				}
				Model obj = this.getCommonManager().findEntityByPK( Model.class, model.getId());
				obj.setBrand( model.getBrand());
				obj.setName( model.getName() );
				this.getCommonManager().modifyEntity( obj );
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}

	//判断型号是否存在
	private boolean isExistModel( String name ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("name");
		param.setOp(OP.equal);
		param.setValue(new Object[]{name});
		paramList.add(param);
		return this.getCommonManager().findByCustomized( Model.class, paramList, null).size()>0;
	}
	
	/**
	 * 删除型号
	 */
	@Action("delModel")
	public void delModel(){
		try {
			if( !isExsitModelData(id) ){
				this.getCommonManager().deleteEntityByPK( Brand.class, id );
				this.outputData(1);
				return;
			}
			this.outputData(3);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	//判断品牌能否删除
	private boolean isExsitModelData( long id ){
		return this.getCommonManager().findByCustomizedSQL( Product.class, "modelId="+id).size()>0;
	}
	
	
	/***************************商品***********************************/
	private Product product;
	/**
	 * 商品列表
	 * @return
	 */
	@Action("listProduct")
	public String listProduct(){
		String startTime = this.getRequest().getParameter("startTime");
		String endTime = this.getRequest().getParameter("endTime");
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		if( product.getBrand()!=null && product.getBrand().getId()!=0 ){
			QueryParam param = new QueryParam();
			param.setField("brand.id");
			param.setOp(OP.equal);
			param.setValue(new Object[]{product.getBrand().getId()});
			paramList.add(param);	
		}
		if( product.getModel()!=null && product.getModel().getId()!=0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("model.id");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{product.getModel().getId()});
			paramList.add(param2);	
		}
		if( product.getStatus()!=null && product.getStatus()!=0 ){
			QueryParam param3 = new QueryParam();
			param3.setField("status");
			param3.setOp(OP.equal);
			param3.setValue(new Object[]{product.getStatus()});
			paramList.add(param3);	
		}
		if( startTime!=null && startTime.trim().length()>0 ){
			QueryParam param4 = new QueryParam();
			param4.setField("createDate");
			param4.setOp(OP.greaterEqual);
			param4.setValue(new Object[]{ TimeUtil.controlTime(startTime)});
			paramList.add(param4);	
		}
		if( endTime!=null && endTime.trim().length()>0 ){
			QueryParam param5 = new QueryParam();
			param5.setField("createDate");
			param5.setOp(OP.lessEqual);
			param5.setValue(new Object[]{ TimeUtil.controlTime(endTime)});
			paramList.add(param5);	
		}
		pager = this.getCommonManager().findPageByCustomized(pageNumber, pageSize, Product.class, paramList, null);
		return "";
	}
	
	/**
	 * 编辑商品
	 */
	@Action("editProduct")
	public String editProduct(){
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null );
		listModel = this.getCommonManager().findByCustomized( Model.class, null, null );
		if( id == 0 ){
			product = new Product();
		}else{
			product = this.getCommonManager().findEntityByPK( Product.class, id );
		}
		return "";
	}
	/**
	 * 保存商品
	 */
	@Action("saveProduct")
	public void saveProduct(){
		try {
			if( product.getId() == 0 ){
				this.getCommonManager().saveEntity( product );
			}else{
				this.getCommonManager().modifyEntity( product );
			}
			this.outputData(1);
		} catch (Exception e) {
			this.outputData(2);
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除商品
	 */
	@Action("delProduct")
	public void delProduct(){
		try {
			if( !isExsitProductData(id) ){
				this.getCommonManager().deleteEntityByPK( Brand.class, id );
				this.outputData(1);
				return;
			}
			this.outputData(3);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	//判断品牌能否删除
	private boolean isExsitProductData( long id ){
		return this.getCommonManager().findByCustomizedSQL( SellRecord.class, "productId="+id).size()>0;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNumber() {
		return pageNumber;
	}


	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public Page getPager() {
		return pager;
	}


	public void setPager(Page pager) {
		this.pager = pager;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public List<Category> getListCategory() {
		return listCategory;
	}


	public void setListCategory(List<Category> listCategory) {
		this.listCategory = listCategory;
	}


	public Brand getBrand() {
		return brand;
	}


	public void setBrand(Brand brand) {
		this.brand = brand;
	}


	public List<Brand> getListBrand() {
		return listBrand;
	}


	public void setListBrand(List<Brand> listBrand) {
		this.listBrand = listBrand;
	}


	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}


	public List<Model> getListModel() {
		return listModel;
	}


	public void setListModel(List<Model> listModel) {
		this.listModel = listModel;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
