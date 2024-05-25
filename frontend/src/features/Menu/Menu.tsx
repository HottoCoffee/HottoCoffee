import { Button } from "@/components/ui/button";
import { useTransitionNavigate } from "@/hook/useTransitionNavigate";
import { RiTimelineView } from "react-icons/ri";
import { MdOutlinePostAdd } from "react-icons/md";
import { useLocation } from "react-router-dom";

export const Menu = () => {
  const { transitionNavigate } = useTransitionNavigate();
  const location = useLocation();
  const path = location.pathname;

  return (
    <nav
      className="flex justify-around items-center w-full bg-white rounded-lg shadow-md p-2 fixed bottom-3 m-auto left-0 right-0"
      style={{ viewTransitionName: "menu", maxWidth: "calc(100vw - 20px)" }}
    >
      <Button
        variant="link"
        disabled={"/" === path}
        onClick={() => {
          const targetPath = "/";

          if (targetPath === path) {
            return;
          }

          transitionNavigate(targetPath);
        }}
        className="flex gap-2 items-center"
      >
        <RiTimelineView size={20} />
        <span>timeline</span>
      </Button>
      <Button
        variant="link"
        disabled={"/post" === path}
        onClick={() => {
          const targetPath = "/post";

          if (targetPath === path) {
            return;
          }

          transitionNavigate(targetPath);
        }}
        className="flex gap-2 items-center"
      >
        <MdOutlinePostAdd size={20} />
        <span>post</span>
      </Button>
      {/* <a
        href="#"
        className="flex flex-col items-center justify-center w-1/3 py-2 text-gray-700"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="w-6 h-6 mb-1"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"></path>
        </svg>
        <span>profile</span>
      </a> */}
    </nav>
  );
};
