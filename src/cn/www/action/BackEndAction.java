package cn.www.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import cn.www.jdo.Article;
import cn.www.jdo.ArticleCategory;
import cn.www.jdo.Brand;
import cn.www.jdo.BrandMany;
import cn.www.jdo.Category;
import cn.www.jdo.Model;
import cn.www.jdo.Product;
import cn.www.jdo.SellRecord;
import cn.www.jdo.User;
import cn.www.utils.CommUtils;
import cn.www.utils.ImageUtil;
import cn.www.utils.MD5;
import cn.www.utils.TimeUtil;
import cn.www.utils.query.OP;
import cn.www.utils.query.Page;
import cn.www.utils.query.QueryOrder;
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
	private File myFile;
	private String contentType;
	private String fileName;
	private String myFileContentType;
	private String myFileFileName;
	public static final int BUFFER_SIZE = 5 * 1024;
	@SuppressWarnings({ "unused", "rawtypes" })
	private Page pager;
	/************************用户登入和退出***********************************************/
	@Action("toLogin")
	public String toLogin(){
		return "login";
	}
	@Action("login")
	public void login(){
		HttpServletRequest request = this.getRequest();
		try{
			String username = request.getParameter("username");
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
	@Action(value="logout",results = { @Result(name = "success", location = "toLogin",type="redirect") })
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
		return "/user/user";
	}
	
	/**
	 * 保存用户
	 */
	@Action("saveUser")
	public void saveUser(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String oldName = request.getParameter("oldName");
			
			if( user.getUserPass().trim().length()>0) user.setUserPass( MD5.toMD5( user.getUserPass()));
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
				if( user.getUserPass().trim().length()>0) oldUser.setUserPass( user.getUserPass());
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
	@Action("isNameExist")
	public void isNameExist(){
		
		if( this.isExistUser( this.getRequest().getParameter("userName"))){
			this.outputData(1);
		}
		this.outputData(2);
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
	}
	//判断用房是否能删除
	private boolean isExsitUserData( long id ){
		return this.getCommonManager().findByCustomizedSQL( Product.class, "userId="+id).size()>0;
	}
	
	/**
	 * 禁止/取消禁止用户(修改模特VIP状态)
	 */
	@Action("chageUserStauts")
	public void chageUserStauts() {
		HttpServletRequest request = this.getRequest();
		String status = request.getParameter("status");
		user = this.getCommonManager().findEntityByPK(User.class, id);
		if (status.equals("1")) {
			user.setStatus(2);
		} else if (status.equals("2") || status.equals("0")) {
			user.setStatus(1);
		}
		int code = 0;
		try {
			this.getCommonManager().modifyEntity(user);
		} catch (Exception e) {
			code = 1;
			e.printStackTrace();
		}
		this.outputData(code);
	}

	/*******************************菜单******************************************/
	private Category category;
	private List<Category> listCategory;
	/**
	 * 菜单列表
	 */
	@Action("listCategory")
	public String listCategory(){
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Category.class,null, null);
		return "/category/listcategory";
	}
	
	/**
	 * 编辑菜单
	 * @return
	 */
	@Action("editCategory")
	public String editCategory(){
		listCategory = this.getCommonManager().findByCustomizedSQL( Category.class, "(parentId is null)");
		this.getRequest().setAttribute( "listCategory", listCategory );
		if( id==0 ){
			category = new Category();
		}else{
			category = this.getCommonManager().findEntityByPK( Category.class, id );
		}
		return "/category/category";
	}
	
	/**
	 * 保存菜单
	 */
	@Action("saveCategory")
	public void saveCategory(){
		try {
			if( category.getParent().getId() == 0 ){
				category.setParent( null );
			}
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
				obj.setType( category.getType());
				this.getCommonManager().modifyEntity( obj );
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}

	@Action("isNameCategoryExist")
	public void isNameCategoryExist(){
		if( this.isExistCategory( this.getRequest().getParameter("name"))){
			this.outputData(1);
		}
		this.outputData(2);
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
	@Action("listBrand")
	public String listBrand(){
		listCategory = this.getCommonManager().findByCustomizedSQL( Category.class, "(type>1)");
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		if(brand!=null &&  brand.getName()!=null && brand.getName().trim().length()>0 ){
			QueryParam param = new QueryParam();
			param.setField("name");
			param.setOp(OP.like);
			param.setValue(new Object[]{ "%"+brand.getName()+"%"});
			paramList.add(param);
		}
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Brand.class,null, null);
		List<Brand> list = pager.getCurrentPageElements();
		for( Brand obj: list ){
			obj.setList( this.getCategoryByBrand(obj));
		}
		pager.setElements( list );
		return "/brand/listbrand";
	}
	
	/**
	 * 编辑品牌
	 * @return
	 */
	@Action("editBrand")
	public String editBrand(){
		this.getCommonManager().findByCustomizedSQL( Category.class, "(type>1)");
		if( id==0 ){
			brand = new Brand();
		}else{
			brand = this.getCommonManager().findEntityByPK( Brand.class, id );
			List<Category> list = getCategoryByBrand(brand);
			this.getRequest().setAttribute("list", list);
		}
		return "/brand/brand";
	}
	
	private List<Category> getCategoryByBrand(Brand brand ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("brand");
		param.setOp(OP.equal);
		param.setValue(new Object[]{ brand });
		paramList.add(param);
		List<BrandMany> list = this.getCommonManager().findByCustomized( BrandMany.class , paramList, null);
		List<Category> listCategory = new ArrayList<Category>();
		for( BrandMany obj: list ){
			listCategory.add(obj.getCategory());
		}
		return listCategory;
	}
	private List<BrandMany> getBrandManyByBrand(Brand brand ){
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		QueryParam param = new QueryParam();
		param.setField("brand");
		param.setOp(OP.equal);
		param.setValue(new Object[]{ brand });
		paramList.add(param);
		List<BrandMany> list = this.getCommonManager().findByCustomized( BrandMany.class , paramList, null);
		return list;
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
				obj.setName( brand.getName() );
				this.getCommonManager().modifyEntity( obj );
				
				//删除原来关联的数据
				List<BrandMany> list = getBrandManyByBrand(brand);
				for( BrandMany o: list ){
					this.getCommonManager().deleteEntity(o);
				}
			}
			

			String[] categorys =this.getRequest().getParameterValues("categorys");
			for( String obj: categorys ){
				if( CommUtils.isCorrectNumber( obj )){
					BrandMany newObj = new BrandMany();
					newObj.setBrand(brand);
					newObj.setCategory( this.getCommonManager().findEntityByPK( Category.class, Long.parseLong(obj)));
					this.getCommonManager().saveEntity( newObj);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	@Action("isNameBrandExist")
	public void isNameBrandExist(){
		if( this.isExistBrand( this.getRequest().getParameter("name"))){
			this.outputData(1);
		}
		this.outputData(2);
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
	@Action("listModel")
	public String listModel(){
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null);
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		
		if( model!=null &&  model.getName()!=null && model.getName().trim().length()>0 ){
			QueryParam param = new QueryParam();
			param.setField("name");
			param.setOp(OP.like);
			param.setValue(new Object[]{ "%"+model.getName()+"%"});
			paramList.add(param);
		}
		if( model!=null && model.getBrand()!=null && model.getBrand().getId()!=0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("brand.id");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{ model.getBrand().getId()});
			paramList.add(param2);
		}
		pager = this.getCommonManager().findPageByCustomized( pageNumber, pageSize, Model.class,paramList, null);
		return "/model/listmodel";
	}
	
	/**
	 * 编辑型号
	 * @return
	 */
	@Action("editModel")
	public String editModel(){
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null);
		List<BrandMany> list = this.getCommonManager().findByCustomized( BrandMany.class, null, null);
		this.getRequest().setAttribute("list", list);
		if( id==0 ){
			model = new Model();
		}else{
			model = this.getCommonManager().findEntityByPK( Model.class, id );
		}
		return "/model/model";
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
				obj.setCategory( model.getCategory() );
				this.getCommonManager().modifyEntity( obj );
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.outputData(2);
		}
	}
	
	
	@Action("isNameModelExist")
	public void isNameModelExist(){
		if( this.isExistBrand( this.getRequest().getParameter("name"))){
			this.outputData(1);
		}
		this.outputData(2);
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
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null );
		String startTime = this.getRequest().getParameter("startTime");
		String endTime = this.getRequest().getParameter("endTime");
		List<QueryParam> paramList = new ArrayList<QueryParam>();
		if( product!=null && product.getBrand()!=null && product.getBrand().getId()!=0 ){
			QueryParam param = new QueryParam();
			param.setField("brand.id");
			param.setOp(OP.equal);
			param.setValue(new Object[]{product.getBrand().getId()});
			paramList.add(param);	
		}
		if(  product!=null && product.getModel()!=null && product.getModel().getId()!=0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("model.id");
			param2.setOp(OP.equal);
			param2.setValue(new Object[]{product.getModel().getId()});
			paramList.add(param2);	
		}
		if(  product!=null && product.getStatus()!=null && product.getStatus()!=0 ){
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
		return "/product/listproduct";
	}
	
	/**
	 * 编辑商品
	 */
	@Action("editProduct")
	public String editProduct(){
		List<BrandMany> list = this.getCommonManager().findByCustomized( BrandMany.class, null, null);
		this.getRequest().setAttribute("list", list);
		listBrand = this.getCommonManager().findByCustomized( Brand.class, null, null );
		listModel = this.getCommonManager().findByCustomized( Model.class, null, null );
		if( id == 0 ){
			product = new Product();
		}else{
			product = this.getCommonManager().findEntityByPK( Product.class, id );
		}
		return "/product/product";
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

	/*******************************文章*************************************************/
	private Article article;

	@Action("listArticle")
	public String listArticle(){
		HttpServletRequest request = this.getRequest();
		
		listCategory = this.getCommonManager().findByCustomized( Category.class, null, null);
		request.setAttribute( "listCategory", listCategory );
		
		QueryOrder order = new QueryOrder();
		order.setField("publicDate");
		order.setAscOrder(false);
		List<QueryOrder> queryOrder = new ArrayList<QueryOrder>();
		queryOrder.add(order);
		List<QueryParam> queryParam = new ArrayList<QueryParam>();
		if( article!=null && article.getCategory()!=null && article.getCategory().getId()!=0){
			QueryParam param = new QueryParam();
			param.setField("catalog.id");
			param.setOp(OP.equal);
			param.setValue(new Object[]{ article.getCategory().getId()});
			queryParam.add(param);
		}
		if( article!=null && article.getTitle()!=null && article.getTitle().trim().length()>0 ){
			QueryParam param2 = new QueryParam();
			param2.setField("title");
			param2.setOp(OP.like);
			param2.setValue(new Object[]{ "%"+article.getTitle()+"%"});
			queryParam.add(param2);
		}
		if( article!=null && article.getKeyword()!=null && article.getKeyword().trim().length()>0 ){
			QueryParam param3 = new QueryParam();
			param3.setField("keyword");
			param3.setOp(OP.like);
			param3.setValue(new Object[]{ "%"+article.getKeyword()+"%"});
			queryParam.add(param3);
		}
		String startTime = request.getParameter("startTime");
		if( startTime!=null && startTime.trim().length()>0 ){
			Date date1 = TimeUtil.controlTime(startTime);
			QueryParam param4 = new QueryParam();
			param4.setField("publicDate");
			param4.setOp(OP.greaterEqual);
			param4.setValue(new Object[]{ date1 });
			queryParam.add(param4);
			request.setAttribute("startTime", startTime);
		}
		
		String endTime = request.getParameter("endTime");
		if( startTime!=null && startTime.trim().length()>0 ){
			Date date2 = TimeUtil.controlTime(endTime);
			QueryParam param5 = new QueryParam();
			param5.setField("publicDate");
			param5.setOp(OP.lessEqual);
			param5.setValue(new Object[]{ date2 });
			queryParam.add(param5);
			request.setAttribute("endTime", endTime);
		}
		
		pager = this.getCommonManager().findPageByCustomized(pageNumber, pageSize, Article.class, queryParam, queryOrder);
		return "/article/listarticle";
	}
	/*******
	 * 此方法添加图文的Article
	 */
	@Action("editArticle")
	public String editArticle(){
		listCategory = this.getCommonManager().findByCustomized( Category.class, null, null);
		if( id == 0 ){
			article = new Article();
		}else{
			article = this.getCommonManager().findEntityByPK( Article.class, id );
		}
		return "/article/article";
	}
	
	@Action(value="saveArticle" , results={@Result(name="message" , location = "listArticle",type="redirect" )})
	public String saveArticle(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String subCategoryIds = request.getParameter("subCategoryIds");
		User loginUser = this.getLoginUser();
		String catalogId = request.getParameter("catalogId");
		String time = request.getParameter("article.publicDate");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date publicDate = new Date();
		try {
			publicDate = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		article.setPublicDate(publicDate);
		article.setCategory( this.getCommonManager().findEntityByPK( Category.class, Long.parseLong(catalogId)));
		if( article.getId() == 0 ){
			article.setUser( loginUser );
			/********************保存同时上传文件*********************/
			if(CommUtils.isValidStr(myFileFileName) && myFile != null){
				String path = ServletActionContext.getServletContext().getRealPath("")+File.separator +"images"+File.separator+"a";
				this.saveFile( article, path, myFileFileName, myFile);
			}
		}else{
			this.getCommonManager().modifyEntity( article );
			/********************保存上传文件*********************/
			if(CommUtils.isValidStr(myFileFileName) && myFile != null){
				String path = ServletActionContext.getServletContext().getRealPath("")+File.separator +"images"+File.separator+"a";
				this.saveFile( article, path, myFileFileName, myFile);
			}
		}
		saveArticleCategory(article,subCategoryIds);
		return "message";
	}
	
	/****
	 * 保存中间表
	 */
	private void saveArticleCategory( Article article,String subCategoryIds ){
	//	if( (subCategoryIds == null || subCategoryIds.trim().length() == 0) && article.getId() !=0 ) return;
		String arr[] = subCategoryIds.split(";");
		if( article.getId() !=0 ){//清空原来的和article 的Id相关的ArticleCategory数据
			this.getCommonManager().delArticleCategory( article.getId() );
		}
		for( String obj: arr ){
			if( CommUtils.isCorrectNumber( obj )){
				ArticleCategory newObj = new ArticleCategory();
				newObj.setArticle(article);
				newObj.setCategory( this.getCommonManager().findEntityByPK( Category.class, Long.parseLong( obj )));
				this.getCommonManager().saveEntity( newObj );
			}
		}
		ArticleCategory newObj = new ArticleCategory();
		newObj.setArticle(article);
		newObj.setCategory( article.getCategory());
		this.getCommonManager().saveEntity( newObj );
	}
	
	/***
	 * 清除子栏目
	 */
	@Action("deSubArticleCategory")
	public void deSubArticleCategory(){
		int code = 0;
		try {
			this.getCommonManager().delArticleCategory( id );//清空原来的和article 的Id相关的ArticleCategory数据
		} catch (Exception e) {
			e.printStackTrace();
			code = 1;
		}
		article = this.getCommonManager().findEntityByPK( Article.class, id );
		ArticleCategory newObj = new ArticleCategory();
		newObj.setArticle(article);
		newObj.setCategory( article.getCategory());
		this.getCommonManager().saveEntity( newObj );
		this.outputData( code );
	}
	
	/**
	 * 保存图片
	 * 
	 * @param id 付款表的主键，用做图片名
	 * @param srcFileName 用户上传时图片名
	 * @param folderName 文件夹
	 * @param src 图片文件
	 */
	private void saveFile( Article article, String path, String srcFileName, File src){
		if(!CommUtils.isValidStr(srcFileName) || srcFileName.indexOf(".") == -1){
			return;
		}
		String subFilePath = ImageUtil.getFolderDir();
		
		int random = ((int)((Math.random()*9+1)*10000));
		String imageFileName = System.currentTimeMillis()+random + getExtention(srcFileName);
		
		String randomFilename = ImageUtil.genFileName(imageFileName);
		try{
			InputStream in = null;
			OutputStream out = null;
			try{
				String fileFolderName = path + subFilePath;
				File file = new File( fileFolderName );
				if( !file.isDirectory() ){
					file.mkdirs();
				}
				File dst = new File(fileFolderName  + randomFilename);
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while(in.read(buffer) > 0){
					out.write(buffer);
				}
				article.setLogo(subFilePath + randomFilename);
				if( article.getId() == 0 ){
					this.getCommonManager().saveEntity( article );
				}else{
					this.getCommonManager().modifyEntity( article );
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * 取得文件的后缀名
	 */
	private String getExtention(String fileName){
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 上架/下架
	 */
	@Action("chageArticleStauts")
	public void chageArticleStauts(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		article = this.getCommonManager().findEntityByPK( Article.class, id );
		if( status.equals("0")){
			article.setStatus(1);
		}else if( status.equals("1")){
			article.setStatus(0);
		}
		int code = 0;
		try {
			this.getCommonManager().modifyEntity( article );
		} catch (Exception e) {
			code =1 ;
			e.printStackTrace();
		}
		this.outputData( code );
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
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMyFileContentType() {
		return myFileContentType;
	}
	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	
}
