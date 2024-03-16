import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";

const PostPage = lazy(() => import("./pages/PostPage"));
const TimelinePage = lazy(() => import("./pages/TimelinePage"));

export const router = createBrowserRouter([
  {
    path: "/",
    element: <TimelinePage />,
  },
  {
    path: "/post",
    element: <PostPage />,
  },
]);
