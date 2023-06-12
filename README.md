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

# Apache Lucene

Apache Lucene is a powerful and high-performance open-source search library written in Java. It provides full-text search capabilities for indexing and searching structured and unstructured data. Lucene uses an inverted index structure to efficiently store and retrieve text-based information, making it an ideal choice for applications that require fast and accurate searching.

**Key Features of Apache Lucene:**

- **Indexing**: Lucene allows you to create an index of your data, which enables efficient searching and retrieval.
- **Querying**: Lucene provides a flexible query API that supports complex search queries, including boolean queries, wildcard searches, phrase searches, and more.
- **Ranking**: Lucene employs a scoring mechanism to rank search results based on relevance, allowing you to retrieve the most relevant documents first.
- **Analysis**: Lucene includes a powerful analysis toolkit for processing text data, including tokenization, stemming, stop-word removal, and other linguistic operations.
- **Extensibility**: Lucene is highly extensible and can be integrated with other tools and frameworks to enhance search capabilities.
- **Performance**: Lucene is designed for high performance, with optimizations for memory usage, disk access, and search speed.

By leveraging the capabilities of Apache Lucene, Book Search API can deliver fast and accurate search results, allowing users to find books based on their title, author, and category efficiently.

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

## Possible Enhancements

Here are some potential future enhancements that can be considered for further improving the functionality and user experience of the Book Search API:

- **Integration with Apache Solr or Elasticsearch**: Explore the possibility of integrating Apache Solr or Elasticsearch as the search engine for the Book Search API. Both Solr and Elasticsearch build upon the powerful search capabilities of Apache Lucene and provide additional features, scalability, and performance enhancements.
- **Advanced Search Features**: Leverage the advanced search capabilities offered by Apache Solr or Elasticsearch, such as faceted navigation, advanced query syntax, multi-language support, and relevancy scoring, to enhance the search experience and provide more accurate and comprehensive search results.
- **Scalability and Performance**: Consider using Apache SolrCloud or Elasticsearch clusters to achieve horizontal scalability, fault tolerance, and improved search performance, enabling the API to handle larger volumes of data and user requests.
- **Real-time Indexing and Updates**: Utilize the real-time indexing and updates features provided by Apache Solr or Elasticsearch to ensure that new books or modifications to existing books are immediately reflected in the search results without delays.
- **Monitoring and Analytics**: Take advantage of the monitoring and analytics capabilities offered by Apache Solr or Elasticsearch to gain insights into search usage patterns, monitor system health, and optimize the search engine performance.
- **Custom Search Filters**: Implement custom search filters based on specific book attributes or metadata, such as publication year, genre, or author popularity, to enable more precise search refinement.
- **Auto-Suggest and Spell Correction**: Introduce auto-suggest functionality and spell correction using Apache Solr or Elasticsearch to provide users with search suggestions, handle typos, and enhance the search experience.
- **Localization and Language Support** Utilize the language-specific analyzers and tokenizers provided by Apache Solr or Elasticsearch to improve search accuracy for different languages and support multi-language search queries.

