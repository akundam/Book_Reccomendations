import React, { useState } from "react";
import API from "../services/api";

const SearchBooks = ({ setSelectedBook }) => {
  const [query, setQuery] = useState("");
  const [books, setBooks] = useState([]);

  const handleSearch = async () => {
    try {
      const response = await API.get(`/books/search?query=${query}`);
      setBooks(response.data);
    } catch (error) {
      
    }
  };

  return (
    <div className="card">
      <h2>Search Books</h2>
      <input
        type="text"
        placeholder="Search by title or author"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>
      <ul>
        {books.map((book) => (
          <li key={book.isbn} onClick={() => setSelectedBook(book)}>
            {book.title} by {book.author}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchBooks;
