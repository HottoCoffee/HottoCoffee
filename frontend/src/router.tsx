import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import { MainLayout } from "./features/MainLayout";

const PostPage = lazy(() => import("./pages/PostPage"));
const TimelinePage = lazy(() => import("./pages/TimelinePage"));
const ProfilePage = lazy(() => import("./pages/ProfilePage"));
const LoginPage = lazy(() => import("./pages/LoginPage"));
const SignUpPage = lazy(() => import("./pages/SignUpPage"));

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

  // public
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/sign-up",
    element: <SignUpPage />,
  },
]);
