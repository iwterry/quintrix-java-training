Fourth week's assignment
- Spring Boot hello world assignment: 
  - for request GET /api/v1/hello-world
- Spring Boot using POST and GET requests:
  - uses RequestParam annotation for request GET /api/v1/people?shouldSortByAgeAsc=true
    - NOTE: by default, shouldSortByAgeAsc is false.
  - uses PathVariable annotation for request GET /api/v1/people/{id}
  - uses RequestBody annotation for request POST /api/v1/people