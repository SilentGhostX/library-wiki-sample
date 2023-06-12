# Book Search API

The Book Search API is a Spring Boot application that provides endpoints for searching books based on title, author, and category. It utilizes Apache Lucene for indexing and searching books, along with other libraries like Lombok, MapStruct, Spring Data, and Spring Validation.

## Features

- Search books by title, author, and category or any fields of you choice.
- Sort search results in alphabetical order by any field.
- Input validation for search queries.
- Integration with Apache Lucene for efficient indexing and searching.
- Utilization of Lombok for reducing boilerplate code.
- MapStruct for mapping between entities and DTOs.
- Spring Data for database interaction.

## Prerequisites

Make sure you have the following installed:

- Java Development Kit (JDK) 17

## Installation

Follow these steps to set up and run the project:

1. Clone the repository: `git clone https://github.com/your-username/book-search-api.git`
2. Navigate to the project directory: `cd book-search-api`
3. Build the project: `mvn clean install`
4. Run the application: `mvn spring-boot:run`

## Usage

Once the application is up and running, you can use the following endpoints to interact with the API:

- `/books`: Retrieve the list of all books in the database `GET` or create them by `POST`
- `/books/search`: Search for books based on a search term (`GET`)
- `/books/index`: Index all books that is not indexed yet.
- `/books/clear-and-index`: Clear all indexed books within index registry and index books again.

Please refer to the [API Endpoints](#api-endpoints) section for detailed information on how to use these endpoints.

## API Endpoints

### Get All Books

Retrieve the list of all books in the database.

- **Endpoint**: `/books`
- **Method**: GET

### Search Books

Search for books based on a search term. The search term will be matched against the book's title, author, and category fields.

- **Endpoint**: `/search`
- **Method**: GET
- **Query Parameters**:
    - `q` (required): The search term
    - `page` Page index
    - `size` Size of books per page
    - `fields` Fields used for search `default` (`title`, `author`, `category`)
    - `orderBy` Field used for order `default` (`title`)
    - `order` Order in ascending order descending `values` (`asc`, `desc`), `default`: `asc`

### Other endpoints
You can find all endpoints exposed by the project within the postman collection.



