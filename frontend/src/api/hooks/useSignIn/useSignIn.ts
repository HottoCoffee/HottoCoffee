import { useApi } from "@/api/utils";
import { useToken } from "@/hook/useToken";
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
  const { api } = useApi();
  const [_, setLocalStorage] = useToken();

  return useMutation<Response, Error, Payload>({
    mutationKey: ["SIGN_IN"],
    mutationFn: async (payload) => {
      const res = await api<Payload, Response>({
        path: "/user/sign-in",
        payload,
      });

      setLocalStorage({ token: res.token });

      return res;
    },
    onSuccess: () => {
      toast.success("ログインしました");
    },
    onError: () => {
      toast.error("メールアドレスかパスワードが間違っています");
    },
  });
};
