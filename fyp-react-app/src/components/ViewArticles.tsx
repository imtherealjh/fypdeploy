import React, { useEffect, useState } from "react";
import "../css/viewarticles.css";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

interface Article {
  id: string;
  title: string;
  author: string;
  content: string;
}

function ViewArticles() {
  const dummyData: Article[] = [
    {
      id: "1",
      title: "How to Stay Healthy",
      author: "Dr. John Doe",
      content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
    },
    {
      id: "2",
      title: "Understanding Diabetes",
      author: "Dr. Jane Smith",
      content: "Suspendisse tincidunt ut lacus nec cursus...",
    },
    {
      id: "3",
      title: "What is AIDS",
      author: "Dr. Mary Jane",
      content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
    },
    {
      id: "4",
      title: "Who is Spiderman?",
      author: "Dr. Eddie Brock",
      content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
    },
  ];

  const [articles, setArticles] = useState<Article[]>(dummyData);
  // const axiosPrivate = useAxiosPrivate();

  // useEffect(() => {
  //   const fetchArticles = async () => {
  //     try {
  //       const response = await axiosPrivate.get("<your-backend-api-url>/articles");
  //       setArticles(response.data);
  //     } catch (error) {
  //       console.error("Error fetching articles:", error);
  //     }
  //   };

  //   fetchArticles();
  // }, []);

  return (
    <div className="view-articles">
      <h2>Public Articles</h2>
      {articles.map((article: Article) => (
        <div key={article.id} className="article-card">
          <h3>{article.title}</h3>
          <p className="author">By {article.author}</p>
          <p>{article.content}</p>
        </div>
      ))}
    </div>
  );
}

export default ViewArticles;
