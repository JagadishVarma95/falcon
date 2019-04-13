# falcon

Spring Boot Ecommerce application

How to run and deploy ?
1. Git clone the repo.
2. Change the mysql username,password & dbUrl as per your local in application.properties
3. Run mvn clean install.
4. Deploy the war file in target folder

API'S

1.Add item to database
  POST http://localhost:8080/falcon/item/add
  Request : {
	          "quantity" : 10,
	          "name" : "chairs"
            }
         
2. Update item quantity details
  POST http://localhost:8080/falcon/item/update/quantity
   Request : {
	          "id" : 1,
	          "quantity" : 20,
            }
 
3.Get the item details
  GET http://localhost:8080/falcon/item/1
  
4.Fetch all the items in the inventory
  GET http://localhost:8080/falcon/item/details?page=0&size=5
      Default page = 0 & size 10
      
5.Place an order
  POST http://localhost:8080/falcon/order/add
  {
	"emailId" : "jagadishvarma@gmail.com",
	"orderItems" : [{
	    	"itemId" : 1,
	    	"quantity" : 2
	       },{
		     "itemId" : 2,
		     "quantity" : 4
	       }]
   }
6.Get the Order details
  GET http://localhost:8080/falcon/item/1
  
7.Fetch all the orders present in db or orders by email
  GET http://localhost:8080/falcon/item/details?emailId={emailId}&page=0&size=5
  GET http://localhost:8080/falcon/item/details?page=0&size=5
      Default page = 0 & size 10
      
8.Delete item
  DELETE http://localhost:8080falcon/item/delete/1
  
  
