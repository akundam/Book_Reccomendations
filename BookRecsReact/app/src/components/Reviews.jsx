import React, { useEffect, useState } from "react";
import API from "../services/api";

const Reviews = ({ book, loggedInUser }) => {
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState({ rating: 0, comment: "" });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (book && book.isbn) {
      fetchReviews();
    }
  }, [book]);

  const fetchReviews = async () => {
    setLoading(true);
    try {
      const response = await API.get(`/reviews/book/${book.isbn}`);
      setReviews(response.data);
    } catch (error) {
      console.error("Error fetching reviews:", error);
      alert("Failed to fetch reviews. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const handleAddReview = async () => {
    try {
      await API.post(`/reviews/${book.isbn}?username=${loggedInUser}`, newReview);
      alert("Review added!");
      fetchReviews();
    } catch (error) {
      console.error("Error adding review:", error);
    }
  };
  

  if (!book) {
    return <div>Please select a book to view details and reviews.</div>;
  }

  return (
    <div>
      {loading && <p>Loading...</p>}
      {!loading && (
        <>
          <h3>Reviews</h3>
          {reviews.length === 0 ? (
            <p>No reviews yet for this book. Be the first to add one!</p>
          ) : (
            <ul>
              {reviews.map((review) => (
                <li key={review.id}>
                  {review.rating}/5 - {review.comment} (by {review.user.username})
                </li>
              ))}
            </ul>
          )}
          <div className="card">
            <h4>Add a Review</h4>
            <input
              type="number"
              min="1"
              max="5"
              placeholder="Rating"
              value={newReview.rating}
              onChange={(e) =>
                setNewReview({ ...newReview, rating: parseInt(e.target.value) })
              }
            />
            <textarea
              placeholder="Comment"
              value={newReview.comment}
              onChange={(e) =>
                setNewReview({ ...newReview, comment: e.target.value })
              }
            />
            <button onClick={handleAddReview}>Submit</button>
          </div>
        </>
      )}
    </div>
  );
};

export default Reviews;
