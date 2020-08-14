# Adidas challenge

## Requirements

* Docker 

## Run

To run this project you need to use docker-compose

```
docker-compose up -d
```

## How to use 

You can test it using any rest client as Postman or Insomnia.

### Endpoints

| PATH   | METHOD  | PARAMS  |  Authenticated | Description  |
|---|---|---|---|---|
|  /product | GET  | -  |  Yes  |  Returns all products  |
|  /product/{productId} |  GET  |  PathParam(productId) Long  | Yes   | Returns product by id   |
|  /product  | POST  | BodyParam ProductDto, QueryParam(currency) String  | Yes   |  Creates new product. If currency is not null the price will be converted to EUR.  |
| /product/{productId}  | PUT   | BodyParam ProductDto, PathParam(productId) Long  | Yes  | Updates product  |
| /product/{productId}  |  DELETE  |  PathParam(productId) Long |  Yes |  Product with the given id will be deleted |
| /category  |  GET | -  | Yes  | Returns list of root categories with their children subcategories  |
|   /category/{categoryId} | GET  |  PathParam(categoryId) Long | Yes  | Returns a category with its children and parent |
| /category  | POST  | QueryParam(name) String, QueryParam(parentId) Long  | Yes  |  Creates new category with given name. Parent is optional, if there is no parent then it will be created as root category. |
|   /category/{categoryId} |  PATCH  |  QueryParam(name) String, PathParam(categoryId) Long | Yes  |  Category's  name will be updated   |
|  /category/{categoryId |  DELETE | PathParam(categoryId) Long    | Yes  | Category with the given id and its children will be deleted  |

### Authenticate

This service is secured using Basic authentication with in memory user. To test it use this credentials:

* username: username
* password: testPassword


## Improvements

* Architecture: Create a new microservice to centralize security and use it as a gateway service.
* Security: Use oAuth2 instead of Basic auth.
* Test: Add more tests. Add integration test
* Common library
* Fixes: The current category entity data model is a workaraound to prevent circular reference. 
