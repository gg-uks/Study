function goRegister(){
    //POST 방식으로 저장 http://localhost:8082/restful/api/products[제품등록하기]--->
     let product_name = document.getElementById('product_name').value;
       let inventory = parseInt(document.getElementById('inventory').value);
       let price = parseFloat(document.getElementById('price').value);
       let manufacturer = document.getElementById('manufacturer').value;
 // JavaScript Object(JSON)

 let formData={
    "product_name" : product_name,
    "inventory" : inventory,
    "price" : price,
    "manufacturer" : manufacturer
    };

    fetch("http://localhost:8082/restful/api/products",{
     method:"POST",
     headers:{
      "Content-Type" : "application/json" //이 폼데이터의 형식은 json스트링 포맷으로 전달하겠다라는 뜻
      },
        body : JSON.stringify(formData) //formData 는 JSON 인데 서버로 날라갈떄는 스트링 타입으로 보내줘야 한다
     })
    .then(function(response){
      if(!response.ok){
        throw new Error("Network response was not ok");
      }
      location.href="/restful/list"; //restful
      })

    .catch(function(error){
            console.log("error",error);
        });
}