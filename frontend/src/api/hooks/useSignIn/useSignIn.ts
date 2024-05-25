import { API_BASE_URL } from "@/api/config";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";

type Payload = {
  email: string;
  password: string;
};

type Response = {
  token: string;
};

export const useSignIn = () => {
  return useMutation<Response, Error, Payload>({
    mutationKey: ["SIGN_IN"],
    mutationFn: async (payload) => {
      const res = await fetch(`${API_BASE_URL}/user/sign-in`, {
        method: "POST",
        body: JSON.stringify(payload),
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      });

      return res.json();
    },
    onSuccess: () => {
      toast.success("ログインしました");
    },
    onError: () => {
      toast.error("メールアドレスかパスワードが間違っています");
    },
  });
};
