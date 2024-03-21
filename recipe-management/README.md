# Recipe Management Application

## Functional Requirements

### Database Model

The application includes two models at the database level: Recipe and Ingredient.

- **Recipe** contains:
   - Name (string)
   - Description (string)
   - Cooking Instructions (string)
   - A list of Ingredients

- **Ingredient** contains:
   - Name (string)
   - Quantity (string, e.g., "2 cups" or "1 teaspoon")
   - Recipe (relation to Recipe)

Both models include a timestamp for when the entity was created and a timestamp for the last update.

### API

The application provides a JSON API for users to perform CRUD operations on recipes and ingredients. Additionally, the API allows users to:
- Add an ingredient to a recipe
- Remove an ingredient from a recipe

All lists of entities (recipes and ingredients within a recipe) are sorted alphabetically (recipes by name, ingredients by name within each recipe).
## API Reference

### Recipes
#### Get all recipes

```http
  GET /api/recipes
```

#### Get recipe by id

```http
  GET /api/recipes/${id}
```

| Parameter | Type     | Description       |
| :-------- | :------- |:------------------|
| `id`      | `string` | **Required**. Id  |

#### Save recipe in the database

```http
  Post /api/recipes
```

| Request Body | Type   | Description             |
|:-------------|:-------|:------------------------|
| `Recipes`    | `json` | **Required**. Recipes   |

#### Update recipe in the database

```http
  Put /api/recipes
```

| Request Body | Type   | Description             |
|:-------------|:-------|:------------------------|
| `Recipes`    | `json` | **Required**. Recipes   |

#### Delete recipe by id

```http
  Delete /api/recipes/${id}
```

| Parameter | Type     | Description       |
| :-------- | :------- |:------------------|
| `id`      | `string` | **Required**. Id  |

### Ingredients

#### Get all ingredients

```http
  GET /api/ingredients
```

#### Get ingredient by id

```http
  GET /api/ingredients/${id}
```

| Parameter | Type     | Description       |
| :-------- | :------- |:------------------|
| `id`      | `string` | **Required**. Id  |

#### Update ingredient in the database

```http
  Put /api/ingredients
```

| Request Body  | Type   | Description              |
|:--------------|:-------|:-------------------------|
| `ingredients` | `json` | **Required**. Ingredient |

#### Delete Ingredient by id

```http
  Delete /api/ingredients/${id}
```

| Parameter | Type     | Description       |
| :-------- | :------- |:------------------|
| `id`      | `string` | **Required**. Id  |

#### Add ingredient to recipe

```http
  Post /api/ingredients/${id}
```

| Request Body   | Type     | Description                |
|:---------------|:---------|:---------------------------|
| `Ingredient`   | `json`   | **Required**. Ingredient   |

| Parameter      | Type     | Description                |
| :------------- | :------- | :------------------------- |
| `id`           | `string` | **Required**. Id           |
## Technical Requirements

### Technology Stack

- Compatible with the JVM
- Java 17 as the programming language
- Maven for build automation
- Spring Boot as the application framework
- PostgreSQL for the database

## Documentation

### Database Configuration Details

1. **Database Setup**: Ensure that PostgreSQL is installed on your system. You can download and install PostgreSQL from the [official website](https://www.postgresql.org/download/).

2. **Configuration Database**: Create your PostgreSQL database.

3. **Configuration File**: Database configuration is located in the `application.properties` file under `src/main/resources`.
   
4. **Update `application.properties`:** Open the `src/main/resources/application.properties` file and update the following properties with your PostgreSQL database connection details:

   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect


## Instructions

To compile (also runs unit tests)

```
mvn clean package
```

## To run the webapp manually

```
mvn spring-boot:run
```

....and navigate your browser to  http://localhost:8080/

## To run integration tests

```
mvn spring-boot:run
mvn verify
```