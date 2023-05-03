import { Link } from "react-router-dom";
import ViewArticles from "../../components/ViewArticles";

export default function Articles() {
  return (
    <>
      <h1>Articles</h1>
      <div className="d-flex flex-column gap-2">
        <ViewArticles />
        <Link to="publish">
          <button className="btn btn-primary w-100" type="submit">
            Publish New Article
          </button>
        </Link>
      </div>
    </>
  );
}
