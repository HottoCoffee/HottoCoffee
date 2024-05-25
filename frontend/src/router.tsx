import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import { MainLayout } from "./features/MainLayout";
import { ProfilePage } from "./pages/ProfilePage";

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
      {
        path: "/profile",
        element: <ProfilePage />,
      },
    ],
  },
]);
