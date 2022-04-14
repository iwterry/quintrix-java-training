Fourth week's assignment
- Spring Boot hello world assignment: 
  - for request GET /hello-world
- Spring Boot using POST and GET requests:
  - uses RequestParam annotation for request GET /people?shouldSortByAgeAsc=true
    - NOTE: by default, shouldSortByAgeAsc is false.
  - uses PathVariable annotation for request GET /people/{id}
  - uses RequestBody annotation for request POST /people