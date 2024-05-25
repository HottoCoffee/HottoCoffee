import { Outlet } from "react-router-dom";
import { Menu } from "../Menu/Menu";

export const MainLayout = () => {
  return (
    <div>
      <Outlet />

      <Menu />
    </div>
  );
};
