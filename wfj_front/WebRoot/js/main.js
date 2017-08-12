function openW(name,url,type){
    appcan.window.open(name,url,type);//name为打开窗口名，url为窗口连接，type为窗口打开方式
}
function closeW(){
    appcan.window.close(-1);//关闭当前窗口
}
function openFrame(x){
            if(x==0){
              $("#recommend").addClass("bc-bg1");
              $("#shopping,#food,#entertainment,#beauty,#hotel").removeClass("bc-bg1"); 
              appcan.frame.open("content", "shopping.html",radwidth+8,titHeight+10); 
            }else if(x==1){
                $("#shopping").addClass("bc-bg1");
                $("#recommend,#food,#entertainment,#beauty,#hotel").removeClass("bc-bg1");
                appcan.frame.open("content", "restaurant.html",radwidth+8,titHeight+10);  
            }else if(x==2){
                $("#food").addClass("bc-bg1");
                $("#recommend,#shopping,#entertainment,#beauty,#hotel").removeClass("bc-bg1"); 
                appcan.frame.open("content", "shopping.html",radwidth+8,titHeight+10); 
            }else if(x==3){
                $("#entertainment").addClass("bc-bg1");
                $("#recommend,#food,#shopping,#beauty,#hotel").removeClass("bc-bg1"); 
                appcan.frame.open("content", "restaurant.html",radwidth+8,titHeight+10); 
            }else if(x==4){
                $("#beauty").addClass("bc-bg1");
                $("#recommend,#food,#entertainment,#shopping,#hotel").removeClass("bc-bg1"); 
                appcan.frame.open("content", "shopping.html",radwidth+8,titHeight+10); 
            }else if(x==5){
                $("#hotel").addClass("bc-bg1");
                $("#recommend,#food,#entertainment,#beauty,#shopping").removeClass("bc-bg1");
                appcan.frame.open("content", "restaurant.html",radwidth+8,titHeight+10);  
            } 
}
function setVal(key,value){
    appcan.locStorage.setVal(key,value);
}
function getVal(key){
    var value = appcan.locStorage.getVal(key);
    return value;
}
function $$(id)
{
    return document.getElementById(id);
}
 