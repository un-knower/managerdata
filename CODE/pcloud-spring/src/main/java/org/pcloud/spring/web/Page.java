package org.pcloud.spring.web;
import java.util.* ;  
public class Page {  
  
    //结果集  
    private List<Map<String,Object>> List ;  
      
    //查询总记录数  
    private int total ;  
      
    //每页多少条数据  
    private int pageSize ;  
      
    //第几页  
    private int pageNo ;  
    
    //排序 如：age asc | age asc,time desc ,..... 
    private String order;
      
    /**  
     * 总页数  
     * @return  
     */  
    public int getTotalPages(){  
        return (total + pageSize -1) / pageSize ;  
    }  
      
    /**  
     * 取得首页  
     * @return  
     */  
    public int getTopPageNo(){  
        return 1 ;  
    }  
      
    /**  
     * 上一页  
     * @return  
     */  
    public int getPreviousPageNo(){  
        if(pageNo <= 1){  
            return 1 ;  
        }  
        return pageNo - 1 ;  
    }  
      
    /**  
     * 下一页  
     * @return  
     */  
    public int getNextPageNo(){  
        if(pageNo >= getBottomPageNo()){  
            return getBottomPageNo() ;  
        }  
        return pageNo + 1 ;  
    }  
      
    /**  
     * 取得尾页  
     * @return  
     */  
    public int getBottomPageNo(){  
        return getTotalPages() ;  
    }  
  
	public List<Map<String, Object>> getList() {
		return List;
	}

	public void setList(List<Map<String, Object>> list) {
		List = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {  
        return pageSize;  
    }  
  
    public void setPageSize(int pageSize) {  
        this.pageSize = pageSize;  
    }  
  
    public int getPageNo() {  
        return pageNo;  
    }  
    
    public int getPageNo(String dbType) {  
		if(dbType.equals("oracle")||dbType.equals("mysql") || dbType.equals("sqlserver")){
			return pageNo-1;
		}
		return pageNo;  
}  
  
    public void setPageNo(int pageNo) {  
        this.pageNo = pageNo;  
    }
    
    

	public String getOrder() {
		if(this.order != null && !this.order.equals("")){
			return "order by " + this.order;
		}
		return "";
	}

	public void setOrder(String order) {
		this.order = order;
	}  
}  