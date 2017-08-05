/**
 * Created by zzg on 2017/4/26.
 */
var Page ={
	//当前页
	CurrentPage:1,
	//totalPage当前数据中的页数
	TotalPage:"",
	//页面显示条数(通过设置传入)
	PageSize:10,
	//总的条数(后台传入)
	TotalSize:"",
	initPageInfo:function(TotalSize,PageSize){
		Page.PageSize=PageSize;
		if(TotalSize<=0){
			Page.TotalSize=0;
		}else{
			Page.TotalSize=TotalSize;
		}
		var NUM=TotalSize%PageSize==0?TotalSize/PageSize:parseInt(TotalSize/PageSize)+1;
		Page.TotalPage=NUM;
		$("#corrent").text(Page.CurrentPage);
	},
	
    next:function(){
    	if(Page.CurrentPage<Page.TotalPage){
			Page.CurrentPage+=1;
		}
		if(Page.CurrentPage>=Page.TotalPage){
			//alert("最后一页！");
		}
    },
    pre:function(){
		if(Page.CurrentPage>1){
			Page.CurrentPage=Page.CurrentPage-1;
		}
    }

}

