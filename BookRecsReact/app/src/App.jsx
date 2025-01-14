import React, { useState } from "react";
import Login from "./components/Login";
import Register from "./components/Register";
import SearchBooks from "./components/SearchBooks";
import BookDetails from "./components/BookDetails";
import "./index.css";

function App() {
  const [loggedInUser, setLoggedInUser] = useState(null); 
  const [selectedBook, setSelectedBook] = useState(null); 

  return (
    <div className="App">
      <h1>Book Review System</h1>
      {!loggedInUser ? (
        <>
          <Login setLoggedInUser={setLoggedInUser} />
          <Register />
        </>
      ) : (
        <>
          <SearchBooks setSelectedBook={setSelectedBook} />
          {selectedBook && (
            <BookDetails book={selectedBook} loggedInUser={loggedInUser} />
          )}
        </>
      )}
    </div>
  );
}

export default App;
