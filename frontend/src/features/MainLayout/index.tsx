import { Outlet } from "react-router-dom";
import { Menu } from "../Menu/Menu";
import { Suspense } from "react";
import { IoIosCafe } from "react-icons/io";

export const MainLayout = () => {
  return (
    <div className="pb-32">
      <Suspense
        fallback={
          <div className="w-screen h-screen flex justify-center items-center">
            <IoIosCafe size={52} className="text-primary" />
          </div>
        }
      >
        <Outlet />
      </Suspense>

      <Menu />
    </div>
  );
};
