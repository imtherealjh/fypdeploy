import React, { useState } from "react";
import { axiosPrivate } from "../../api/axios";
import "../../css/articles.css";

function Articles() {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [content, setContent] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await axiosPrivate.post("<YOUR_ENDPOINT>", {
        title,
        author,
        content,
      });

      // Handle the response here (e.g., show a success message or clear the form)
      console.log("Article saved:", response.data);
      setTitle("");
      setAuthor("");
      setContent("");
    } catch (error) {
      // Handle the error here (e.g., show an error message)
      console.error("Error saving article:", error);
    }
  };

  return (
    <div className="articles-container">
      <h1>Publish Article</h1>
      <form className="articles-form" onSubmit={handleSubmit}>
        <div>
          <label htmlFor="title">Title:</label>
          <input
            type="text"
            id="title"
            name="title"
            value={title}
            onChange={(event) => setTitle(event.target.value)}
          />
        </div>
        <div>
          <label htmlFor="author">Author:</label>
          <input
            type="text"
            id="author"
            name="author"
            value={author}
            onChange={(event) => setAuthor(event.target.value)}
          />
        </div>
        <div>
          <label htmlFor="content">Content:</label>
          <textarea
            id="content"
            name="content"
            rows={10}
            value={content}
            onChange={(event) => setContent(event.target.value)}
          />
        </div>
        <button type="submit">Publish</button>
      </form>
    </div>
  );
}

export default Articles;
