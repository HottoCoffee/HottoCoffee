import { Button } from "@/components/ui/button";
import { LoginForm } from "@/features/LoginForm";
import { useTransitionNavigate } from "@/hook/useTransitionNavigate";

export const LoginPage = () => {
  const { transitionNavigate } = useTransitionNavigate();

  return (
    <div className="p-5 grid justify-center items-center grid-rows-[1fr_0fr] min-h-screen">
      <div>
        <h1 className="m-auto w-full text-center mb-8">
          Welcome to HottoCoffee!
        </h1>
        <LoginForm />
      </div>

      <div className="text-primary underline-offset-4 mt-4">
        まだ登録されていませんか？新規登録は
        <Button
          variant="link"
          onClick={() => {
            transitionNavigate("/sign-up", "slide-to-left");
          }}
        >
          こちら
        </Button>
      </div>
    </div>
  );
};
