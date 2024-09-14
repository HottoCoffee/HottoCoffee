import { Button } from "@/components/ui/button";
import { useTransitionNavigate } from "@/hook/useTransitionNavigate";
import { RiTimelineView } from "react-icons/ri";
import { MdOutlinePostAdd } from "react-icons/md";
import { useLocation } from "react-router-dom";
import { CgProfile } from "react-icons/cg";
import clsx from "clsx";

const PATHS = [
  {
    path: "/",
    label: "タイムライン",
    icon: <RiTimelineView size={20} />,
  },
  {
    path: "/post",
    label: "新規投稿",
    icon: <MdOutlinePostAdd size={20} />,
  },
  {
    path: "/profile",
    label: "プロフィール",
    icon: <CgProfile size={20} />,
  },
];

const calcDirection = (
  currentPath: string,
  nextPath: string
): "slide-to-left" | "slide-to-right" => {
  const currentPathIndex = PATHS.findIndex((path) => path.path === currentPath);
  const nextPathIndex = PATHS.findIndex((path) => path.path === nextPath);

  return currentPathIndex > nextPathIndex ? "slide-to-right" : "slide-to-left";
};

export const Menu = () => {
  const { transitionNavigate } = useTransitionNavigate();
  const location = useLocation();
  const currentPath = location.pathname;

  return (
    <nav
      className="flex justify-around items-center w-full bg-white rounded-lg shadow-md p-2 fixed bottom-3 m-auto left-0 right-0"
      style={{ viewTransitionName: "menu", maxWidth: "calc(100vw - 20px)" }}
    >
      {PATHS.map((path) => {
        return (
          <Button
            variant="link"
            disabled={currentPath === path.path}
            onClick={() => {
              if (currentPath === path.path) {
                return;
              }

              transitionNavigate(
                path.path,
                calcDirection(currentPath, path.path)
              );
            }}
            className={clsx("flex gap-2 items-center", {
              "shadow-inner-strong": currentPath === path.path,
            })}
          >
            {path.icon}
            <span>{path.label}</span>
          </Button>
        );
      })}
    </nav>
  );
};
