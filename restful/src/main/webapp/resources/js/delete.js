function goDel(product_number){
//DELETE http://localhost:8082/restful/api/products/{id} [특정 제품 삭제하기]
    let deleteUrl="http://localhost:8082/restful/api/products/"+product_number;
    fetch(deleteUrl,{
        method:"DELETE" //DELETE 방식 RESTful서비스
    })
    .then(function(response){
        if(!response.ok){
            throw new Error("Network response was not ok");
        }
        location.href="/restful/list"; //삭제성공후 리스트페이지로
    })
    .catch(function(error){
            console.log("error",error);
        });
}