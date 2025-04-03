function goUpdate(product_number){
    location.href="/restful/update/"+product_number;
}

function goSubmit(product_number){
  let product_name = document.getElementById("product_name").value;
  let inventory = parseInt(document.getElementById("inventory").value);
  let price = parseFloat(document.getElementById("price").value);

    let formData={
    product_number : product_number,
    product_name : product_name,
    inventory : inventory,
    price : price
    };
    //서버에 수정할값을 넘김
    //PUT(POST방식이랑 같음)(업데이트방식으로생각하면된다) http://localhost:8082/restful/api/products/{id} [특정 제품 수정하기]
    fetch("http://localhost:8082/restful/api/products/"+product_number,{method : "PUT" ,
                                            headers:{"Content-Type":"application/json;charset=UTF-8"},
                                            body:JSON.stringify(formData)}) //바디에 json객체를 전달해줘야 한다
    .then(function(response){
        if(!response.ok){
        throw new Error("Network response was not ok");
        }
        location.href="/restful/list";
    })
    .catch(function(error){
                console.log("error",error);
            });
}