async function goSearch(){
      let keyword=document.getElementById("keyword").value;
      // KEY(X), Data(JSON : String)
      const res1=await fetch("http://localhost:8081/s10/api/products/search",{
              method : "POST",
              headers :  { //헤더는 우리가넘기는 타입을 서버에 알려줘야 한다
                  "Content-Type" : "application/json;charset=UTF-8"
               },
               body : JSON.stringify({"keyword" : keyword}) // let keyword 를 스트링형태로 서버로 전송된다
       });
      if(!res1.ok){
                       throw new Error("Network response was not ok");
      }
      const products=await res1.json();
      // 리스트 출력.....
      resultList(products);
}
function resultList(products){
    let pList="";
    products.forEach((product)=>{
           let pnum=product.product_number;
           pList+=`<tr>`;
           pList+=`<td>${product.product_number}</td>`;
           pList+=`<td><a href="/s10/detail/${pnum}">${product.product_name}</a></td>`;
           pList+=`<td><input id="iv${pnum}" type="number" class="form-control" value="${product.inventory}"/><button class="btn btn-outline-secondary" onclick="goInventory(${pnum})">Update</button></td>`;
           pList+=`<td>${product.price}</td>`;
           pList+=`<td>${product.manufacturer}</td>`;
           pList+=`</tr>`;
      });
      document.getElementById("pList").innerHTML=pList;
}
async function goInventory(pnum){
        console.log(pnum);
        // id="iv15"
        let inventory=parseInt(document.getElementById("iv"+pnum).value); // "5"->5   인풋박스안에 있는 값을 저장함 그래서 바디부분에 전달해줌 서버로   각 고유한 아이디 값을 가져오기 위해 이름을 동적으로 지정한것
        const res1=await fetch(`http://localhost:8081/s10/api/products/${pnum}/inventory`,{
                      method : "PUT",   // update
                      headers :  {
                          "Content-Type" : "application/json;charset=UTF-8"
                       },
                       body : JSON.stringify({"inventory" : inventory})
               });
              if(!res1.ok){
                               throw new Error("Network response was not ok");
              }
         const res2=await res1.text(); // text(), json()  응답받은 데이터가 텍스트면 텍스트 json이면 json
         alert(res2); // 다이얼로그->Modal(모달)
         location.href="/s10/list";
}

async function restProductListPaging(page,size){ //  1, 10 / 4, 10  맨처음에 화면로딩시 restProductListPaging 가 호출되어 기본값인 페이징이 실행됨
   let formData={
       page : page,
       size : size
     };
   const res1=await fetch("http://localhost:8081/s10/api/products/paging", {
       method : "POST",
       headers :  {
                         "Content-Type" : "application/json;charset=UTF-8"
      },
      body : JSON.stringify(formData) // Object->JSON(String)  객체전달
     });
   if(!res1.ok){
                 throw new Error("Network response was not ok");
   }
    const pm=await res1.json();
    resultList(pm.lists); // 페이징 인경우.... 페이지당 결과 리스트 출력  맨처음에 호출됨가 동시에 자동으로 호출해줘서 리스트를 뿌릴수 있게 해줌
    // [Prev 1,2,3,4, Next]
    console.log(pm);
    pagingList(pm);
}

function pagingList(pm){
    let pagination=document.querySelector(".pagination"); //클래스 선택해서 변수에 담음
    let plist="";
    // 이전
    if(pm.prev){
         plist+=`<li class="page-item"><a class="page-link" href="/s10/list?page=${pm.startPage-1}">Prev</a></li>`;
    }
    // 페이지 리스트
     for(let page=pm.startPage;page<=pm.endPage;page++){ //1부터 2까지 반복 해서 뿌려줌  예시> 1,2,3,4,5
          plist+=`<li class="page-item ${pm.cri.page==page ? 'active' : ''}"><a class="page-link" href="/s10/list?page=${page}">${page}</a> </li>`;
     }
     // 다음
     if(pm.next){
              plist+=`<li class="page-item"><a class="page-link" href="/s10/list?page=${pm.endPage+1}">Next</a></li>`;
     }
    document.querySelector(".pagination").innerHTML=plist;      //Prev 12345 Next//
}