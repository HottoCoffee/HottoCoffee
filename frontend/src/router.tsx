import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

const PostPage = lazy(() => import("./pages/PostPage"));

export const router = createBrowserRouter([
  {
    path: "/post",
    element: <PostPage />,
  },
]);
