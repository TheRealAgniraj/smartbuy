1. Import all 3 projects(productsprovider, ordersprovider, smartbuy) in STS or Eclipse.
2. Start the projects [productsprovider -> ordersprovider -> smartbuy].
3. Load below URL in browser to launch the Swagger UI page.
	http://localhost:8080/smartbuy/swagger-ui.html
4. Click 'show/hide' option to see the running services.
5. Play with the services.

Samples Request Data
---------------------
GET /cancelorder		: orderID=112
GET /getdetails/{productID}	: productID=1 or 2 or 3 or 4 or 5
POST /placeorder		: 
	Payload ={
  		"productID": "2",
 		 "quantity": 1,
  		"walletBalance": 100
		}

Swagger JSON URL -> http://localhost:8080/smartbuy/v2/api-docs