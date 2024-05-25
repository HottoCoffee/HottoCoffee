import { Button } from "@/components/ui/button";
import { SignUpForm } from "@/features/SignUpForm";
import { useTransitionNavigate } from "@/hook/useTransitionNavigate";

export const SignUpPage = () => {
  const { transitionNavigate } = useTransitionNavigate();

  return (
    <div className="p-5 grid justify-center items-center grid-rows-[1fr_0fr] min-h-screen">
      <div>
        <h1 className="m-auto w-full text-center mb-8">
          Welcome to HottoCoffee!
        </h1>
        <SignUpForm />
      </div>

      <div className="text-primary underline-offset-4 mt-4">
        すでに登録済みですか？ログインは
        <Button
          variant="link"
          onClick={() => {
            transitionNavigate("/login", "slide-to-right");
          }}
        >
          こちら
        </Button>
      </div>
    </div>
  );
};
