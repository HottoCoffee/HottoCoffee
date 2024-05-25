import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import { MainLayout } from "./features/MainLayout";

const PostPage = lazy(() => import("./pages/PostPage"));
const TimelinePage = lazy(() => import("./pages/TimelinePage"));

export const router = createBrowserRouter([
  {
    path: "/",
    element: <MainLayout />,
    children: [
      {
        index: true,
        element: <TimelinePage />,
      },
      {
        path: "/post",
        element: <PostPage />,
      },
    ],
  },
]);
