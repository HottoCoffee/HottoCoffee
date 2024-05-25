import { API_BASE_URL } from "@/api/config";
import { User } from "@/api/interfaces";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";

type Payload = Omit<User, "user_id">;

type Response = {};

export const useSignUp = () => {
  return useMutation<Response, Error, Payload>({
    mutationKey: ["SIGN_UP"],
    mutationFn: async (payload) => {
      const res = await fetch(`${API_BASE_URL}/user`, {
        method: "POST",
        body: JSON.stringify(payload),
        headers: {
          "Content-Type": "application/json",
        },
      });

      return res.json();
    },
    onSuccess: () => {
      toast.success("新規登録しました");
    },
    onError: () => {
      toast.error("新規登録に失敗しました");
    },
  });
};
