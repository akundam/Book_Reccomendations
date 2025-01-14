import React from "react";
import Reviews from "./Reviews";

const BookDetails = ({ book, loggedInUser }) => {
  return (
    <div className="card">
      <h2>{book.title}</h2>
      <p>
        <strong>Author:</strong> {book.author}
      </p>
      <p>
        <strong>ISBN:</strong> {book.isbn}
      </p>
      <Reviews book={book} loggedInUser={loggedInUser} />
    </div>
  );
};

export default BookDetails;
