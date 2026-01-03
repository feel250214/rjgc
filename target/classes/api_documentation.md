# HR Management API Documentation

## Server Configuration

- **Base URL**: `http://localhost:8080/api`
- **Context Path**: `/api`
- **Port**: `8080`

## Common 404 Error Causes

1. Missing context path `/api` in URL
2. Incorrect HTTP method (e.g., using GET instead of POST)
3. Typo in URL path
4. Incorrect parameter format
5. Missing required request body

## API Endpoints

### 1. Create Employee

**URL**: `POST http://localhost:8080/api/employees`
**HTTP Method**: POST
**Content-Type**: application/json

**Required Fields**:

- `username` (string, unique)
- `name` (string)
- `departmentId` (long)

**Optional Fields**:

- `password` (string, default: "123456")
- `email` (string)
- `phone` (string)
- `positionId` (long)

**JSON Example**:

```json
{
  "username": "zhangsan",
  "password": "123456",
  "name": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138000",
  "departmentId": 1,
  "positionId": 1
}
```

### 2. Update Employee

**URL**: `PUT http://localhost:8080/api/employees/{id}`
**HTTP Method**: PUT
**Content-Type**: application/json

**Fields to Update**:

- `username` (string, optional, unique)
- `password` (string, optional)
- `name` (string, optional)
- `email` (string, optional)
- `phone` (string, optional)

**JSON Example**:

```json
{
  "username": "zhangsan",
  "name": "张三-更新",
  "email": "zhangsan-updated@example.com",
  "phone": "13800138001"
}
```

### 3. Update Employee Profile

**URL**: `PUT http://localhost:8080/api/employees/profile/{id}`
**HTTP Method**: PUT
**Content-Type**: application/json

**Fields to Update** (non-sensitive information only):

- `name` (string, optional)
- `email` (string, optional)
- `phone` (string, optional)

**JSON Example**:

```json
{
  "name": "张三-更新",
  "email": "zhangsan-updated@example.com",
  "phone": "13800138001"
}
```

### 4. Delete Employee

**URL**: `DELETE http://localhost:8080/api/employees/{id}`
**HTTP Method**: DELETE
**No Request Body Required**

### 5. Get All Employees

**URL**: `GET http://localhost:8080/api/employees`
**HTTP Method**: GET
**Query Parameters** (optional):

- `page` (int, default: 0)
- `size` (int, default: 10)

### 6. Get All Employees (No Pagination)

**URL**: `GET http://localhost:8080/api/employees/all`
**HTTP Method**: GET

### 7. Get Employees by Department ID

**URL**: `GET http://localhost:8080/api/employees/department/{departmentId}`
**HTTP Method**: GET

### 8. Get Employee by ID

**URL**: `GET http://localhost:8080/api/employees/{id}`
**HTTP Method**: GET

## Response Format

All API responses follow this format:

```json
{
  "success": true, // true or false
  "code": 200, // HTTP status code
  "message": "Success", // response message
  "data": {} // response data (optional)
}
```

## Example Error Responses

### Username Already Exists

```json
{
  "success": false,
  "code": 400,
  "message": "Username already exists",
  "data": null
}
```

### Department ID Cannot Be Null

```json
{
  "success": false,
  "code": 400,
  "message": "Department ID cannot be null",
  "data": null
}
```

### Employee Not Found

```json
{
  "success": false,
  "code": 404,
  "message": "Employee not found",
  "data": null
}
```

## Testing Tips

1. Always include the context path `/api` in the URL
2. Use the correct HTTP method for each endpoint
3. Ensure JSON format is valid
4. Include all required fields for POST/PUT requests
5. Check that employee IDs exist before updating/deleting
6. Verify department IDs and position IDs are valid

## Employee IDs Range

From the sample data, employee IDs range from 18 to 24.

## Common Data Types

- `id`: Long (e.g., 1, 2, 3)
- `username`: String (e.g., "zhangsan")
- `password`: String (e.g., "123456")
- `name`: String (e.g., "张三")
- `email`: String (e.g., "zhangsan@example.com")
- `phone`: String (e.g., "13800138000")
- `departmentId`: Long (e.g., 1, 2, 3)
- `positionId`: Long (e.g., 1, 2, 3)
