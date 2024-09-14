import { Outlet } from "react-router-dom";
import { Menu } from "../Menu/Menu";
import { Suspense } from "react";

export const MainLayout = () => {
  return (
    <div className="pb-32">
      <Suspense fallback={null}>
        <Outlet />
      </Suspense>

      <Menu />
    </div>
  );
};
