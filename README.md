HTTP project Two models: 

Product 
    - Product Id (must be unique) 
    - Product Name 
    - Price 
    - Seller Name 

Seller 
    - Seller Name (must be unique) - x

Create/Read functionality on Seller 
GET /seller/ - All sellers - x
POST /seller/ - Seller names must be non-null (x) & unique (x)

CRUD functionality on Product 
- GET /product/ - All products 
- GET /product/{id} - Get a single product 
  - We should get a 404 error when we try to access a non-existed product. (x)
- POST /product/ - Add a single product (x)
  - Product ids should be non-null and unique (x)
  - Product names should be non-null (x)
  - Price should be over 0 (x)
  - Seller name should refer to an actually existing seller (x)
- PUT /product/{id} - Update a single product 
  - Product names should be non-null (x)
  - Price should be over 0 (x)
  - Seller name should refer to an actually existing seller (x)
- DELETE /product/{id} - Delete a single product 
  - DELETE should always return 200, regardless of if the item existed at the start or not. This is convention.

Unit testing of service classes (x)
Logging within service classes (x)

Javalin